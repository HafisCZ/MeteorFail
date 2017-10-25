package mar21.input;

import java.util.HashMap;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import mar21.input.bind.KeyBind;
import mar21.input.bind.StrokeType;

public class InputManager {
	
	private class Tuple<T, U> {
		private T t;
		private U u;
		
		public Tuple(T t, U u) {
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
	
	private EventAdapter adapter;
	private HashMap<KeyCode, Tuple<StrokeType, KeyBind>> binds = new HashMap<KeyCode, Tuple<StrokeType, KeyBind>>();
	
	public InputManager(EventAdapter adapter) {
		this.adapter = adapter;
	}
	
	public void bind(KeyCode key, StrokeType type, KeyBind bind) {
		this.binds.put(key, new Tuple<StrokeType, KeyBind>(type, bind));
	}
	
	public void unbind(KeyCode key) {
		this.binds.remove(key);
	}
	
	public void clearBinds() {
		this.binds.clear();
	}
	
	public boolean isKeyHeld(KeyCode key) {
		return adapter.isHeld(key);
	}
	
	public boolean isKeyPressed(KeyCode key) {
		return adapter.isPressed(key);
	}

	public boolean isMouseButtonHeld(MouseButton key) {
		return adapter.isHeld(key);
	}
	
	public boolean isMouseButtonPressed(MouseButton key) {
		return adapter.isPressed(key);
	}

	public double getMouseX() {
		return adapter.getX();
	}

	public double getMouseY() {
		return adapter.getY();
	}

	public void update() {
		binds.forEach((KeyCode, Tuple) -> {
			if ((Tuple.first().equals(StrokeType.KEY_PRESSED) && isKeyPressed(KeyCode)) || (Tuple.first().equals(StrokeType.KEY_HELD) && isKeyHeld(KeyCode))) {
				Tuple.second().handle();
			}
		});
		
		adapter.update();
	}
}
