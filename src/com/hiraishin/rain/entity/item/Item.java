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
	public static final double DEFAULT_SPEED_Y = 4;

	/*
	 * Contructors
	 */
	protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY,
			Level level) {
		this(x, y, width, height, sprite, offsetX, offsetY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y, level);
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
	public final void tick() {
		this.x += this.dx;
		this.y += this.dy;

		if (this.x < Commons.ZERO) {
			this.x = 0;
			this.dx = 0;
		} else if (this.x + width > Commons.SCENE_WIDTH) {
			this.x = Commons.SCENE_WIDTH - this.width;
			this.dx = 0;
		}

		if (this.y + this.height > Commons.SCENE_GROUND) {
			this.y = Commons.SCENE_GROUND - this.height;
			this.dy = 0;

			kill();
		}

		if (this.level.getPlayer().collidesAABB(this)) {
			effect();
			kill();
		}
	}

	/*
	 * Abstract functions
	 */
	public abstract void effect();

}
