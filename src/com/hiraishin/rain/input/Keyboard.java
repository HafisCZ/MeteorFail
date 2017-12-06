package com.hiraishin.rain.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Keyboard implements EventHandler<KeyEvent> {

	/**
	 * List of controlled entities
	 */
	private List<Controlled> controlled = new ArrayList<>();

	/**
	 * Maps for mapping key presses / releases
	 */
	private Map<KeyCode, Boolean> currentMap = new HashMap<>();
	private Map<KeyCode, Boolean> previousMap = new HashMap<>();

	/**
	 * Constructor
	 * 
	 * @param stage
	 *            Source of events
	 */
	public Keyboard(Stage stage) {
		synchronized (stage) {
			stage.addEventHandler(KeyEvent.KEY_PRESSED, this);
			stage.addEventHandler(KeyEvent.KEY_RELEASED, this);
		}
	}

	/**
	 * Add entity to control
	 * 
	 * @param entity
	 */
	public void addControl(Controlled entity) {
		controlled.add(entity);
	}

	/**
	 * Release entity from control
	 * 
	 * @param entity
	 */
	public void removeControl(Controlled entity) {
		controlled.remove(entity);
	}

	/**
	 * Update
	 */
	public void update() {
		previousMap.putAll(currentMap);
		controlled.removeIf(Objects::isNull);
		controlled.forEach(C -> C.control(this));
	}

	/**
	 * Overriden Event Handler function
	 */
	@Override
	public void handle(KeyEvent keyEvent) {
		final KeyCode keyCode = keyEvent.getCode();
		final EventType<KeyEvent> eventType = keyEvent.getEventType();

		if (eventType == KeyEvent.KEY_PRESSED) {
			currentMap.put(keyCode, true);
		} else if (eventType == KeyEvent.KEY_RELEASED) {
			currentMap.put(keyCode, false);
		}

		keyEvent.consume();
	}

	/**
	 * If key is held
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean isHeld(KeyCode keyCode) {
		return currentMap.getOrDefault(keyCode, false);
	}

	/**
	 * If key was held
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean wasHeld(KeyCode keyCode) {
		return previousMap.getOrDefault(keyCode, false);
	}

	/**
	 * If key was pressed
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean isPressed(KeyCode keyCode) {
		return isHeld(keyCode) && !wasHeld(keyCode);
	}

	/**
	 * If key was released
	 * 
	 * @param keyCode
	 * @return
	 */
	public boolean wasPressed(KeyCode keyCode) {
		return !isHeld(keyCode) && wasHeld(keyCode);
	}

}
