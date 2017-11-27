package com.hiraishin.undefined.entity;

import com.hiraishin.undefined.Game;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.sheetview.SheetView;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;


public class Mob extends Entity {
	
	protected final Timeline exitAnimation;
	
	public Mob(double x, double y, SheetView view, CollisionBox box) {
		super(x, y, view, box);
		
		this.dy = DEFAULT_SPEED;
		
		this.yProperty.addListener((Observable, Old, New) -> {
			if (New.doubleValue() >= Game.RES_HEIGHT / 7 * 5 + box.getHeight()) {
				setState(EntityState.C2);
			}
		});
		
		exitAnimation = new Timeline();
		exitAnimation.setOnFinished(e -> setState(EntityState.C2));
		exitAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
	
		}));
	}
	
	public final void onStateC1() {
		this.dy = -DEFAULT_SPEED;
		exitAnimation.play();
	}
	
	@Override
	public void update() {
		move(this.dx * DEFAULT_SPEED, this.dy * DEFAULT_SPEED);
	}
	
}
