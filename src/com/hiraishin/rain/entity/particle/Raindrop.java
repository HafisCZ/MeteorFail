package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.entity.UpdateCycle;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Raindrop extends Particle {

	private final Color color;
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param dx
	 * @param dy
	 */
	public Raindrop(double x, double y, double w, double h, double dx, double dy) {
		super(x, y, Math.max(1, w), Math.max(1, h));

		this.dx = dx;
		this.dy = dy;

		this.color = Commons.RANDOM.nextBoolean() ? Color.rgb(100, 149, 237, 0.2) : Color.rgb(173, 216, 230, 0.2);
	}

	/**
	 * Draw particle
	 */
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x, y, width, (y + height > Commons.GAME_SCENE_GROUND) ? y - Commons.GAME_SCENE_GROUND : height);
	}

	/**
	 * Tick
	 */
	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

		if (y > Commons.GAME_SCENE_GROUND) {
			state = UpdateCycle.CLOSED;
		}
	}

}
