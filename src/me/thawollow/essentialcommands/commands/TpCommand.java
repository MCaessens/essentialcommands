package me.thawollow.essentialcommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thawollow.essentialcommands.Main;
import me.thawollow.essentialcommands.Services.MessageService;

public class TpCommand implements CommandExecutor {
	
	private Main _plugin;
	
	public TpCommand(Main plugin) {
		_plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean succeeded = false;
		if (!(sender instanceof Player)) {
			sender.sendMessage(MessageService.onlyForPlayers());
		}
		Player p = (Player) sender;
		if (args.length < 1) {
			p.sendMessage(MessageService.createErrorMessage(MessageService.argumentNotFoundMessage));
		}
		else {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				p.sendMessage(MessageService.createErrorMessage(MessageService.playerNotFoundMessage));
			}
			else {
				p.teleport(target);
				succeeded = true;
			}
		}
		
		return succeeded;
	}

}
