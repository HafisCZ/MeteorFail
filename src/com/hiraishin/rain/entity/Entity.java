package com.hiraishin.rain.entity;

import java.util.Objects;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Drawable {

	/**
	 * Position
	 */
	protected double x;
	protected double y;

	/**
	 * Entity bounds
	 */
	protected final double width;
	protected final double height;

	/**
	 * Sprite
	 */
	protected final Sprite sprite;

	/**
	 * Change in position every tick
	 */
	protected double dx = 0;
	protected double dy = 0;

	/**
	 * Update state of entity
	 */
	protected UpdateCycle state = UpdateCycle.DEFAULT;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param bounds
	 * @param sheet
	 */
	protected Entity(double x, double y, double width, double height, Sprite sprite) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.sprite = sprite;
	}

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param sprite
	 */
	protected Entity(double x, double y, Sprite sprite) {
		this(x, y, sprite.getWidth(), sprite.getHeight(), sprite);
	}

	/**
	 * Physics only constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected Entity(double x, double y, double width, double height) {
		this(x, y, width, height, null);
	}

	/**
	 * Get x
	 * 
	 * @return
	 */
	public final double getX() {
		return x;
	}

	/**
	 * Get y
	 * 
	 * @return
	 */
	public final double getY() {
		return y;
	}

	/**
	 * Get width
	 * 
	 * @return
	 */
	public final double getWidth() {
		return width;
	}

	/**
	 * Get height
	 * 
	 * @return
	 */
	public final double getHeight() {
		return height;
	}

	/**
	 * Draw entity to graphics context
	 * 
	 * @param gc
	 */
	@Override
	public void draw(GraphicsContext gc) {
		if (Objects.nonNull(sprite)) {
			sprite.draw(gc, x, y);
		}
	}

	/**
	 * Get bounds
	 * 
	 * @return
	 */
	public final Rectangle2D getBounds() {
		return new Rectangle2D(x, y, width, height);
	}

	/**
	 * Check AABB collision
	 * 
	 * @param entity
	 * @return
	 */
	public final boolean collidesAABB(Entity entity) {
		final boolean a = entity.x + entity.width > x;
		final boolean b = x + width > entity.x;
		final boolean c = entity.y + entity.height > y;
		final boolean d = y + height > entity.y;
		return a && b && c && d;
	}

	/**
	 * Update cycle method
	 */
	public abstract void tick();

	/**
	 * Kill entity
	 */
	public void kill() {
		state = UpdateCycle.CLOSED;
	}

	/**
	 * Is dead
	 * 
	 * @return
	 */
	public boolean isDead() {
		return state == UpdateCycle.CLOSED;
	}

}
