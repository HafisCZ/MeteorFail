/*
 * Copyright (c) 2017 - 2018 Hiraishin Software. All Rights Reserved.
 */

package com.hiraishin.rain.event;

import javafx.event.Event;
import javafx.event.EventType;

public class StateEvent extends Event {

    /**
     * 
     */
    private static final long serialVersionUID = 4606669955706420844L;

    /*
     * Definitions
     */
    public static final EventType<StateEvent> STATE = new EventType<>(ANY, "STATE");

    public static final EventType<StateEvent> MENU = new EventType<>(STATE, "MENU");
    public static final EventType<StateEvent> SHOP = new EventType<>(STATE, "SHOP");
    public static final EventType<StateEvent> STAT = new EventType<>(STATE, "STAT");
    public static final EventType<StateEvent> HELP = new EventType<>(STATE, "HELP");
    public static final EventType<StateEvent> QUIT = new EventType<>(STATE, "QUIT");

    public static final EventType<StateEvent> GAME = new EventType<>(STATE, "GAME");

    public static final EventType<StateEvent> PLAY = new EventType<>(GAME, "PLAY");
    public static final EventType<StateEvent> PAUSE = new EventType<>(GAME, "PAUSE");
    public static final EventType<StateEvent> UNPAUSE = new EventType<>(GAME, "UNPAUSE");
    public static final EventType<StateEvent> STOP = new EventType<>(GAME, "STOP");

    /*
     * Constructors
     */
    public StateEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

}
