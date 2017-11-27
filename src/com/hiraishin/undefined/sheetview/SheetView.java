package com.hiraishin.undefined.sheetview;

import com.hiraishin.undefined.utils.ResourceLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class SheetView extends javafx.scene.image.ImageView {
	
	private int cols, rows;
	private double sheetWidth, sheetHeight, textureWidth, textureHeight;

	public SheetView(SheetViewBuilder builder) {
		this(builder.id, builder.requestedWidth, builder.requestedHeight, builder.rows, builder.cols);
	}
	
	public SheetView(String id, double requestedWidth, double requestedHeight, int rows, int cols) {
		Image image = ResourceLoader.getTexture(id);
		setImage(image);
		setFitWidth(requestedWidth);
		setFitHeight(requestedHeight);
		
		this.rows = rows;
		this.cols = cols;
		
		sheetWidth = image.getWidth();
		sheetHeight = image.getHeight();
		
		textureWidth = sheetWidth / cols;
		textureHeight = sheetHeight / rows;
		
		setViewport(new Rectangle2D(0, 0, textureWidth, textureHeight));
	}
	
	public void show(int row, int col) {
		if (row >= rows || col >= cols) {
			throw new IllegalArgumentException();
		}
		
		setViewport(new Rectangle2D(col * textureWidth, row * textureHeight, textureWidth, textureHeight));
	}
	
	public static class SheetViewBuilder {
		
		private final String id;
		private double requestedWidth, requestedHeight;
		private int cols = 1, rows = 1;
		
		public SheetViewBuilder(String id) {
			this.id = id;
		}
		
		public SheetViewBuilder setDimensions(double width, double height) {
			requestedWidth = width;
			requestedHeight = height;
			return this;
		}
		
		public SheetViewBuilder setRows(int rows) {
			this.rows = rows;
			return this;
		}
		
		public SheetViewBuilder setColumns(int cols) {
			this.cols = cols;
			return this;
		}
		
		public SheetView build() {
			return new SheetView(this);
		}
	}
}
