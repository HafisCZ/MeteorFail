package com.hiraishin.undefined.entity.mob;

import com.hiraishin.undefined.Game;
import com.hiraishin.undefined.entity.Entity;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.input.UIAdapter;
import com.hiraishin.undefined.input.bind.KeyStroke;
import com.hiraishin.undefined.scene.overlay.Upgrade;
import com.hiraishin.undefined.scene.overlay.Upgrade.UpgradeType;
import com.hiraishin.undefined.sheetview.SheetView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public final class Player extends Entity {
	
	public static final CollisionBox DEFAULT_COLLISION_BOX_PLAYER = new CollisionBox(6, 0, 60, 80, 66, 80);

	public static final double DEFAULT_PLAYER_SPEED = 5;
	public static final double DEFAULT_PLAYER_JUMPD = -12;
	
	private final IntegerProperty health;
	private final Timeline walkAnimation;
	private final Upgrade upgrade;
	
	private double walkSpeed;
	private double jumpSpeed;
	
	private boolean ground = true;
	private int walkDirection = 0;
	
	public Player(double x, double y, UIAdapter adapter) {
		super(x, y, new SheetView("player", 66, 80, 2, 4), DEFAULT_COLLISION_BOX_PLAYER);
		
		this.upgrade = Upgrade.getInstance();
		
		this.walkSpeed = DEFAULT_PLAYER_SPEED + upgrade.get(UpgradeType.SPEED);
		this.jumpSpeed = DEFAULT_PLAYER_JUMPD + upgrade.get(UpgradeType.JUMP_HEIGHT);
		this.health = new SimpleIntegerProperty(1 + upgrade.get(UpgradeType.LIFE));
		
		walkAnimation = new Timeline();
		walkAnimation.setCycleCount(Timeline.INDEFINITE);
		walkAnimation.getKeyFrames().addAll(
			new KeyFrame(Duration.millis(000), e -> view.show(1, 0)), 
			new KeyFrame(Duration.millis(100), e -> view.show(1, 1)), 
			new KeyFrame(Duration.millis(200), e -> view.show(1, 2)), 
			new KeyFrame(Duration.millis(300), e -> view.show(1, 3))
		);
		
		xProperty.addListener((Observable, Old, New) -> {
			if (New.doubleValue() < 0) {
				xProperty.set(0);
			} else if (New.doubleValue() + bounds.getWidth() > Game.RES_WIDTH) {
				xProperty.set(Game.RES_WIDTH - bounds.getWidth());
			}
		});
		
		yProperty.addListener((Observable, Old, New) -> {
			if (New.doubleValue() < 0) {
				yProperty.set(0);
			} else if (New.doubleValue() > Game.RES_HEIGHT / 7 * 5) {
				yProperty.set(Game.RES_HEIGHT / 7 * 5);
				this.ground = true;
			}
		});
		
		adapter.bind(KeyCode.SPACE, KeyStroke.KEY_HELD, () -> {
			if (ground) {
				this.dy = jumpSpeed;
				ground = false;
			}
		});
		
		adapter.bind(KeyCode.A, KeyStroke.KEY_HELD, () -> {
			this.walkDirection = -1;
		});
		
		adapter.bind(KeyCode.D, KeyStroke.KEY_HELD, () -> {
			this.walkDirection = 1;
		});
	}
	
	public void reset(double x, double y) {
		health.set(1 + upgrade.get(UpgradeType.LIFE));
		setPosition(x, y);
	}

	public IntegerProperty getHealthProperty() {
		return this.health;
	}
	
	public int getHealth() {
		return this.health.get();
	}
	
	public void reduceHealth() {
		this.health.set(this.health.get() - 1);
	}
	
	@Override
	public void update() {
		dy += (ground ? (dy >= 0.5 ? -dy : 0) : 0.5);
		move(dx, dy);
		
		if (walkDirection < 0) {
			move(-walkSpeed, 0);
			walkAnimation.play();
			view.setScaleX(-1);
		} else if (walkDirection > 0) {
			move(walkSpeed, 0);
			view.setScaleX(1);
			walkAnimation.play();
		} else {
			walkAnimation.stop();
			view.show(0, 0);
		}
		
		walkDirection = 0;
	}
}