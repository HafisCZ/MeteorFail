package mar21.entity;

import java.util.Random;

import mar21.entity.collision.CollisionBox;
import mar21.resources.SheetView;

public final class Meteor extends FallingEntity {
	
	private static final Random RANDOM = new Random();

	public Meteor(double x, double y) {
		super(x, y, new SheetView("meteor0", 40, 40, 2, 1), null);
		
		if (RANDOM.nextBoolean()) {
			view.show(1, 0);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleX(-1);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleY(-1);
		}
		
		collisionBox = new CollisionBox(this, 6, 6, 34, 34);
	}
	
}
