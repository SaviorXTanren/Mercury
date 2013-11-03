package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;;

/**
 * A rectangle shape.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Rectangle extends Shape {

	public Rectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
		super(x1, y1, x2, y2, x3, y3, x4, y4);
	}
	
	public Rectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Color[] colors) {
		super(colors, new float[]{x1, y1, x2, y2, x3, y3, x4, y4});
	}

	public float getArea() {
		return getWidth() * getHeight();
	}

	public boolean intersects(Shape s) {
		return ((s.getX1() > getX1() && s.getX1() < getX2()) && (s.getY1() > getY1() && s.getY1() < getY2())) || ((s.getX2() < getX2() && s.getX2() > getX1()) && (s.getY2() < getY2() && s.getY2() > getY1()));
	}

	public boolean contains(Vector2f v) {
		return (v.x >= getX1() && v.x <= getX2()) && (v.y >= getY1() && v.y <= getY2());
	}
}
