package com.radirius.mercury.math.geometry;

/**
 * A line class.
 *
 * @author wessles
 * @author Jeviny
 */
public class Line extends Shape {
	private float slope;
	private float xIntercept, yIntercept;

	/**
	 * @param x1 The starting x position of the line.
	 * @param y1 The starting y position of the line.
	 * @param x2 The ending x position of the line.
	 * @param y2 The ending y position of the line.
	 */
	public Line(float x1, float y1, float x2, float y2) {
		this(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}

	/**
	 * @param p1 The starting vector of the line.
	 * @param p2 The ending vector of the line.
	 */
	public Line(Vector2f p1, Vector2f p2) {
		super(p1, p2);
	}

	/**
	 * Returns The slope of the line.
	 */
	public float getSlope() {
		return slope;
	}

	/**
	 * Returns The y intercept.
	 */
	public float getYIntercept() {
		return yIntercept;
	}

	/**
	 * Returns The x intercept.
	 */
	public float getXIntercept() {
		return xIntercept;
	}

	@Override
	public boolean intersects(Shape s) {
		if (s instanceof Line) {
			Vector2f m00 = vertices[0];
			Vector2f m01 = vertices[1];
			Vector2f m10 = s.vertices[0];
			Vector2f m11 = s.vertices[1];

			float UA = ((m11.x - m10.x) * (m00.y - m10.y) - (m11.y - m10.y) * (m00.x - m10.x)) / ((m11.y - m10.y) * (m01.x - m00.x) - (m11.x - m10.x) * (m01.y - m00.y));
			float UB = ((m01.x - m00.x) * (m00.y - m10.y) - (m01.y - m00.y) * (m00.x - m10.x)) / ((m11.y - m10.y) * (m01.x - m00.x) - (m11.x - m10.x) * (m01.y - m00.y));

			if (UA >= 0 && UA <= 1 && UB >= 0 && UB <= 1)
				return true;
		} else
			return super.intersects(s);

		return false;
	}

	@Override
	public boolean contains(Vector2f p) {
		// Plug the point into this formula, and see if it
		// checks out.
		// ----- b = y-mx

		return p.y - slope * p.x == yIntercept;
	}

	@Override
	public float getArea() {
		// Lines always have an area of 1. Proof:
		// http://math.stackexchange.com/questions/256803/is-the-area-of-a-line-1
		return 1;
	}

	@Override
	public void regen() {
		// Get our two points.
		Vector2f p1 = vertices[0], p2 = vertices[1];

		// ----- y = mx + b
		// or,
		// ----- b = y - mx
		// Where:
		// ----- y is the y value of a point on the line
		// ----- x is the x value of said point on the line
		// ----- m is the slope of the line
		// ----- b is the y intercept of the line

		slope = (p2.y - p2.y) / (p2.x - p1.x);

		yIntercept = p1.y - slope * p1.x;
		xIntercept = -yIntercept / slope;
	}
}
