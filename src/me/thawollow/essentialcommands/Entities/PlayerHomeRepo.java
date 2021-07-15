package me.thawollow.essentialcommands.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("PlayerHomeRepo")
public class PlayerHomeRepo implements ConfigurationSerializable {
	public ArrayList<PlayerHome> playerHomes;
	
	public PlayerHomeRepo() {
		playerHomes = new ArrayList<PlayerHome>();
	}
	
	public PlayerHomeRepo(Map<String, Object> serializedPlayerHomeRepo) {
		ArrayList<Map<String, Object>> playerHomesMapped = (ArrayList<Map<String, Object>>) serializedPlayerHomeRepo.get("player-homes");
		playerHomes = new ArrayList<PlayerHome>();
		for(var mappedHome : playerHomesMapped) {
			playerHomes.add(new PlayerHome(mappedHome));
		}
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> mapSerializer = new HashMap<>();
		
		ArrayList<Map<String, Object>> serializedPlayerHomes = new ArrayList<Map<String, Object>>();
		
		for (var pHome : playerHomes) {
			serializedPlayerHomes.add(pHome.serialize());
		}
		
		mapSerializer.put("player-homes", serializedPlayerHomes);
		
		return mapSerializer;
	}
	
	@Override
	public String toString() {
		return "test";
	}
	
}
