package me.thawollow.essentialcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thawollow.essentialcommands.Main;
import me.thawollow.essentialcommands.Services.MessageService;

public class SuicideCommand implements CommandExecutor {

	private Main _plugin;
	public SuicideCommand(Main plugin) {
		_plugin = plugin;
		_plugin.getCommand("suicide").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageService.onlyForPlayers());
		}
		
		Player p = (Player) sender;
		p.damage(1000);
		p.sendMessage("You just killed yourself!");
		var onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		
		onlinePlayers.forEach(player -> {
			player.sendMessage(p.getName() + " just offed themselves");
		});
		
		return true;
	}

}
