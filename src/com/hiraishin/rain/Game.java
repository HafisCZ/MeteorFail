package com.hiraishin.rain;

import com.hiraishin.rain.event.StateEvent;
import com.hiraishin.rain.input.Keyboard;
import com.hiraishin.rain.level.Level;
import com.hiraishin.rain.level.Level.LevelController;
import com.hiraishin.rain.util.Commons;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Game {

    private final Canvas canvas = new Canvas(Commons.SCENE_WIDTH, Commons.SCENE_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private final Level level;
    private final LevelController levelController;

    public Game(Keyboard keyboard) {
        this.level = new Level(keyboard);
        this.levelController = level.getLevelController();

        new AnimationTimer() {

            @Override
            public void handle(long now) {
                level.tick();
                level.draw(gc);

                if (levelController.isClosed()) {
                    canvas.fireEvent(new StateEvent(StateEvent.MENU));
                }

                if (keyboard.isPressed(KeyCode.ESCAPE)) {
                    if (levelController.isRunning()) {
                        if (levelController.isPaused()) {
                            canvas.fireEvent(new StateEvent(StateEvent.UNPAUSE));
                        } else {
                            canvas.fireEvent(new StateEvent(StateEvent.PAUSE));
                        }
                    } else {
                        canvas.fireEvent(new StateEvent(StateEvent.MENU));
                    }
                }

                keyboard.update();
            }
        }.start();
    }

    public void close() {
        this.levelController.endGame();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void pause() {
        this.levelController.pauseGame();
    }

    public void play() {
        this.levelController.startGame();
    }

    public void unpause() {
        this.levelController.unpauseGame();
    }
}
