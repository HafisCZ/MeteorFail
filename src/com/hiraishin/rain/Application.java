package com.hiraishin.rain;

import java.util.Objects;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.experimental.HelpPane;
import com.hiraishin.rain.experimental.MenuPane;
import com.hiraishin.rain.experimental.PausePane;
import com.hiraishin.rain.experimental.ShopPane;
import com.hiraishin.rain.experimental.StatPane;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.PlayData;
import com.hiraishin.rain.util.Commons;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private Scene scene;
    private Group root;
    private Group group;

    private Keyboard keyboard;

    private Game game;

    private Pane paneMenu = new MenuPane();
    private Pane paneShop = new ShopPane();
    private Pane paneStat = new StatPane();
    private Pane paneHelp = new HelpPane();
    private Pane panePause = new PausePane();

    public static void main(String... args) {
        launch(args);
    }

    private static void switchPane(Group parent, Pane pane) {
        parent.getChildren().clear();

        if (Objects.nonNull(pane)) {
            parent.getChildren().add(pane);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        PlayData.load();

        this.keyboard = new Keyboard(stage);

        this.game = new Game(this.keyboard);

        this.group = new Group();
        this.root = new Group(this.game.getCanvas(), this.group);

        this.scene = new Scene(this.root, Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
        stage.setScene(this.scene);
        stage.sizeToScene();

        stage.addEventHandler(StateEvent.STATE, event -> {
            final EventType<? extends Event> eventType = event.getEventType();

            if (eventType == StateEvent.MENU) {
                switchPane(this.group, this.paneMenu);
            } else if (eventType == StateEvent.PLAY) {
                switchPane(this.group, null);
                this.game.play();
            } else if (eventType == StateEvent.SHOP) {
                ((ShopPane) this.paneShop).refresh();
                switchPane(this.group, this.paneShop);
            } else if (eventType == StateEvent.STAT) {
                ((StatPane) this.paneStat).refresh();
                switchPane(this.group, this.paneStat);
            } else if (eventType == StateEvent.QUIT) {
                Platform.exit();
            } else if (eventType == StateEvent.HELP) {
                switchPane(this.group, this.paneHelp);
            } else if (eventType == StateEvent.PAUSE) {
                switchPane(this.group, this.panePause);
                this.game.pause();
            } else if (eventType == StateEvent.UNPAUSE) {
                switchPane(this.group, null);
                this.game.unpause();
            } else if (eventType == StateEvent.STOP) {
                switchPane(this.group, this.paneMenu);
                this.game.close();
            }

            event.consume();
        });

        stage.setTitle("Rain");
        stage.setResizable(false);
        stage.show();
        stage.fireEvent(new StateEvent(StateEvent.MENU));
    }

}
