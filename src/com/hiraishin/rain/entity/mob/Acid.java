/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.mob;

import com.hiraishin.rain.entity.particle.AcidParticle;
import com.hiraishin.rain.graphics.animation.AnimatedSprite;
import com.hiraishin.rain.graphics.animation.Step;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImageLoader;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class Acid extends Mob {

    public static final double WIDTH = 10;
    public static final double HEIGHT = 10;
    public static final double SPEED_X_DEFAULT = 0;
    public static final double SPEED_Y_DEFAULT = 10;
    public static final Image IMAGE = ImageLoader.INTERNAL.getImage("entity/acid");
    public static final int IMAGE_ROWS = 1;
    public static final int IMAGE_COLS = 4;
    public static final double SPRITE_X_OFFSET = -1;
    public static final double SPRITE_Y_OFFSET = -14;
    public static final int ANIMATION_DELTA = 10;
    public static final Step ANIMATION_STEPS[] = { new Step(0, 0), new Step(0, 1), new Step(0, 2),
            new Step(0, 3), new Step(0, 0) };
    public static final int PARTICLE_COUNT = 5;

    public Acid(double x, double y, double dx, double dy, Level level) {
        super(x, y, WIDTH, HEIGHT,
                new AnimatedSprite(IMAGE, IMAGE_ROWS, IMAGE_COLS, ANIMATION_DELTA, ANIMATION_STEPS),
                SPRITE_X_OFFSET, SPRITE_Y_OFFSET, level);
        ((AnimatedSprite) this.sprite).play();

        this.dx = dx;
        this.dy = dy;
    }

    public Acid(double x, double y, Level level) {
        this(x, y, SPEED_X_DEFAULT, SPEED_Y_DEFAULT, level);
    }

    @Override
    public final void tick() {
        this.x += this.dx;
        this.y += this.dy;

        if (this.y + this.height >= Commons.SCENE_GROUND) {
            this.y = Commons.SCENE_GROUND - this.height;

            kill();
            spawnParticles(PARTICLE_COUNT, 0);
        }

        if (this.level.isCollidingPlayerAABB(this)) {
            this.level.getPlayerProperties().damage();

            this.y -= this.height;

            if (this.level.getPlayerProperties().getHealth() > 0) {
                spawnParticles(PARTICLE_COUNT, -1);
            }

            kill();
        }

        ((AnimatedSprite) this.sprite).tick();
    }

    protected void spawnParticles(int amount, double ySpeed) {
        Platform.runLater(() -> {
            for (int i = 0; i < amount; i++) {
                double particleSize = Commons.RANDOM.nextInt(5) + 1;
                double particleXSpeed = Commons.RANDOM.nextInt(5) - 2.5;
                this.level.add(new AcidParticle(this.x, this.y + this.height - particleSize,
                                                particleSize, particleSize, particleXSpeed, ySpeed,
                                                this.level));
            }
        });
    }

}
