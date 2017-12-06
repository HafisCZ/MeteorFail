package com.hiraishin.rain.scene;

import com.hiraishin.rain.util.Commons;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MenuItem extends HBox {

	private Text label = new Text();
	private Runnable action;

	public MenuItem(String label, Runnable action) {
		this.label.setText(label);
		this.label.setFont(Commons.FONT_MENUITEM);
		this.action = action;

		setSelected(false);

		getChildren().add(this.label);
	}

	public void setSelected(boolean selected) {
		if (selected) {
			label.setFill(Color.WHITESMOKE);
		} else {
			label.setFill(Color.GRAY);
		}
	}

	public void run() {
		if (action != null) {
			action.run();
		}
	}
}
