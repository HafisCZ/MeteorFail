package com.hiraishin.rain.entity.particle;

import java.util.Objects;

import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RainParticle extends Particle {

	/*
	 * Definitions
	 */
	private static final Color COLOR_1 = Color.rgb(100, 149, 237, 0.2);
	private static final Color COLOR_2 = Color.rgb(173, 216, 230, 0.2);

	/*
	 * Instance final variables
	 */
	private final Color color;

	/*
	 * Constructors
	 */
	public RainParticle(double x, double y, double width, double height, double dx, double dy, Level level) {
		super(x, y, width, height, level);

		this.dx = dx;
		this.dy = dy;

		this.color = Commons.RANDOM.nextBoolean() ? COLOR_1 : COLOR_2;
	}

	/*
	 * Instance functions
	 */
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x, y, width, (y + height > Commons.SCENE_GROUND) ? y - Commons.SCENE_GROUND : height);
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (Objects.nonNull(level.getPlayer())) {
			if (level.getPlayer().collidesAABB(this)) {
				kill();
			}
		}

		if (y > Commons.SCENE_GROUND) {
			kill();
		}
	}

}
