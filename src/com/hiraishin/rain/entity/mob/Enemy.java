package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

public class Enemy extends Mob {

	public Enemy(double x, double y) {
		super(x, y, 30, 30, new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("enemy"), 2, 1));

		sprite.select(0, Commons.RANDOM.nextInt(2));

		dy = 4;
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;
	}

}
