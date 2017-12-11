package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.entity.particle.AcidParticle;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImageLoader;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class Acid extends Mob {

	/*
	 * Definitions
	 */
	public static final double WIDTH = 10;
	public static final double HEIGHT = 23;
	public static final double SPEED_X_DEFAULT = 0;
	public static final double SPEED_Y_DEFAULT = 10;

	public static final Image IMAGE = ImageLoader.DEFAULT.requestImage("entity/acid");
	public static final int IMAGE_ROWS = 1;
	public static final int IMAGE_COLS = 1;
	public static final double SPRITE_X_OFFSET = -1;
	public static final double SPRITE_Y_OFFSET = 0;

	public static final int PARTICLE_COUNT = 5;

	/*
	 * Constructors
	 */
	public Acid(double x, double y, Level level) {
		this(x, y, SPEED_X_DEFAULT, SPEED_Y_DEFAULT, level);
	}

	public Acid(double x, double y, double dx, double dy, Level level) {
		super(x, y, WIDTH, HEIGHT, new Sprite(IMAGE, IMAGE_ROWS, IMAGE_COLS), SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);

		this.sprite.select(0, Commons.RANDOM.nextBoolean() ? 0 : 1);

		this.dx = dx;
		this.dy = dy;
	}

	/*
	 * Instance functions
	 */
	protected void spawnParticles(int amount, double ySpeed) {
		Platform.runLater(() -> {
			for (int i = 0; i < amount; i++) {
				final double particleSize = Commons.RANDOM.nextInt(5) + 1;
				final double particleXSpeed = Commons.RANDOM.nextInt(5) - 2.5;
				this.level.add(new AcidParticle(this.x, this.y + HEIGHT - particleSize, particleSize, particleSize,
						particleXSpeed, ySpeed, this.level));
			}
		});
	}

	@Override
	public final void tick() {
		this.x += this.dx;
		this.y += this.dy;

		if (this.y + this.height >= Commons.SCENE_GROUND) {
			this.y = Commons.SCENE_GROUND - this.height;

			kill();
			spawnParticles(PARTICLE_COUNT, 0);
		}
		
		if (this.level.isCollidingPlayerAABB(this)) {
			this.level.getPlayerProperties().damage();

			if (this.level.getPlayerProperties().getHealth() > 0) {
				spawnParticles(PARTICLE_COUNT, -1);
			}

			kill();
		}
	}

}
