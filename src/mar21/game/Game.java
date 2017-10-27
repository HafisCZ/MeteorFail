package mar21.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;
import mar21.entity.Entity;
import mar21.entity.Entity.OnUpdateAction;
import mar21.entity.FallingEntity;
import mar21.entity.items.Coin;
import mar21.entity.items.Meteor;
import mar21.entity.player.Player;
import mar21.event.GameEvent;
import mar21.input.InputHandler;
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
	private InputHandler input;
	private Player player;
	
	public Game(Group content, InputHandler input) {
		this.content = content;
		this.input = input;
		
		scene = new Scene(content, SCREEN_WIDTH, SCREEN_HEIGHT);
		player = new Player((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND, input);
		overlay = new Overlay(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		content.getChildren().addAll(
			ResourceManager.requestInstance().buildImageView("bg7", SCREEN_WIDTH, SCREEN_HEIGHT),
			player.getView(),
			overlay
		);

		overlay.init(player);
		
		spawner.play();
	}
	
	public void reset() {
		Platform.runLater(() -> {
			Upgrades.load();
			
			content.getChildren().remove(1, content.getChildren().size());
			entities.clear();
		
			player = new Player((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND, input);	
			content.getChildren().add(player.getView());
			
			overlay.init(player);
		
			spawner.play();
		});
	}
	
	public Scene getScene() {
		return scene;
	}
	
	private void add(Entity... entity) {
		for (Entity e : entity) {
			content.getChildren().add(e.getView());
			entities.add(e);
		}
	}
	
	public void remove(Entity... entity) {
		Platform.runLater(() -> {
			for (Entity e : entity) {
				content.getChildren().remove(e.getView());
				entities.remove(e);
			}
		});
	}
	
	public void removeSheduled() {
		Platform.runLater(() -> {
			entities.removeIf((Entity) -> {
				if (Entity.getAction().equals(OnUpdateAction.REMOVAL)) {
					content.getChildren().remove(Entity.getView());
					return true;
				} else {
					return false;
				}
			});
		});
	}
	
	private void updateEntities() {
		player.update();
		entities.forEach((Entity) -> {
			switch (Entity.getAction()) {
				case NONE:
					{
						Entity.update();
						if (Entity instanceof Meteor) {
							if (player.getView().intersects(Entity.getX() + 6, Entity.getY() + 6, Entity.getWidth() - 12, Entity.getHeight() - 12)) {	
								player.applyDamage();
								Entity.requestRemoval(true);
							}
						} else {
							if (player.getView().intersects(Entity.getView().getBoundsInLocal())) {
								Upgrades.getInstance().addCoins();
								Entity.requestRemoval(true);
							}
						}
					}					
					break;
				case ANIMATION:
					{
						Entity.update();
					}
					break;
				case REMOVAL:
					{
					
					}
					break;
			}
		});
		
		removeSheduled();
	}
	
	public void update() {		
		updateEntities();
		
		overlay.update();
		
		if (player.getHealth() <= 0) {
			spawner.stop();
			Upgrades.save();
			content.fireEvent(new GameEvent(GameEvent.GAME_OVER));
		}
	}
}
