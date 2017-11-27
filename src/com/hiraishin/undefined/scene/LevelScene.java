package com.hiraishin.undefined.scene;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import com.hiraishin.undefined.Game;
import com.hiraishin.undefined.entity.Entity;
import com.hiraishin.undefined.entity.EntityState;
import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.entity.mob.Enemy;
import com.hiraishin.undefined.entity.mob.Item;
import com.hiraishin.undefined.entity.mob.Player;
import com.hiraishin.undefined.event.GameEvent;
import com.hiraishin.undefined.input.UIAdapter;
import com.hiraishin.undefined.scene.overlay.Overlay;
import com.hiraishin.undefined.scene.overlay.Upgrade;
import com.hiraishin.undefined.sheetview.SheetView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.util.Duration;

public class LevelScene extends GameScene {

	public static final Random RANDOM = new Random();
	
	private Group entityGroup;
	private ArrayList<Entity> entityList;
	
	private Overlay overlay;
	private Timeline spawner;
	private Player player;
	
	public LevelScene(UIAdapter input) {
		super(Game.RES_WIDTH, Game.RES_HEIGHT);
		
		spawner = new Timeline(
				new KeyFrame(Duration.millis(200), e -> {
					double rndX = RANDOM.nextDouble() * (Game.RES_WIDTH - 40) + 20;
					double rndY = -50;
					if (RANDOM.nextBoolean()) {
						add(new Enemy(rndX, rndY));
					} else {
						add(new Item(rndX, rndY));
					}
				})
			);
		spawner.setCycleCount(Timeline.INDEFINITE);
		spawner.play();
		
		player = new Player((Game.RES_WIDTH - Player.DEFAULT_COLLISION_BOX_PLAYER.getWidth()) / 2, Game.RES_HEIGHT / 7 * 5, input);
		player.getHealthProperty().addListener((Observable, OldValue, NewValue) -> {
			if (NewValue.intValue() <= 0) {
				spawner.stop();
				Upgrade.save();
				group.fireEvent(new GameEvent(GameEvent.GAME_OVER));
			}
		});
		
		overlay = new Overlay(Game.RES_WIDTH, Game.RES_HEIGHT);
		
		entityGroup = new Group();
		entityList = new ArrayList<>();
		
		add(
			new SheetView.SheetViewBuilder("bg").setDimensions(Game.RES_WIDTH, Game.RES_HEIGHT).build(),
			overlay,
			player.getView(),
			entityGroup
		);
		
		overlay.init(player);
	}
	
	public void reset() {
		Platform.runLater(() -> {
			Upgrade.load();
			
			entityList.clear();
			entityGroup.getChildren().clear();

			player.reset((Game.RES_WIDTH - Player.DEFAULT_COLLISION_BOX_PLAYER.getWidth()) / 2, Game.RES_HEIGHT / 7 * 5);
			overlay.update();
			spawner.play();
		});
	}
	
	private void add(Entity... entity) {
		for (Entity e : entity) {
			Objects.requireNonNull(e);
			entityGroup.getChildren().add(e.getView());
			entityList.add(e);
		}
	}
	
	public void removeSheduled() {
		Platform.runLater(() -> {
			entityList.removeIf((Entity) -> {
				if (Objects.isNull(Objects.requireNonNull(Entity).getState())) {
					return false;
				} else {
					switch (Entity.getState()) {
						case C2:
							entityGroup.getChildren().remove(Entity.getView());
							return true;
						case C1:
						default:
							return false;
					}
				}
			});
		});
	}
	
	@Override
	public void update() {		
		player.update();
		entityList.forEach(Entity -> {
			Objects.requireNonNull(Entity);
			
			if (Objects.isNull(Entity.getState())) {
				Entity.update();
				
				if (CollisionBox.intersects(player, Entity)) {
					if (Entity instanceof Enemy) {
						player.reduceHealth();
					} else if (Entity instanceof Item) {
						Upgrade.getInstance().addCoins();
					}
					
					Entity.setState(EntityState.C1);
				}
			} else if (Objects.equals(Entity.getState(), EntityState.C1)) {
				Entity.update();
			}
		});
		
		removeSheduled();
		
		overlay.update();
	}
}
