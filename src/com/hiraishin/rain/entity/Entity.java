package com.hiraishin.rain.entity;

import java.util.Objects;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Drawable {

	protected final Level level;

	protected double x;
	protected double y;

	protected final double width;
	protected final double height;

	protected final Sprite sprite;

	protected double dx = 0;
	protected double dy = 0;

	protected Lifetime state = Lifetime.DEFAULT;

	protected Entity(double x, double y, double width, double height, Sprite sprite, Level level) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.sprite = sprite;

		this.level = level;
	}

	protected Entity(double x, double y, Sprite sprite, Level level) {
		this(x, y, sprite.getWidth(), sprite.getHeight(), sprite, level);
	}

	protected Entity(double x, double y, double width, double height, Level level) {
		this(x, y, width, height, null, level);
	}

	public final double getX() {
		return x;
	}

	public final double getY() {
		return y;
	}

	public final double getWidth() {
		return width;
	}

	public final double getHeight() {
		return height;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (Objects.nonNull(sprite)) {
			sprite.draw(gc, x, y);
		}
	}

	public final Rectangle2D getBounds() {
		return new Rectangle2D(x, y, width, height);
	}

	public final boolean collidesAABB(Entity entity) {
		final boolean a = entity.x + entity.width > x;
		final boolean b = x + width > entity.x;
		final boolean c = entity.y + entity.height > y;
		final boolean d = y + height > entity.y;
		return a && b && c && d;
	}

	public abstract void tick();

	public void kill() {
		state = Lifetime.CLOSED;
	}

	public boolean isDead() {
		return state == Lifetime.CLOSED;
	}

}
