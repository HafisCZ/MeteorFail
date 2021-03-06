/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.level.player;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.GameData;
import com.hiraishin.rain.level.Level;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerProperties {

    public static final int EXP_RATE = 1;
    public static final int EXP_POOL = 40 * 60;
    public static final double EXP_POOL_MOD = 1.2;

    private final IntegerProperty healthProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty armorProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty levelProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty experienceProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty energyProperty = new SimpleIntegerProperty(0);
    private final int energyRate;

    private boolean skillActive = false;
    private boolean boostActive = false;
    private int skillBurnout = 0;
    private int boostBurnout = 0;
    private int experience = 0;
    private int experienceMultiplier = EXP_RATE;
    private Skill selectedSkill = null;

    public PlayerProperties() {
        healthProperty.set(GameData.PLAYER_HEALTH.getValue());
        levelProperty.set(GameData.PLAYER_LEVEL.getValue());

        int ord = GameData.PLAYER_SELECTEDSKILL.getValue();
        selectedSkill = (ord > 0) ?
                ((ord - 1 < Skill.values().length) ? Skill.values()[ord - 1] : null) :
                null;

        energyRate = (Objects.isNull(selectedSkill) ? 0 :
                selectedSkill.getRateMultiplier() * GameData.UPGRADE_POWERRATE.getValue());
    }

    public void activateSkill(Entity source, Level level) {
        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() >= 100) {
                    energyProperty.set(100);

                    skillActive = true;
                    skillBurnout = selectedSkill.getDuration();
                    selectedSkill.applyEffect(source.getX() + source.getWidth() / 2,
                                              source.getY() + source.getHeight() / 2, level, this);

                    GameData.STAT_COUNT_SKILLACTIVATION.increment();
                }
            }
        }
    }

    public void addEnergy(int multiplier) {
        GameData.STAT_COUNT_NODES.increment();

        if (!skillActive) {
            if (Objects.nonNull(selectedSkill)) {
                if (energyProperty.intValue() < 100) {
                    energyProperty.set(energyProperty.intValue() + energyRate * multiplier);
                    if (energyProperty.intValue() > 100) {
                        energyProperty.setValue(100);
                    }
                }
            }
        }
    }

    public void addExperience(int multiplier) {
        GameData.STAT_COUNT_EXPERIENCE.incrementBy(experienceMultiplier * multiplier);

        experience += experienceMultiplier * multiplier;
        experienceProperty.set((int) (100 * experience /
                (EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue()))));
        if (experience >= EXP_POOL * Math.pow(EXP_POOL_MOD, levelProperty.intValue())) {
            experience = 0;

            levelProperty.set(levelProperty.intValue() + 1);
            GameData.PLAYER_POINTS.increment();
            GameData.PLAYER_LEVEL.setValue(levelProperty.intValue());
        }
    }

    public void addExperience() {
        addExperience(1);
    }

    public void addShield() {
        GameData.STAT_COUNT_SHIELD.increment();

        if (armorProperty.intValue() < healthProperty.intValue()) {
            armorProperty.set(armorProperty.intValue() + 1);
        }
    }

    public void damage() {
        GameData.STAT_COUNT_DAMAGE.increment();

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
            energyProperty.set(100 * skillBurnout / selectedSkill.getDuration());
            if (--skillBurnout < 0) {
                energyProperty.set(0);
                skillActive = false;
            }
        }
    }

}
