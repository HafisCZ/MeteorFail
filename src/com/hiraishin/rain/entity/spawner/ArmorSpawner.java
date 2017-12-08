package com.hiraishin.rain.entity.spawner;

import com.hiraishin.rain.entity.item.Armor;
import com.hiraishin.rain.level.Level;

public class ArmorSpawner extends Spawner {

	public ArmorSpawner(double x, double y, double w, double h, Level level, int frameRate) {
		super(x, y, w, h, level, frameRate);
	}

	@Override
	public void spawn() {
		level.add(new Armor(randomX(), randomY(), level));
	}

}
