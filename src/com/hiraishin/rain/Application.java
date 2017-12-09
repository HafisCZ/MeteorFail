package com.hiraishin.rain;

import java.util.Objects;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.layout.pane.HelpPane;
import com.hiraishin.rain.layout.pane.MenuPane;
import com.hiraishin.rain.layout.pane.PausePane;
import com.hiraishin.rain.layout.pane.ShopPane;
import com.hiraishin.rain.layout.pane.StatPane;
import com.hiraishin.rain.util.Commons;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	/*
	 * Screen nodes
	 */
	private Scene scene;
	private Group root;
	private Group group;

	/*
	 * Keyboard listener
	 */
	private Keyboard keyboard;

	/*
	 * Game
	 */
	private Game game;

	/*
	 * Menu panes
	 */
	private Pane paneMenu = new MenuPane();
	private Pane paneShop = new ShopPane();
	private Pane paneStat = new StatPane();
	private Pane paneHelp = new HelpPane();
	private Pane panePause = new PausePane();

	/*
	 * Main function
	 */
	public static void main(String... args) {
		launch(args);
	}

	/*
	 * Start function
	 */
	@Override
	public void start(Stage stage) throws Exception {
		/*
		 * Create keyboard
		 */
		this.keyboard = new Keyboard(stage);

		/*
		 * Create game
		 */
		this.game = new Game(this.keyboard);

		/*
		 * Create groups
		 */
		this.group = new Group();
		this.root = new Group(this.game.getCanvas(), this.group);

		/*
		 * Create scene
		 */
		this.scene = new Scene(this.root, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

		/*
		 * Size window to scene
		 */
		stage.setScene(this.scene);
		stage.sizeToScene();

		/*
		 * Add State event listener
		 */
		stage.addEventHandler(StateEvent.STATE, event -> {
			if (Objects.equals(event.getEventType(), StateEvent.MENU)) {
				switchPane(this.group, this.paneMenu);
			} else if (Objects.equals(event.getEventType(), StateEvent.PLAY)) {
				switchPane(this.group, null);
				this.game.play();
			} else if (Objects.equals(event.getEventType(), StateEvent.SHOP)) {
				switchPane(this.group, this.paneShop);
			} else if (Objects.equals(event.getEventType(), StateEvent.STAT)) {
				switchPane(this.group, this.paneStat);
			} else if (Objects.equals(event.getEventType(), StateEvent.QUIT)) {
				Platform.exit();
			} else if (Objects.equals(event.getEventType(), StateEvent.HELP)) {
				switchPane(this.group, this.paneHelp);
			} else if (Objects.equals(event.getEventType(), StateEvent.PAUSE)) {
				switchPane(this.group, this.panePause);
				this.game.pause();
			} else if (Objects.equals(event.getEventType(), StateEvent.UNPAUSE)) {
				switchPane(this.group, null);
				this.game.unpause();
			} else if (Objects.equals(event.getEventType(), StateEvent.STOP)) {
				switchPane(this.group, this.paneMenu);
				this.game.close();
			}

			event.consume();
		});

		/*
		 * Set window properties
		 */
		stage.setTitle("Rain");
		stage.setResizable(false);

		/*
		 * Show window
		 */
		stage.show();

		/*
		 * Fire event to switch to Menu
		 */
		stage.fireEvent(new StateEvent(StateEvent.MENU));
	}

	/*
	 * Helper function
	 */
	private static void switchPane(Group parent, Pane pane) {
		parent.getChildren().clear();

		if (Objects.nonNull(pane)) {
			parent.getChildren().add(pane);
		}
	}

}
