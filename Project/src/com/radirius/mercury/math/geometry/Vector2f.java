package com.radirius.mercury.math.geometry;

import com.radirius.mercury.math.MathUtil;

/**
 * A class for 2-dimensional vectors.
 *
 * @authors wessles, Jeviny
 */
public class Vector2f extends Vector {
	public float x = 0, y = 0;

	public Vector2f() {
	}

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(float theta) {
		x = MathUtil.cos(theta);
		y = MathUtil.sin(theta);
	}

	@Override
	public Vector add(Vector vec) {
		Vector2f vec2 = (Vector2f) vec;

		x += vec2.x;
		y += vec2.y;

		return this;
	}

	public Vector add(float theta) {
		x += (float) Math.cos(theta);
		y += (float) Math.sin(theta);

		return this;
	}

	@Override
	public Vector sub(Vector vec) {
		Vector2f vec2 = (Vector2f) vec;

		x -= vec2.x;
		y -= vec2.y;

		return this;
	}

	public Vector sub(float theta) {
		x -= MathUtil.cos(theta);
		y -= MathUtil.sin(theta);

		return this;
	}

	@Override
	public Vector mul(Vector vec) {
		Vector2f vec2 = (Vector2f) vec;

		x *= vec2.x;
		y *= vec2.y;

		return this;
	}

	@Override
	public Vector div(Vector vec) {
		Vector2f vec2 = (Vector2f) vec;

		x /= vec2.x;
		y /= vec2.y;

		return this;
	}

	public Vector set(float theta) {
		x = (float) Math.toDegrees(Math.cos(theta));
		y = (float) Math.toDegrees(Math.sin(theta));

		return this;
	}

	@Override
	public Vector set(Vector vec) {
		Vector2f vec2 = (Vector2f) vec;

		x = vec2.x;
		y = vec2.y;

		return this;
	}

	@Override
	public Vector set(float... coords) {
		set(new Vector2f(coords[0], coords[1]));

		return this;
	}

	@Override
	public Vector scale(float a) {
		x *= a;
		y *= a;

		return this;
	}

	@Override
	public Vector negate() {
		scale(-1);

		return this;
	}

	@Override
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	@Override
	public Vector normalize() {
		float l = length();
		x /= l;
		y /= l;

		return this;
	}

	@Override
	public float dot(Vector other) {
		Vector2f vec2 = (Vector2f) other;

		return x * vec2.x + y * vec2.y;
	}

	@Override
	public float distance(Vector other) {
		Vector2f vec2 = (Vector2f) other;
		float dx = vec2.x - x;
		float dy = vec2.y - y;

		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public Vector rotate(float angle) {
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

	@Override
	public Vector copy() {
		return new Vector2f(x, y);
	}

	@Override
	public Vector setZero() {
		return new Vector2f(0, 0);
	}

	@Override
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
