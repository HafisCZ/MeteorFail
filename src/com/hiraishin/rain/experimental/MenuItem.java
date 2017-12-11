package com.hiraishin.rain.experimental;

import com.hiraishin.rain.event.StateEvent;

import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuItem extends HBox {

	/*
	 * Definitions
	 */
	public static final Font FONT_LAYOUT_ITEM = Font.font("", FontWeight.BOLD, 18);

	/*
	 * Constructors
	 */
	public MenuItem(String label, EventType<StateEvent> eventType) {
		Text labelText = new Text(label);
		labelText.setFont(FONT_LAYOUT_ITEM);
		labelText.setFill(Color.GRAY);

		labelText.setOnMouseClicked(event -> {
			fireEvent(new StateEvent(eventType));
		});

		labelText.setOnMouseEntered(event -> {
			labelText.setFill(Color.WHITESMOKE);
		});

		labelText.setOnMouseExited(event -> {
			labelText.setFill(Color.GRAY);
		});

		this.getChildren().add(labelText);
		this.setAlignment(Pos.BASELINE_CENTER);
	}

}