package com.hiraishin.undefined.event;

import javafx.event.EventType;

public class GameEvent extends javafx.event.Event {

	private static final long serialVersionUID = 1L;
	
	public static final EventType<GameEvent> GAME_ANY = new EventType<>(ANY, "GAME_ANY");
	public static final EventType<GameEvent> GAME_OVER = new EventType<>(GAME_ANY, "GAME_OVER");
	public static final EventType<GameEvent> GAME_PLAY = new EventType<>(GAME_ANY, "GAME_PLAY");
	public static final EventType<GameEvent> GAME_PAUSE = new EventType<>(GAME_ANY, "GAME_PAUSE");
	
	public GameEvent(EventType<? extends javafx.event.Event> arg0) {
		super(arg0);
	}
	
}
