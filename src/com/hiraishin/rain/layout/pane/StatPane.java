package com.hiraishin.rain.layout.pane;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.layout.BackButton;
import com.hiraishin.rain.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StatPane extends BorderPane {

	/*
	 * Constructors
	 */
	public StatPane() {
		this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
		this.setPadding(new Insets(10, 10, 10, 10));

		BackButton menu = new BackButton(StateEvent.MENU);
		this.setTop(menu);

		VBox vbox = new VBox(30);
		this.setCenter(vbox);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.TOP_CENTER);

		Text label = new Text("Statistics");
		label.setFont(Font.font("", FontWeight.BOLD, 30));
		label.setFill(Commons.GRADIENT2);
		label.setUnderline(true);

		vbox.getChildren().addAll(label);
	}

}
