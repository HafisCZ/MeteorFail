package mar21.entity;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import mar21.entity.collision.CollisionBox;
import mar21.game.Game;
import mar21.game.Upgrades;
import mar21.game.Upgrades.UpgradeType;
import mar21.input.InputHandler;
import mar21.input.KeyStroke;
import mar21.resources.SheetView;

public final class Player extends Entity {

	public static final double HEIGHT = 80, WIDTH = 66;
	
	private boolean walkR, walkL;
	
	private Upgrades upgrades = Upgrades.getInstance();
	
	private Timeline walk;

	private boolean onGround;
	
	private IntegerProperty hp;
	
	private double speed = 5 + upgrades.get(UpgradeType.SPEED);
	private double jump = -12 - upgrades.get(UpgradeType.JUMP_HEIGHT);
	
	public Player(double x, double y, InputHandler input) {
		super(x, y, new SheetView("player", WIDTH, HEIGHT, 2, 4), null);
	
		collisionBox = new CollisionBox(this, 4, 0, WIDTH - 4, HEIGHT);
		
		hp = new SimpleIntegerProperty(1 + upgrades.get(UpgradeType.LIFE));
		
		walk = new Timeline();
		walk.setCycleCount(Timeline.INDEFINITE);
		walk.getKeyFrames().addAll(
			new KeyFrame(Duration.millis(000), e -> view.show(1, 0)), 
			new KeyFrame(Duration.millis(100), e -> view.show(1, 1)), 
			new KeyFrame(Duration.millis(200), e -> view.show(1, 2)), 
			new KeyFrame(Duration.millis(300), e -> view.show(1, 3))
		);
		
		xProperty.addListener(e -> {
			if (xProperty.get() < 0) {
				xProperty.set(0);
			} else if (xProperty.get() > Game.SCREEN_WIDTH - WIDTH) {
				xProperty.set(Game.SCREEN_WIDTH - WIDTH);
			}
		});
		
		yProperty.addListener(e -> {
			if (yProperty.get() < 0) {
				yProperty.set(0);	
			} else if (yProperty.get() > Game.GROUND) {
				yProperty.set(Game.GROUND);
				onGround = true;
			}
		});
		
		input.bind(KeyCode.SPACE, KeyStroke.KEY_HELD, () -> {
			if (onGround) {
				dy = jump;
				onGround= false;
			}
		});

		input.bind(KeyCode.A, KeyStroke.KEY_HELD, () -> {
			walkL = true;
		});
		
		input.bind(KeyCode.D, KeyStroke.KEY_HELD, () -> {
			walkR = true;
		});
	}
	
	@Override
	public void update() {
		dy += (onGround ? (dy >= 0.5 ? -dy : 0) : 0.5);
		move(dx, dy);

		if (walkR && !walkL) {
			move(speed, 0);
			view.setScaleX(1);
			walk.play();
		} else if (!walkR && walkL) {
			move(-speed, 0);
			walk.play();
			view.setScaleX(-1);
		} else {
			walk.stop();
			view.show(0, 0);
		}
		
		walkR = false;
		walkL = false;
	}
	
	public void reset(double x, double y) {
		hp.set(1 + upgrades.get(UpgradeType.LIFE));
		setPosition(x, y);
	}
	
	public IntegerProperty getHealthProperty() {
		return hp;
	}
	
	public void reduceHealth() {
		hp.set(hp.get() - 1);
	}
	
	public int getHealth() {
		return hp.get();
	}
}