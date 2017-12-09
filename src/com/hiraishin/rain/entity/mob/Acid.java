package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.entity.particle.AcidParticle;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

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

	public static final Image IMAGE = ImagePreloader.DEFAULT_LOADER.getImage("acid");
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

		sprite.select(0, Commons.RANDOM.nextBoolean() ? 0 : 1);

		this.dx = dx;
		this.dy = dy;
	}

	/*
	 * Instance functions
	 */
	private void spawnParticles(int amount, double ySpeed) {
		Platform.runLater(() -> {
			for (int i = 0; i < amount; i++) {
				final double particleSize = Commons.RANDOM.nextInt(5) + 1;
				final double particleXSpeed = Commons.RANDOM.nextInt(5) - 2.5;
				level.add(new AcidParticle(x, y + HEIGHT - particleSize, particleSize, particleSize, particleXSpeed,
						ySpeed, level));
			}
		});
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (x < Commons.ZERO) {
			x = 0;
		} else if (x + width > Commons.SCENE_WIDTH) {
			x = Commons.SCENE_WIDTH - width;
		}

		if (y + height >= Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;

			kill();
			spawnParticles(PARTICLE_COUNT, 0);
		}

		if (level.getPlayer().collidesAABB(this)) {
			level.getPlayerProperties().damage();

			if (level.getPlayerProperties().getHealthProperty().intValue() > 0) {
				spawnParticles(PARTICLE_COUNT, -1);
			}

			kill();
		}
	}

}
