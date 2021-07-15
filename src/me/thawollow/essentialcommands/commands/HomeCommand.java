package me.thawollow.essentialcommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import me.thawollow.essentialcommands.Main;
import me.thawollow.essentialcommands.Entities.Home;
import me.thawollow.essentialcommands.Entities.PlayerHome;
import me.thawollow.essentialcommands.Entities.PlayerHomeRepo;
import me.thawollow.essentialcommands.Services.MessageService;
public class HomeCommand implements CommandExecutor {
	
	private Main _plugin;
	private PlayerHomeRepo playerHomesRepo;
	
	public HomeCommand(Main plugin) {
		_plugin = plugin;
		_plugin.getCommand("home").setExecutor(this);
		_plugin.getCommand("sethome").setExecutor(this);
		playerHomesRepo = (PlayerHomeRepo) _plugin.getConfig().get("player-data");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageService.onlyForPlayers());
			return false;
		}
		if (playerHomesRepo == null) playerHomesRepo = new PlayerHomeRepo();
		Player p = (Player) sender;
	    PlayerHome selectedPlayerHome = playerHomesRepo.playerHomes.stream()
	    		.filter(playerHome -> playerHome.playerName.equals(p.getName()))
	    		.findFirst()
	    		.orElse(null);
	    
		// SETHOME 
		if (cmd.getName().equals("sethome")) {
			if (args.length < 1) {
				p.sendMessage(MessageService.createErrorMessage("No name for the home was entered. Please enter a name for the home."));
				return false;
			}
			else {
				String homeName = args[0];
				// NEW PLAYER
				if (selectedPlayerHome == null) {
					PlayerHome newPlayerHome = new PlayerHome(p);
					
					newPlayerHome.Homes.add(createHome(homeName, p.getLocation()));
					
					playerHomesRepo.playerHomes.add(newPlayerHome);
					p.sendMessage(ChatColor.GREEN + "Your home has been set!");
					_plugin.getConfig().set("player-data", playerHomesRepo);
					_plugin.saveConfig();
					return true;
				}
				// RETURNING PLAYER
				else {
					playerHomesRepo.playerHomes.remove(selectedPlayerHome);
					var newHome = createHome(homeName, p.getLocation());
					var homeExists = selectedPlayerHome.doesHomeExist(homeName);
					Log.info(homeExists);
					if (homeExists) {
						var selectedHome = selectedPlayerHome.getHome(homeName);
						selectedPlayerHome.Homes.remove(selectedHome);
					}
					
					selectedPlayerHome.Homes.add(newHome);
					playerHomesRepo.playerHomes.add(selectedPlayerHome);
					_plugin.getConfig().set("player-data", playerHomesRepo);
					_plugin.saveConfig();
					p.sendMessage(ChatColor.GREEN + "Your home has been set!");
					return true;
				}

			}
		}
		
		// HOME
		else if (cmd.getName().equals("home")) {
			// NO HOMES
			if (selectedPlayerHome == null) {
				p.sendMessage(MessageService.createErrorMessage("You do not have a home yet"));
				return false;
			}
			// 1 HOME
			else if (selectedPlayerHome.Homes.size() == 1) {
				p.teleport(selectedPlayerHome.Homes.stream().findFirst().orElse(null).location);
				p.sendMessage(ChatColor.GREEN + "Teleporting...");
				return true;
			}
			// > 1 HOME
			else {
				if (args.length < 1) {
					StringBuilder message = new StringBuilder();
					message.append(ChatColor.GREEN + "Homes:\n" + ChatColor.WHITE);
					
					for(int i = 0; i < selectedPlayerHome.Homes.size(); i++) {
						if (i == selectedPlayerHome.Homes.size() - 1) message.append(selectedPlayerHome.Homes.get(i).name + "");
						else message.append(selectedPlayerHome.Homes.get(i).name + ", ");
					}
					
					p.sendMessage(message.toString());
				}
				else {
					var selectedHome = selectedPlayerHome.getHome(args[0]);
					
					if (selectedHome == null) {
						p.sendMessage(MessageService.createErrorMessage("Home was not found"));
					}
					else {
						p.teleport(selectedHome.location);
						p.sendMessage(ChatColor.GREEN + "Teleporting...");
					}
				}

				return true;
			}
		}
		else return false;
	}
	
	private Home createHome(String name, Location location) {
		return new Home(name, location);
	}
}
