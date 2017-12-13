/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class AcidParticle extends Particle {

    public static final double SPEED_X_INCREMENT = 0.2;
    public static final double SPEED_Y_INCREMENT = 0.5;
    public static final int TICKS_DESPAWN_MIN = 10;
    public static final int TICKS_DESPAWN_MAX = 30;

    private final Color color;

    private int despawnTicks = TICKS_DESPAWN_MIN +
            Commons.RANDOM.nextInt(TICKS_DESPAWN_MAX - TICKS_DESPAWN_MIN + 1);
    private boolean despawnActive = false;

    public AcidParticle(double x, double y, double width, double height, double dx, double dy,
                        Level level) {
        super(x, y, width, height, level);

        this.dx = dx;
        this.dy = dy;

        this.color = Commons.RANDOM.nextBoolean() ? Color.GREENYELLOW : Color.LAWNGREEN;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(this.x, this.y, this.width, this.height);
    }

    @Override
    public void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.dx > 0) {
            this.dx = Math.max(0, this.dx - SPEED_X_INCREMENT);
        } else if (this.dx < 0) {
            this.dx = Math.min(0, this.dx + SPEED_X_INCREMENT);
        }

        if (this.x < Commons.ZERO) {
            this.x = 0;
            this.dx *= -1;
        } else if (this.x + this.width > Commons.SCENE_WIDTH) {
            this.x = Commons.SCENE_WIDTH - this.width;
            this.dx *= -1;
        }

        if (this.y < Commons.ZERO) {
            this.y = 0;
        } else if (this.y + this.height > Commons.SCENE_GROUND) {
            this.y = Commons.SCENE_GROUND - this.height;
            this.despawnActive = true;
            this.dy = 0;
        } else {
            this.dy += SPEED_Y_INCREMENT;
        }

        if (this.despawnActive) {
            this.despawnTicks--;

            if (this.despawnTicks <= 0) {
                kill();
            }
        }
    }

}
