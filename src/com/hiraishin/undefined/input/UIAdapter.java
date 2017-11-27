package com.hiraishin.undefined.input;

import java.util.HashMap;

import com.hiraishin.undefined.input.bind.KeyAction;
import com.hiraishin.undefined.input.bind.KeyStroke;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class UIAdapter implements EventHandler<Event> {

	private class Pair<T, U> {
		
		private T t;
		private U u;
		
		public Pair(T t, U u) {
			this.t = t;
			this.u = u;
		}
		
		public T first() {
			return this.t;
		}
		
		public U second() {
			return this.u;
		}
		
	}
	
	private final HashMap<KeyCode, Boolean> kP = new HashMap<>(), kC = new HashMap<>();
	private final HashMap<MouseButton, Boolean> mP = new HashMap<>(), mC = new HashMap<>();
	private final HashMap<Pair<KeyCode, KeyStroke>, KeyAction> kB = new HashMap<>();
	private double mX, mY;
	
	@Override
	public void handle(Event event) {
		if (event instanceof KeyEvent) {
			handleKeyEvent((KeyEvent) event);
		} else if (event instanceof MouseEvent) {
			handleMouseEvent((MouseEvent) event);
		}
		
		event.consume();
	}

	private void handleKeyEvent(KeyEvent event) {	
		if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
			kC.put(event.getCode(), true);
		} else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
			kC.put(event.getCode(), false);
		}
	}
	
	private void handleMouseEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			mC.put(event.getButton(), true);
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			mC.put(event.getButton(), false);
		}
		
		mX = event.getX();
		mY = event.getY();
	}
	
	private void handleBinds() {
		kB.forEach((Pair, KeyBind) -> {
			switch (Pair.second()) {
				case KEY_HELD:
					if (isHeld(Pair.first())) {
						KeyBind.handle();
					}
					
					break;
				case KEY_PRESSED:
					if (isPressed(Pair.first())) {
						KeyBind.handle();
					}
					
					break;
				case KEY_RELEASED:
					if (wasPressed(Pair.first())) {
						KeyBind.handle();
					}
					
					break;
			}
		});
	}
	
	public void update() {
		handleBinds();
		
		mP.putAll(mC);
		kP.putAll(kC);
	}
	
	public void bind(KeyCode key, KeyStroke stroke, KeyAction bind) {
		kB.put(new Pair<>(key, stroke), bind);
	}
	
	public boolean isHeld(KeyCode key) {
		if (kC.containsKey(key)) {
			return kC.get(key);
		} else {
			return false;
		}
	}
	
	public boolean wasHeld(KeyCode key) {
		if (kP.containsKey(key)) {
			return kP.get(key);
		} else {
			return false;
		}
	}
	
	public boolean isPressed(KeyCode key) {
		return (isHeld(key) && !wasHeld(key));
	}
	
	public boolean wasPressed(KeyCode key) {
		return (!isHeld(key) && wasHeld(key));
	}
	
	public boolean isHeld(MouseButton key) {
		if (mC.containsKey(key)) {
			return mC.get(key);
		} else {
			return false;
		}
	}
	
	public boolean wasHeld(MouseButton key) {
		if (mP.containsKey(key)) {
			return mP.get(key);
		} else {
			return false;
		}
	}
	
	public boolean isPressed(MouseButton key) {
		return (isHeld(key) && !wasHeld(key));
	}
	
	public boolean wasPressed(MouseButton key) {
		return (!isHeld(key) && wasHeld(key));
	}
	
	public double getX() {
		return mX;
	}
	
	public double getY() {
		return mY;
	}
	
}
