package mar21.entity;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import mar21.game.Game;
import mar21.resources.ShatteredImageView;

public class FallingEntity extends Entity {
	
	public final static double SIZE = 40;
	public final static double SPEED = 3; // - Upgrades.getInstance().get(UpgradeType.FALL_SPEED);

	public FallingEntity(double x, double y, ShatteredImageView view) {
		super(x, y, view);
		
		dy = SPEED;
		
		removalAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			
		}));
		
		assignAction(OnUpdateAction.ANIMATION, () -> {
			dy = -SPEED;
		});
	}
	
	@Override
	public void update() {
		move(dx * SPEED, dy * SPEED);
		
		if (getY() >= Game.GROUND + SIZE) {
			requestRemoval(false);
		}
	}
	
}
