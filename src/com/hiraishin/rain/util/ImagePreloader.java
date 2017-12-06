package com.hiraishin.rain.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.hiraishin.rain.util.logger.Logger;
import com.hiraishin.rain.util.logger.Severity;

import javafx.scene.image.Image;

public enum ImagePreloader {

	/**
	 * Create only single ImagePreloader instance
	 */
	DEFAULT_LOADER();

	/**
	 * Map for storing images
	 */
	private final Map<String, Image> images = new HashMap<>();

	/**
	 * Load requested images
	 * 
	 * @param useClassLoader
	 * @param paths
	 */
	public void load(boolean useClassLoader, String... paths) {
		if (useClassLoader) {
			for (String path : paths) {
				final InputStream istream = ClassLoader.class.getResourceAsStream(path);
				final Image image = new Image(istream);
				if (image.isError()) {
					Logger.INSTANCE.log(Severity.ERROR, image.toString());
				}

				images.put(strip(path), image);
			}
		} else {
			for (String path : paths) {
				final Image image = new Image("file:" + path);
				if (image.isError()) {
					Logger.INSTANCE.log(Severity.ERROR, image.toString());
				}

				images.put(strip(path), image);
			}
		}
	}

	/**
	 * Get image associated with id
	 * 
	 * @param id
	 * @return image
	 */
	public Image getImage(String id) {
		return images.get(id);
	}

	/**
	 * Strip image url to id
	 * 
	 * @param url
	 * @return id
	 */
	private static String strip(String url) {
		StringBuilder builder = new StringBuilder().append(url);

		final int index0 = builder.lastIndexOf("/");
		final int index1 = builder.indexOf(".");

		return builder.substring((index0 < 0 ? 0 : index0 + 1), index1);
	}

}
