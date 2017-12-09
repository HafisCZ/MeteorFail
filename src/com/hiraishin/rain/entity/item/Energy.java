package com.hiraishin.rain.entity.item;

import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.image.Image;

public class Energy extends Item {

	/*
	 * Definitions
	 */
	public static final double WIDTH = 7;
	public static final double HEIGHT = 7;

	public static final Image IMAGE = ImagePreloader.DEFAULT_LOADER.getImage("energy");
	public static final int IMAGE_ROWS = 1;
	public static final int IMAGE_COLS = 1;
	public static final double SPRITE_X_OFFSET = -4;
	public static final double SPRITE_Y_OFFSET = -4;

	/*
	 * Constructors
	 */
	public Energy(double x, double y, Level level) {
		super(x, y, WIDTH, HEIGHT, new Sprite(IMAGE, IMAGE_ROWS, IMAGE_COLS), SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);
	}

	/*
	 * Instance functions
	 */
	@Override
	public void effect() {
		level.getPlayerProperties().addEnergy();
	}
}
