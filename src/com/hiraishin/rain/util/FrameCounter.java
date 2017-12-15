/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.util;

public class FrameCounter {

    private static final int SAMPLE_SIZE = 100;

    private final double frameRates[] = new double[SAMPLE_SIZE];

    private int frameIndex = 0;
    private long lastFrameTime = 0;

    public void sample(long nano) {
        if (this.lastFrameTime > 0) {
            final long nanoTimeElapsed = nano - this.lastFrameTime;
            double frameRate = 1_000_000_000D / nanoTimeElapsed;

            this.frameIndex %= this.frameRates.length;
            this.frameRates[this.frameIndex++] = frameRate;
        }

        this.lastFrameTime = nano;
    }

    public double getInstantFPS() {
        return this.frameRates[this.frameIndex % this.frameRates.length];
    }

    public double getAverageFPS() {
        double frames = 0;
        for (int i = 0; i < this.frameRates.length; i++) {
            frames += this.frameRates[i];
        }

        return frames / this.frameRates.length;
    }
}
