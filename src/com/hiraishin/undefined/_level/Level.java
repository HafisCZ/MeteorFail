package com.hiraishin.undefined._level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.hiraishin.undefined._entity.Entity;
import com.hiraishin.undefined._entity.controller.Controller;
import com.hiraishin.undefined._entity.mob.Enemy;
import com.hiraishin.undefined._entity.mob.Item;
import com.hiraishin.undefined._entity.mob.Player;
import com.hiraishin.undefined._entity.mob.Spawner;
import com.hiraishin.undefined._essentials.Upgrades;
import com.hiraishin.undefined._event.GameEvent;
import com.hiraishin.undefined._util.Dimensions;
import com.hiraishin.undefined._util.PortView;
import com.hiraishin.undefined._util.logger.Logger;
import com.hiraishin.undefined._util.logger.Severity;
import com.hiraishin.undefined._util.resource.ResourceLoader;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;

public class Level {

	private final ObservableList<Node> windowObjects;
	private final List<Entity> gameObjects;

	private final Spawner itemSpawner;
	private final Spawner enemySpawner;

	public Level(Group group, Controller playerController) {
		windowObjects = Objects.requireNonNull(group).getChildren();
		gameObjects = new ArrayList<>();
		itemSpawner = new Spawner(Item.class, 20, Dimensions.SCREEN_WIDTH + 20, -40, -40, 500);
		enemySpawner = new Spawner(Enemy.class, 20, Dimensions.SCREEN_WIDTH + 20, -40, -40, 500);

		windowObjects.add(new PortView(ResourceLoader.INSTANCE.getTexture("bg"), Dimensions.SCREEN_WIDTH,
				Dimensions.SCREEN_HEIGHT, 1, 1));

		Player player = new Player((Dimensions.SCREEN_WIDTH - Player.DEFAULT_COLLISION_BOX.getWidth()) / 2,
				Dimensions.GROUND_LEVEL - Player.DEFAULT_COLLISION_BOX.getHeight());
		gameObjects.add(player);
		windowObjects.add(player.getPortView());

		player.healthProperty().addListener((O, Prev, Next) -> {
			if (Next.intValue() <= 0) {
				itemSpawner.stop();
				enemySpawner.stop();
				group.fireEvent(new GameEvent(GameEvent.GAME_OVER));
			}
		});

		playerController.setTarget(player);

		/**
		 * OVERLAY SETUP
		 */

		itemSpawner.start();
		enemySpawner.start();
	}

	private void add(Entity... entities) {
		Platform.runLater(() -> {
			for (Entity e : entities) {
				if (Objects.nonNull(e)) {
					gameObjects.add(e);
					windowObjects.add(e.getPortView());
				}
			}
		});
	}

	@SuppressWarnings("unused")
	private void remove(Entity... entities) {
		Platform.runLater(() -> {
			for (Entity e : entities) {
				if (Objects.nonNull(e)) {
					windowObjects.remove(e.getPortView());
					gameObjects.remove(e);
				}
			}
		});
	}

	private void removeSheduled() {
		Platform.runLater(() -> {
			gameObjects.removeIf(Entity -> {
				if (Objects.isNull(Entity)) {
					return true;
				} else {
					switch (Entity.getStatus()) {
					case C0:
					case C1:
					case C2:
					case C3:
					default:
						return false;
					case C4:
						windowObjects.remove(Entity.getPortView());
						return true;
					}
				}
			});
		});
	}

	private Player getPlayer() {
		return (Player) gameObjects.get(0);
	}

	public void tick() {
		add(itemSpawner.next(), enemySpawner.next());

		gameObjects.forEach(Entity -> {
			switch (Entity.getStatus()) {
			case C0:
				Logger.INSTANCE.log(Severity.WARNING, "Entity remained in C0 state");
				return;
			case C1:
				Entity.tick();

				if (!(Entity instanceof Player)) {
					if (Entity.getY() + Entity.getCollisionBox().getApproxHeight() >= Dimensions.GROUND_LEVEL) {
						Entity.advanceStatus();
						return;
					}

					if (Entity.collides(getPlayer())) {
						if (Entity instanceof Item) {
							Upgrades.INSTANCE.addMoney();
						} else if (Entity instanceof Enemy) {
							getPlayer().damage(1);
						}

						Entity.advanceStatus();
					}
				}
				return;
			case C2:
				Entity.tick();
				return;
			case C3:
				Entity.tick();
				return;
			case C4:
				return;
			}
		});

		removeSheduled();

		/**
		 * OVERLAY UPDATE
		 */
	}
}
