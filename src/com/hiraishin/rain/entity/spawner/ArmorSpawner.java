package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.item.Shield;
import com.hiraishin.rain.level.Level;

public class ArmorSpawner extends Spawner {

	/*
	 * Constructors
	 */
	public ArmorSpawner(double x, double y, double width, double height, Level level, int rate, int variation,
			int count) {
		super(x, y, width, height, level, rate, variation, count);
	}

	/*
	 * Instance functions
	 */
	@Override
	public void spawn() {
		level.add(new Shield(getRandomX(), getRandomY(), level));
	}

}
