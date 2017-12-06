package com.hiraishin.rain;

import java.util.ArrayList;
import java.util.List;

import com.hiraishin.rain.entity.Entity;
import com.hiraishin.rain.entity.mob.Player;
import com.hiraishin.rain.entity.particle.Particle;
import com.hiraishin.rain.entity.spawner.RaindropSpawner;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImagePreloader;
import com.hiraishin.rain.util.logger.Logger;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ImagePreloader.DEFAULT_LOADER.load(false, "res/background.png", "res/coin.png", "res/enemy.png",
				"res/heart.png", "res/player.png", "res/bg.png");

		Logger.INSTANCE.print(System.out::println);

		final Keyboard keyboard = new Keyboard(stage);

		List<Particle> particles = new ArrayList<>();

		Canvas canvas = new Canvas(Commons.GAME_SCENE_WIDTH, Commons.GAME_SCENE_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Player player = new Player(20, 200);
		keyboard.addControl(player);

		RaindropSpawner rs = new RaindropSpawner(0, -20, Commons.GAME_SCENE_WIDTH, 0);

		Scene scene = new Scene(new Group(canvas), Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);

		// stage.setScene(Menu.INSTANCE.getScene());
		stage.setScene(scene);

		stage.setTitle("Rain");

		stage.show();
		
		Image i1 = ImagePreloader.DEFAULT_LOADER.getImage("bg");
		
		new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				keyboard.update();

				for (int i = 0; i < 4; i++) {
					particles.add(rs.get());
				}

				player.tick();
				for (Particle particle : particles) {
					particle.tick();
					
					if (player.getBounds().intersects(particle.getBounds())) {
						particle.kill();
					}
				}
				
				particles.removeIf(Entity::isDead);
				
				gc.drawImage(i1, 0, 0, Commons.GAME_SCENE_WIDTH, Commons.GAME_SCENE_HEIGHT);
				
				player.draw(gc);
				for (Particle particle : particles) {
					particle.draw(gc);
				}

			}

		}.start();
	}

}
