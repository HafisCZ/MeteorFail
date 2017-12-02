package com.hiraishin.undefined.util.resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.hiraishin.undefined.util.logger.Logger;
import com.hiraishin.undefined.util.logger.Severity;

import javafx.scene.image.Image;

public enum ResourceLoader {

	INSTANCE;

	private final Map<String, Image> imageMap;
	private String commonClassPath;

	private ResourceLoader() {
		imageMap = new HashMap<>();
		commonClassPath = "";
	}

	public void setCommonClassPath(String classPath) {
		commonClassPath = Objects.requireNonNull(classPath);
	}

	public void load(String... classPaths) {
		for (String classPath : classPaths) {
			InputStream inputStream = ClassLoader.class.getResourceAsStream(commonClassPath + classPath);
			Image image = new Image(inputStream);
			if (image.isError()) {
				Logger.INSTANCE.log(Severity.ERROR, "Image not loaded");
			} else {
				imageMap.put(strip(classPath), image);
			}
		}
	}

	public Image getTexture(String id) {
		Image image = imageMap.getOrDefault(id, null);
		if (Objects.isNull(image)) {
			Logger.INSTANCE.log(Severity.ERROR, "Image does not exist");
		}

		return image;
	}

	private static String strip(String classPath) {
		Objects.requireNonNull(classPath);
		StringBuilder builder = new StringBuilder().append(classPath);
		if (builder.indexOf("/") >= 0) {
			builder.replace(0, builder.lastIndexOf("/") + 1, "");
		}
		builder.replace(builder.length() - 4, builder.length(), "");
		return builder.toString();
	}

}
