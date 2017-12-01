package com.hiraishin.undefined._entity.mob;

import com.hiraishin.undefined._entity.Entity;
import com.hiraishin.undefined._entity.Lifetime;
import com.hiraishin.undefined._entity.collision.CollisionBox;
import com.hiraishin.undefined._essentials.Upgrades;
import com.hiraishin.undefined._essentials.upgrade.Upgrade;
import com.hiraishin.undefined._util.PortView;
import com.hiraishin.undefined._util.resource.ResourceLoader;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Item extends Entity {

	public static final CollisionBox DEFAULT_COLLISION_BOX = new CollisionBox(0, 0, 30, 30);

	private final Timeline C1_timeline;

	public Item(double initialX, double initialY) {
		super(initialX, initialY, new PortView(ResourceLoader.INSTANCE.getTexture("coin"), 30, 30, 1, 5),
				DEFAULT_COLLISION_BOX);
		C1_timeline = new Timeline();
		C1_timeline.setCycleCount(Timeline.INDEFINITE);
		C1_timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(80), e -> {
			view.setScaleX(1);
			view.setPort(0, 0);
		}), new KeyFrame(Duration.millis(160), e -> view.setPort(0, 1)),
				new KeyFrame(Duration.millis(240), e -> view.setPort(0, 2)),
				new KeyFrame(Duration.millis(320), e -> view.setPort(0, 3)),
				new KeyFrame(Duration.millis(400), e -> view.setPort(0, 4)), new KeyFrame(Duration.millis(480), e -> {
					view.setScaleX(-1);
					view.setPort(0, 3);
				}), new KeyFrame(Duration.millis(560), e -> view.setPort(0, 2)),
				new KeyFrame(Duration.millis(640), e -> view.setPort(0, 1)));
		C1_timeline.play();

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
			C1_timeline.stop();
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
