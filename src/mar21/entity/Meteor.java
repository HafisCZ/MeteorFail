package mar21.entity;

import java.util.Random;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import mar21.resources.SheetView;

public final class Meteor extends FallingEntity {
	
	private static final Random RANDOM = new Random();

	public Meteor(double x, double y) {
		super(x, y, new SheetView("meteor0", 40, 40, 2, 1));
		
		if (RANDOM.nextBoolean()) {
			view.show(1, 0);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleX(-1);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleY(-1);
		}
	}
	
	@Override
	public Bounds getBounds() {
		return  new BoundingBox(getX() + 6, getY() + 6, getWidth() - 12, getHeight() - 12);
	}
	
}
