package mar21.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import mar21.entity.Entity;
import mar21.entity.Entity.State;
import mar21.entity.FallingEntity;
import mar21.entity.items.Coin;
import mar21.entity.items.Meteor;
import mar21.entity.player.Player;
import mar21.event.GameEvent;
import mar21.input.InputManager;
import mar21.input.bind.StrokeType;
import mar21.resources.ResourceManager;

public class Game {
	
	public static final Random RANDOM = new Random();
	
	public static final double SCREEN_WIDTH = 1000;
	public static final double SCREEN_HEIGHT = 700;
	public static final double GROUND = 490;
	
	private final Timeline spawner = new Timeline();
	{
		spawner.setCycleCount(Timeline.INDEFINITE);
		spawner.getKeyFrames().add(
			new KeyFrame(Duration.millis(200), e -> {
				double rndX = RANDOM.nextDouble() * (SCREEN_WIDTH - 40 - FallingEntity.SIZE) + 20;
				double rndY = -50;
				if (RANDOM.nextBoolean()) {
					add(new Meteor(rndX, rndY));
				} else {
					add(new Coin(rndX, rndY));
				}
			})
		);
	}
	
	private Scene scene;
	private Group content;
	private Overlay overlay;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private InputManager input;
	private Player player;
	
	public Game(Group content, InputManager input) {
		this.content = content;
		this.input = input;
		input.bind(KeyCode.NUMPAD0, StrokeType.KEY_PRESSED, () -> {
			if (FallingEntity.SPEED == 6) {
				FallingEntity.SPEED = 3;
			} else {
				FallingEntity.SPEED = 6;
			}
		});
		
		scene = new Scene(content, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		content.getChildren().add(ResourceManager.requestInstance().buildImageView("bg7", SCREEN_WIDTH, SCREEN_HEIGHT));
		
		player = new Player((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND, input);
		content.getChildren().add(player.getImageView());
		
		overlay = new Overlay(SCREEN_WIDTH, SCREEN_HEIGHT);
		overlay.init(player);
		content.getChildren().add(overlay);
		
		updateImageViews();
		spawner.play();
	}
	
	public void reset() {
		Platform.runLater(() -> {
			Upgrades.load();
			
			content.getChildren().remove(player.getImageView());
			for (Entity e : entities) {
				content.getChildren().remove(e.getImageView());
			}
			
			entities.clear();
		
			player = new Player((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND, input);
			content.getChildren().add(player.getImageView());
			overlay.init(player);
			
			updateImageViews();
			spawner.play();
		});
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	private void add(Entity... entity) {
		for (Entity e : entity) {
			content.getChildren().add(e.getImageView());
			entities.add(e);
		}
	}
	
	public void remove(Entity... entity) {
		Platform.runLater(() -> {
			for (Entity e : entity) {
				content.getChildren().remove(e.getImageView());
				entities.remove(e);
			}
		});
	}
	
	public void removeSheduled() {
		Platform.runLater(() -> {
			entities.removeIf((Entity) -> {
				if (Entity.state().equals(State.MARKED_FOR_REMOVAL)) {
					content.getChildren().remove(Entity.getImageView());
					return true;
				} else {
					return false;
				}
			});
		});
	}
	
	private void updateImageViews() {
		player.updateImageView();
		entities.forEach(Entity::updateImageView);
	}
	
	private void updateEntities() {
		player.update();
		entities.forEach((Entity) -> {
			switch (Entity.state()) {
				case NONE:
					{
						Entity.update();
						if (Entity.getImageView().intersects(player.x(), player.y(), Player.HEIGHT - Player.COLLISION_HEIGHT_OFFSET, Player.WIDTH)) {
							if (Entity instanceof Meteor) {
								player.applyDamage();
							} else {
								Upgrades.getInstance().addCoins();
							}
							
							Entity.requestAnimatedRemoval();
						}
					}					
					break;
				case REMOVAL_ANIMATION:
					{
						Entity.update();
					}
					break;
				case MARKED_FOR_REMOVAL:
					{
					
					}
					break;
			}
		});
		
		removeSheduled();
	}
	
	public void update() {		
		updateEntities();
		updateImageViews();
		overlay.update();
		
		if (player.getHealth() <= 0) {
			spawner.stop();
			Upgrades.save();
			content.fireEvent(new GameEvent(GameEvent.GAME_OVER));
		}
	}
}
