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
import com.hiraishin.rain.entity.spawner.RainSpawner;
import com.hiraishin.rain.entity.spawner.Spawner;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;
import com.hiraishin.rain.util.RegistryManager;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Level {

	/**
	 * Entities
	 */
	private final List<Entity> mobs = new ArrayList<>();

	/**
	 * Player essentials
	 */
	private PlayerProperties properties;
	private Overlay overlay;

	/**
	 * Effects
	 */
	private final List<Entity> particles = new ArrayList<>();
	private final List<Spawner> spawners = new ArrayList<>();
	private final Image background = ImagePreloader.DEFAULT_LOADER.getImage("bg");

	public Level() {
		spawners.add(new RainSpawner(0, -20, Commons.SCENE_WIDTH, 0, this, 0));
	}

	public void init(Keyboard keyboard) {
		spawners.subList(1, spawners.size()).clear();
		spawners.add(new AcidSpawner(0, -20, Commons.SCENE_WIDTH, 0, this, 10));
		spawners.add(new ArmorSpawner(0, -20, Commons.SCENE_WIDTH, 0, this, 60 * 60));

		mobs.clear();
		mobs.add(new Player((Commons.SCENE_WIDTH - Player.WIDTH) / 2, Commons.SCENE_GROUND, this, keyboard));

		particles.removeIf(E -> !(E instanceof RainParticle));

		properties = new PlayerProperties(RegistryManager.INSTANCE);
		overlay = new Overlay(0, 0, properties);
	}

	public Player getPlayer() {
		return (mobs.size() > 0) ? (Player) mobs.get(0) : null;
	}

	public List<Entity> getMobs() {
		return mobs.subList(1, mobs.size());
	}

	public List<Entity> getParticles() {
		return particles;
	}

	public List<Spawner> getSpawners() {
		return spawners;
	}

	public PlayerProperties getPlayerProperties() {
		return properties;
	}

	public void add(Entity e) {
		if (e instanceof Mob || e instanceof Item) {
			mobs.add(e);
		} else if (e instanceof Particle) {
			particles.add(e);
		}
	}

	public void tick() {
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
			properties.addExperience();
		}

		mobs.removeIf(Entity::isDead);
		particles.removeIf(Entity::isDead);
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

}
