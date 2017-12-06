package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.Entity;

public abstract class Spawner extends Entity {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	protected Spawner(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	/**
	 * 
	 * @return
	 */
	public abstract <T extends Entity> T get();

	/**
	 * Tick
	 */
	@Override
	public void tick() {

	}

}
