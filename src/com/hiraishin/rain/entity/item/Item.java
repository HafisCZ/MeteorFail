package com.hiraishin.rain.entity.item;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Item extends Entity {

	protected Item(double x, double y, double width, double height, Sprite sprite, Level level) {
		this(x, y, width, height, sprite, 0, 4, level);
	}

	protected Item(double x, double y, double width, double height, Sprite sprite, double dx, double dy, Level level) {
		super(x, y, width, height, sprite, level);

		this.dx = dx;
		this.dy = dy;
	}

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

		if (y < Commons.ZERO) {
			y = 0;
		} else if (y + height > Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;
			dy = 0;
		}
	}

}
