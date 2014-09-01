package radirius.merc.math.geometry;

/**
 * A point shape. No sides, just a point.
 * 
 * @author wessles
 */

public class Point extends Shape {

	/**
	 * @param x
	 *            The x position.
	 * @param y
	 *            The y position.
	 */
	public Point(float x, float y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "Point at " + getX() + ", " + getY();
	}

	@Override
	public float getArea() {
		return 1;
	}

	/** @return The point in the form of a vector. */
	public Vec2 toVec2() {
		return new Vec2(getX(), getY());
	}

	@Override
	public boolean intersects(Shape s) {
		return s.contains(new Vec2(getX(), getY()));
	}

	@Override
	public boolean contains(Vec2 v) {
		return v.x == getX() && v.y == getY();
	}
}
