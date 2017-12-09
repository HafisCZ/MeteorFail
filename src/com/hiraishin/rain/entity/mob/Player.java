package com.hiraishin.rain.entity.mob;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.animation.AnimatedSprite;
import com.hiraishin.rain.graphics.animation.Step;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.level.PlayerProperties;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Entity {

	/*
	 * Definitions
	 */
	public static final double WIDTH = 47;
	public static final double HEIGHT = 65;
	public static final double SPEED_X_LIMIT = 10;
	public static final double SPEED_Y_LIMIT = -10;
	public static final double SPEED_X_INCREMENT = 0.5;
	public static final double SPEED_X_INCREMENT2 = 0.6;
	public static final double SPEED_Y_INCREMENT = 0.5;

	public static final Image IMAGE = ImagePreloader.DEFAULT_LOADER.getImage("player");
	public static final int IMAGE_ROWS = 2;
	public static final int IMAGE_COLS = 4;
	public static final double SPRITE_X_OFFSET = -4;
	public static final double SPRITE_Y_OFFSET = -1;

	public static final int ANIMATION_DELTA = 8;
	public static final Step[] ANIMATION_STEPS = { new Step(0, 0), new Step(1, 0), new Step(1, 1), new Step(1, 2),
			new Step(1, 3) };

	/*
	 * Instance final variables
	 */
	private final Keyboard keyboard;
	private final double speed;

	/*
	 * Instance variables
	 */
	private boolean jump = true;

	/*
	 * Constructors
	 */
	public Player(double x, double y, Level level, Keyboard keyboard, PlayerProperties properties) {
		super(x, y, WIDTH, HEIGHT, new AnimatedSprite(IMAGE, IMAGE_ROWS, IMAGE_COLS, ANIMATION_DELTA, ANIMATION_STEPS),
				SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);

		this.keyboard = Objects.requireNonNull(keyboard);
		this.speed = properties.hasManeuverUpgrade() ? SPEED_X_INCREMENT2 : SPEED_X_INCREMENT;
	}

	/*
	 * Instance functions
	 */
	@Override
	public void tick() {
		if (keyboard.isHeld(KeyCode.D) && !keyboard.isHeld(KeyCode.A)) {
			dx = Math.min(dx + speed, SPEED_X_LIMIT);
		}

		if (keyboard.isHeld(KeyCode.A) && !keyboard.isHeld(KeyCode.D)) {
			dx = Math.max(dx - speed, -SPEED_X_LIMIT);
		}

		if (!keyboard.isHeld(KeyCode.A) && !keyboard.isHeld(KeyCode.D)) {
			if (dx > 0) {
				dx = Math.max(dx - speed, 0);
			} else if (dx < 0) {
				dx = Math.min(dx + speed, 0);
			}
		}

		if (keyboard.isHeld(KeyCode.SPACE)) {
			if (!jump) {
				jump = true;
				dy = SPEED_Y_LIMIT;
			}
		}

		if (keyboard.isPressed(KeyCode.F)) {
			level.getPlayerProperties().activateSkill(this, level);
		}

		this.x += dx;
		this.y += dy;

		if (jump) {
			dy += SPEED_Y_INCREMENT;
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
