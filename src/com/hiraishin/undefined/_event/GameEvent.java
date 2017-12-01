package com.hiraishin.undefined._event;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event {

	private static final long serialVersionUID = 1L;

	public static final EventType<GameEvent> GAME_ANY, GAME_OVER, GAME_START, GAME_MENU, GAME_SHOP;
	static {
		GAME_ANY = new EventType<>(ANY, "GAME_ANY");
		GAME_OVER = new EventType<>(GAME_ANY, "GAME_OVER");
		GAME_START = new EventType<>(GAME_ANY, "GAME_START");
		GAME_MENU = new EventType<>(GAME_ANY, "GAME_MENU");
		GAME_SHOP = new EventType<>(GAME_ANY, "GAME_SHOP");
	}

	public GameEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

}
