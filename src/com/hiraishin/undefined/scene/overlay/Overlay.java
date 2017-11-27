package com.hiraishin.undefined.scene.overlay;

import com.hiraishin.undefined.entity.mob.Player;
import com.hiraishin.undefined.utils.ResourceLoader;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Overlay extends Group {

	private static final double X_OFFSET = 10;
	private static final double Y_OFFSET = 0;
	private static final double HEARTS_SPRITE_WIDTH = 500;
	private static final double HEARTS_SPRITE_HEIGHT = 100;

	private Player player;
	private ImageView hearts;
	private Text text;
	
	public Overlay(double screenWidth, double screenHeight) {
		hearts = new ImageView();
		hearts.setImage(ResourceLoader.getTexture("heart0"));
		hearts.setFitWidth(HEARTS_SPRITE_WIDTH / 3);
		hearts.setFitHeight(HEARTS_SPRITE_HEIGHT / 3);
		hearts.setX(X_OFFSET);
		hearts.setY(screenHeight - Y_OFFSET - HEARTS_SPRITE_HEIGHT / 2);
		this.getChildren().add(hearts);
		
		ImageView coin = new ImageView();
		coin.setImage(ResourceLoader.getTexture("coin0"));
		coin.setFitWidth( HEARTS_SPRITE_WIDTH / 15);
		coin.setFitHeight(HEARTS_SPRITE_HEIGHT / 3);
		hearts.setX(X_OFFSET);
		hearts.setY(screenHeight - Y_OFFSET - HEARTS_SPRITE_HEIGHT / 2);
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
		text.setText("" + Upgrade.getInstance().getCoins());
	}
}
