package mar21.input;
import java.util.HashMap;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

public class EventAdapter implements EventHandler<Event> {

	private double x = 0;
	private double y = 0;	
	
	private Map<MouseButton, Boolean> buttonMap = new HashMap<MouseButton, Boolean>();
	private Map<MouseButton, Boolean> buttonMapPrev = new HashMap<MouseButton, Boolean>();
	private Map<KeyCode, Boolean> keyMap = new HashMap<KeyCode, Boolean>();
	private Map<KeyCode, Boolean> keyMapPrev = new HashMap<KeyCode, Boolean>();
	
	@Override
	public void handle(Event event) {
		if (event instanceof KeyEvent) {
			handleKeyEvent((KeyEvent) event);
		} else if (event instanceof MouseEvent) {
			handleMouseEvent((MouseEvent) event);
		}
		
		event.consume();
	}
	
	public void handleKeyEvent(KeyEvent event) {
		if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
			keyMap.put(event.getCode(), true);
		} else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
			keyMap.put(event.getCode(), false);
		}
	}
	
	public void handleMouseEvent(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			buttonMap.put(event.getButton(), true);
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			buttonMap.put(event.getButton(), false);
		}
		
		this.x = event.getX();
		this.y = event.getY();
	}

	public void update() {
		keyMapPrev.putAll(keyMap);
		buttonMapPrev.putAll(buttonMap);
	}
	
	public boolean isHeld(KeyCode key) {
		if (keyMap.containsKey(key)) {
			return keyMap.get(key);
		} else {
			return false;
		}
	}
	
	public boolean wasHeld(KeyCode key) {
		if (keyMapPrev.containsKey(key)) {
			return keyMapPrev.get(key);
		} else {
			return false;
		}
	}
	
	public boolean isPressed(KeyCode key) {
		return (isHeld(key) & !wasHeld(key));
	}
	
	public boolean isHeld(MouseButton key) {
		if (buttonMap.containsKey(key)) {
			return buttonMap.get(key);
		} else {
			return false;
		}
	}
	
	public boolean wasHeld(MouseButton key) {
		if (buttonMapPrev.containsKey(key)) {
			return buttonMapPrev.get(key);
		} else {
			return false;
		}
	}
	
	public boolean isPressed(MouseButton key) {
		return (isHeld(key) & !wasHeld(key));
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
}
