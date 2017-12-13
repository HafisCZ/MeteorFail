/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.mob;

import java.util.Objects;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;

public abstract class Mob extends Entity {

    protected Mob(double x, double y, double width, double height, Sprite sprite, double offsetX,
                  double offsetY, Level level) {
        super(x, y, width, height, Objects.requireNonNull(sprite), offsetX, offsetY, level);
    }

}
