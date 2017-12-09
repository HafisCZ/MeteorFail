package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;

public abstract class Particle extends Entity {

	/*
	 * Constructors
	 */
	protected Particle(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
	}

	/*
	 * Abstract functions
	 */
	@Override
	public abstract void tick();

}
