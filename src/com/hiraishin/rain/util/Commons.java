package com.hiraishin.rain.util;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public class Commons {

    public static final Random RANDOM = new Random();

    public static final double ZERO = 0;

    public static final double SCENE_WIDTH = 1000;
    public static final double SCENE_HEIGHT = 700;
    public static final double SCENE_GROUND = SCENE_HEIGHT / 40 * 33;

    public static final LinearGradient GRADIENT = new LinearGradient(0, 0, 0.5, 1, true,
                                                                     CycleMethod.NO_CYCLE,
                                                                     new Stop[] {
                                                                             new Stop(0,
                                                                                      Color.DARKCYAN),
                                                                             new Stop(1,
                                                                                      Color.BLUEVIOLET) });

    public static final LinearGradient GRADIENT2 = new LinearGradient(0, 0, 0.5, 1, true,
                                                                      CycleMethod.NO_CYCLE,
                                                                      new Stop[] {
                                                                              new Stop(0,
                                                                                       Color.WHITESMOKE),
                                                                              new Stop(1,
                                                                                       Color.LIGHTGRAY) });

}
