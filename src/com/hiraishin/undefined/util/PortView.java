package com.hiraishin.undefined.util;

import com.hiraishin.undefined.util.logger.Logger;
import com.hiraishin.undefined.util.logger.Severity;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PortView extends ImageView {

	private final int rows, cols;
	private final double imageWidth, imageHeight, portWidth, portHeight;

	public PortView(Image image, double fitWidth, double fitHeight, int rows, int cols) {
		setImage(image);
		setFitWidth(fitWidth);
		setFitHeight(fitHeight);

		this.rows = rows;
		this.cols = cols;
		
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();

		portWidth = imageWidth / cols;
		portHeight = imageHeight / rows;

		setViewport(new Rectangle2D(0, 0, portWidth, portHeight));
	}

	public void setPort(int r, int c) {
		if (r < rows && c < cols) {
			setViewport(new Rectangle2D(c * portWidth, r * portHeight, portWidth, portHeight));
		} else {
			Logger.INSTANCE.log(Severity.ERROR, "Port selector out of bounds.");
		}
	}

}
