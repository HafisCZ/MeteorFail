package mar21.entity;

import java.util.Objects;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import mar21.resources.SheetView;

public abstract class Entity {
	
	public static enum State {
		MARKED_AS_ANIMATED, MARKED_AS_REMOVED
	}
	
	protected final DoubleProperty xProperty, yProperty;
	protected final DoubleProperty widthProperty, heightProperty;
	protected final SheetView view;
	
	protected double dx, dy;
	
	protected State state = null;
	
	protected final Timeline timeline;

	protected Entity(double x, double y, SheetView view) {
		this.view = Objects.requireNonNull(view);
		widthProperty = view.fitWidthProperty();
		heightProperty = view.fitHeightProperty();
		
		xProperty = new SimpleDoubleProperty(x);
		yProperty = new SimpleDoubleProperty(y);
		
		view.xProperty().bind(xProperty);
		view.yProperty().bind(yProperty);
		
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

	public final ImageView getView() {
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
		return widthProperty.get();
	}
	
	public final double getHeight() {
		return heightProperty.get();
	}
	
	public final DoubleProperty getWidthProperty() {
		return widthProperty;
	}
	
	public final DoubleProperty getHeightProperty() {
		return heightProperty;
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
	
	public Bounds getBounds() {
		return view.getBoundsInLocal();
	}
	
	public abstract void update();
}
