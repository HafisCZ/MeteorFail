package com.hiraishin.undefined.scene;

import java.util.Objects;

import com.hiraishin.undefined._entity.mob.Player;
import com.hiraishin.undefined._essentials.Upgrades;
import com.hiraishin.undefined._util.Dimensions;
import com.hiraishin.undefined._util.PortView;
import com.hiraishin.undefined._util.resource.ResourceLoader;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Overlay {

	public Overlay(Group group, Player player) {
		Objects.requireNonNull(group);
		Objects.requireNonNull(player);

		PortView heartView = new PortView(ResourceLoader.INSTANCE.getTexture("heart0"), 200, 40, 5, 1);
		heartView.setSmooth(true);
		heartView.setX(Dimensions.OVERLAY_X + 10);
		heartView.setY(Dimensions.OVERLAY_Y);
		heartView.setPort(player.getHealth() - 1, 0);
		group.getChildren().add(heartView);

		player.healthProperty().addListener((O, Prev, Next) -> {
			heartView.setPort(player.getHealth() - 1, 0);
		});

		PortView coinView = new PortView(ResourceLoader.INSTANCE.getTexture("coin"), 40, 40, 1, 5);
		coinView.setSmooth(true);
		coinView.setX(Dimensions.OVERLAY_X + Dimensions.SCREEN_WIDTH / 2);
		coinView.setY(Dimensions.OVERLAY_Y);
		coinView.setPort(0, 0);
		group.getChildren().add(coinView);

		Text coinCount = new Text();
		coinCount.setSmooth(true);
		coinCount.setX(Dimensions.OVERLAY_X + Dimensions.SCREEN_WIDTH / 2 + 40);
		coinCount.setY(Dimensions.OVERLAY_Y + 35);
		coinCount.setFont(Font.font("comic sans", 40));
		coinCount.setFill(Color.TOMATO);
		coinCount.setText("" + Upgrades.INSTANCE.moneyProperty().get());
		group.getChildren().add(coinCount);

		Upgrades.INSTANCE.moneyProperty().addListener((O, Prev, Next) -> {
			coinCount.setText("" + Upgrades.INSTANCE.moneyProperty().get());
		});
	}
}
