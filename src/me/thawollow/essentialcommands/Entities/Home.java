package me.thawollow.essentialcommands.Entities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Home implements ConfigurationSerializable {

	public String name;
	public Location location;
	
	public Home (String homeName, Location location) {
		name = homeName;
		this.location = location;
	}
	public Home(Map<String, Object> serializedHome) {
		this.name = (String) serializedHome.get("name");
		this.location = Location.deserialize((Map<String, Object>) serializedHome.get("location"));
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> mapSerializer = new HashMap<>();
		
		mapSerializer.put("name", this.name);
		mapSerializer.put("location", location.serialize());
		
		return mapSerializer;
	}
}
