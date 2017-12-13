/*
 * DISCLAIMER:
 * 
 * Content of this class is purely experimental and should not be used as a measurement of quality
 * of this project. It is distributed AS-IS without any guarantees or rights reserved.
 */

package com.hiraishin.rain.experimental;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.util.Commons;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PausePane extends BorderPane {

    /*
     * Constructors
     */
    public PausePane() {
        this.setPrefSize(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        this.setPadding(new Insets(10, 10, 10, 10));

        MenuButton menu = new MenuButton(StateEvent.STOP);
        this.setBottom(menu);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text label = new Text("Game paused");
        label.setFont(Font.font("", FontWeight.BOLD, 30));
        label.setFill(Commons.GRADIENT2);
        label.setUnderline(true);

        Text text = new Text("Press ESC to unpause");
        text.setFont(Font.font("", FontWeight.BOLD, 15));
        text.setFill(Color.LIGHTSLATEGRAY);

        vbox.getChildren().addAll(label, text);

        this.setCenter(vbox);
    }

}
