package com.hiraishin.undefined.entity.mob;

import com.hiraishin.undefined.entity.Entity;
import com.hiraishin.undefined.entity.Lifetime;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.entity.controller.Controlled;
import com.hiraishin.undefined.game.Upgrade;
import com.hiraishin.undefined.game.Upgrades;
import com.hiraishin.undefined.input.InputEventAdapter;
import com.hiraishin.undefined.util.Commons;
import com.hiraishin.undefined.util.PortView;
import com.hiraishin.undefined.util.resource.ResourceLoader;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Player extends Entity implements Controlled {

	public static final CollisionBox DEFAULT_COLLISION_BOX = new CollisionBox(2, 0, 41, 60);

	private final IntegerProperty healthProperty;

	private final Timeline C1A_timeline;

	private double horizontalSpeed, verticalSpeed, verticalJumpSpeed, horizontalStepSpeed;
	private boolean onGround, moving;

	public Player(double initialX, double initialY) {
		super(initialX, initialY, new PortView(ResourceLoader.INSTANCE.getTexture("player"), 45, 60, 2, 4),
				DEFAULT_COLLISION_BOX);

		healthProperty = new SimpleIntegerProperty(1 + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_MAX_HEALTH));

		horizontalStepSpeed = (ENTITY_SPEED_HORIZONTAL + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_HORIZONTAL_SPEED))
				/ 5;
		verticalJumpSpeed = 2.5 * -ENTITY_SPEED_HORIZONTAL + Upgrades.INSTANCE.valueOf(Upgrade.PLAYER_VERTICAL_SPEED);

		C1A_timeline = new Timeline();
		C1A_timeline.setCycleCount(Timeline.INDEFINITE);
		C1A_timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(000), e -> view.setPort(1, 0)),
				new KeyFrame(Duration.millis(100), e -> view.setPort(1, 1)),
				new KeyFrame(Duration.millis(200), e -> view.setPort(1, 2)),
				new KeyFrame(Duration.millis(300), e -> view.setPort(1, 3)));
		C1A_timeline.setOnFinished(e -> view.setPort(0, 0));

		xProperty.addListener((O, Prev, Next) -> {
			if (Next.doubleValue() < Commons.ZERO) {
				xProperty.set(Commons.ZERO);
				horizontalSpeed = 0;
			} else if (Next.doubleValue() + collisionBox.getApproxWidth() > Commons.SCREEN_WIDTH) {
				xProperty.set(Commons.SCREEN_WIDTH - collisionBox.getApproxWidth());
				horizontalSpeed = 0;
			}

			if (Next.doubleValue() > Prev.doubleValue()) {
				view.setScaleX(1);
				moving = true;
				C1A_timeline.play();
			} else if (Prev.doubleValue() > Next.doubleValue()) {
				view.setScaleX(-1);
				moving = true;
				C1A_timeline.play();
			}
		});

		yProperty.addListener((O, Prev, Next) -> {
			if (Next.doubleValue() < Commons.ZERO) {
				yProperty.set(Commons.ZERO);
			} else if (Next.doubleValue() + getHeight() > Commons.GROUND_LEVEL) {
				yProperty.set(Commons.GROUND_LEVEL - getHeight());
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
		final boolean leftHeld = adapter.isHeld(KeyCode.LEFT);
		final boolean rightHeld = adapter.isHeld(KeyCode.RIGHT);

		if (leftHeld && !rightHeld) {
			horizontalSpeed -= horizontalStepSpeed;
			if (horizontalSpeed < -10) {
				horizontalSpeed = -10;
			}
		} else if (rightHeld && !leftHeld) {
			horizontalSpeed += horizontalStepSpeed;
			if (horizontalSpeed > 10) {
				horizontalSpeed = 10;
			}
		} else {
			if (horizontalSpeed < 0) {
				horizontalSpeed += horizontalStepSpeed / 2;
				if (horizontalSpeed > 0) {
					horizontalSpeed = 0;
				}
			} else if (horizontalSpeed > 0) {
				horizontalSpeed -= horizontalStepSpeed / 2;
				if (horizontalSpeed < 0) {
					horizontalSpeed = 0;
				}
			}
		}

		if (horizontalSpeed != 0) {
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
