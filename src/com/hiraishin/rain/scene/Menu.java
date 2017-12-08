package com.hiraishin.rain.scene;

import java.util.ArrayList;
import java.util.List;

import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.util.Commons;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;

public enum Menu {

	INSTANCE();

	private final Group group = new Group();
	private final Group como = new Group(group);
	private final Scene scene = new Scene(como, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

	private final List<MenuItem> menuItems = new ArrayList<>();

	private int selector = 0;

	private final Game game;

	public Game getGame() {
		return game;
	}

	private Keyboard keyboard;

	public void setKeyboard(Keyboard k) {
		keyboard = k;
	}

	private Menu() {
		game = Game.INSTANCE;

		final VBox menuItems = new VBox(10);
		menuItems.setLayoutX(Commons.SCENE_WIDTH / 8);
		menuItems.setLayoutY(Commons.SCENE_HEIGHT / 4);
		menuItems.getChildren().addAll(buildMenuItem("PLAY", () -> {
			como.getChildren().remove(group);
			game.playLevel(keyboard);
		}), buildMenuItem("SHOP", () -> System.out.println("SHOP")), buildMenuItem("QUIT", () -> Platform.exit()));

		final Text gameLogo = new Text("Rain");
		gameLogo.setTranslateX(Commons.SCENE_WIDTH / 8);
		gameLogo.setTranslateY(Commons.SCENE_HEIGHT / 5);
		gameLogo.setFont(Commons.FONT_GAMELOGO);
		gameLogo.setOpacity(0.7);

		final Stop[] stops = new Stop[] { new Stop(0, Color.AQUAMARINE), new Stop(1, Color.BLANCHEDALMOND) };

		final LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		gameLogo.setFill(gradient);

		game.setLevel(new Level());

		como.getChildren().add(0, game.getCanvas());

		group.getChildren().addAll(menuItems, gameLogo);

		scene.setOnKeyPressed(event -> {
			final KeyCode keyCode = event.getCode();

			if (keyCode == KeyCode.UP) {
				if (selector > 0) {
					this.menuItems.get(selector).setSelected(false);
					this.menuItems.get(--selector).setSelected(true);
				}
			}

			if (keyCode == KeyCode.DOWN) {
				if (selector < this.menuItems.size() - 1) {
					this.menuItems.get(selector).setSelected(false);
					this.menuItems.get(++selector).setSelected(true);
				}
			}

			if (keyCode == KeyCode.ENTER) {
				this.menuItems.get(selector).run();
			}
		});

		this.menuItems.get(0).setSelected(true);
	}

	private Node buildMenuItem(String label, Runnable action) {
		final MenuItem menuItem = new MenuItem(label, action);
		menuItems.add(menuItem);
		return menuItem;
	}

	public Scene getScene() {
		return scene;
	}
}
