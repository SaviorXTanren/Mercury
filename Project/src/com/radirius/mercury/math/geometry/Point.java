package com.radirius.mercury.math.geometry;

/**
 * A figure of exactly 1 point.
 *
 * @author wessles
 * @author Jeviny
 */
public class Point extends Figure {

	public Point(float x, float y) {
		super(x, y);
	}

	@Override
	public boolean intersects(Figure figure) {
		return figure.contains(new Vector2f(getX(), getY()));
	}

	@Override
	public boolean contains(Vector2f vertex) {
		return vertex.x == getX() && vertex.y == getY();
	}


	@Override
	public float getArea() {
		return 1;
	}

	/**
	 * @return the point in the form of a vector.
	 */
	public Vector2f toVector() {
		return new Vector2f(getX(), getY());
	}

	@Override
	public String toString() {
		return "(Point) " + toVector().toString();
	}
}
