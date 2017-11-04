package mar21.entity;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import mar21.game.Game;
import mar21.resources.SheetView;

public class FallingEntity extends Entity {
	
	public final static double SIZE = 40;
	public final static double SPEED = 3;

	public FallingEntity(double x, double y, SheetView view) {
		super(x, y, view);
		
		dy = SPEED;
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
	
		}));
	}
	
	@Override
	protected void onMarkedAsAnimated() {
		dy = -SPEED;
	}
	
	@Override
	public void update() {
		move(dx * SPEED, dy * SPEED);
		
		if (getY() >= Game.GROUND + SIZE) {
			setState(State.MARKED_AS_REMOVED);
		}
	}
	
}
