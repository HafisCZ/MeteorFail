package com.hiraishin.rain;

import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.scene.Menu;
import com.hiraishin.rain.util.ImagePreloader;
import com.hiraishin.rain.util.logger.Logger;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ImagePreloader.DEFAULT_LOADER.load(false, "res/coin.png", "res/acid.png", "res/heart.png", "res/player.png",
				"res/bg.png", "res/armor.png", "res/heart_frame.png", "res/heart_armor.png", "res/xpbar.png",
				"res/bar_outline.png", "res/energybar.png");

		Logger.INSTANCE.print(System.out::println);

		final Keyboard keyboard = new Keyboard(stage);

		Menu.INSTANCE.setKeyboard(keyboard);

		stage.setScene(Menu.INSTANCE.getScene());
		// stage.setScene(scene);

		stage.setTitle("Rain");
		stage.show();

		new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// keyboard.update();

				Menu.INSTANCE.getGame().update();
			}

		}.start();
	}

}
