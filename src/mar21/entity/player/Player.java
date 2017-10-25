package mar21.entity.player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import mar21.entity.Entity;
import mar21.game.Game;
import mar21.game.Upgrades;
import mar21.input.InputManager;
import mar21.input.bind.StrokeType;
import mar21.resources.ShatteredImageView;
import static mar21.game.Upgrades.UpgradeType;

public final class Player extends Entity {

	public static final double HEIGHT = 80;
	public static final double COLLISION_HEIGHT_OFFSET = 11;
	
	public static final double WIDTH = 66;
	
	private final Timeline walking = new Timeline();
	{
		walking.getKeyFrames().addAll(
			new KeyFrame(Duration.millis( 50), e -> this.imageView.shatter(1, 0)), 
			new KeyFrame(Duration.millis(100), e -> this.imageView.shatter(1, 1)), 
			new KeyFrame(Duration.millis(150), e -> this.imageView.shatter(1, 2)), 
			new KeyFrame(Duration.millis(200), e -> this.imageView.shatter(0, 0))
		);
		
		walking.setOnFinished(e -> {
			this.imageView.shatter(0, 0);
		});
	}
	
	private Upgrades upgrades = Upgrades.getInstance();
	private boolean onGround;
	private int health = 1 + upgrades.get(UpgradeType.LIFE);
	private double speed = 5 + upgrades.get(UpgradeType.SPEED);
	private double jump = -12 - upgrades.get(UpgradeType.JUMP_HEIGHT);
	
	public Player(double x, double y, InputManager input) {
		super(x, y, new ShatteredImageView("player", 3 * WIDTH, 2 * HEIGHT, 2, 3));
		
		input.bind(KeyCode.SPACE, StrokeType.KEY_HELD, () -> {
			if (onGround) {
				dy = jump;
				onGround= false;
			}
		});

		input.bind(KeyCode.A, StrokeType.KEY_HELD, () -> {
			move(-speed, 0);
			walking.play();
			imageView.setScaleX(-1);
		});
		
		input.bind(KeyCode.D, StrokeType.KEY_HELD, () -> {
			move(speed, 0);
			walking.play();
			imageView.setScaleX(1);
		});
	}

	@Override
	public void update() {
		if (!onGround) {
			this.dy += 0.5;
			if (this.dy <= 0) {
				((ShatteredImageView) this.imageView).shatter(0, 1);
			} else {
				((ShatteredImageView) this.imageView).shatter(0, 2);
			}
		} else if (this.dy >= 0.5) {
			this.dy = 0;
			((ShatteredImageView) this.imageView).shatter(0, 0);
		}

		this.x += dx;
		this.y += dy;
		
		this.x += (x < 0 ? -x : (x > Game.SCREEN_WIDTH - WIDTH ? (Game.SCREEN_WIDTH - WIDTH) - x : 0));
		if (this.y < 0) {
			this.y = 0;
		} else if (this.y > Game.GROUND) {
			this.onGround = true;
			this.y = Game.GROUND;
		}
	}
	
	public void applyDamage() {
		this.health -= 1;
	}
	
	public int getHealth() {
		return this.health;
	}
}
