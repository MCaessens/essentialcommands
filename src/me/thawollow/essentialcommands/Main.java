package me.thawollow.essentialcommands;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import me.thawollow.essentialcommands.Entities.Home;
import me.thawollow.essentialcommands.Entities.PlayerHome;
import me.thawollow.essentialcommands.Entities.PlayerHomeRepo;
import me.thawollow.essentialcommands.commands.GhostCommand;
import me.thawollow.essentialcommands.commands.HomeCommand;
import me.thawollow.essentialcommands.commands.MessageCommand;
import me.thawollow.essentialcommands.commands.SuicideCommand;
import me.thawollow.essentialcommands.commands.TpCommand;

public class Main extends JavaPlugin {
	public Main() {
		ConfigurationSerialization.registerClass(Home.class);
		ConfigurationSerialization.registerClass(PlayerHome.class);
		ConfigurationSerialization.registerClass(PlayerHomeRepo.class);
	}
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		new MessageCommand(this);
		new GhostCommand(this);
		new SuicideCommand(this);
		new TpCommand(this);
		new HomeCommand(this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
