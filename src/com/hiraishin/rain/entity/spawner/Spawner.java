/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.spawner;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Spawner extends Entity {

    /*
     * Instance final variables
     */
    protected final int rate;
    protected final int variation;
    protected final int count;

    /*
     * Instance variables
     */
    protected int frameCount = 0;
    protected int frameLimit;

    /*
     * Constructors
     */
    protected Spawner(double x, double y, double width, double height, Level level, int rate,
                      int variation, int count) {
        super(x, y, width, height, level);

        this.count = count;

        this.variation = variation;
        this.rate = rate;

        this.frameLimit = rate;
    }

    /*
     * Abstract functions
     */
    public abstract void spawn();

    /*
     * Instance functions
     */
    @Override
    public final void tick() {
        if (this.frameCount++ >= this.frameLimit) {
            this.frameCount = 0;
            this.frameLimit = this.rate +
                    (this.variation > 1 ? Commons.RANDOM.nextInt(this.variation) : 0);

            if (Objects.nonNull(this.level)) {
                for (int i = 0; i < this.count; i++) {
                    spawn();
                }
            }
        }
    }

    /*
     * Getters & Setters
     */
    protected double getRandomX() {
        return this.x + (this.width == 0 ? 0 : Commons.RANDOM.nextInt((int) this.width));
    }

    protected double getRandomY() {
        return this.y + (this.height == 0 ? 0 : Commons.RANDOM.nextInt((int) this.height));
    }

}
