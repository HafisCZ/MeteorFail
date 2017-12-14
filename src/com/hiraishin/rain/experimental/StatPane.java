/*
 * DISCLAIMER:
 * 
 * Content of this class is purely experimental and should not be used as a measurement of quality
 * of this project. It is distributed AS-IS without any guarantees or rights reserved.
 */

package com.hiraishin.rain.experimental;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.level.PlayData;
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

public class StatPane extends BorderPane {

    private VBox vbox1, vbox2;

    /*
     * Static functions
     */
    private static void addEntry(VBox v1, VBox v2, String name, PlayData pd) {
        Text t1 = new Text(name);
        t1.setFill(Color.LIGHTSLATEGRAY);
        t1.setFont(Font.font("", FontWeight.BOLD, 20));

        Text t2 = new Text("" + pd.getValue());
        t2.setFill(Color.LIGHTSLATEGRAY);
        t2.setFont(Font.font("", FontWeight.NORMAL, 20));

        v1.getChildren().add(t1);
        v2.getChildren().add(t2);
    }

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
    }

    /*
     * Instance functions
     */
    public void refresh() {
        vbox1.getChildren().clear();
        vbox2.getChildren().clear();

        addEntry(vbox1, vbox2, "Damage taken:", PlayData.STAT_COUNT_DAMAGE);
        addEntry(vbox1, vbox2, "Experience gained:", PlayData.STAT_COUNT_EXPERIENCE);
        addEntry(vbox1, vbox2, "Times jumped:", PlayData.STAT_COUNT_JUMP);
        addEntry(vbox1, vbox2, "Nodes collected:", PlayData.STAT_COUNT_NODES);
        addEntry(vbox1, vbox2, "Shields collected:", PlayData.STAT_COUNT_SHIELD);
        addEntry(vbox1, vbox2, "Skills activated:", PlayData.STAT_COUNT_SKILLACTIVATION);
    }

}
