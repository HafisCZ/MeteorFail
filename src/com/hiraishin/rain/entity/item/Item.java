package com.hiraishin.rain.entity.item;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Item extends Entity {

	/*
	 * Definitions
	 */
	public static final double DEFAULT_SPEED_X = 0;
	public static final double DEFUALT_SPEED_Y = 4;

	/*
	 * Contructors
	 */
	protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY,
			Level level) {
		this(x, y, width, height, sprite, offsetX, offsetY, DEFAULT_SPEED_X, DEFUALT_SPEED_Y, level);
	}

	protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY,
			double dx, double dy, Level level) {
		super(x, y, width, height, sprite, offsetX, offsetY, level);

		this.dx = dx;
		this.dy = dy;
	}

	/*
	 * Instance functions
	 */
	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (x < Commons.ZERO) {
			x = 0;
			dx = 0;
		} else if (x + width > Commons.SCENE_WIDTH) {
			x = Commons.SCENE_WIDTH - width;
			dx = 0;
		}

		if (y + height > Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;
			dy = 0;

			kill();
		}

		if (level.getPlayer().collidesAABB(this)) {
			effect();
			kill();
		}
	}

	/*
	 * Abstract functions
	 */
	public abstract void effect();

}
