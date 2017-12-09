package com.hiraishin.rain.scene;

import java.util.Objects;

import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public enum Game {

	INSTANCE();

	private final Canvas canvas = new Canvas(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	private Level level;

	private Game() {

	}

	public void playLevel(Keyboard keyboard) {
		if (Objects.nonNull(level)) {
			level.open(keyboard);
		}
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void update() {
		if (Objects.nonNull(level)) {
			level.tick();
			level.draw(gc);
		}
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
