/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    private final int rows;
    private final int cols;
    private final double width;
    private final double height;
    private final Image image;

    private int selRow = 0;
    private int selCol = 0;
    private int strRow = 1;
    private int strCol = 1;
    private double scaleX = 1;
    private double scaleY = 1;
    private boolean flipX = false;
    private boolean flipY = false;

    public Sprite(Image image, int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Sprite has to have at least 1 row and 1 column.\nYou specified " +
                    rows + " rows and " + columns + " columns!");
        }

        this.image = image;
        this.rows = rows;
        this.cols = columns;

        this.width = image.getWidth() / columns;
        this.height = image.getHeight() / rows;
    }

    public void draw(GraphicsContext gc, double x, double y) {
        if (this.strRow > 0 && this.strCol > 0) {
            final double sx = this.selCol * this.width;
            final double sy = this.selRow * this.height;
            final double sw = this.strCol * this.width;
            final double sh = this.strRow * this.height;

            final double dx = x + (this.flipX ? this.width * this.strCol : 0);
            final double dy = y + (this.flipY ? this.height * this.strRow : 0);
            final double dw = this.strCol * this.scaleX * (this.flipX ? -1 : 1) * this.width;
            final double dh = this.strRow * this.scaleY * (this.flipY ? -1 : 1) * this.height;

            gc.drawImage(this.image, sx, sy, sw, sh, dx, dy, dw, dh);
        }
    }

    public void setScale(double sx, double sy) {
        this.scaleX = sx;
        this.scaleY = sy;
    }

    public void setFlipAxis(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public double getHeight() {
        return this.height;
    }

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
