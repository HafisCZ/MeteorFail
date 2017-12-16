/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum GameData {

    STAT_COUNT_EXPERIENCE(0, Integer.MAX_VALUE, "Experience gained"),
    STAT_COUNT_DAMAGE(0, Integer.MAX_VALUE, "Damage taken"),
    STAT_COUNT_SHIELD(0, Integer.MAX_VALUE, "Shields collected"),
    STAT_COUNT_NODES(0, Integer.MAX_VALUE, "Nodes collected"),
    STAT_COUNT_STARS(0, Integer.MAX_VALUE, "Stars collected"),
    STAT_COUNT_JUMP(0, Integer.MAX_VALUE, "Times jumped"),
    STAT_COUNT_SKILLACTIVATION(0, Integer.MAX_VALUE, "Skills activated"),

    PLAYER_LEVEL(1, Integer.MAX_VALUE, "Levels"),
    PLAYER_POINTS(0, Integer.MAX_VALUE, "Upgrade points"),
    PLAYER_SELECTEDSKILL(0, 3, "Selected Skill"),
    PLAYER_HEALTH(3, 10, "Health"),

    UPGRADE_MOVEMENT(0, 1, "Movement upgrade"),
    UPGRADE_POWERRATE(1, 2, "Power rate"),
    UPGRADE_SHOCKWAVE(0, 1, "Shockwave Skill"),
    UPGRADE_DOUBLEXP(0, 1, "Double XP Skill"),
    UPGRADE_SHIELDSPAWN(0, 1, "Shield spawn Skill");

    private static final String SER_FILE = "playdata.ser";

    private final int min;
    private final int max;
    private final String name;

    private int value;

    public static GameData[] getStatistics() {
        return new GameData[] { STAT_COUNT_EXPERIENCE, STAT_COUNT_DAMAGE, STAT_COUNT_SHIELD,
                STAT_COUNT_NODES, STAT_COUNT_STARS, STAT_COUNT_JUMP, STAT_COUNT_SKILLACTIVATION };
    }

    public static GameData[] getPlayerProperties() {
        return new GameData[] { PLAYER_LEVEL, PLAYER_POINTS, PLAYER_SELECTEDSKILL, PLAYER_HEALTH };
    }

    public static GameData[] getUpgrades() {
        return new GameData[] { UPGRADE_MOVEMENT, UPGRADE_POWERRATE, UPGRADE_SHOCKWAVE,
                UPGRADE_DOUBLEXP, UPGRADE_SHIELDSPAWN };
    }

    public static void load() {
        try {
            read(SER_FILE);
        } catch (Exception e) {
            try {
                write(SER_FILE);
            } catch (Exception f) {
                f.printStackTrace();
            }
        }
    }

    public static void save() {
        try {
            write(SER_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void read(String file) throws ClassNotFoundException, IOException {
        FileInputStream fiStream = new FileInputStream(new File(file));
        ObjectInputStream oiStream = new ObjectInputStream(fiStream);

        for (int i = 0; i < GameData.values().length; i++) {
            int reading = oiStream.readInt();
            if (reading < GameData.values()[i].min) {
                reading = GameData.values()[i].min;
            } else if (reading > GameData.values()[i].max) {
                reading = GameData.values()[i].max;
            }

            GameData.values()[i].value = reading;
        }

        oiStream.close();
        fiStream.close();
    }

    private static void write(String file) throws IOException {
        FileOutputStream foStream = new FileOutputStream(new File(file), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < GameData.values().length; i++) {
            ooStream.writeInt(GameData.values()[i].value);
        }

        ooStream.close();
        foStream.close();
    }

    private GameData(int defaultValue, int max, String name) {
        this.value = defaultValue;
        this.max = max;
        this.min = defaultValue;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getMax() {
        return this.max;
    }

    public int getMin() {
        return this.min;
    }

    public int getValue() {
        return this.value;
    }

    public void increment() {
        if (this.value < this.max) {
            this.value++;
        }
    }

    public void incrementBy(int value) {
        if (this.value + value > this.max) {
            this.value = this.max;
        } else if (this.value + value < this.min) {
            this.value = this.min;
        } else {
            this.value += value;
        }
    }

    public void setValue(int value) {
        if (value > this.max) {
            this.value = this.max;
        } else if (value < this.min) {
            this.value = this.min;
        } else {
            this.value = value;
        }
    }

}
