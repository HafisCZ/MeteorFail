package com.hiraishin.rain.level;

import java.util.Objects;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.skill.Skill;
import com.hiraishin.rain.util.ImagePreloader;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Overlay implements Drawable {

	private static final Sprite HLT_BAR = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart"), 1, 10);
	private static final Sprite ARM_BAR = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart_armor"), 1, 10);
	private static final Sprite EXP_BAR = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("xpbar"), 1, 100);
	private static final Sprite PWR_BAR = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("energybar"), 2, 100);

	private static final Sprite ABL_ICO = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("abilities"), 1, 4);
	private static final Sprite HLC_ICO = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("heart_icon"), 1, 1);
	private static final Sprite EXP_ICO = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("xp_icon"), 1, 1);
	private static final Sprite PWR_ICO = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("power_icon"), 1, 1);

	private static final Sprite SQ_FRAME = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("icon_frame"), 1, 1);
	private static final Sprite RC_FRAME = new Sprite(ImagePreloader.DEFAULT_LOADER.getImage("bar_outline"), 1, 1);

	private final double x;
	private final double y;

	private Skill skill;

	private int level;

	public Overlay(double x, double y, PlayerProperties properties) {
		this.x = x;
		this.y = y;
		this.skill = properties.getSelectedSkill();

		level = properties.getLevelProperty().intValue();

		HLT_BAR.stretch(1, properties.getMaximumHealth());
		ARM_BAR.stretch(1, 0);
		EXP_BAR.stretch(1, 0);
		PWR_BAR.stretch(1, 0);

		if (Objects.nonNull(skill)) {
			switch (skill) {
			case SHOCKWAVE:
				ABL_ICO.select(0, 0);
				break;
			case SHIELD_SPAWN:
				ABL_ICO.select(0, 1);
				break;
			case EXPERIENCE_BOOST:
				ABL_ICO.select(0, 2);
				break;
			}
		}

		properties.getLevelProperty().addListener((Observable, OldValue, NewValue) -> {
			level = NewValue.intValue();
		});

		properties.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
			HLT_BAR.stretch(1, NewValue.intValue());
		});

		properties.getArmorProperty().addListener((Observable, OldValue, NewValue) -> {
			ARM_BAR.stretch(1, NewValue.intValue());
		});

		properties.getExperienceProperty().addListener((Observable, OldValue, NewValue) -> {
			EXP_BAR.stretch(1, NewValue.intValue());
		});

		properties.getEnergyProperty().addListener((Observable, OldValue, NewValue) -> {
			PWR_BAR.stretch(1, NewValue.intValue());

			if (properties.isSkillActive() || NewValue.intValue() >= 100) {
				PWR_BAR.select(1, 0);
			} else {
				PWR_BAR.select(0, 0);
			}
		});
	}

	private void drawStatic(GraphicsContext gc) {
		HLC_ICO.draw(gc, x + 2, y + 10);
		EXP_ICO.draw(gc, x + 2, y + 30);
		PWR_ICO.draw(gc, x + 2, y + 50);

		RC_FRAME.draw(gc, x + 20, y + 10);
		RC_FRAME.draw(gc, x + 20, y + 30);
		RC_FRAME.draw(gc, x + 20, y + 50);

		if (Objects.nonNull(skill)) {
			SQ_FRAME.draw(gc, x + 20, y + 70);
			ABL_ICO.draw(gc, x + 22, y + 72);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		drawStatic(gc);

		HLT_BAR.draw(gc, x + 20, y + 10);
		ARM_BAR.draw(gc, x + 20, y + 10);
		EXP_BAR.draw(gc, x + 20, y + 30);
		PWR_BAR.draw(gc, x + 20, y + 50);

		gc.setFill(Color.YELLOW);
		gc.fillText("" + level, x + 230, y + 42);
	}

}
