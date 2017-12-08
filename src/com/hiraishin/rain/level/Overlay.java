package com.hiraishin.rain.level;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.canvas.GraphicsContext;

public class Overlay implements Drawable {

	private final double x;
	private final double y;

	private final int healthSlots;

	private final Sprite healthSprites[] = new Sprite[] {
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart_frame"), 1, 10),
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart"), 1, 10),
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart_armor"), 1, 10) };

	private final Sprite xpSprites[] = new Sprite[] {
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("bar_outline"), 1, 1),
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("xpbar"), 1, 100), };

	private final Sprite energySprites[] = new Sprite[] {
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("bar_outline"), 1, 1),
			new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("energybar"), 2, 100), };

	public Overlay(double x, double y, PlayerProperties pp) {
		this.x = x;
		this.y = y;

		healthSlots = pp.getMaxHealth();

		healthSprites[0].stretch(1, healthSlots);
		healthSprites[1].stretch(1, healthSlots);
		healthSprites[2].stretch(1, 0);

		xpSprites[1].stretch(1, 0);
		
		energySprites[1].select(0, 0);
		energySprites[1].stretch(1, 50);

		pp.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
			healthSprites[1].stretch(1, NewValue.intValue());
		});

		pp.getArmorProperty().addListener((Observable, OldValue, NewValue) -> {
			healthSprites[2].stretch(1, NewValue.intValue());
		});

		pp.getXPBarProperty().addListener((Observable, OldValue, NewValue) -> {
			xpSprites[1].stretch(1, NewValue.intValue());
		});
	}

	@Override
	public void draw(GraphicsContext gc) {
		for (Sprite s : healthSprites) {
			s.draw(gc, x + 10, y + 10);
		}

		for (Sprite s : xpSprites) {
			s.draw(gc, x + 10, y + 50);
		}
		
		for (Sprite s : energySprites) {
			s.draw(gc, x + 10, y + 70);
		}
	}

}
