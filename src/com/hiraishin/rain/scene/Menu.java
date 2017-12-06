package com.hiraishin.rain.scene;

import java.util.ArrayList;
import java.util.List;

import com.hiraishin.rain.util.Commons;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Stop;
import javafx.scene.paint.LinearGradient;

public enum Menu {

	INSTANCE();

	private final Group group = new Group();
	private final Scene scene = new Scene(group, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

	private final List<MenuItem> menuItems = new ArrayList<>();

	private int selector = 0;

	private Menu() {
		final VBox menuItems = new VBox(10);
		menuItems.setLayoutX(Commons.SCENE_WIDTH / 8);
		menuItems.setLayoutY(Commons.SCENE_HEIGHT / 4);
		menuItems.getChildren().addAll(buildMenuItem("PLAY", () -> System.out.println("PLAY")),
				buildMenuItem("SHOP", () -> System.out.println("SHOP")),
				buildMenuItem("QUIT", () -> System.out.println("QUIT")));

		final Text gameLogo = new Text("Rain");
		gameLogo.setTranslateX(Commons.SCENE_WIDTH / 8);
		gameLogo.setTranslateY(Commons.SCENE_HEIGHT / 5);
		gameLogo.setFont(Commons.FONT_GAMELOGO);
		gameLogo.setOpacity(0.7);
		
		final Stop[] stops = new Stop[] {
			new Stop(0, Color.AQUAMARINE), new Stop(1, Color.BLANCHEDALMOND)
		};
		
		final LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		gameLogo.setFill(gradient);

		final Rectangle simpleBackground = new Rectangle(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
		simpleBackground.setFill(Color.DARKSLATEGRAY);

		group.getChildren().addAll(simpleBackground, menuItems, gameLogo);

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
