package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.particle.Particle;
import com.hiraishin.rain.entity.particle.Raindrop;
import com.hiraishin.rain.util.Commons;

public final class RaindropSpawner extends Spawner {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public RaindropSpawner(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	/**
	 * Return new Particle
	 */
	@SuppressWarnings("unchecked")
	public Particle get() {
		return new Raindrop((width == 0) ? 0 : Commons.RANDOM.nextInt((int) width) + x,
				(height == 0) ? 0 : Commons.RANDOM.nextInt((int) height) + y, 1, Commons.RANDOM.nextInt(30) + 10, 0,
				Commons.RANDOM.nextInt(10) + 10);
	}

	/**
	 * 
	 */
	@Override
	public void tick() {

	}
}
