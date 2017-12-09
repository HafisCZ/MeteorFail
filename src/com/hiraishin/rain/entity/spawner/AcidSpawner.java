package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.mob.Acid;
import com.hiraishin.rain.level.Level;

public class AcidSpawner extends Spawner {

	/*
	 * Constructors
	 */
	public AcidSpawner(double x, double y, double width, double height, Level level, int rate, int variation,
			int count) {
		super(x, y, width, height, level, rate, variation, count);
	}

	/*
	 * Instance functions
	 */
	@Override
	public void spawn() {
		this.level.add(new Acid(getRandomX(), getRandomY(), this.level));
	}

}
