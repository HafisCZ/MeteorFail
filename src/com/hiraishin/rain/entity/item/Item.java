/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.item;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

public abstract class Item extends Entity {

    /*
     * Definitions
     */
    public static final double DEFAULT_SPEED_X = 0;
    public static final double DEFAULT_SPEED_Y = 4;

    protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX,
                   double offsetY, double dx, double dy, Level level) {
        super(x, y, width, height, sprite, offsetX, offsetY, level);

        this.dx = dx;
        this.dy = dy;
    }

    /*
     * Contructors
     */
    protected Item(double x, double y, double width, double height, Sprite sprite, double offsetX,
                   double offsetY, Level level) {
        this(x, y, width, height, sprite, offsetX, offsetY, DEFAULT_SPEED_X, DEFAULT_SPEED_Y,
                level);
    }

    /*
     * Abstract functions
     */
    public abstract void applyEffect();

    /*
     * Instance functions
     */
    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height > Commons.SCENE_GROUND) {
            kill();
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            applyEffect();
            kill();
        }
    }

}
