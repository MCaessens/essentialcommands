package me.thawollow.essentialcommands.commands;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thawollow.essentialcommands.Main;
import me.thawollow.essentialcommands.commands.Entities.PlayerMessageLog;
import net.md_5.bungee.api.ChatColor;

public class MessageCommand implements CommandExecutor {

	private Main _plugin;
	private List<PlayerMessageLog> messageLogs;
	public MessageCommand(Main plugin) {
		_plugin = plugin;
		_plugin.getCommand("msg").setExecutor(this);
		messageLogs = new ArrayList<PlayerMessageLog>();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(createErrorMessage("Only players can run this command!"));
		}
		
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage(createErrorMessage("Please enter a player name."));
			return false;
		}
		
		var playerLog = messageLogs.stream()
		.filter(mLog -> mLog.player.equals(p))
		.findFirst()
		.orElse(null);
		
		Player selectedPlayer = Bukkit.getServer().getPlayer(args[0]);
		
		if (selectedPlayer == null) {
			p.sendMessage(createErrorMessage("Player is not online."));
			return false;
		}
		if (args.length < 2) {
			p.sendMessage(createErrorMessage("Please enter a message."));
			return false;
		}
			
		if(playerLog != null) {
			if (((LocalDateTime.now().getSecond()) - playerLog.date.getSecond()) < 5) {
				p.sendMessage(createErrorMessage("Previous message was too close. Please refrain from spamming."));
				return false;
			}
		}
		
		var message = args[1];
		
		StringBuilder formattedMessage = new StringBuilder();
		
		formattedMessage.append(ChatColor.GOLD + "(" + ChatColor.WHITE + p.getName() + ChatColor.GOLD + " => " + ChatColor.WHITE + "Me" + ChatColor.GOLD +"): " + ChatColor.WHITE );
		formattedMessage.append(message);
		
		selectedPlayer.sendMessage(formattedMessage.toString());
		createMessageLog(p);
		
		return true;

	}
	
	private void createMessageLog(Player p) {
		var playerLog = messageLogs.stream()
		.filter(mLog -> mLog.player.equals(p))
		.findFirst()
		.orElse(null);
		
		if (playerLog != null) {
			messageLogs.remove(playerLog);
		}
		var log = new PlayerMessageLog();
		log.date = LocalDateTime.now();
		log.player = p;
		messageLogs.add(log);
	}
	private String createErrorMessage(String message) {
		return ChatColor.DARK_RED + "Error: " + ChatColor.RED + message;
	}

}
