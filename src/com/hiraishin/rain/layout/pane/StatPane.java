package com.hiraishin.rain.layout.pane;

import java.util.Map;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.layout.MenuButton;
import com.hiraishin.rain.level.player.PlayData;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.Data;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StatPane extends BorderPane {

	private VBox vbox1, vbox2;

	/*
	 * Constructors
	 */
	public StatPane() {
		this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
		this.setPadding(new Insets(10, 10, 10, 10));

		MenuButton menu = new MenuButton(StateEvent.MENU);
		this.setTop(menu);

		VBox vbox = new VBox(30);
		this.setCenter(vbox);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setAlignment(Pos.TOP_CENTER);

		Text label = new Text("Statistics");
		label.setFont(Font.font("", FontWeight.BOLD, 30));
		label.setFill(Commons.GRADIENT2);
		label.setUnderline(true);

		HBox hbox = new HBox(60);
		hbox.setAlignment(Pos.CENTER);

		vbox1 = new VBox(10);
		vbox1.setAlignment(Pos.CENTER);

		vbox2 = new VBox(10);
		vbox2.setAlignment(Pos.CENTER_LEFT);

		hbox.getChildren().addAll(vbox1, vbox2);
		vbox.getChildren().addAll(label, hbox);

		refresh();
	}

	/*
	 * Instance functions
	 */
	public void refresh() {
		vbox1.getChildren().clear();
		vbox2.getChildren().clear();

		Map<Integer, Data<Integer>> stats = PlayData.INSTANCE.getStatistics();
		for (Map.Entry<Integer, Data<Integer>> e : stats.entrySet()) {
			addEntry(vbox1, vbox2, e.getValue().getName(), e.getValue().getValue().toString());
		}
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
