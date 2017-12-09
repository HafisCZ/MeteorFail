package com.hiraishin.rain.level.player;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.RegistryManager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerProperties {

	/*
	 * Definitions
	 */
	public static final int DEFAULT_EXP_RATE = 1;
	public static final int DEFAULT_EXP_POOL = 30 * 60;
	public static final int DEFAULT_HEALTH = 5;
	public static final int DEFAULT_ENERGY_RATE = 1;

	public static final String REG_KEY_MAXIMUM_HEALTH = "p_health";
	public static final String REG_KEY_LEVEL = "p_level";
	public static final String REG_KEY_LEVEL_POINTS = "p_points";
	public static final String REG_KEY_SELECTED_SKILL = "p_selskill";

	public static final String REG_KEY_UPGRADE_POWERRATE = "p_powerrate";
	public static final String REG_KEY_UPGRADE_MANEUVER = "p_ignorefs";

	/*
	 * Instance variables
	 */
	private final IntegerProperty healthProperty = new SimpleIntegerProperty(0);
	private final IntegerProperty armorProperty = new SimpleIntegerProperty(0);
	private final IntegerProperty levelProperty = new SimpleIntegerProperty(0);
	private final IntegerProperty experienceProperty = new SimpleIntegerProperty(0);
	private final IntegerProperty energyProperty = new SimpleIntegerProperty(0);

	private final int healthMax;
	private final int energyRate;
	
	private final boolean upgrade_maneuver;

	private int experience = 0;
	private int experienceMultiplier = DEFAULT_EXP_RATE;

	private int levelPoints = 0;

	private Skill selectedSkill = null;

	private int skillBurnout = 0;
	private boolean skillActive = false;

	private int boostBurnout = 0;
	private boolean boostActive = false;

	/*
	 * Constructors
	 */
	public PlayerProperties(RegistryManager rm) {
		healthMax = rm.readInteger(REG_KEY_MAXIMUM_HEALTH, DEFAULT_HEALTH);
		upgrade_maneuver = rm.readBoolean(REG_KEY_UPGRADE_MANEUVER, false);

		healthProperty.set(healthMax);

		levelProperty.set(rm.readInteger(REG_KEY_LEVEL, 1));
		levelPoints = rm.readInteger(REG_KEY_LEVEL_POINTS, 0);

		int ord = rm.readInteger(REG_KEY_SELECTED_SKILL, 0);
		selectedSkill = (ord > 0) ? ((ord - 1 < Skill.values().length) ? Skill.values()[ord - 1] : null) : null;

		energyRate = (selectedSkill == null ? 0
				: selectedSkill.getChargeRate() * rm.readInteger(REG_KEY_UPGRADE_POWERRATE, DEFAULT_ENERGY_RATE));
	}

	/*
	 * Instance functions
	 */

	public void tick() {
		addExperience();

		if (boostActive) {
			if (--boostBurnout < 0) {
				experienceMultiplier = DEFAULT_EXP_RATE;
				boostActive = false;
			}
		}

		if (skillActive) {
			energyProperty.set((int) 100 * skillBurnout / selectedSkill.getBurnoutTime());
			if (--skillBurnout < 0) {
				energyProperty.set(0);
				skillActive = false;
			}
		}
	}

	public boolean hasManeuverUpgrade() {
		return upgrade_maneuver;
	}

	public void addShield() {
		if (armorProperty.intValue() < healthProperty.intValue()) {
			armorProperty.set(armorProperty.intValue() + 1);
		}
	}

	public void setTemporaryExperienceBoost(int multiplier, int burnout) {
		boostBurnout = burnout;
		experienceMultiplier = multiplier;
		boostActive = true;
	}

	public void save(RegistryManager rm) {
		rm.writeInteger(REG_KEY_LEVEL, levelProperty.intValue());
		rm.writeInteger(REG_KEY_LEVEL_POINTS, levelPoints);
	}

	public void activateSkill(Entity source, Level level) {
		if (!skillActive) {
			if (Objects.nonNull(selectedSkill)) {
				if (energyProperty.intValue() >= 100) {
					energyProperty.set(100);

					skillActive = true;
					skillBurnout = selectedSkill.getBurnoutTime();
					selectedSkill.activate(source.getX() + source.getWidth() / 2,
							source.getY() + source.getHeight() / 2, level, this);
				}
			}
		}
	}

	public void addEnergy() {
		if (!skillActive) {
			if (Objects.nonNull(selectedSkill)) {
				if (energyProperty.intValue() < 100) {
					energyProperty.set(energyProperty.intValue() + energyRate);
				}
			}
		}
	}

	public void damage() {
		if (armorProperty.intValue() > 0) {
			armorProperty.set(armorProperty.intValue() - 1);
		} else if (healthProperty.intValue() > 0) {
			healthProperty.set(healthProperty.intValue() - 1);
		}
	}

	public void addExperience() {
		experience += experienceMultiplier;
		experienceProperty.set((int) 100 * experience / (DEFAULT_EXP_POOL * levelProperty.intValue()));
		if (experience >= DEFAULT_EXP_POOL * levelProperty.intValue()) {
			experience = 0;

			levelProperty.set(levelProperty.intValue() + 1);
			levelPoints++;
		}
	}

	/*
	 * Getters & Setters
	 */

	public int getMaximumHealth() {
		return healthMax;
	}

	public Skill getSelectedSkill() {
		return selectedSkill;
	}

	public boolean isSkillActive() {
		return skillActive;
	}

	public IntegerProperty getLevelProperty() {
		return levelProperty;
	}

	public IntegerProperty getExperienceProperty() {
		return experienceProperty;
	}

	public IntegerProperty getArmorProperty() {
		return armorProperty;
	}

	public IntegerProperty getHealthProperty() {
		return healthProperty;
	}

	public IntegerProperty getEnergyProperty() {
		return energyProperty;
	}

}
