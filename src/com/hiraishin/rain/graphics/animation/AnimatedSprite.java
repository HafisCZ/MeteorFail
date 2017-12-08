package com.hiraishin.rain.graphics.animation;

import com.hiraishin.rain.graphics.Sprite;

import javafx.scene.image.Image;

public class AnimatedSprite extends Sprite {

	private final Step start;
	private final Step steps[];

	private int counter = 0;
	private int selector = 0;

	private final int delay;

	private boolean isRunning = false;

	public AnimatedSprite(Image image, int rows, int columns, int delay, Step... steps) {
		super(image, rows, columns);

		this.delay = delay;

		this.start = steps[0];

		this.steps = new Step[steps.length - 1];
		for (int i = 1; i < steps.length; i++) {
			this.steps[i - 1] = steps[i];
		}
	}

	public void start() {
		if (!isRunning) {
			isRunning = true;
			selector = 0;
			counter = 0;

			select(steps[selector].row, steps[selector].col);
		}
	}

	public void stop() {
		if (isRunning) {
			isRunning = false;
			select(start.row, start.col);
		}
	}

	public void tick() {
		if (isRunning) {
			counter++;
			if (counter >= delay) {
				if (++selector >= steps.length) {
					selector = 0;
				}

				counter = 0;

				select(steps[selector].row, steps[selector].col);
			}
		}
	}

}
