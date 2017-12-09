package com.hiraishin.rain;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.level.PlayState;
import com.hiraishin.rain.util.Commons;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Game {

	private final Canvas canvas = new Canvas(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();

	private final Level level;
	private final Keyboard keyboard;

	public Game(Keyboard keyboard) {
		this.keyboard = keyboard;

		this.level = new Level();

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				level.tick();
				level.draw(gc);

				if (level.getState() == PlayState.STOP) {
					canvas.fireEvent(new StateEvent(StateEvent.MENU));
					level.exit();
				}

				if (keyboard.isPressed(KeyCode.ESCAPE)) {
					switch (level.getState()) {
					case PLAY:
						canvas.fireEvent(new StateEvent(StateEvent.PAUSE));
						break;
					case PAUSE:
						canvas.fireEvent(new StateEvent(StateEvent.UNPAUSE));
						break;
					default:
						break;
					}
				}

				keyboard.update();
			}
		}.start();
	}

	public void play() {
		this.level.open(keyboard);
	}

	public void pause() {
		this.level.pause();
	}

	public void unpause() {
		this.level.unpause();
	}

	public void close() {
		this.level.close();
	}

	public Canvas getCanvas() {
		return canvas;
	}
}
