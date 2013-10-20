package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;;

/**
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
}
