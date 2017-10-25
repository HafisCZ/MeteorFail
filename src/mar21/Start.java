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
import mar21.input.EventAdapter;
import mar21.input.InputManager;
import mar21.input.bind.StrokeType;
import mar21.resources.ResourceManager;

public class Start extends Application {	
	public InputManager input;
	private EventAdapter adapter;

	private Game level;
	
	@Override
	public void start(Stage stage) throws Exception {
		ResourceManager resources = ResourceManager.requestInstance();
		resources.loadFiles(
			"/res/bg7.png",
			"/res/coin.png",
			"/res/coin0.png",
			"/res/heart0.png",
			"/res/meteor0.png",
			"/res/player.png"
		);
		Upgrades.load();

		this.adapter = new EventAdapter();
		this.input = new InputManager(adapter);
		stage.addEventHandler(KeyEvent.ANY, adapter);
		
		this.level = new Game(new Group(), input);
		stage.setScene(level.getScene());	
		
		stage.addEventHandler(GameEvent.GAME_OVER, e -> {
			level.reset();
			e.consume();
		});
		
		input.bind(KeyCode.ESCAPE, StrokeType.KEY_PRESSED, () -> stage.fireEvent(new GameEvent(GameEvent.GAME_OVER)));
	
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
