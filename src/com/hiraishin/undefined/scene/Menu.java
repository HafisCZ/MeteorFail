package com.hiraishin.undefined.scene;

import com.hiraishin.undefined.event.GameEvent;
import com.hiraishin.undefined.util.Commons;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public enum Menu {

	INSTANCE;

	private final VBox menuItems;
	private final Text gameLogo;

	private final Scene menuScene;

	private int selected;

	private Menu() {
		final MenuItem mi_quit = new MenuItem("QUIT", new GameEvent(GameEvent.GAME_QUIT));
		final MenuItem mi_play = new MenuItem("PLAY", new GameEvent(GameEvent.GAME_START));
		final MenuItem mi_shop = new MenuItem("SHOP", new GameEvent(GameEvent.GAME_SHOP));

		menuItems = new VBox(10);
		menuItems.getChildren().addAll(mi_play, mi_shop, mi_quit);
		menuItems.setTranslateX(Commons.SCREEN_WIDTH / 8);
		menuItems.setTranslateY(Commons.SCREEN_HEIGHT / 4);

		gameLogo = new Text("UNDEFINED_");
		gameLogo.setTranslateX(Commons.SCREEN_WIDTH / 8);
		gameLogo.setTranslateY(Commons.SCREEN_HEIGHT / 5);
		gameLogo.setFont(Font.font("", FontWeight.SEMI_BOLD, 50));
		gameLogo.setFill(Color.DARKSEAGREEN);
		gameLogo.setOpacity(0.7);

		getMenuItem(0).setActive(true);

		Group local = new Group(new Rectangle(Commons.SCREEN_WIDTH, Commons.SCREEN_HEIGHT), menuItems, gameLogo);
		menuScene = new Scene(local, Commons.SCREEN_WIDTH, Commons.SCREEN_HEIGHT);
		menuScene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				if (selected > 0) {
					getMenuItem(selected).setActive(false);
					getMenuItem(--selected).setActive(true);
				}
			}

			if (event.getCode() == KeyCode.DOWN) {
				if (selected < menuItems.getChildren().size() - 1) {
					getMenuItem(selected).setActive(false);
					getMenuItem(++selected).setActive(true);
				}
			}

			if (event.getCode() == KeyCode.ENTER) {
				getMenuItem(selected).triggerEvent();
			}
		});

		for (Node node : menuItems.getChildren()) {
			MenuItem menuItem = (MenuItem) node;
			menuItem.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
				menuItem.triggerEvent();
			});
		}
	}

	public Scene getScene() {
		return menuScene;
	}

	private MenuItem getMenuItem(int index) {
		return (MenuItem) menuItems.getChildren().get(index);
	}

	private static class MenuItem extends HBox {

		private Text text;
		private GameEvent event;

		public MenuItem(String label, GameEvent response) {
			setAlignment(Pos.CENTER);

			text = new Text(label);
			text.setFont(Commons.FONT);

			getChildren().add(text);

			setActive(false);

			this.event = response;
		}

		public void setActive(boolean active) {
			text.setFill(active ? Color.WHITESMOKE : Color.GREY);
		}

		public void triggerEvent() {
			if (event != null) {
				fireEvent(event);
			}
		}
	}

}
