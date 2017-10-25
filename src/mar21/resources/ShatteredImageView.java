package mar21.resources;

public class ShatteredImageView extends javafx.scene.image.ImageView {
	
	private double cols, rows, width, height;
	
	public ShatteredImageView(String resource, double width, double height, int rows, int cols) {
		super(ResourceManager.requestInstance().getResource(resource));
		this.rows = rows;
		this.cols = cols;
		this.width = width / cols;
		this.height = height / rows;
		
		this.setFitWidth(this.width);
		this.setFitHeight(this.height);
		shatter(0, 0);
	}
	
	public ShatteredImageView(String resource, double width, double height) {
		this(resource, width, height, 1, 1);
	}

	public void shatter(int row, int col) {
		if (row >= rows || col >= cols) {
			throw new IllegalArgumentException("Out of bounds.");
		}
		
		setViewport(new javafx.geometry.Rectangle2D(col * width, row * height, width, height));
	}
}
