package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.input.Controlled;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.input.KeyCode;

public class Player extends Entity implements Controlled {

	private boolean jump = true;

	public Player(double x, double y) {
		super(x, y, 53, 65, new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("player"), 2, 4));
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (jump) {
			dy += 0.5;
		}

		if (x < Commons.ZERO) {
			x = 0;
			dx = 0;
		} else if (x + width > Commons.GAME_SCENE_WIDTH) {
			x = Commons.GAME_SCENE_WIDTH - width;
			dx = 0;
		}

		if (y < Commons.ZERO) {
			y = 0;
			dy = 0;
		} else if (y + height > Commons.GAME_SCENE_GROUND) {
			y = Commons.GAME_SCENE_GROUND - height;
			dy = 0;

			jump = false;
		}
	}

	@Override
	public void control(Keyboard keyboard) {
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
	}

}
