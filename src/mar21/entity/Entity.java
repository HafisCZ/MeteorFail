package mar21.entity;

import java.util.Objects;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import mar21.entity.collision.CollisionBox;
import mar21.resources.SheetView;

public abstract class Entity {
	
	public static enum State {
		MARKED_AS_ANIMATED, MARKED_AS_REMOVED
	}
	
	protected final DoubleProperty xProperty, yProperty;
	protected final SheetView view;
	protected final Timeline timeline;
	
	protected CollisionBox collisionBox;
	protected double width, height, dx, dy;
	
	protected State state = null;
	
	protected Entity(double x, double y, SheetView view, CollisionBox box) {
		xProperty = new SimpleDoubleProperty(x);
		yProperty = new SimpleDoubleProperty(y);
		
		this.view = Objects.requireNonNull(view);
		this.view.xProperty().bind(xProperty);
		this.view.yProperty().bind(yProperty);
		width = view.getFitWidth();
		height = view.getFitHeight();
		
		collisionBox = (Objects.nonNull(box) ? box : new CollisionBox(this));
		
		timeline = new Timeline();
		timeline.setOnFinished(e -> {
			setState(State.MARKED_AS_REMOVED);
		});
	}

	public final State getState() {
		return state;
	}
	
	protected void onMarkedAsAnimated() { }
	protected void onMarkedAsRemoved() { }
	
	public final void setState(State state) {
		this.state = Objects.requireNonNull(state);
		switch (state) {
			case MARKED_AS_ANIMATED:
				timeline.playFromStart();
				onMarkedAsAnimated();
				break;
			case MARKED_AS_REMOVED:
				onMarkedAsRemoved();
				break;
		}
	}

	public final SheetView getView() {
		return view;
	}
	
	public final double getX() {
		return xProperty.get();
	}
	
	public final double getY() {
		return yProperty.get();
	}
	
	public final DoubleProperty getXProperty() {
		return xProperty;
	}

	public final DoubleProperty getYProperty() {
		return yProperty;
	}
	
	public final double getWidth() {
		return width;
	}
	
	public final double getHeight() {
		return height;
	}
	
	public final void setPosition(Double x, Double y) {
		if (Objects.nonNull(x)) {
			xProperty.set(x);
		}
		
		if (Objects.nonNull(y)) {
			yProperty.set(y);
		}
	}
	
	public final void move(double dx, double dy) {
		xProperty.setValue(xProperty.get() + dx);
		yProperty.setValue(yProperty.get() + dy);
	}
	
	public CollisionBox getCollisionBox() {
		return collisionBox;
	}
	
	public abstract void update();
}
