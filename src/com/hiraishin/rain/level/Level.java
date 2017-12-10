package com.hiraishin.rain.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.entity.item.Item;
import com.hiraishin.rain.entity.mob.Mob;
import com.hiraishin.rain.entity.mob.Player;
import com.hiraishin.rain.entity.particle.Particle;
import com.hiraishin.rain.entity.particle.RainParticle;
import com.hiraishin.rain.entity.spawner.AcidSpawner;
import com.hiraishin.rain.entity.spawner.ArmorSpawner;
import com.hiraishin.rain.entity.spawner.EnergySpawner;
import com.hiraishin.rain.entity.spawner.RainSpawner;
import com.hiraishin.rain.entity.spawner.Spawner;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.overlay.Overlay;
import com.hiraishin.rain.level.player.PlayerData;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImageLoader;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Level {

	/*
	 * Instance variables
	 */
	private final List<Entity> mobs = new ArrayList<>();
	private final List<Spawner> spawners = new ArrayList<>();
	private final List<Entity> particles = new ArrayList<>();

	private PlayerData properties;
	private Overlay overlay;

	private final Image background = ImageLoader.DEFAULT.requestImage("background/background");

	private LevelState state = LevelState.STOP;

	/*
	 * Constructors
	 */
	public Level() {
		spawners.add(new RainSpawner(0, -20, Commons.SCENE_WIDTH, 0, this, 0, 0, 5));
	}

	/*
	 * Instance functions
	 */
	public void add(Entity e) {
		if (e instanceof Mob || e instanceof Item) {
			mobs.add(e);
		} else if (e instanceof Particle) {
			particles.add(e);
		}
	}

	public void tick() {
		switch (state) {

		case PLAY: {
			for (Entity s : spawners) {
				s.tick();
			}

			for (Entity m : mobs) {
				m.tick();
			}

			for (Entity p : particles) {
				p.tick();
			}

			if (Objects.nonNull(properties)) {
				properties.tick();
			}

			mobs.removeIf(Entity::isDead);
			particles.removeIf(Entity::isDead);
			break;
		}

		case PAUSE: {
			break;
		}

		default: {
			for (Entity s : spawners) {
				if (s instanceof RainSpawner) {
					s.tick();
				}
			}

			for (Entity p : particles) {
				if (p instanceof RainParticle) {
					p.tick();
				}
			}

			particles.removeIf(E -> E.isDead() && (E instanceof Particle));
			break;
		}

		}

	}

	public void draw(GraphicsContext gc) {
		gc.drawImage(background, 0, 0, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

		for (Entity p : particles) {
			p.draw(gc);
		}

		for (Entity m : mobs) {
			m.draw(gc);
		}

		if (Objects.nonNull(overlay)) {
			overlay.draw(gc);
		}
	}

	public void open(Keyboard keyboard) {
		if (state == LevelState.PLAY) {
			return;
		}

		close();

		state = LevelState.PLAY;

		properties = new PlayerData();
		overlay = new Overlay(0, 0, properties);

		mobs.add(
				new Player((Commons.SCENE_WIDTH - Player.WIDTH) / 2, Commons.SCENE_GROUND, this, keyboard, properties));

		spawners.add(new AcidSpawner(0, -50, Commons.SCENE_WIDTH, 0, this, 10, 5, 2));
		spawners.add(new ArmorSpawner(0, -50, Commons.SCENE_WIDTH, 0, this, 60 * 60, 30 * 60, 1));
		spawners.add(new EnergySpawner(0, -50, Commons.SCENE_WIDTH, 0, this, 60, 60, 1));

		properties.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
			if (NewValue.intValue() <= 0) {
				Platform.runLater(() -> {
					PlayData.save();
					close();
				});
			}
		});
	}

	public void exit() {
		if (state != LevelState.EXIT) {
			state = LevelState.EXIT;
		}
	}

	public void pause() {
		if (state == LevelState.PLAY) {
			state = LevelState.PAUSE;
		}
	}

	public void unpause() {
		if (state == LevelState.PAUSE) {
			state = LevelState.PLAY;
		}
	}

	public void close() {
		if (state == LevelState.STOP) {
			return;
		}

		state = LevelState.STOP;

		properties = null;
		overlay = null;

		mobs.clear();
		spawners.subList(1, spawners.size()).clear();
		particles.removeIf(E -> !(E instanceof RainParticle));
	}

	/*
	 * Getters & Setters
	 */
	public Player getPlayer() {
		return (mobs.size() > 0) ? (Player) mobs.get(0) : null;
	}

	public List<Entity> getMobs() {
		return mobs.subList(1, mobs.size());
	}

	public LevelState getState() {
		return state;
	}

	public List<Entity> getParticles() {
		return particles;
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public PlayerData getPlayerProperties() {
		return properties;
	}

}
