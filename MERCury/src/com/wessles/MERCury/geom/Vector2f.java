package com.wessles.MERCury.geom;

/**
 * A Vector that contains 2 values: x and y.
 * This class makes vector math easy, and makes your life a whole lot easier.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Vector2f {
	public float x=0, y=0;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f(float theta) {
		this.x = (float) Math.toDegrees(Math.cos(theta));
		this.y = (float) Math.toDegrees(Math.sin(theta));
	}

	public void add(float theta) {
		this.x += (float) Math.cos(theta);
		this.y += (float) Math.sin(theta);
	}

	public void add(Vector2f vec) {
		add(vec.getTheta());
	}

	public void set(float theta) {
		this.x = (float) Math.toDegrees(Math.cos(theta));
		this.y = (float) Math.toDegrees(Math.sin(theta));
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void negate() {
		set(-x, -y);
	}

	public void sub(float theta) {
		set(getTheta() - theta);
	}

	public void scale(float a) {
		x *= a;
		y *= a;
	}

	public void normalize() {
		float l = getLength();

		x /= l;
		y /= l;
	}

	public float getTheta() {
		return (float) Math.toDegrees(Math.atan2(y, x));
	}

	public float dot(Vector2f other) {
		return (x * other.x) + (y * other.y);
	}

	public float getLengthSquared() {
		return (x * x) + (y * y);
	}

	public float getLength() {
		return (float) Math.sqrt(getLengthSquared());
	}

	public float distance(Vector2f other) {
		float dx = other.x - x;
		float dy = other.y - y;

		return (float) Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public static Vector2f get(float x, float y) {
		return new Vector2f(x, y);
	}
}
