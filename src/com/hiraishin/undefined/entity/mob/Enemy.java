package com.hiraishin.undefined.entity.mob;

import java.util.Random;

import com.hiraishin.undefined.entity.Mob;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.sheetview.SheetView;

public final class Enemy extends Mob {
	
	private static final Random RANDOM = new Random();
	
	public static final CollisionBox DEFAULT_COLLISION_BOX_ENEMY = new CollisionBox(6, 6, 34, 34, 40, 40);

	public Enemy(double x, double y) {
		super(x, y, new SheetView("meteor0", 40, 40, 2, 1), DEFAULT_COLLISION_BOX_ENEMY);
		
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
	
}
