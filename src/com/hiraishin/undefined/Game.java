package com.hiraishin.undefined;

import com.hiraishin.undefined.entity.controller.Controller;
import com.hiraishin.undefined.event.GameEvent;
import com.hiraishin.undefined.game.Upgrades;
import com.hiraishin.undefined.input.InputEventAdapter;
import com.hiraishin.undefined.level.Level;
import com.hiraishin.undefined.scene.Menu;
import com.hiraishin.undefined.util.Commons;
import com.hiraishin.undefined.util.logger.Logger;
import com.hiraishin.undefined.util.logger.Severity;
import com.hiraishin.undefined.util.resource.ResourceLoader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Game extends Application {

	public enum State {
		SETUP, PLAY, MENU, EXIT
	}

	private State state = State.SETUP;
	private Level level;

	private Scene playScene, menuScene;

	@Override
	public void start(Stage stage) {
		ResourceLoader.INSTANCE.load("/res/bg.png", "/res/coin.png", "/res/coin0.png", "/res/heart0.png",
				"/res/meteor0.png", "/res/player.png", "/res/pixel.png");

		Logger.INSTANCE.print();
		Logger.INSTANCE.flush();

		Group local = new Group();
		playScene = new Scene(local, Commons.SCREEN_WIDTH, Commons.SCREEN_HEIGHT);

		menuScene = Menu.INSTANCE.getScene();

		Controller globalController = new Controller();

		InputEventAdapter inputEventAdapter = new InputEventAdapter(stage);
		inputEventAdapter.addController(globalController);

		stage.setOnCloseRequest(e -> Platform.exit());

		stage.addEventFilter(GameEvent.GAME_ANY, e -> {
			EventType<?> type = e.getEventType();
			if (type == GameEvent.GAME_OVER) {
				Upgrades.INSTANCE.save();

				globalController.detach();
				local.getChildren().clear();
				level = null;

				stage.setScene(menuScene);

				state = State.MENU;
			} else if (type == GameEvent.GAME_MENU) {
				// WHEN GO INTO MENU
			} else if (type == GameEvent.GAME_SHOP) {
				// WHEN GO INTO SHOP
			} else if (type == GameEvent.GAME_START) {
				Upgrades.INSTANCE.load();

				stage.setScene(playScene);

				level = new Level(local, globalController);
				state = State.PLAY;
			} else if (type == GameEvent.GAME_QUIT) {
				Platform.exit();
			} else {
				Logger.INSTANCE.log(Severity.WARNING, "Invalid event captured");
			}
		});

		new AnimationTimer() {
			@Override
			public void handle(long l) {
				/**
				 * TEST CODE
				 */
				if (inputEventAdapter.isPressed(KeyCode.ESCAPE)) {
					stage.fireEvent(new GameEvent(GameEvent.GAME_OVER));
				}

				if (inputEventAdapter.isPressed(KeyCode.F1)) {
					stage.fireEvent(new GameEvent(GameEvent.GAME_START));
				}

				switch (state) {
				case SETUP:
					stage.setScene(menuScene);
					state = State.MENU;
					break;
				case PLAY:
					level.tick();
					break;
				case MENU:
					break;
				case EXIT:
					stop();
				}

				inputEventAdapter.update();

				Logger.INSTANCE.print();
				Logger.INSTANCE.flush();
			}
		}.start();

		stage.setTitle("Undefined");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.centerOnScreen();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
