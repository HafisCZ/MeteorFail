/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javafx.scene.image.Image;

public enum ImageLoader {

    INTERNAL("/res/", ".png", S -> {
        return new Image(ClassLoader.class.getResourceAsStream(S));
    }),

    EXTERNAL("res/", ".png", S -> {
        try (FileInputStream fiStream = new FileInputStream(new File(S))) {
            return new Image(fiStream);
        } catch (IOException e) {
            e.printStackTrace();
            return INTERNAL.getImage(S);
        }
    });

    private static final File EXTERNAL_LOADER_MARKER = new File("res");
    private static final boolean EXTERNAL_LOADER_ENABLED = EXTERNAL_LOADER_MARKER.isDirectory();

    private final Map<String, Image> loadedImages = new HashMap<>();
    private final Function<String, Image> loader;
    private final String prefix;
    private final String suffix;

    public static final ImageLoader getLoader() {
        return (EXTERNAL_LOADER_ENABLED ? EXTERNAL : INTERNAL);
    }

    private ImageLoader(String prefix, String suffix, Function<String, Image> loader) {
        this.loader = Objects.requireNonNull(loader);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Image getImage(String token) {
        if (!this.loadedImages.containsKey(token)) {
            load(token);
        }

        return this.loadedImages.get(token);
    }

    public void loadAll(String... tokens) {
        for (String token : tokens) {
            load(token);
        }
    }

    public void load(String token) {
        this.loadedImages.put(token, this.loader.apply(this.prefix + token + this.suffix));
    }
}
