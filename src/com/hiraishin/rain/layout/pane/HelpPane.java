package com.hiraishin.rain.layout.pane;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.layout.MenuButton;
import com.hiraishin.rain.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class HelpPane extends BorderPane {

	/*
	 * Constructors
	 */
	public HelpPane() {
		this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
		this.setPadding(new Insets(10, 10, 10, 10));

		MenuButton menu = new MenuButton(StateEvent.MENU);
		this.setTop(menu);

		VBox vbox = new VBox(30);
		this.setCenter(vbox);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.TOP_CENTER);

		Text label = new Text("Controls");
		label.setFont(Font.font("", FontWeight.BOLD, 30));
		label.setFill(Commons.GRADIENT2);
		label.setUnderline(true);

		HBox hbox = new HBox(60);
		hbox.setAlignment(Pos.CENTER);

		VBox vbox1 = new VBox(10);
		vbox1.setAlignment(Pos.CENTER);

		VBox vbox2 = new VBox(10);
		vbox2.setAlignment(Pos.CENTER_LEFT);

		addEntry(vbox1, vbox2, "A", "Move left");
		addEntry(vbox1, vbox2, "D", "Move right");
		addEntry(vbox1, vbox2, "SPACE", "Jump");
		addEntry(vbox1, vbox2, "F", "Activate skill");

		hbox.getChildren().addAll(vbox1, vbox2);

		Text label2 = new Text("About");
		label2.setFont(Font.font("", FontWeight.BOLD, 30));
		label2.setFill(Commons.GRADIENT2);
		label2.setUnderline(true);

		Text about = new Text("Evade falling acid, touching it removes your health."
				+ "\nYou can collect shields to protect your remaining hearts."
				+ "\nWhen you play you automaticaly gain experience."
				+ "\nYou get one point for every level reached for what you can buy upgrades and unlock skills."
				+ "\nCollect power nodes to charge your skill."
				+ "\nDifferent skills take different amount of power nodes to charge."
				+ "\nWhen skill is charged, the bar turns yellow.");
		about.setFill(Color.LIGHTSLATEGRAY);
		about.setFont(Font.font("", FontWeight.LIGHT, 15));
		about.setTextAlignment(TextAlignment.CENTER);

		vbox.getChildren().addAll(label, hbox, label2, about);
	}

	/*
	 * Static functions
	 */
	private static void addEntry(VBox v1, VBox v2, String s1, String s2) {
		Text t1 = new Text(s1);
		t1.setFill(Color.LIGHTSLATEGRAY);
		t1.setFont(Font.font("", FontWeight.BOLD, 20));

		Text t2 = new Text(s2);
		t2.setFill(Color.LIGHTSLATEGRAY);
		t2.setFont(Font.font("", FontWeight.NORMAL, 20));

		v1.getChildren().add(t1);
		v2.getChildren().add(t2);
	}
}