package com.wessles.MERCury.geom;

/**
 * A point shape. No sides, just a point.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Point extends Shape {

	public Point(float x, float y) {
		super(x, y);
	}

	public String toString() {
		return "Point at " + nx + ", " + ny;
	}
	
	public float getArea() {
		return 1;
	}
	
	public Vector2f toVector2f() {
		return new Vector2f(nx, ny);
	}

	public static Point getPoint(float x, float y) {
		return new Point(x, y);
	}

	public static Point getPoint(Point point) {
		return new Point(point.nx, point.ny);
	}
}
