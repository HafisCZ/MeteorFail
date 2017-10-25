package mar21.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mar21.entity.player.Player;
import mar21.resources.ResourceManager;

public class Overlay extends Group {

	private static final double X_OFFSET = 10;
	private static final double Y_OFFSET = 0;
	private static final double HEARTS_SPRITE_WIDTH = 500;
	private static final double HEARTS_SPRITE_HEIGHT = 100;
	
	private final ResourceManager resources = ResourceManager.requestInstance();
	
	private Player player;
	private ImageView hearts;
	private Text text;
	
	public Overlay(double screenWidth, double screenHeight) {
		hearts = resources.buildImageView("heart0", HEARTS_SPRITE_WIDTH / 3, HEARTS_SPRITE_HEIGHT / 3);
		hearts.setX(X_OFFSET);
		hearts.setY(screenHeight - Y_OFFSET - HEARTS_SPRITE_HEIGHT / 2);
		this.getChildren().add(hearts);
		
		ImageView coin = resources.buildImageView("coin0", HEARTS_SPRITE_WIDTH / 15, HEARTS_SPRITE_HEIGHT / 3);
		coin.setX(X_OFFSET);
		coin.setY(screenHeight - Y_OFFSET - 90);
		this.getChildren().add(coin);
		
		text = new Text();
		text.setFont(Font.font("comic sans", HEARTS_SPRITE_HEIGHT / 3));
		text.setFill(Color.LIGHTGOLDENRODYELLOW);
		text.setX(X_OFFSET + 40);
		text.setY(screenHeight - Y_OFFSET - 60);
		this.getChildren().add(text);
	}
	
	public void init(Player player) {
		this.player = player;
		update();
	}
	
	public void update() {
		hearts.setViewport(new Rectangle2D(0, (player.getHealth() - 1) * HEARTS_SPRITE_HEIGHT, HEARTS_SPRITE_WIDTH, HEARTS_SPRITE_HEIGHT));
		text.setText("" + Upgrades.getInstance().getCoins());
	}
}
