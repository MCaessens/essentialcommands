package me.thawollow.essentialcommands.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thawollow.essentialcommands.Main;
import me.thawollow.essentialcommands.commands.Entities.PlayerInfo;

public class GhostCommand implements CommandExecutor {

	private Main _plugin;
	private List<PlayerInfo> playersInfo;
	private String ghostOnMessage = "You have turned into a ghost! Roam around freely!";
	
	public GhostCommand(Main plugin) {
		_plugin = plugin;
		_plugin.getCommand("ghost").setExecutor(this);
		playersInfo = new ArrayList<PlayerInfo>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command!");
			return true;
		}
		Player p = (Player) sender;
		PlayerInfo playerInfo = playersInfo.stream()
				.filter(pInfo -> pInfo.name.equals(p.getName()))
				.findFirst()
				.orElse(null);
		
		if (playerInfo == null) {
			PlayerInfo newInfo = new PlayerInfo();
			newInfo.name = p.getName();
			SetToGhost(newInfo, p);
		}
		else {
			playersInfo.remove(playerInfo);
			if (playerInfo.isGhost) {
				playerInfo.isGhost = false;
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(ChatColor.RED + "You no longer are a ghost.. You will be teleported to your previous location..");
				p.teleport(playerInfo.previousLocation);
				playersInfo.add(playerInfo);
			}
			else {
				SetToGhost(playerInfo, p);
			}
		}
		
		return false;
	}
	
	private void SetToGhost(PlayerInfo info, Player player) {
		info.isGhost = true;
		info.previousLocation = player.getLocation();
		player.sendMessage(ChatColor.GREEN + ghostOnMessage);
		player.setGameMode(GameMode.SPECTATOR);
		playersInfo.add(info);
	}

}
