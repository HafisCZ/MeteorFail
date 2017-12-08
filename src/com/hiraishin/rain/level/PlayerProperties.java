package com.hiraishin.rain.level;

import com.hiraishin.rain.util.RegistryManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerProperties {

	private final IntegerProperty healthProperty;
	private final IntegerProperty armorProperty;

	private final IntegerProperty levelProperty;
	private final IntegerProperty experienceProperty;

	private final IntegerProperty xpBarProperty;

	private final int max_health;

	private int points;

	private RegistryManager rm;

	public PlayerProperties(RegistryManager rm) {
		this.rm = rm;

		max_health = rm.readInteger("health", 10);

		healthProperty = new SimpleIntegerProperty(max_health);
		armorProperty = new SimpleIntegerProperty(0);

		levelProperty = new SimpleIntegerProperty(rm.readInteger("level", 1));
		experienceProperty = new SimpleIntegerProperty(0);

		xpBarProperty = new SimpleIntegerProperty(0);

		points = rm.readInteger("points", 0);
	}

	public void save() {
		rm.writeInteger("level", levelProperty.intValue());
		rm.writeInteger("points", points);
	}

	public IntegerProperty getLevelProperty() {
		return levelProperty;
	}

	public IntegerProperty getExperienceProperty() {
		return experienceProperty;
	}

	public IntegerProperty getXPBarProperty() {
		return xpBarProperty;
	}

	public IntegerProperty getArmorProperty() {
		return armorProperty;
	}

	public IntegerProperty getHealthProperty() {
		return healthProperty;
	}

	public void addExperience() {
		experienceProperty.set(experienceProperty.intValue() + 1);

		xpBarProperty.set(
				(int) 100 * experienceProperty.intValue() / (30 * 60 * levelProperty.intValue()));

		if (experienceProperty.intValue() >= 30 * 60 * levelProperty.intValue() * levelProperty.intValue()) {
			experienceProperty.set(0);

			levelProperty.set(levelProperty.intValue() + 1);
			points++;
		}
	}

	public void addArmor() {
		if (armorProperty.intValue() < healthProperty.intValue()) {
			armorProperty.set(armorProperty.intValue() + 1);
		}
	}

	public void removeHealth() {
		if (armorProperty.intValue() > 0) {
			armorProperty.set(armorProperty.intValue() - 1);
		} else if (healthProperty.intValue() > 0) {
			healthProperty.set(healthProperty.intValue() - 1);
		}
	}

	public int getMaxHealth() {
		return max_health;
	}

}
