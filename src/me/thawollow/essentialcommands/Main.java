package me.thawollow.essentialcommands;

import org.bukkit.plugin.java.JavaPlugin;

import me.thawollow.essentialcommands.commands.GhostCommand;
import me.thawollow.essentialcommands.commands.MessageCommand;
import me.thawollow.essentialcommands.commands.SuicideCommand;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		new MessageCommand(this);
		new GhostCommand(this);
		new SuicideCommand(this);
	}
}
