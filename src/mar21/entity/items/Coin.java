package mar21.entity.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mar21.entity.FallingEntity;
import mar21.resources.ShatteredImageView;

public final class Coin extends FallingEntity {
	
	private final Timeline spin = new Timeline();
	{
		spin.setCycleCount(Timeline.INDEFINITE);
		spin.getKeyFrames().addAll(
			new KeyFrame(Duration.millis(80),e -> {
				view.setScaleX(1);
				view.shatter(0, 0);
			}),
			new KeyFrame(Duration.millis(160), e -> view.shatter(0, 1)),
			new KeyFrame(Duration.millis(240), e -> view.shatter(0, 2)),
			new KeyFrame(Duration.millis(320), e -> view.shatter(0, 3)),
			new KeyFrame(Duration.millis(400), e -> view.shatter(0, 4)),
			new KeyFrame(Duration.millis(480),e -> {
				view.setScaleX(-1);
				view.shatter(0, 3);
			}),
			new KeyFrame(Duration.millis(560), e -> view.shatter(0, 2)),
			new KeyFrame(Duration.millis(640), e -> view.shatter(0, 1))
		);
	}
	
	public Coin(double x, double y) {
		super(x, y, new ShatteredImageView("coin", 150, 30, 1, 5));
		spin.play();
	}

}
