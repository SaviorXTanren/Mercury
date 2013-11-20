package com.wessles.MERCury.geom;

/**
 * An ellipse in which the length and width are the same.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Circle extends Ellipse {

	public Circle(float x, float y, float radius) {
		super(x, y, radius, radius);
	}
}
