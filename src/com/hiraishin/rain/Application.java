package com.hiraishin.rain;

import java.util.Objects;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.experimental.HelpPane;
import com.hiraishin.rain.experimental.MenuPane;
import com.hiraishin.rain.experimental.PausePane;
import com.hiraishin.rain.experimental.ShopPane;
import com.hiraishin.rain.experimental.StatPane;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.GameData;
import com.hiraishin.rain.util.Commons;
import com.hiraishin.rain.util.ImageLoader;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private static final String VERSION = "v3.4.6";
    private static final String TITLE_DEBUG_PREFIX = "[DEBUG MODE]";
    private static final String TITLE = "Rain";

    private static final String ARG_DEBUG = "-debug";

    public static boolean DEBUG_MODE = false;

    private Scene scene;
    private Group root;
    private Group group;

    private Keyboard keyboard;

    private Game game;

    private Pane paneMenu;
    private Pane paneShop;
    private Pane paneStat;
    private Pane paneHelp;
    private Pane panePause;

    public static void main(String... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(ARG_DEBUG)) {
                DEBUG_MODE = true;
            }
        }

        LauncherImpl.launchApplication(Application.class, args);
    }

    private static void switchPane(Group parent, Pane pane) {
        parent.getChildren().clear();

        if (Objects.nonNull(pane)) {
            parent.getChildren().add(pane);
        }
    }

    @Override
    public void init() {
        GameData.load();

        ImageLoader.INSTANCE.setCommonPrefix("res/");
        ImageLoader.INSTANCE.setCommonSuffix(".png");
        ImageLoader.INSTANCE.preferExternalSources(true);

        ImageLoader.INSTANCE.load("background/background");
        ImageLoader.INSTANCE.load("entity/acid");
        ImageLoader.INSTANCE.load("entity/armor");
        ImageLoader.INSTANCE.load("entity/energy");
        ImageLoader.INSTANCE.load("entity/player");
        ImageLoader.INSTANCE.load("entity/star");
        ImageLoader.INSTANCE.load("gui/bars/armor");
        ImageLoader.INSTANCE.load("gui/bars/energy");
        ImageLoader.INSTANCE.load("gui/bars/experience");
        ImageLoader.INSTANCE.load("gui/bars/frame");
        ImageLoader.INSTANCE.load("gui/bars/health");
        ImageLoader.INSTANCE.load("gui/icons/ability");
        ImageLoader.INSTANCE.load("gui/icons/back");
        ImageLoader.INSTANCE.load("gui/icons/energy");
        ImageLoader.INSTANCE.load("gui/icons/experience");
        ImageLoader.INSTANCE.load("gui/icons/frame");
        ImageLoader.INSTANCE.load("gui/icons/health");

        this.paneMenu = new MenuPane();
        this.paneShop = new ShopPane();
        this.paneStat = new StatPane();
        this.paneHelp = new HelpPane();
        this.panePause = new PausePane();

        this.keyboard = new Keyboard();
        this.game = new Game(this.keyboard);

        this.group = new Group();
        this.root = new Group(this.game.getCanvas(), this.group);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.keyboard.addEventSource(stage);

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

        stage.setTitle((DEBUG_MODE ? TITLE_DEBUG_PREFIX + " " : "") + TITLE + " " + VERSION);
        stage.setResizable(false);
        stage.show();

        stage.fireEvent(new StateEvent(StateEvent.MENU));
    }

}
