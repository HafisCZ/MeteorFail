package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.entity.Entity;

public abstract class Particle extends Entity {

	/**
	 * 
	 * @param x
	 * @param y
	 */
	protected Particle(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	/**
	 * Tick
	 */
	public abstract void tick();

}
