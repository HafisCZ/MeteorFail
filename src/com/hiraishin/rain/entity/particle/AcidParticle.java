package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AcidParticle extends Particle {

	/*
	 * Definitions
	 */
	public static final double SPEED_X_INCREMENT = 0.2;
	public static final double SPEED_Y_INCREMENT = 0.5;

	public static final int TICKS_DESPAWN_MIN = 10;
	public static final int TICKS_DESPAWN_MAX = 30;

	/*
	 * Instance final variables
	 */
	private final Color color;

	/*
	 * Instance variables
	 */
	private int despawnTicks = TICKS_DESPAWN_MIN + Commons.RANDOM.nextInt(TICKS_DESPAWN_MAX - TICKS_DESPAWN_MIN + 1);
	private boolean despawnActive = false;

	/*
	 * Constructors
	 */
	public AcidParticle(double x, double y, double width, double height, double dx, double dy, Level level) {
		super(x, y, width, height, level);

		this.dx = dx;
		this.dy = dy;

		this.color = Commons.RANDOM.nextBoolean() ? Color.GREENYELLOW : Color.LAWNGREEN;
	}

	/*
	 * Instance functions
	 */
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x, y, width, height);
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (dx > 0) {
			dx = Math.max(0, dx - SPEED_X_INCREMENT);
		} else if (dx < 0) {
			dx = Math.min(0, dx + SPEED_X_INCREMENT);
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
		} else if (y + height > Commons.SCENE_GROUND) {
			y = Commons.SCENE_GROUND - height;
			despawnActive = true;
			dy = 0;
		} else {
			dy += SPEED_Y_INCREMENT;
		}

		if (despawnActive) {
			despawnTicks--;

			if (despawnTicks <= 0) {
				kill();
			}
		}
	}

}
