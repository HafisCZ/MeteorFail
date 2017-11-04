package mar21.resources;

public class SheetView extends javafx.scene.image.ImageView {
	
	private int cols, rows;
	private double ow, oh, iw, ih;
	
	public SheetView(SheetViewBuilder b) {
		this(b.res, b.rw, b.rh, b.rows, b.cols);
	}
	
	public SheetView(String res, double rw, double rh, int rows, int cols) {
		super(ResourceLoader.getTexture(res));
		
		this.rows = rows;
		this.cols = cols;
		
		this.ow = this.getImage().getWidth();
		this.oh = this.getImage().getHeight();
		
		this.iw = ow / cols;
		this.ih = oh / rows;
		
		this.setFitWidth(rw);
		this.setFitHeight(rh);
		
		show(0, 0);
	}
	
	public void show(int row, int col) {
		if (row >= rows || col >= cols) {
			throw new IllegalArgumentException("Out of bounds.");
		}
		
		setViewport(new javafx.geometry.Rectangle2D(col * iw, row * ih, iw, ih));
	}
	
	public static class SheetViewBuilder {
		
		private final String res;
		private double rw, rh;
		private int cols = 1, rows = 1;
		
		public SheetViewBuilder(String res) {
			this.res = res;
		}
		
		public SheetViewBuilder setDimensions(double w, double h) {
			rw = w;
			rh = h;
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
