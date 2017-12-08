package com.hiraishin.rain.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

	private final Image image;

	private final int rows;
	private final int cols;

	private final double width;
	private final double height;

	private int selRow = 0;
	private int selCol = 0;

	private int strRow = 1;
	private int strCol = 1;

	private boolean flipX = false;
	private boolean flipY = false;

	public Sprite(Image image) {
		this(image, 1, 1);
	}

	public Sprite(Image image, int size) {
		this(image, size, size);
	}

	public Sprite(Image image, int rows, int columns) {
		this.image = image;
		this.rows = rows;
		this.cols = columns;

		width = image.getWidth() / columns;
		height = image.getHeight() / rows;
	}

	public void setFlip(boolean flipX, boolean flipY) {
		this.flipX = flipX;
		this.flipY = flipY;
	}

	public void select(int row, int col) {
		if (row < rows && col < cols) {
			selRow = row;
			selCol = col;
		}
	}

	public void stretch(int rows, int cols) {
		if (rows >= 0 && rows <= this.rows && cols >= 0 && cols <= this.cols) {
			strRow = rows;
			strCol = cols;
		}
	}

	public void draw(GraphicsContext gc, double x, double y) {
		if (strRow > 0 && strCol > 0) {
			gc.drawImage(
				image,
				selCol * width, 
				selRow * height, 
				strCol * width, 
				strRow * height, 
				x + (flipX ? width * strCol : 0), 
				y + (flipY ? height * strRow : 0), 
				(flipX ? -1 : 1) * width * strCol,
				(flipY ? -1 : 1) * height * strRow);
		}
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
