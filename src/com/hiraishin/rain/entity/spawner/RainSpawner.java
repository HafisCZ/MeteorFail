package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.particle.RainParticle;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public final class RainSpawner extends Spawner {

	public RainSpawner(double x, double y, double w, double h, Level level, int frameRate) {
		super(x, y, w, h, level, frameRate);
	}

	@Override
	public void spawn() {	
		for (int i = 0; i < 5; i++) {
			level.add(new RainParticle(randomX(), randomY(), 1, Commons.RANDOM.nextInt(30) + 10, 0,
					Commons.RANDOM.nextInt(10) + 10, level));
		}
	}
}
