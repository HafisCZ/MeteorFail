/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    /*
     * Instance final variables
     */
    private final Image image;

    private final int rows;
    private final int cols;

    private final double width;
    private final double height;

    /*
     * Instance variables
     */
    private int selRow = 0;
    private int selCol = 0;

    private int strRow = 1;
    private int strCol = 1;

    private boolean flipX = false;
    private boolean flipY = false;

    /*
     * Constructors
     */
    public Sprite(Image image, int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException();
        }

        this.image = image;
        this.rows = rows;
        this.cols = columns;

        this.width = image.getWidth() / columns;
        this.height = image.getHeight() / rows;
    }

    public void draw(GraphicsContext gc, double x, double y) {
        if (this.strRow > 0 && this.strCol > 0) {
            gc.drawImage(this.image, this.selCol * this.width, this.selRow *
                    this.height, this.strCol * this.width, this.strRow * this.height, x +
                            (this.flipX ? this.width * this.strCol : 0), y +
                                    (this.flipY ? this.height * this.strRow : 0), (this.flipX ? -1 :
                                            1) * this.width * this.strCol, (this.flipY ? -1 : 1) *
                                                    this.height * this.strRow);
        }
    }

    /*
     * Instance functions
     */
    public void flip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public double getHeight() {
        return this.height;
    }

    /*
     * Getters & Setters
     */
    public double getWidth() {
        return this.width;
    }

    public void select(int row, int col) {
        if (row < this.rows && col < this.cols) {
            this.selRow = row;
            this.selCol = col;
        }
    }

    public void stretch(int rows, int cols) {
        if (rows >= 0 && rows <= this.rows && cols >= 0 && cols <= this.cols) {
            this.strRow = rows;
            this.strCol = cols;
        }
    }
}
