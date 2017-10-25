package mar21.entity.items;

import mar21.entity.FallingEntity;
import mar21.resources.ShatteredImageView;

public final class Meteor extends FallingEntity {

	public Meteor(double x, double y) {
		super(x, y, new ShatteredImageView("meteor0", 30, 30));
	}
}
