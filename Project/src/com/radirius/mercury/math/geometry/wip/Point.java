package com.radirius.mercury.math.geometry.wip;

import com.radirius.mercury.math.geometry.Vector2f;

/**
 * A figure of exactly one vertex.
 *
 * @author wessles
 */
public class Point extends Figure {

	public float x;
	public float y;

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void regenerate() {
	}

	@Override
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}

	@Override
	public void rotate(float originX, float originY, float angle) {
		// Get the sine/cosine from the angle
		float sine = (float) Math.sin(angle);
		float cosine = (float) Math.cos(angle);

		// Move back by the origin
		x -= originX;
		y -= originY;

		// Multiply by the sine and cosine
		float xNew = x * cosine - y * sine;
		float yNew = x * sine + y * cosine;

		// Move forward by the origin
		x = xNew + originX;
		y = yNew + originY;

		rotation += angle;
	}

	@Override
	public void rotate(float angle) {
		// Do nothing, since a point cannot be rotated with itself as an origin
		rotation += angle;
	}

	@Override
	public void dilate(float originX, float originY, float scaleFactor) {
		// Move back by the origin
		x -= originX;
		y -= originY;

		// Dilate
		x *= scaleFactor;
		y *= scaleFactor;

		// Move forward by the origin
		x += originX;
		y += originY;

		dilation += scaleFactor;
	}

	@Override
	public void dilate(float scaleFactor) {
		// Do nothing, since a point cannot be scaled with itself as an origin
		dilation += scaleFactor;
	}

	// TODO add in intersection
	@Override
	public boolean intersects(Figure figure) {
		return false;
	}

	// TODO add in containment
	@Override
	public boolean contains(Figure figure) {
		return false;
	}

	@Override
	public Point getCenter() {
		return this;
	}

	/**
	 * Returns a two dimensional vector representing the point's location.
	 */
	public Vector2f getVector() {
		return new Vector2f(x, y);
	}
}
