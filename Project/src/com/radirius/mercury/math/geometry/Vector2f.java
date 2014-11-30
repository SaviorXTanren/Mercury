package com.radirius.mercury.math.geometry;

import com.radirius.mercury.math.MathUtil;

/**
 * A class for 2-dimensional vectors.
 */
public class Vector2f {
	public float x = 0, y = 0;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(float theta) {
		x = MathUtil.cos(theta);
		y = MathUtil.sin(theta);
	}


	public Vector2f add(Vector2f vec) {
		x += vec.x;
		y += vec.y;

		return this;
	}

	public Vector2f add(float theta) {
		x += (float) Math.cos(theta);
		y += (float) Math.sin(theta);

		return this;
	}


	public Vector2f sub(Vector2f vec) {
		x -= vec.x;
		y -= vec.y;

		return this;
	}

	public Vector2f sub(float theta) {
		x -= MathUtil.cos(theta);
		y -= MathUtil.sin(theta);

		return this;
	}


	public Vector2f mul(Vector2f vec) {
		x *= vec.x;
		y *= vec.y;

		return this;
	}


	public Vector2f div(Vector2f vec) {
		x /= vec.x;
		y /= vec.y;

		return this;
	}

	public Vector2f set(float theta) {
		x = (float) Math.toDegrees(Math.cos(theta));
		y = (float) Math.toDegrees(Math.sin(theta));

		return this;
	}


	public Vector2f set(Vector2f vec) {
		x = vec.x;
		y = vec.y;

		return this;
	}


	public Vector2f set(float... coords) {
		set(new Vector2f(coords[0], coords[1]));

		return this;
	}


	public Vector2f scale(float amt) {
		x *= amt;
		y *= amt;

		return this;
	}


	public Vector2f negate() {
		scale(-1);

		return this;
	}


	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}


	public Vector2f normalize() {
		float l = length();
		x /= l;
		y /= l;

		return this;
	}


	public float dot(Vector2f vec) {
		return x * vec.x + y * vec.y;
	}


	public float distance(Vector2f vec) {
		float dx = vec.x - x;
		float dy = vec.y - y;

		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public Vector2f rotate(float angle) {
		double rad = MathUtil.toRadians(angle);
		double cos = MathUtil.cos((float) rad);
		double sin = MathUtil.sin((float) rad);

		x = (float) (x * cos - y * sin);
		y = (float) (x * sin + y * cos);

		return this;
	}

	public float theta() {
		return (float) Math.toDegrees(Math.atan2(y, x));
	}


	public Vector2f copy() {
		return new Vector2f(x, y);
	}

	public Vector2f setZero() {
		return new Vector2f(0, 0);
	}


	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
