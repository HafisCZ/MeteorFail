/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.graphics.animation;

import com.hiraishin.rain.graphics.Sprite;

import javafx.scene.image.Image;

public class AnimatedSprite extends Sprite {

    /*
     * Instance final variables
     */
    private final Step beginFrame;
    private final Step animatedFrames[];

    private final int frameTime;

    /*
     * Instance variables
     */
    private int selectedFrame = 0;
    private int currentTime = 0;

    private boolean isPlaying = false;

    /*
     * Constructors
     */
    public AnimatedSprite(Image image, int rows, int columns, int frameTime, Step... frames) {
        super(image, rows, columns);

        if (frames.length < 1) {
            throw new IllegalArgumentException();
        }

        this.frameTime = frameTime;
        this.beginFrame = frames[0];
        this.animatedFrames = new Step[frames.length - 1];

        for (int i = 0; i < this.animatedFrames.length;) {
            this.animatedFrames[i] = frames[++i];
        }
    }

    /*
     * Instance functions
     */
    public void play() {
        if (this.isPlaying) {
            return;
        }

        this.isPlaying = true;

        this.selectedFrame = 0;
        this.currentTime = 0;

        select(this.animatedFrames[this.selectedFrame].row, this.animatedFrames[this.selectedFrame].col);
    }

    public void stop() {
        if (!this.isPlaying) {
            return;
        }

        this.isPlaying = false;
        select(this.beginFrame.row, this.beginFrame.col);
    }

    public void tick() {
        if (!this.isPlaying) {
            return;
        }

        if (++this.currentTime >= this.frameTime) {
            if (++this.selectedFrame >= this.animatedFrames.length) {
                this.selectedFrame = 0;
            }

            this.currentTime = 0;
            select(this.animatedFrames[this.selectedFrame].row, this.animatedFrames[this.selectedFrame].col);
        }
    }

}
