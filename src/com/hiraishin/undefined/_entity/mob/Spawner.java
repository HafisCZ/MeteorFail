package com.hiraishin.undefined._entity.mob;

import com.hiraishin.undefined._entity.Entity;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Spawner {

	private final Timeline spawnCycle;

	private Entity entity;
	private boolean claimed;

	public Spawner(Class<? extends Entity> type, double minx, double maxx, double miny, double maxy, double msDelay) {
		if (msDelay < 1) {
			throw new IllegalArgumentException();
		}

		spawnCycle = new Timeline();
		spawnCycle.setCycleCount(Timeline.INDEFINITE);
		spawnCycle.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), e -> {
			if (claimed) {
				double randomX = Entity.RANDOM.nextInt((int) (maxx - minx + 1)) + (int) minx;
				double randomY = Entity.RANDOM.nextInt((int) (maxy - miny + 1)) + (int) miny;

				if (type.equals(Item.class)) {
					entity = new Item(randomX, randomY);
				} else if (type.equals(Enemy.class)) {
					entity = new Enemy(randomX, randomY);
				} else if (type.equals(Player.class)) {
					entity = new Player(randomX, randomY);
				}

				claimed = false;
			}
		}), new KeyFrame(Duration.millis(msDelay), e -> {
		}));

		spawnCycle.play();
	}

	public Entity next() {
		if (claimed) {
			return null;
		} else {
			claimed = true;
			return entity;
		}
	}

	public void start() {
		spawnCycle.play();
	}

	public void stop() {
		spawnCycle.stop();
	}

}
