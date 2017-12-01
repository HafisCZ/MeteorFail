package com.hiraishin.undefined._entity.mob;

import com.hiraishin.undefined._entity.Entity;
import com.hiraishin.undefined._entity.Lifetime;
import com.hiraishin.undefined._entity.collision.CollisionBox;
import com.hiraishin.undefined._entity.controller.Controlled;
import com.hiraishin.undefined._essentials.Upgrades;
import com.hiraishin.undefined._essentials.upgrade.Upgrade;
import com.hiraishin.undefined._input.InputEventAdapter;
import com.hiraishin.undefined._util.Dimensions;
import com.hiraishin.undefined._util.PortView;
import com.hiraishin.undefined._util.resource.ResourceLoader;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Player extends Entity implements Controlled {

	public static final CollisionBox DEFAULT_COLLISION_BOX = new CollisionBox(6, 0, 60, 80);

	private final IntegerProperty healthProperty;

	private final Timeline C1A_timeline;

	private double horizontalSpeed, verticalSpeed, verticalJumpSpeed;
	private boolean onGround, moving;

	public Player(double initialX, double initialY) {
		super(initialX, initialY, new PortView(ResourceLoader.INSTANCE.getTexture("player"), 66, 80, 2, 4),
				DEFAULT_COLLISION_BOX);

		healthProperty = new SimpleIntegerProperty(1 + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_MAX_HEALTH));

		horizontalSpeed = ENTITY_SPEED_HORIZONTAL + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_HORIZONTAL_SPEED);
		verticalJumpSpeed = 2.5 * -ENTITY_SPEED_HORIZONTAL + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_VERTICAL_SPEED);

		C1A_timeline = new Timeline();
		C1A_timeline.setCycleCount(Timeline.INDEFINITE);
		C1A_timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(000), e -> view.setPort(1, 0)),
				new KeyFrame(Duration.millis(100), e -> view.setPort(1, 1)),
				new KeyFrame(Duration.millis(200), e -> view.setPort(1, 2)),
				new KeyFrame(Duration.millis(300), e -> view.setPort(1, 3)));
		C1A_timeline.setOnFinished(e -> view.setPort(0, 0));

		xProperty.addListener((O, Prev, Next) -> {
			if (Next.doubleValue() > Prev.doubleValue()) {
				view.setScaleX(1);
				moving = true;
				C1A_timeline.play();
			} else if (Prev.doubleValue() > Next.doubleValue()) {
				view.setScaleX(-1);
				moving = true;
				C1A_timeline.play();
			}

			if (Next.doubleValue() < Dimensions.ZERO) {
				xProperty.set(Dimensions.ZERO);
			} else if (Next.doubleValue() + getWidth() > Dimensions.SCREEN_WIDTH) {
				xProperty.set(Dimensions.SCREEN_WIDTH - getWidth());
			}
		});

		yProperty.addListener((O, Prev, Next) -> {
			if (Next.doubleValue() < Dimensions.ZERO) {
				yProperty.set(Dimensions.ZERO);
			} else if (Next.doubleValue() + getHeight() > Dimensions.GROUND_LEVEL) {
				yProperty.set(Dimensions.GROUND_LEVEL - getHeight());
				onGround = true;
			}
		});
		
		onGround = true;

		status = Lifetime.C1;
	}

	public IntegerProperty healthProperty() {
		return healthProperty;
	}

	public int getHealth() {
		return healthProperty.get();
	}

	public void damage(int healthReduction) {
		healthProperty.set(healthProperty.get() - healthReduction);
	}

	@Override
	public void tick() {
		switch (status) {
		case C0: {

			return;
		}
		case C1: {
			if (onGround) {
				verticalSpeed = 0;
			} else {
				move(0, verticalSpeed);
				verticalSpeed += ENTITY_SPEED_VERTICAL / 24;
			}

			if (moving) {
				moving = false;
			} else {
				C1A_timeline.stop();
				view.setPort(0, 0);
			}
			return;
		}
		case C2: {
			C1A_timeline.stop();
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

	@Override
	public void control(InputEventAdapter adapter) {
		if (adapter.isHeld(KeyCode.LEFT)) {
			move(-horizontalSpeed, 0);
		} else if (adapter.isHeld(KeyCode.RIGHT)) {
			move(horizontalSpeed, 0);
		}

		if (adapter.isHeld(KeyCode.UP)) {
			if (onGround) {
				onGround = false;
				verticalSpeed = verticalJumpSpeed;
			}
		}
	}

}
