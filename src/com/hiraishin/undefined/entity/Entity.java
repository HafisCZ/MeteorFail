package com.hiraishin.undefined.entity;

import java.util.Objects;

import com.hiraishin.undefined.entity.collision.CollisionBox;
import com.hiraishin.undefined.sheetview.SheetView;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Entity {
	
	public final static double DEFAULT_SPEED = 3;
	
	protected final DoubleProperty xProperty, yProperty;
	protected final CollisionBox bounds;
	protected final SheetView view;
	
	protected EntityState state = null;
	protected double dx, dy;
	
	protected Entity(double x, double y, SheetView view, CollisionBox bounds) {
		xProperty = new SimpleDoubleProperty(x);
		yProperty = new SimpleDoubleProperty(y);
		
		this.view = Objects.requireNonNull(view);
		this.view.xProperty().bind(xProperty);
		this.view.yProperty().bind(yProperty);
		
		this.bounds = Objects.requireNonNull(bounds);
	}
	
	public final EntityState getState() {
		return this.state;
	}
	
	public final void setState(EntityState state) {
		if (Objects.nonNull(state)) {
			switch (state) {
				case C1:
					onStateC1();
					break;
				case C2:
					onStateC2();
					break;
			}
		}
		
		this.state = state;
	}
	
	protected void onStateC1() {
		
	}
	
	protected void onStateC2() {
		
	}
	
	public final SheetView getView() {
		return this.view;
	}
	
	public final double getX() {
		return this.xProperty.get();
	}
	
	public final double getY() {
		return this.yProperty.get();
	}
	
	public final DoubleProperty getXProperty() {
		return this.xProperty;
	}

	public final DoubleProperty getYProperty() {
		return this.yProperty;
	}
	
	public final double getWidth() {
		return this.bounds.getWidth();
	}
	
	public final double getHeight() {
		return this.bounds.getHeight();
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
		return bounds;
	}
	
	public abstract void update();
	
}
