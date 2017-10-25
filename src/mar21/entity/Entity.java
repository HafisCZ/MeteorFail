package mar21.entity;

import javafx.scene.image.ImageView;
import mar21.resources.ShatteredImageView;

public abstract class Entity {
	
	public enum State {
		NONE, MARKED_FOR_REMOVAL, REMOVAL_ANIMATION
	};
	
	protected double x, y, dx, dy, spriteWidth, spriteHeight;
	protected ShatteredImageView imageView;
	protected State state = State.NONE;
	private Runnable mfrAction, raAction;
	
	public Entity(double x, double y, ShatteredImageView sheet) {
		this.x = x;
		this.y = y;
		this.spriteWidth = sheet.getFitWidth();
		this.spriteHeight = sheet.getFitHeight();
		this.imageView = sheet;
	}
	
	public final void assignAction(State state, Runnable r) {
		switch (state) {
			case MARKED_FOR_REMOVAL:
				this.mfrAction = r;
				break;
			case REMOVAL_ANIMATION:
				this.raAction = r;
				break;
			case NONE:
				throw new IllegalArgumentException("");
		}
	}
	
	public final State state() {
		return this.state;
	}
	
	public final void requestRemoval() {
		this.state = State.MARKED_FOR_REMOVAL;
		if (this.mfrAction != null) {
			this.mfrAction.run();
		}
	}
	
	public final void requestAnimatedRemoval() {
		this.state = State.REMOVAL_ANIMATION;
		if (this.raAction != null) {
			this.raAction.run();
		}
	}
	
	public final ImageView getImageView() {
		return this.imageView;
	}
	
	public final void updateImageView() {
		this.imageView.setX(this.x);
		this.imageView.setY(this.y);
	}
	
	public final double x() {
		return this.x;
	}
	
	public final double y() {
		return this.y;
	}
	
	public final void setPosition(Double x, Double y) {
		if (x != null) {
			this.x = x;
		}
		
		if (y != null) {
			this.y = y;
		}
	}
	
	public final void move(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}

	public abstract void update();
}
