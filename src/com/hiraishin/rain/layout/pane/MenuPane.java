package com.hiraishin.rain.layout.pane;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.layout.LayoutItem;
import com.hiraishin.rain.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuPane extends GridPane {

	/*
	 * Constructors
	 */
	public MenuPane() {
		this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setAlignment(Pos.CENTER);

		Text label = new Text("RAIN");
		label.setFont(Font.font("", FontWeight.BOLD, 70));
		label.setFill(Commons.GRADIENT);
		label.setUnderline(true);

		this.add(label, 0, 0);

		VBox vbox = new VBox(15);
		vbox.getChildren().addAll(new LayoutItem("PLAY", StateEvent.PLAY), new LayoutItem("UPGRADES", StateEvent.SHOP),
				new LayoutItem("STATISTICS", StateEvent.STAT), new LayoutItem("HELP", StateEvent.HELP), new HBox(),
				new LayoutItem("QUIT", StateEvent.QUIT));

		this.add(vbox, 0, 1);
	}
}
