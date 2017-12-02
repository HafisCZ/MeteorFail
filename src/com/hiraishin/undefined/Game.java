package com.hiraishin.undefined;

import com.hiraishin.undefined._entity.controller.Controller;
import com.hiraishin.undefined._essentials.Upgrades;
import com.hiraishin.undefined._event.GameEvent;
import com.hiraishin.undefined._input.InputEventAdapter;
import com.hiraishin.undefined._level.Level;
import com.hiraishin.undefined._util.Dimensions;
import com.hiraishin.undefined._util.logger.Logger;
import com.hiraishin.undefined._util.logger.Severity;
import com.hiraishin.undefined._util.resource.ResourceLoader;

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

	@Override
	public void start(Stage stage) {
		ResourceLoader.INSTANCE.load("/res/bg.png", "/res/coin.png", "/res/coin0.png", "/res/heart0.png",
				"/res/meteor0.png", "/res/player.png", "/res/pixel.png");

		Logger.INSTANCE.print();
		Logger.INSTANCE.flush();

		Group local = new Group();
		Scene scene = new Scene(local, Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
		stage.setScene(scene);

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
				state = State.MENU;
			} else if (type == GameEvent.GAME_MENU) {
				// WHEN GO INTO MENU
			} else if (type == GameEvent.GAME_SHOP) {
				// WHEN GO INTO SHOP
			} else if (type == GameEvent.GAME_START) {
				Upgrades.INSTANCE.load();
				
				level = new Level(local, globalController);
				state = State.PLAY;
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
