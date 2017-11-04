package mar21.game;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;
import mar21.entity.Coin;
import mar21.entity.Entity;
import mar21.entity.Entity.State;
import mar21.entity.FallingEntity;
import mar21.entity.Meteor;
import mar21.entity.Player;
import mar21.event.GameEvent;
import mar21.input.InputHandler;
import mar21.resources.SheetView.SheetViewBuilder;

public class Game {
	
	public static final Random RANDOM = new Random();
	
	public static final double SCREEN_WIDTH = 1000;
	public static final double SCREEN_HEIGHT = 700;
	public static final double GROUND = 490;
	
	private Scene scene;
	private Group dynamic;
	private Overlay overlay;
	private Timeline spawner;
	
	private Player player;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Game(Group content, InputHandler input) {
		spawner = new Timeline(
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
		spawner.setCycleCount(Timeline.INDEFINITE);
		spawner.play();
		
		scene = new Scene(content, SCREEN_WIDTH, SCREEN_HEIGHT);
		player = new Player((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND, input);
		overlay = new Overlay(SCREEN_WIDTH, SCREEN_HEIGHT);
		dynamic = new Group();
		
		content.getChildren().addAll(
			new SheetViewBuilder("bg7").setDimensions(SCREEN_WIDTH, SCREEN_HEIGHT).build(),
			overlay,
			player.getView(),
			dynamic
		);

		overlay.init(player);
		
		player.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
			if (NewValue.intValue() <= 0) {
				spawner.stop();
				Upgrades.save();
				content.fireEvent(new GameEvent(GameEvent.GAME_OVER));
			}
		});
	}
	
	public void reset() {
		Platform.runLater(() -> {
			Upgrades.load();
			
			entities.clear();
			dynamic.getChildren().clear();

			player.reset((SCREEN_WIDTH - Player.WIDTH) / 2, GROUND);
			overlay.update();
			spawner.play();
		});
	}
	
	public Scene getScene() {
		return scene;
	}
	
	private void add(FallingEntity... entity) {
		for (FallingEntity e : entity) {
			Objects.requireNonNull(e);
			dynamic.getChildren().add(e.getView());
			entities.add(e);
		}
	}
	
	public void remove(FallingEntity... entity) {
		Platform.runLater(() -> {
			for (FallingEntity e : entity) {
				if (Objects.nonNull(e)) {
					dynamic.getChildren().remove(e.getView());
				}
				
				entities.remove(e);
			}
		});
	}
	
	public void removeSheduled() {
		Platform.runLater(() -> {
			entities.removeIf((Entity) -> {
				if (Objects.isNull(Objects.requireNonNull(Entity).getState())) {
					return false;
				} else {
					switch (Entity.getState()) {
						case MARKED_AS_REMOVED:
							dynamic.getChildren().remove(Entity.getView());
							return true;
						case MARKED_AS_ANIMATED:
						default:
							return false;
					}
				}
			});
		});
	}
	
	public void update() {		
		player.update();
		entities.forEach(Entity -> {
			Objects.requireNonNull(Entity);
			
			if (Objects.isNull(Entity.getState())) {
				Entity.update();
				if (player.getBounds().intersects(Entity.getBounds())) {
					if (Entity instanceof Meteor) {
						player.reduceHealth();
					} else if (Entity instanceof Coin) {
						Upgrades.getInstance().addCoins();
					}
					
					Entity.setState(State.MARKED_AS_ANIMATED);
				}
			} else if (Objects.equals(Entity.getState(), State.MARKED_AS_ANIMATED)) {
				Entity.update();
			}
		});
		
		removeSheduled();
		
		overlay.update();
	}
}
