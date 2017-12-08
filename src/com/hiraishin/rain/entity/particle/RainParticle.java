package com.hiraishin.rain.entity.particle;

import java.util.Objects;

import com.hiraishin.rain.entity.Lifetime;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RainParticle extends Particle {

	private final Color color;

	public RainParticle(double x, double y, double w, double h, double dx, double dy, Level level) {
		super(x, y, w, h, level);

		this.dx = dx;
		this.dy = dy;

		this.color = Commons.RANDOM.nextBoolean() ? Color.rgb(100, 149, 237, 0.2) : Color.rgb(173, 216, 230, 0.2);
	}

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
				state = Lifetime.CLOSED;
			}
		}

		if (y > Commons.SCENE_GROUND) {
			state = Lifetime.CLOSED;
		}
	}

}
