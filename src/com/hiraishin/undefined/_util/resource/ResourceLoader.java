package com.hiraishin.undefined._util.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.hiraishin.undefined._util.logger.Logger;
import com.hiraishin.undefined._util.logger.Severity;

import javafx.scene.image.Image;

public enum ResourceLoader {

	INSTANCE;

	private final Map<String, Image> imageMap;

	private ResourceLoader() {
		imageMap = new HashMap<>();
	}

	public void load(String... classPaths) {
		for (String classPath : classPaths) {
			if (Objects.nonNull(classPath)) {
				Image image = new Image(ClassLoader.class.getResourceAsStream(classPath));
				if (image.isError()) {
					Logger.INSTANCE.log(Severity.WARNING, classPath + " loaded incorrectly");
				} else {
					imageMap.put(strip(classPath), image);
					Logger.INSTANCE.log(Severity.INFORMATION, classPath + " loaded");
				}
			} else {
				Logger.INSTANCE.log(Severity.ERROR, "Invalid class path");
			}
		}
	}

	public Image getTexture(String id) {
		if (imageMap.containsKey(id)) {
			return imageMap.get(id);
		} else {
			Logger.INSTANCE.log(Severity.ERROR, "Requested " + id + " does not exist");
			return null;
		}
	}

	public void flush() {
		Logger.INSTANCE.log(Severity.WARNING, "Image map cleared");
		imageMap.clear();
	}

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

}
