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

public enum PlayData {

    /*
     * Definitions
     */
    STAT_COUNT_EXPERIENCE(0, Integer.MAX_VALUE),
    STAT_COUNT_DAMAGE(0, Integer.MAX_VALUE),
    STAT_COUNT_SHIELD(0, Integer.MAX_VALUE),
    STAT_COUNT_NODES(0, Integer.MAX_VALUE),
    STAT_COUNT_JUMP(0, Integer.MAX_VALUE),
    STAT_COUNT_SKILLACTIVATION(0, Integer.MAX_VALUE),

    PLAYER_LEVEL(1, Integer.MAX_VALUE),
    PLAYER_POINTS(0, Integer.MAX_VALUE),
    PLAYER_SELECTEDSKILL(0, 3),
    PLAYER_HEALTH(3, 10),

    UPGRADE_MOVEMENT(0, 1),
    UPGRADE_POWERRATE(1, 2),
    UPGRADE_SHOCKWAVE(0, 1),
    UPGRADE_DOUBLEXP(0, 1),
    UPGRADE_SHIELDSPAWN(0, 1);

    private static final String SER_FILE = "playdata.ser";

    /*
     * Instance final variables
     */
    private final int min, max;

    /*
     * Instace variables
     */
    private int value;

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

    /*
     * Static functions
     */
    private static void read(String file) throws ClassNotFoundException, IOException {
        FileInputStream fiStream = new FileInputStream(new File(file));
        ObjectInputStream oiStream = new ObjectInputStream(fiStream);

        for (int i = 0; i < PlayData.values().length; i++) {
            PlayData.values()[i].value = oiStream.readInt();
        }

        oiStream.close();
        fiStream.close();
    }

    private static void write(String file) throws IOException {
        FileOutputStream foStream = new FileOutputStream(new File(file), false);
        ObjectOutputStream ooStream = new ObjectOutputStream(foStream);
        ooStream.reset();

        for (int i = 0; i < PlayData.values().length; i++) {
            ooStream.writeInt(PlayData.values()[i].value);
        }

        ooStream.close();
        foStream.close();
    }

    /*
     * Constructors
     */
    private PlayData(int defaultValue, int max) {
        this.value = defaultValue;
        this.max = max;
        this.min = defaultValue;
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
