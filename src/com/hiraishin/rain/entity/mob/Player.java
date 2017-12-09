package com.hiraishin.rain.entity.mob;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.animation.AnimatedSprite;
import com.hiraishin.rain.graphics.animation.Step;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.level.player.PlayerProperties;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImageLoader;

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

	public static final Image IMAGE = ImageLoader.DEFAULT.requestImage("entity/player");
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
		if (this.keyboard.isHeld(KeyCode.D) && !this.keyboard.isHeld(KeyCode.A)) {
			this.dx = Math.min(this.dx + this.speed, SPEED_X_LIMIT);
		}

		if (this.keyboard.isHeld(KeyCode.A) && !this.keyboard.isHeld(KeyCode.D)) {
			this.dx = Math.max(this.dx - this.speed, -SPEED_X_LIMIT);
		}

		if (!this.keyboard.isHeld(KeyCode.A) && !this.keyboard.isHeld(KeyCode.D)) {
			if (this.dx > 0) {
				this.dx = Math.max(this.dx - this.speed, 0);
			} else if (this.dx < 0) {
				this.dx = Math.min(this.dx + this.speed, 0);
			}
		}

		if (this.keyboard.isHeld(KeyCode.SPACE)) {
			if (!this.jump) {
				this.jump = true;
				this.dy = SPEED_Y_LIMIT;
			}
		}

		if (this.keyboard.isPressed(KeyCode.F)) {
			this.level.getPlayerProperties().activateSkill(this, this.level);
		}

		this.x += this.dx;
		this.y += this.dy;

		if (this.jump) {
			this.dy += SPEED_Y_INCREMENT;
		}

		if (this.x < Commons.ZERO) {
			this.x = 0;
			this.dx = 0;
		} else if (this.x + this.width > Commons.SCENE_WIDTH) {
			this.x = Commons.SCENE_WIDTH - this.width;
			this.dx = 0;
		}

		if (this.y < Commons.ZERO) {
			this.y = 0;
			this.dy = 0;
		} else if (this.y + this.height > Commons.SCENE_GROUND) {
			this.y = Commons.SCENE_GROUND - this.height;
			this.dy = 0;

			this.jump = false;
		}

		if (this.dx != 0) {
			this.sprite.setFlip(this.dx > 0 ? false : true, false);
			((AnimatedSprite) this.sprite).play();
		} else {
			((AnimatedSprite) this.sprite).stop();
		}

		((AnimatedSprite) this.sprite).tick();
	}

}
