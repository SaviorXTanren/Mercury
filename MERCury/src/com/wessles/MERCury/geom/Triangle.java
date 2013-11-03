package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;
import com.wessles.MERCury.utils.ColorUtils;

/**
 * A triangle shape.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Triangle extends Shape {

	public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
		this(x1, y1, x2, y2, x3, y3, ColorUtils.getColorArray(ColorUtils.DEFAULT_DRAWING, 3));
	}

	public Triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color[] colors) {
		super(colors, new float[] { x1, y1, x2, y2, x3, y3 });
	}

	public float getArea() {
		return getWidth() * getHeight() / 2;
	}

	public boolean intersects(Shape s) {
		return new Rectangle(getX1(), getY1(), getX2() / 2, getY1(), getX2() / 2, getY2() / 2, getX1(), getY2() / 2).intersects(s);
	}

	public boolean contains(Vector2f v) {
		return new Rectangle(getX1(), getY1(), getX2() / 2, getY1(), getX2() / 2, getY2() / 2, getX1(), getY2() / 2).contains(v);
	}
}
