package com.hiraishin.rain.entity.mob;

import java.util.Objects;

import com.hiraishin.rain.entity.Lifetime;
import com.hiraishin.rain.entity.particle.AcidParticle;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.application.Platform;

public class Acid extends Mob {

	public static final double WIDTH = 12;
	public static final double HEIGHT = 23;

	public Acid(double x, double y, double dx, double dy, Level level) {
		super(x, y, WIDTH, HEIGHT, new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("acid")), level);

		sprite.select(0, Commons.RANDOM.nextBoolean() ? 0 : 1);

		this.dx = dx;
		this.dy = dy;
	}

	private void spawnParticles(double verticalSpeed) {
		Platform.runLater(() -> {
			for (int i = 0; i < 5; i++) {
				final double particleSize = 1 + Commons.RANDOM.nextInt(5);
				final double particleXSpeed = Commons.RANDOM.nextInt(5) - 2.5;
				level.add(new AcidParticle(x, y + HEIGHT - particleSize, particleSize, particleSize, particleXSpeed,
						verticalSpeed, level));
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

		if (y < Commons.ZERO) {
			y = 0;
		} else if (y + height > Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;
		}

		if (y + height >= Commons.SCENE_GROUND) {
			state = Lifetime.CLOSED;

			spawnParticles(0);
		}

		if (Objects.nonNull(level.getPlayer()) && level.getPlayer().collidesAABB(this)) {
			state = Lifetime.CLOSED;
			
			level.getPlayerProperties().removeHealth();
			spawnParticles(-1);
		}
	}

}
