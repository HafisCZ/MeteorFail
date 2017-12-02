package com.hiraishin.undefined.entity.mob;

import com.hiraishin.undefined.entity.Entity;
import com.hiraishin.undefined.entity.Lifetime;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.game.Upgrade;
import com.hiraishin.undefined.game.Upgrades;
import com.hiraishin.undefined.util.PortView;
import com.hiraishin.undefined.util.resource.ResourceLoader;

public class Enemy extends Entity {

	public static final CollisionBox DEFAULT_COLLISION_BOX = new CollisionBox(6, 6, 30, 30);

	protected Enemy(double initialX, double initialY) {
		super(initialX, initialY, new PortView(ResourceLoader.INSTANCE.getTexture("meteor0"), 40, 40, 2, 1),
				DEFAULT_COLLISION_BOX);

		if (RANDOM.nextBoolean()) {
			view.setPort(1, 0);
		}

		if (RANDOM.nextBoolean()) {
			view.setScaleX(-1);
		}

		if (RANDOM.nextBoolean()) {
			view.setScaleY(-1);
		}

		status = Lifetime.C1;
	}

	@Override
	public void tick() {
		switch (status) {
		case C0: {

			return;
		}
		case C1: {
			move(0, ENTITY_SPEED_VERTICAL / 3 - Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_VERTICAL_SPEED_INCREMENTAL));
			return;
		}
		case C2: {
			advanceStatus();
			return;
		}
		case C3: {
			advanceStatus();
			return;
		}
		case C4: {

			return;
		}
		}
	}

}
