package mar21.entity.items;

import java.util.Random;

import mar21.entity.FallingEntity;
import mar21.resources.ShatteredImageView;

public final class Meteor extends FallingEntity {
	
	private static final Random RANDOM = new Random();

	public Meteor(double x, double y) {
		super(x, y, new ShatteredImageView("meteor0", 40, 80, 2, 1));
		
		if (RANDOM.nextBoolean()) {
			view.shatter(1, 0);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleX(-1);
		}
		
		if (RANDOM.nextBoolean()) {
			view.setScaleY(-1);
		}
	}
}
