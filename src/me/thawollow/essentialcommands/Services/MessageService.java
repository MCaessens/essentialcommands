package me.thawollow.essentialcommands.Services;

import net.md_5.bungee.api.ChatColor;

public class MessageService {
	public static String playerNotFoundMessage = "Player was not found";
	public static String argumentNotFoundMessage = "Please fill in all required information";
	public static String createErrorMessage(String message) {
		return ChatColor.DARK_RED + "Error: " + ChatColor.RED + message;
	}
	public static String onlyForPlayers() {
		return createErrorMessage("Only players may execute this command");
	}
}
