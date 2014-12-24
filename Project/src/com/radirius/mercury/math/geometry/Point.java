package com.radirius.mercury.math.geometry;

/**
 * A point shape. No sides, just a point.
 *
 * @author wessles
 * @author Jeviny
 */
public class Point extends Shape {

	/**
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Point(float x, float y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "(Point) " + toVector().toString();
	}

	@Override
	public float getArea() {
		return 1;
	}

	/**
	 * Returns The point in the form of a vector.
	 */
	public Vector2f toVector() {
		return new Vector2f(getX(), getY());
	}

	@Override
	public boolean intersects(Shape s) {
		return s.contains(new Vector2f(getX(), getY()));
	}

	@Override
	public boolean contains(Vector2f v) {
		return v.x == getX() && v.y == getY();
	}
}
