package com.hiraishin.rain.entity.spawner;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Spawner extends Entity {

	protected int frameCount = 0;
	protected final int frameRate;

	protected Spawner(double x, double y, double w, double h, Level level, int frameRate) {
		super(x, y, w, h, level);

		this.frameRate = frameRate;
	}

	@Override
	public final void tick() {
		if (frameCount++ >= frameRate) {
			frameCount = 0;

			if (Objects.nonNull(level)) {
				spawn();
			}
		}
	}

	public abstract void spawn();

	protected double randomX() {
		return x + ((width == 0) ? 0 : Commons.RANDOM.nextInt((int) width));
	}

	protected double randomY() {
		return y + ((height == 0) ? 0 : Commons.RANDOM.nextInt((int) height));
	}

}
