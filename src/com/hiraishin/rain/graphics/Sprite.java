package com.hiraishin.rain.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {

	/**
	 * Source image
	 */
	private final Image image;

	/**
	 * Row and column count in sheet
	 */
	private final int rows;
	private final int cols;

	/**
	 * Sprite dimensions
	 */
	private final double width;
	private final double height;

	/**
	 * Selected subimage from image
	 */
	private int selRow = 0;
	private int selCol = 0;

	/**
	 * Transformations
	 */
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

	public void draw(GraphicsContext gc, double x, double y) {
		draw(gc, (int) x, (int) y);
	}

	private void draw(GraphicsContext gc, int x, int y) {
		gc.drawImage(image, selCol * (int) width, selRow * (int) height, (int) width, (int) height, x, y,
				(flipX ? -1 : 1) * (int) width, (flipY ? -1 : 1) * (int) height);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
