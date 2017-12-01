package com.hiraishin.undefined._util.resource;

import java.util.prefs.Preferences;

import com.hiraishin.undefined._util.logger.Logger;
import com.hiraishin.undefined._util.logger.Severity;

public enum RegistryStorage {

	INSTANCE(RegistryStorage.class);

	private final Preferences preferences;

	private RegistryStorage(Class<?> preferenceUserNode) {
		preferences = Preferences.userNodeForPackage(preferenceUserNode);
	}

	public <T> void write(String key, T value) {
		if (value instanceof Integer) {
			preferences.putInt(key, (Integer) value);
			Logger.INSTANCE.log(Severity.INFORMATION, "Variable written");
		} else if (value instanceof Double) {
			preferences.putDouble(key, (Double) value);
			Logger.INSTANCE.log(Severity.INFORMATION, "Variable written");
		} else if (value instanceof String) {
			preferences.put(key, (String) value);
			Logger.INSTANCE.log(Severity.INFORMATION, "Variable written");
		} else {
			Logger.INSTANCE.log(Severity.ERROR, "Type not compatible");
		}
	}

	public String read(String key, String defaultValue) {
		Logger.INSTANCE.log(Severity.INFORMATION, "Variable read");
		return preferences.get(key, defaultValue);
	}

	public Double readDouble(String key, Double defaultValue) {
		Logger.INSTANCE.log(Severity.INFORMATION, "Variable read");
		return preferences.getDouble(key, defaultValue);
	}

	public Integer readInteger(String key, Integer defaultValue) {
		Logger.INSTANCE.log(Severity.INFORMATION, "Variable read");
		return preferences.getInt(key, defaultValue);
	}

}
