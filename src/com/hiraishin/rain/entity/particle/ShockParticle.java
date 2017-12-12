/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.entity.particle;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.entity.mob.Acid;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class ShockParticle extends Particle {

    /*
     * Definitions
     */
    public static final double ARC_RADIUS_INCREMENT = 8;
    public static final double ARC_ANGLE = 180;

    /*
     * Instance variables
     */
    private double radius = 0;

    /*
     * Constructors
     */
    public ShockParticle(double x, double y, double width, double height, Level level) {
        super(x, y, width, height, level);
    }

    /*
     * Instance functions
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.ALICEBLUE);
        gc.strokeArc(this.x - this.radius, this.y - this.radius, this.radius * 2, this.radius *
                2, 0, ARC_ANGLE, ArcType.OPEN);
    }

    @Override
    public void tick() {
        this.radius += ARC_RADIUS_INCREMENT;
        if (this.radius > Commons.SCENE_WIDTH) {
            kill();
        }

        for (Entity m : this.level.getMobs()) {
            if (m instanceof Acid) {
                if (m.getDistance(this.x, this.y) <= this.radius) {
                    m.kill();
                }
            }
        }
    }

}
