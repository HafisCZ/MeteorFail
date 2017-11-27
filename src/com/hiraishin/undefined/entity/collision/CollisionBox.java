package com.hiraishin.undefined.entity.collision;

import com.hiraishin.undefined.entity.Entity;

public class CollisionBox {
	
	private final double[] mat;
	
	public CollisionBox(double x1, double y1, double x2, double y2, double objWidth, double objHeight) {
		/**
		 * (0) MIN X
		 * (1) MAX X - WIDTH
		 * (2) MIN BOUND X
		 * (3) MAX BOUND X
		 * (4) MIN Y
		 * (5) MAX Y - HEIGHT
		 * (6) MIN BOUND Y
		 * (7) MAX BOUND Y
		 */
		this.mat = new double[]{
			0., objWidth,
			x1, x2,
			0., objHeight,
			y1, y2
		};
		
	}
	
	public static boolean intersects(Entity e, Entity f) {
		double ax1 = e.getX() + e.getCollisionBox().mat[2];
		double ax2 = e.getX() + e.getCollisionBox().mat[3];
		double ay1 = e.getY() + e.getCollisionBox().mat[6];
		double ay2 = e.getY() + e.getCollisionBox().mat[7];
		
		double bx1 = f.getX() + f.getCollisionBox().mat[2];
		double bx2 = f.getX() + f.getCollisionBox().mat[3];
		double by1 = f.getY() + f.getCollisionBox().mat[6];
		double by2 = f.getY() + f.getCollisionBox().mat[7];
		
		return (
			(bx1 >= ax1 && bx1 <= ax2) || 
			(bx2 >= ax1 && bx2 <= ax2)) && (
			(by1 >= ay1 && by1 <= ay2) || 
			(by2 >= ay1 && by2 <= ay2)
		);
	}
	
	public double getWidth() {
		return this.mat[1];
	}
	
	public double getHeight() {
		return this.mat[5];
	}
}
