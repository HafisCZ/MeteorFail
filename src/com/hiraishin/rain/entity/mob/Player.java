package com.hiraishin.rain.entity.mob;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.animation.AnimatedSprite;
import com.hiraishin.rain.graphics.animation.Step;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.input.KeyCode;

public class Player extends Entity {

	public static final double WIDTH = 55;
	public static final double HEIGHT = 66;

	private final Keyboard keyboard;

	private boolean jump = true;

	public Player(double x, double y, Level level, Keyboard keyboard) {
		super(x, y, WIDTH, HEIGHT, new AnimatedSprite(ImagePreloader.DEFAULT_LOADER.getImage("player"), 2, 4, 8,
				new Step(0, 0), new Step(1, 0), new Step(1, 1), new Step(1, 2), new Step(1, 3)), level);

		this.keyboard = Objects.requireNonNull(keyboard);
	}

	@Override
	public void tick() {
		if (keyboard.isHeld(KeyCode.D) && !keyboard.isHeld(KeyCode.A)) {
			dx = Math.min(dx + 0.5, 10);
		}

		if (keyboard.isHeld(KeyCode.A) && !keyboard.isHeld(KeyCode.D)) {
			dx = Math.max(dx - 0.5, -10);
		}

		if (!keyboard.isHeld(KeyCode.A) && !keyboard.isHeld(KeyCode.D)) {
			if (dx > 0) {
				dx = Math.max(dx - 0.5, 0);
			} else if (dx < 0) {
				dx = Math.min(dx + 0.5, 0);
			}
		}

		if (keyboard.isHeld(KeyCode.SPACE)) {
			if (!jump) {
				jump = true;
				dy = -10;
			}
		}

		this.x += dx;
		this.y += dy;

		if (jump) {
			dy += 0.5;
		}

		if (x < Commons.ZERO) {
			x = 0;
			dx = 0;
		} else if (x + width > Commons.SCENE_WIDTH) {
			x = Commons.SCENE_WIDTH - width;
			dx = 0;
		}

		if (y < Commons.ZERO) {
			y = 0;
			dy = 0;
		} else if (y + height > Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;
			dy = 0;

			jump = false;
		}

		if (dx != 0) {
			sprite.setFlip(dx > 0 ? false : true, false);
			((AnimatedSprite) sprite).start();
		} else {
			((AnimatedSprite) sprite).stop();
		}

		((AnimatedSprite) sprite).tick();
	}

}
