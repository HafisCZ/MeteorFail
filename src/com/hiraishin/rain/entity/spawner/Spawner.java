package com.hiraishin.rain.entity.spawner;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Spawner extends Entity {

	/*
	 * Instance final variables
	 */
	protected final int rate;
	protected final int variation;
	protected final int count;

	/*
	 * Instance variables
	 */
	protected int frameCount = 0;
	protected int frameLimit;

	/*
	 * Constructors
	 */
	protected Spawner(double x, double y, double width, double height, Level level, int rate, int variation,
			int count) {
		super(x, y, width, height, level);

		this.count = count;

		this.variation = variation;
		this.rate = rate;

		frameLimit = rate;
	}

	/*
	 * Instance functions
	 */
	@Override
	public final void tick() {
		if (frameCount++ >= frameLimit) {
			frameCount = 0;
			frameLimit = rate + ((variation > 1) ? Commons.RANDOM.nextInt(variation) : 0);

			if (Objects.nonNull(level)) {
				for (int i = 0; i < count; i++) {
					spawn();
				}
			}
		}
	}

	/*
	 * Getters & Setters
	 */
	protected double getRandomX() {
		return x + ((width == 0) ? 0 : Commons.RANDOM.nextInt((int) width));
	}

	protected double getRandomY() {
		return y + ((height == 0) ? 0 : Commons.RANDOM.nextInt((int) height));
	}

	/*
	 * Abstract functions
	 */
	public abstract void spawn();

}
