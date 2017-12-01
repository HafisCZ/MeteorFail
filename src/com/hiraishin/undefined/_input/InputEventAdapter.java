package com.hiraishin.undefined._input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hiraishin.undefined._entity.controller.Controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public final class InputEventAdapter implements EventHandler<Event> {

	private final Map<KeyCode, Boolean> keyReg, keyRegL;
	private final Map<MouseButton, Boolean> mouseReg, mouseRegL;
	private final List<Controller> controllers;

	private double mouseX, mouseY;

	public InputEventAdapter(Stage source) {
		keyReg = new HashMap<>();
		mouseReg = new HashMap<>();

		keyRegL = new HashMap<>();
		mouseRegL = new HashMap<>();

		controllers = new ArrayList<>();

		source.addEventHandler(KeyEvent.KEY_PRESSED, this);
		source.addEventHandler(KeyEvent.KEY_RELEASED, this);
		source.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		source.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
	}

	@Override
	public void handle(Event event) {
		if (event instanceof KeyEvent) {
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
				keyReg.put(keyEvent.getCode(), true);
			} else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED) {
				keyReg.put(keyEvent.getCode(), false);
			}
		} else if (event instanceof MouseEvent) {
			MouseEvent mouseEvent = (MouseEvent) event;
			if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
				mouseReg.put(mouseEvent.getButton(), true);
			} else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
				mouseReg.put(mouseEvent.getButton(), false);
			}

			mouseX = mouseEvent.getX();
			mouseY = mouseEvent.getY();
		}

		event.consume();
	}

	public void addController(Controller controller) {
		controllers.add(controller);
	}

	public void removeController(Controller controller) {
		controllers.remove(controller);
	}

	public boolean isHeld(KeyCode keyCode) {
		return keyReg.getOrDefault(keyCode, false);
	}

	public boolean wasHeld(KeyCode keyCode) {
		return keyRegL.getOrDefault(keyCode, false);
	}

	public boolean isPressed(KeyCode keyCode) {
		return isHeld(keyCode) && !wasHeld(keyCode);
	}

	public boolean wasPressed(KeyCode keyCode) {
		return !isHeld(keyCode) && wasHeld(keyCode);
	}

	public boolean isHeld(MouseButton mouseButton) {
		return mouseReg.getOrDefault(mouseButton, false);
	}

	public boolean wasHeld(MouseButton mouseButton) {
		return mouseRegL.getOrDefault(mouseButton, false);
	}

	public boolean isPressed(MouseButton mouseButton) {
		return isHeld(mouseButton) && !wasHeld(mouseButton);
	}

	public boolean wasPressed(MouseButton mouseButton) {
		return !isHeld(mouseButton) && wasHeld(mouseButton);
	}

	public double mouseX() {
		return mouseX;
	}

	public double mouseY() {
		return mouseY;
	}

	public void update() {
		for (Controller controller : controllers) {
			controller.notify(this);
		}

		keyRegL.putAll(keyReg);
		mouseRegL.putAll(mouseReg);
	}
}
