package mar21;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import mar21.event.GameEvent;
import mar21.game.Game;
import mar21.game.Upgrades;
import mar21.input.InputHandler;
import mar21.input.KeyStroke;
import mar21.resources.ResourceLoader;

public class Start extends Application {	
	
	public InputHandler input;
	private Game level;
	
	@Override
	public void start(Stage stage) throws Exception {
		ResourceLoader.load(
			"/res/bg7.png",
			"/res/coin.png",
			"/res/coin0.png",
			"/res/heart0.png",
			"/res/meteor0.png",
			"/res/player.png"
		);

		Upgrades.load();

		input = new InputHandler();
		input.bind(KeyCode.ESCAPE, KeyStroke.KEY_PRESSED, () -> {
			stage.fireEvent(new GameEvent(GameEvent.GAME_OVER));
		});
		
		level = new Game(new Group(), input);
		
		stage.setScene(level.getScene());
		stage.addEventHandler(KeyEvent.ANY, input);
		stage.addEventHandler(GameEvent.GAME_OVER, e -> {
			level.reset();
			e.consume();
		});

		stage.sizeToScene();
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
	
		new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				input.update();
				level.update();
			}
		}.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
