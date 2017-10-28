package mar21.entity;

import java.util.Objects;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import mar21.input.Action;
import mar21.resources.ShatteredImageView;

public abstract class Entity {
	
	public static enum OnUpdateAction {
		NONE, ANIMATION, REMOVAL
	}
	
	protected final DoubleProperty xProperty;
	protected final DoubleProperty yProperty;
	protected final DoubleProperty widthProperty;
	protected final DoubleProperty heightProperty;
	
	protected final ShatteredImageView view;
	protected final Timeline removalAnimation = new Timeline();
	{
		removalAnimation.setOnFinished(e -> {
			requestRemoval(false);
		});
	}
	
	private Action onRemoval, onAnimation;
	
	protected double dx, dy;
	protected OnUpdateAction onUpdate = OnUpdateAction.NONE;
	
	public Entity(double x, double y, ShatteredImageView view) {
		xProperty = new SimpleDoubleProperty(x);
		yProperty = new SimpleDoubleProperty(y);
		
		this.view = view;
		widthProperty = new SimpleDoubleProperty(view.getFitWidth());
		heightProperty = new SimpleDoubleProperty(view.getFitHeight());
		
		view.xProperty().bind(xProperty);
		view.yProperty().bind(yProperty);
	}
	
	public final void assignAction(OnUpdateAction type, Action action) {
		switch (type) {
			case NONE:
				throw new IllegalArgumentException(type + " not applicable to assignAction()!");
			case REMOVAL:
				onRemoval = action;
				break;
			case ANIMATION:
				onAnimation = action;
				break;
		}
	}
	
	public final OnUpdateAction getAction() {
		return onUpdate;
	}
	
	public final void requestRemoval(boolean animated) {
		if (animated) {
			onUpdate = OnUpdateAction.ANIMATION;
			removalAnimation.play();
			if (Objects.nonNull(onAnimation)) {
				onAnimation.handle();
			}
		} else {
			onUpdate = OnUpdateAction.REMOVAL;
			if (Objects.nonNull(onRemoval)) {
				onRemoval.handle();
			}
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
