package com.hiraishin.undefined;

import com.hiraishin.undefined.event.GameEvent;
import com.hiraishin.undefined.input.UIAdapter;
import com.hiraishin.undefined.input.bind.KeyStroke;
import com.hiraishin.undefined.scene.GameScene;
import com.hiraishin.undefined.scene.LevelScene;
import com.hiraishin.undefined.utils.ReportManager;
import com.hiraishin.undefined.utils.ResourceLoader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Game extends Application {
	
	public static final double RES_WIDTH = 1000;
	public static final double RES_HEIGHT = 700;
	
	public GameScene gameScene;

	@Override
	public void start(Stage stage) {
		
		/**
		 * Load textures
		 */
		ResourceLoader.load(
			"/res/bg.png",
			"/res/coin.png",
			"/res/coin0.png",
			"/res/heart0.png",
			"/res/meteor0.png",
			"/res/player.png",
			"/res/pixel.png"		
		);
		
		/**
		 * Printout all errors
		 */
		ReportManager.ss13ok();
		ReportManager.clear();
		
		/**
		 * Create adapter and global bindings
		 */
		UIAdapter adapter = new UIAdapter();
		adapter.bind(KeyCode.ESCAPE, KeyStroke.KEY_PRESSED, () -> {
			stage.fireEvent(new GameEvent(GameEvent.GAME_PAUSE));
		});
		
		/**
		 * Add handlers
		 */
		stage.addEventHandler(KeyEvent.ANY, adapter);
		stage.addEventHandler(GameEvent.GAME_OVER, e -> {
			gameScene.reset();
		});
		stage.addEventHandler(GameEvent.GAME_PAUSE, e -> {
			// pause game
		});
		stage.addEventHandler(GameEvent.GAME_PLAY, e -> {
			// start game
		});

		/**
		 * Set up game scene
		 */
		gameScene = new LevelScene(adapter);
		stage.setScene(gameScene.getScene());
		stage.sizeToScene();
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
		
		/**
		 * Launch game loop
		 */
		new AnimationTimer() {
			@Override
			public void handle(long l) {
				/**
				 * Update input adapter
				 */
				adapter.update();
				
				/**
				 * Update game scene
				 */
				gameScene.update();
				
				/**
				 * Printout all errors
				 */
				ReportManager.ss13ok();
				ReportManager.clear();
			}
		}.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
