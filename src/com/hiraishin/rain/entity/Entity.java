package com.hiraishin.rain.entity;

import java.util.Objects;

import com.hiraishin.rain.graphics.Drawable;
import com.hiraishin.rain.graphics.Sprite;
import com.hiraishin.rain.level.Level;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements Drawable {

	/*
	 * Instance final variables
	 */
	protected final Level level;

	protected final double width;
	protected final double height;

	protected final Sprite sprite;
	protected final double spriteOffsetX;
	protected final double spriteOffsetY;

	/*
	 * Instance variables
	 */
	protected double x;
	protected double y;

	protected double dx = 0;
	protected double dy = 0;

	protected boolean dead = false;

	/*
	 * Constructors
	 */
	protected Entity(double x, double y, double width, double height, Sprite sprite, double offsetX, double offsetY,
			Level level) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;

		this.sprite = sprite;
		this.spriteOffsetX = offsetX;
		this.spriteOffsetY = offsetY;

		this.level = level;
	}

	protected Entity(double x, double y, double width, double height, Level level) {
		this(x, y, width, height, null, 0, 0, level);
	}

	/*
	 * Instance Functions
	 */
	@Override
	public void draw(GraphicsContext gc) {
		if (Objects.nonNull(sprite)) {
			sprite.draw(gc, x + spriteOffsetX, y + spriteOffsetY);
		}
	}

	public final boolean collidesAABB(Entity entity) {
		final boolean a = entity.x + entity.width > x;
		final boolean b = x + width > entity.x;
		final boolean c = entity.y + entity.height > y;
		final boolean d = y + height > entity.y;
		return a && b && c && d;
	}

	public void kill() {
		dead = true;
	}

	public boolean isDead() {
		return dead;
	}

	/*
	 * Getters & Setters
	 */
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

	public final Rectangle2D getBounds() {
		return new Rectangle2D(x, y, width, height);
	}

	public final double getDistance(double x, double y) {
		return Math.sqrt((x - (this.x + width / 2)) * (x - (this.x + width / 2))
				+ (y - (this.y + height / 2)) * (y - (this.y + height / 2)));
	}

	/*
	 * Abstract functions
	 */
	public abstract void tick();

}
