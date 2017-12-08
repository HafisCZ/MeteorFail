package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.mob.Acid;
import com.hiraishin.rain.level.Level;

public class AcidSpawner extends Spawner {

	public AcidSpawner(double x, double y, double w, double h, Level level, int frameRate) {
		super(x, y, w, h, level, frameRate);
	}

	@Override
	public void spawn() {
		level.add(new Acid(randomX(), randomY(), 0, 10, level));
		level.add(new Acid(randomX(), randomY(), 0, 10, level));
	}

}
