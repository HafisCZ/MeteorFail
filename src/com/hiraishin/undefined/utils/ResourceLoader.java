package com.hiraishin.undefined.utils;

import java.util.HashMap;
import java.util.Objects;

import com.hiraishin.undefined.utils.Report.Level;

import javafx.scene.image.Image;

public class ResourceLoader {

	private static HashMap<String, Image> resources = new HashMap<>();
	
	/**
	 * Load resources
	 */
	public static void load(String... files) {
		for (String file : files) {
			Image image = new Image(ClassLoader.class.getResourceAsStream(Objects.requireNonNull(file)));
			
			if (image.isError()) {
				ReportManager.report(Level.ERROR, file + " failed to load.");
			} else {
				ReportManager.report(Level.INFORMATION, file + " loaded.");
			}
			
			resources.put(strip(file), image);
		}
	}

	/**
	 * Get loaded texture
	 */
	public static Image getTexture(String id) {
		if (resources.containsKey(id)) {
			return resources.get(id);
		} else {
			ReportManager.report(Level.ERROR, id + " not present");
			return null;
		}
	}
	
	/**
	 * Strip filepath to only filename
	 */
	private static String strip(String path) {
		if (Objects.isNull(path)) {
			throw new NullPointerException();
		}
		
		if (path.contains(".")) {
			path = path.substring(0, path.lastIndexOf("."));
		}
		
		if (path.contains("/")) {
			path = path.substring(path.lastIndexOf("/") + 1);
		}
		
		return path;
	}
	
	/**
	 * Flush all loaded resources
	 */
	public static void flush() {
		ReportManager.report(Level.WARNING, "Textures flushed.");
		resources.clear();
	}
	
}
