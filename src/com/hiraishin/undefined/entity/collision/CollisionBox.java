package com.hiraishin.undefined.entity.collision;

public final class CollisionBox {

	private final double X_OFFSET, Y_OFFSET, WIDTH, HEIGHT;

	public CollisionBox(double width, double height) {
		this(0, 0, width, height);
	}

	public CollisionBox(double xOffset, double yOffset, double width, double height) {
		X_OFFSET = xOffset;
		Y_OFFSET = yOffset;
		WIDTH = width;
		HEIGHT = height;
	}

	public double getWidth() {
		return WIDTH;
	}

	public double getHeight() {
		return HEIGHT;
	}

	public static boolean AABB(double offsetAX, double offsetAY, CollisionBox a, double offsetBX, double offsetBY,
			CollisionBox b) {
		return (offsetBX + b.X_OFFSET + b.WIDTH > offsetAX + a.X_OFFSET)
				&& (offsetAX + a.X_OFFSET + a.WIDTH > offsetBX + b.X_OFFSET)
				&& (offsetBY + b.Y_OFFSET + b.HEIGHT > offsetAY + a.Y_OFFSET)
				&& (offsetAY + a.Y_OFFSET + a.HEIGHT > offsetBY + b.Y_OFFSET);
	}

	public double getApproxHeight() {
		return HEIGHT + 2 * Y_OFFSET;
	}

	public double getApproxWidth() {
		return WIDTH + 2 * X_OFFSET;
	}

}
