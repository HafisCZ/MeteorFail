package com.hiraishin.rain.entity.item;

import java.util.Objects;

import com.hiraishin.rain.entity.Lifetime;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;

public class Armor extends Item {

	public static double WIDTH = 20;
	public static double HEIGHT = 30;

	public Armor(double x, double y, Level level) {
		super(x, y, WIDTH, HEIGHT, new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("armor")), level);
	}

	@Override
	public void tick() {
		this.x += dx;
		this.y += dy;

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
			
			state = Lifetime.CLOSED;
		}

		if (Objects.nonNull(level.getPlayer()) && level.getPlayer().collidesAABB(this)) {
			state = Lifetime.CLOSED;
			
			level.getPlayerProperties().addArmor();	
		}
	}

}
