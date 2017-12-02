package com.hiraishin.undefined.entity;

import java.util.Objects;
import java.util.Random;

import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.util.PortView;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Entity {

	public static final Random RANDOM = new Random();

	public static final double ENTITY_SPEED_HORIZONTAL = 4;
	public static final double ENTITY_SPEED_VERTICAL = 12;

	protected final DoubleProperty xProperty, yProperty;
	protected final CollisionBox collisionBox;
	protected final PortView view;

	protected Lifetime status;

	protected Entity(double initialX, double initialY, PortView portView, CollisionBox collisionBox) {
		xProperty = new SimpleDoubleProperty(initialX);
		yProperty = new SimpleDoubleProperty(initialY);
		view = Objects.requireNonNull(portView);
		this.collisionBox = Objects.requireNonNull(collisionBox);

		view.xProperty().bind(xProperty);
		view.yProperty().bind(yProperty);

		status = Lifetime.C0;
	}

	public final Lifetime getStatus() {
		return status;
	}

	public final void advanceStatus() {
		if (status != Lifetime.C4) {
			status = Lifetime.values()[status.ordinal() + 1];
		}
	}

	public final PortView getPortView() {
		return view;
	}

	public final DoubleProperty xProperty() {
		return xProperty;
	}

	public final DoubleProperty yProperty() {
		return yProperty;
	}

	public final double getX() {
		return xProperty.get();
	}

	public final double getY() {
		return yProperty.get();
	}

	public final void setPosition(Double x, Double y) {
		if (Objects.nonNull(x)) {
			xProperty.set(x);
		}

		if (Objects.nonNull(y)) {
			yProperty.set(y);
		}
	}

	public final double getWidth() {
		return collisionBox.getWidth();
	}

	public final double getHeight() {
		return collisionBox.getHeight();
	}

	public final CollisionBox getCollisionBox() {
		return collisionBox;
	}

	public final boolean collides(Entity entity) {
		return CollisionBox.AABB(getX(), getY(), collisionBox, entity.getX(), entity.getY(), entity.getCollisionBox());
	}

	public final void move(double dx, double dy) {
		xProperty.set(getX() + dx);
		yProperty.set(getY() + dy);
	}

	public abstract void tick();

}
