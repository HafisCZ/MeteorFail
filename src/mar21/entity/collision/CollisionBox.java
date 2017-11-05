package mar21.entity.collision;

import javafx.beans.property.DoubleProperty;
import mar21.entity.Entity;

public class CollisionBox {
	
	private DoubleProperty x, y;
	private double oX, oY, bW, bH;
	
	public CollisionBox(Entity e) {
		this(e, 0, 0, e.getWidth(), e.getHeight());
	}
	
	public CollisionBox(Entity e, double oX, double oY, double bW, double bH) {
		x = e.getXProperty();
		y = e.getYProperty();
		
		this.oX = oX;
		this.oY = oY;
		
		this.bW = bW;
		this.bH = bH;
	}
	
	public boolean collides(Entity entity) {
		return collides(entity.getCollisionBox());
	}
	
	public boolean collides(CollisionBox box) {
		double x1 = x.get() + oX;
		double x2 = x1 + bW;
		double y1 = y.get() + oY;
		double y2 = y1 + bH;
		
		double bx1 = box.x.get() + box.oX;
		double bx2 = bx1 + box.bW;
		double by1 = box.y.get() + box.oY;
		double by2 = by1 + box.bH;
		
		return ((bx1 >= x1 && bx1 <= x2) || (bx2 >= x1 && bx2 <= x2)) && ((by1 >= y1 && by1 <= y2) || (by2 >= y1 && by2 <= y2));
	}

}
