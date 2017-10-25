package mar21.entity;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import mar21.game.Game;
import mar21.resources.ShatteredImageView;

public class FallingEntity extends Entity {

	public static final double SIZE = 40;
	public static double SPEED = 6; // - Upgrades.getInstance().get(UpgradeType.FALL_SPEED);
	
	private final FadeTransition fade = new FadeTransition();
	{
		this.fade.setNode(this.imageView);
		this.fade.setDuration(Duration.millis(200));
		this.fade.setFromValue(1.0);
		this.fade.setToValue(0.0);
		this.fade.setOnFinished(e -> requestRemoval());
	}

	public FallingEntity(double x, double y, ShatteredImageView imageView) {
		super(x, y, imageView);
		this.dy = 1;
		
		assignAction(State.REMOVAL_ANIMATION, () -> {
			this.dy = -1;
			this.fade.play();
		});
	}

	@Override
	public void update() {
		this.x += dx * SPEED;
		this.y += dy * SPEED;
		
		if (this.y >= Game.GROUND + SIZE) {
			requestRemoval();
		}
	}
}
