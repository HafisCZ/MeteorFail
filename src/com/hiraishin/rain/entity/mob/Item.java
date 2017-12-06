package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.graphics.Sprite;

public abstract class Item extends Mob {

	protected Item(double x, double y, Sprite sprite, double dx, double dy) {
		this(x, y, sprite.getWidth(), sprite.getHeight(), sprite, dx, dy);
	}

	protected Item(double x, double y, double width, double height, Sprite sprite, double dx, double dy) {
		super(x, y, width, height, sprite);

		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;
	}

}
