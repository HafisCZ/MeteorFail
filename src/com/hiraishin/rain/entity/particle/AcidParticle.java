package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.entity.Lifetime;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AcidParticle extends Particle {

	private final Color color;

	private int despawnTicks = 10 + Commons.RANDOM.nextInt(20);

	public AcidParticle(double x, double y, double w, double h, double dx, double dy, Level level) {
		super(x, y, w, h, level);

		this.dx = dx;
		this.dy = dy;

		this.color = Commons.RANDOM.nextBoolean() ? Color.GREENYELLOW : Color.LAWNGREEN;
	}

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
			dx = Math.max(0, dx - 0.2);
		} else if (dx < 0) {
			dx = Math.min(0, dx + 0.2);
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
			dy = 0;
		} else {
			dy += 0.5;
		}

		if (dy == 0) {
			despawnTicks--;
		}

		if (despawnTicks <= 0) {
			state = Lifetime.CLOSED;
		}
	}

}
