package com.hiraishin.rain.level.player;

import com.hiraishin.rain.entity.particle.ShockParticle;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public enum Skill {

	/*
	 * Definitions
	 */
	SHOCKWAVE((X, Y, L, PP) -> L.add(new ShockParticle(X, Commons.SCENE_GROUND, 0, 0, L)), 2 * 60, 5), SHIELD_SPAWN(
			(X, Y, L, PP) -> PP.addShield(), 30,
			3), EXPERIENCE_BOOST((X, Y, L, PP) -> PP.setTemporaryExperienceBoost(2, 60 * 60), 60 * 60, 1);

	/*
	 * Instance variables
	 */
	private final QuadConsumer<Double, Double, Level, PlayerData> consumer;
	private final int burnout;
	private final int rate;

	/*
	 * Constructors
	 */
	private Skill(QuadConsumer<Double, Double, Level, PlayerData> consumer, int burnout, int rate) {
		this.consumer = consumer;
		this.burnout = burnout;
		this.rate = rate;
	}

	/*
	 * Instance functions
	 */
	public void activate(double x, double y, Level level, PlayerData properties) {
		consumer.accept(x, y, level, properties);
	}

	/*
	 * Getters & Setters
	 */
	public int getBurnoutTime() {
		return burnout;
	}

	public int getChargeRate() {
		return rate;
	}

	/*
	 * Helper interface
	 */
	private interface QuadConsumer<T, U, V, W> {

		public void accept(T t, U u, V v, W w);

	}

}
