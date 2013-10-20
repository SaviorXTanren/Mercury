package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;
import com.wessles.MERCury.utils.ArrayUtils;
import com.wessles.MERCury.utils.ColorUtils;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public abstract class Shape {
	public boolean ignorecolored;
	
	protected Vector2f center;
	protected Vector2f[] vertices;
	protected Color[] colors;
	protected float nx, ny, fx, fy;
	protected float radius;

	public Shape(float... coords) {
		this(ColorUtils.getColorArray(ColorUtils.DEFAULT_DRAWING, coords.length/2), ArrayUtils.getVector2fs(coords), true);
	}
	
	public Shape(Color[] colors, float[] coords) {
		this(colors, ArrayUtils.getVector2fs(coords), false);
	}

	public Shape(Color[] colors, Vector2f[] vertices, boolean ignorecolored) {
		if (vertices.length != colors.length)
			throw new IllegalArgumentException("The number of colors and vertices must be equal!");

		this.ignorecolored = ignorecolored;
		this.colors = colors;
		this.vertices = vertices;
		regen();
	}

	public void translate(float x, float y) {
		for (Vector2f vertex : vertices) {
			vertex.x += x;
			vertex.y += y;
		}
	}

	public void translateTo(float x, float y) {
		translateTo(x, y, 0, 0);
	}

	public void translateTo(float x, float y, float origx, float origy) {
		for (Vector2f vertex : vertices) {
			vertex.x = Math.abs(vertex.x - nx + origx + y);
			vertex.y = Math.abs(vertex.x - ny + origy + x);
		}
		regen();
	}

	public float getArea() {
		return getWidth() * getHeight();
	}

	public float getX1() {
		return nx;
	}

	public float getY1() {
		return ny;
	}

	public float getX2() {
		return fx;
	}

	public float getY2() {
		return fy;
	}

	public Point getPoint1() {
		return new Point(nx, ny);
	}

	public Point getPoint2() {
		return new Point(fx, fy);
	}

	public float getWidth() {
		return Math.abs(fx - nx);
	}

	public float getHeight() {
		return Math.abs(fy - ny);
	}

	public Vector2f getCenterPoint() {
		return center;
	}

	public float getCenterX() {
		return center.x;
	}

	public float getCenterY() {
		return center.y;
	}

	private void regen() {
		nx = vertices[0].x;
		ny = vertices[0].y;
		fx = nx;
		fy = ny;

		for (Vector2f vertex : vertices) {
			if (vertex.x < nx)
				nx = vertex.x;
			else if (vertex.x > fx)
				fx = vertex.x;

			if (vertex.y < ny || vertex.y > fy)
				ny = vertex.y;
			else if (vertex.y > fy)
				fy = vertex.y;
		}
		this.center = new Vector2f((nx + fx) / 2, (ny + fy) / 2);
		this.radius = Math.abs(getWidth() > getHeight() ? getWidth() : getHeight());
	}

	public Vector2f[] getVertices() {
		return vertices;
	}
	
	public Color[] getColors() {
		return colors;
	}

	public String toString() {
		return "Shape with: x1:" + nx + ", y1:" + ny + ", x2:" + fx + ", y2:" + fy;
	}
}
