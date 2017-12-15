/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.level.player;

import com.hiraishin.rain.entity.particle.ShockParticle;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.QuadConsumer;

public enum Skill {

    SHOCKWAVE((X, Y, L, PP) -> L.add(new ShockParticle(X, Commons.SCENE_GROUND, 0, 0, L)), 2 *
            60, 5),
    SHIELD_SPAWN((X, Y, L, PP) -> PP.addShield(), 30, 3),
    EXPERIENCE_BOOST((X, Y, L, PP) -> PP.setTemporaryExperienceBoost(2, 60 * 60), 60 * 60, 1);

    private final QuadConsumer<Double, Double, Level, PlayerProperties> effect;
    private final int burnoutTicks;
    private final int powerGain;

    private Skill(QuadConsumer<Double, Double, Level, PlayerProperties> consumer, int burnout,
                  int rate) {
        this.effect = consumer;
        this.burnoutTicks = burnout;
        this.powerGain = rate;
    }

    public void applyEffect(double x, double y, Level level, PlayerProperties properties) {
        effect.accept(x, y, level, properties);
    }

    public int getBurnoutTime() {
        return burnoutTicks;
    }

    public int getPowerGain() {
        return powerGain;
    }

}
