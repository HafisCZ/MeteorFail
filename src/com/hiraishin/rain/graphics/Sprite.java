/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

    private final int rowCount;
    private final int colCount;
    private final double tileWidth;
    private final double tileHeight;
    private final Image image;

    private int selectedRow = 0;
    private int selectedCol = 0;

    private int rowSize = 1;
    private int colSize = 1;

    private double xScale = 1;
    private double yScale = 1;

    public Sprite(Image image, int rowCount, int colCount) {
        if (rowCount < 1 || colCount < 1) {
            throw new IllegalArgumentException("Sprite has to have at least 1 row and 1 column!");
        }

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.tileWidth = image.getWidth() / colCount;
        this.tileHeight = image.getHeight() / rowCount;

    }

    public void draw(GraphicsContext gc, double x, double y) {
        if (this.rowSize > 0 && this.colSize > 0) {
            final double sx = this.selectedCol * this.tileWidth;
            final double sy = this.selectedRow * this.tileHeight;
            final double sw = this.colSize * this.tileWidth;
            final double sh = this.rowSize * this.tileHeight;

            final double dx = x + (this.xScale < 0 ? sw : 0);
            final double dy = y + (this.yScale < 0 ? sh : 0);
            final double dw = sw * this.xScale;
            final double dh = sh * this.yScale;

            gc.drawImage(this.image, sx, sy, sw, sh, dx, dy, dw, dh);
        }
    }

    public void setScale(double xScale, double yScale) {
        if (xScale != 0 && yScale != 0) {
            this.xScale = xScale;
            this.yScale = yScale;
        }
    }

    public double getTileWidth() {
        return this.tileWidth;
    }

    public double getTileHeight() {
        return this.tileHeight;
    }

    public void selectTile(int row, int col) {
        if (row >= 0 && row < this.rowCount && col >= 0 && col < this.colCount) {
            this.selectedRow = row;
            this.selectedCol = col;
        }
    }

    public void setTileSpan(int rows, int cols) {
        this.rowSize = rows;
        this.colSize = cols;
    }
}
