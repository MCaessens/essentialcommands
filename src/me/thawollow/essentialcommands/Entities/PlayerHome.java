package me.thawollow.essentialcommands.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class PlayerHome implements ConfigurationSerializable {
	public String playerName;
	public List<Home> Homes;

	public PlayerHome(Player player) {
		this.playerName = player.getName();
		Homes = new ArrayList<Home>();
	}
	public PlayerHome(Map<String, Object> serializedPlayerHome) {
		this.playerName = (String) serializedPlayerHome.get("player");
		
		ArrayList<Map<String, Object>> mappedHomes = (ArrayList<Map<String, Object>>) serializedPlayerHome.get("homes");
		ArrayList<Home> homes = new ArrayList<Home>();
		for (var serializedHome : mappedHomes) {
			homes.add(new Home(serializedHome));
		}
		this.Homes = homes;
	}

	public boolean doesHomeExist(String homeName) {
		var selectedHome = Homes.stream()
				.filter(home -> home.name.equals(homeName))
				.findFirst()
				.orElse(null);
		
		if (selectedHome == null) return false;
		else return true;		
	}
	
	public Home getHome(String homeName) {
		return Homes.stream()
				.filter(home -> home.name.equals(homeName))
				.findFirst()
				.orElse(null);
	}

	@Override
	public Map<String, Object> serialize() {
		
		HashMap<String, Object> mapSerializer = new HashMap<>();
		
		mapSerializer.put("player", playerName);
		ArrayList<Map<String, Object>> serializeHomes = new ArrayList<Map<String, Object>>();
		for(Home home : this.Homes) {
			serializeHomes.add(home.serialize());
		}
		
		mapSerializer.put("homes", serializeHomes);
		
		return mapSerializer;
	}

}
