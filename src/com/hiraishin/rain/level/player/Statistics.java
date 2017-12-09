package com.hiraishin.rain.level.player;

import java.util.HashMap;
import java.util.Map;

import com.hiraishin.rain.util.RegistryManager;

public enum Statistics {

	/*
	 * Definitions
	 */
	INSTANCE(RegistryManager.INSTANCE);

	/*
	 * Instance final variables
	 */
	private final Map<String, Integer> statistics = new HashMap<>();
	{
		statistics.put("", 0);
	}

	private final RegistryManager rm;

	/*
	 * Constructors
	 */
	private Statistics(RegistryManager rm) {
		this.rm = rm;
	}

	/*
	 * Instance functions
	 */
	public void load() {
		for (Map.Entry<String, Integer> e : statistics.entrySet()) {
			e.setValue(rm.readInteger(e.getKey(), e.getValue()));
		}
	}

	public void save() {
		for (Map.Entry<String, Integer> e : statistics.entrySet()) {
			rm.writeInteger(e.getKey(), e.getValue());
		}
	}

}
