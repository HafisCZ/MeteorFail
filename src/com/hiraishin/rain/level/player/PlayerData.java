/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.level.player;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.level.PlayData;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerData {

    /*
     * Definitions
     */
    public static final int EXP_RATE = 1;
    public static final int EXP_POOL = 40 * 60;
    public static final double EXP_POOL_MOD = 1.2;

    /*
     * Instance final variables
     */
    private final IntegerProperty healthProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty armorProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty levelProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty experienceProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty energyProperty = new SimpleIntegerProperty(0);

    private final int energyRate;

    /*
     * Instance variables
     */
    private int experience = 0;
    private int experienceMultiplier = EXP_RATE;

    private Skill selectedSkill = null;

    private int skillBurnout = 0;
    private boolean skillActive = false;

    private int boostBurnout = 0;
    private boolean boostActive = false;

    /*
     * Constructors
     */
    public PlayerData() {
        healthProperty.set(PlayData.PLAYER_HEALTH.getValue());
        levelProperty.set(PlayData.PLAYER_LEVEL.getValue());

        int ord = PlayData.PLAYER_SELECTEDSKILL.getValue();
        selectedSkill = (ord > 0) ?
                ((ord - 1 < Skill.values().length) ? Skill.values()[ord - 1] : null) :
                null;

        energyRate = (Objects.isNull(selectedSkill) ? 0 :
                selectedSkill.getPowerGain() * PlayData.UPGRADE_POWERRATE.getValue());
    }

    /*
     * Instance functions
     */

    public void activateSkill(Entity source, Level level) {
        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() >= 100) {
                    energyProperty.set(100);

                    skillActive = true;
                    skillBurnout = selectedSkill.getBurnoutTime();
                    selectedSkill.activate(source.getX() + source.getWidth() / 2, source.getY() +
                            source.getHeight() / 2, level, this);

                    PlayData.STAT_COUNT_SKILLACTIVATION.increment();
                }
            }
        }
    }

    public void addEnergy() {
        PlayData.STAT_COUNT_NODES.increment();

        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() < 100) {
                    energyProperty.set(energyProperty.intValue() + energyRate);
                }
            }
        }
    }

    public void addExperience() {
        PlayData.STAT_COUNT_EXPERIENCE.incrementBy(experienceMultiplier);

        experience += experienceMultiplier;
        experienceProperty.set((int) (100 * experience /
                (EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue()))));
        if (experience >= EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue())) {
            experience = 0;

            levelProperty.set(levelProperty.intValue() + 1);
            PlayData.PLAYER_POINTS.increment();
            PlayData.PLAYER_LEVEL.setValue(levelProperty.intValue());
        }
    }

    public void addShield() {
        PlayData.STAT_COUNT_SHIELD.increment();

        if (armorProperty.intValue() < healthProperty.intValue()) {
            armorProperty.set(armorProperty.intValue() + 1);
        }
    }

    public void damage() {
        PlayData.STAT_COUNT_DAMAGE.increment();

        if (armorProperty.intValue() > 0) {
            armorProperty.set(armorProperty.intValue() - 1);
        } else if (healthProperty.intValue() > 0) {
            healthProperty.set(healthProperty.intValue() - 1);
        }
    }

    public IntegerProperty getArmorProperty() {
        return armorProperty;
    }

    public IntegerProperty getEnergyProperty() {
        return energyProperty;
    }

    public IntegerProperty getExperienceProperty() {
        return experienceProperty;
    }

    public int getHealth() {
        return this.healthProperty.intValue();
    }

    public IntegerProperty getHealthProperty() {
        return healthProperty;
    }

    public IntegerProperty getLevelProperty() {
        return levelProperty;
    }

    /*
     * Getters & Setters
     */
    public Skill getSelectedSkill() {
        return selectedSkill;
    }

    public boolean isSkillActive() {
        return skillActive;
    }

    public void setTemporaryExperienceBoost(int multiplier, int burnout) {
        boostBurnout = burnout;
        experienceMultiplier = multiplier;
        boostActive = true;
    }

    public void tick() {
        addExperience();

        if (boostActive) {
            if (--boostBurnout < 0) {
                experienceMultiplier = EXP_RATE;
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

}
