package com.hiraishin.rain.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javafx.scene.image.Image;

public enum ImageLoader {

	/*
	 * Definitions
	 */
	DEFAULT("/res/", ".png", S -> {
		return new Image(ClassLoader.class.getResourceAsStream(S));
	});

	/*
	 * Instance final variables
	 */
	private final Map<String, Image> loadedImages = new HashMap<>();
	private final Function<String, Image> loader;
	private final String prefix;
	private final String suffix;

	/*
	 * Constructors
	 */
	private ImageLoader(String prefix, String suffix, Function<String, Image> loader, String... tokens) {
		this.loader = Objects.requireNonNull(loader);
		this.prefix = prefix;
		this.suffix = suffix;

		for (String token : tokens) {
			this.loadedImages.put(token, loader.apply(this.prefix + token + this.suffix));
		}
	}

	/*
	 * Instance functions
	 */
	public Image requestImage(String token) {
		if (!this.loadedImages.containsKey(token)) {
			this.loadedImages.put(token, this.loader.apply(this.prefix + token + this.suffix));
		}

		return this.loadedImages.get(token);
	}
}
