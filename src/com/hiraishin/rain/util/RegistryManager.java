package com.hiraishin.rain.util;

import java.util.prefs.Preferences;

public enum RegistryManager {

	/**
	 * Create single RegistryManager instance
	 */
	INSTANCE(com.hiraishin.rain.Application.class);

	/**
	 * Preferences
	 */
	private final Preferences preferences;

	/**
	 * Use class as root
	 * 
	 * @param userNode
	 */
	private RegistryManager(Class<?> userNode) {
		preferences = Preferences.userNodeForPackage(userNode);
	}

	public void writeInteger(String key, Integer value) {
		preferences.putInt(key, value);
	}

	public void writeDouble(String key, Double value) {
		preferences.putDouble(key, value);
	}

	public void writeString(String key, String value) {
		preferences.put(key, value);
	}

	public void writeBoolean(String key, Boolean value) {
		preferences.putBoolean(key, value);
	}

	public Integer readInteger(String key, Integer def) {
		return preferences.getInt(key, def);
	}

	public Double readDouble(String key, Double def) {
		return preferences.getDouble(key, def);
	}

	public String readString(String key, String def) {
		return preferences.get(key, def);
	}

	public Boolean readBoolean(String key, Boolean def) {
		return preferences.getBoolean(key, def);
	}
}
