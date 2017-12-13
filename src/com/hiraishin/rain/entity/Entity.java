/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity;

import java.util.Objects;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Drawable {

    protected final Level level;
    protected final Sprite sprite;
    protected final double width;
    protected final double height;
    protected final double spriteXOffset;
    protected final double spriteYOffset;

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected boolean dead;

    protected Entity(double x, double y, double width, double height, Level level) {
        this(x, y, width, height, null, 0, 0, level);
    }

    protected Entity(double x, double y, double width, double height, Sprite sprite, double offsetX,
                     double offsetY, Level level) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        this.sprite = sprite;
        this.spriteXOffset = offsetX;
        this.spriteYOffset = offsetY;

        this.level = level;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (Objects.nonNull(this.sprite)) {
            this.sprite.draw(gc, this.x + this.spriteXOffset, this.y + this.spriteYOffset);
        }
    }

    public final double getCenterX() {
        return this.x + this.width / 2;
    }

    public final double getCenterY() {
        return this.y + this.height / 2;
    }

    public final double getDistance(double x, double y) {
        return Math.sqrt(Math.pow(x - getCenterX(), 2) + Math.pow(y - getCenterY(), 2));
    }

    public final double getHeight() {
        return this.height;
    }

    public final double getWidth() {
        return this.width;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final boolean isCollidingAABB(Entity entity) {
        final boolean a = entity.x + entity.width > this.x;
        final boolean b = this.x + this.width > entity.x;
        final boolean c = entity.y + entity.height > this.y;
        final boolean d = this.y + this.height > entity.y;
        return a && b && c && d;
    }

    public final boolean isDead() {
        return this.dead;
    }

    public final void kill() {
        this.dead = true;
    }

    public abstract void tick();

}
