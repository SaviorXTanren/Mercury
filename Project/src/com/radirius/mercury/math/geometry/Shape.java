package com.radirius.mercury.math.geometry;

import java.util.ArrayList;

/**
 * The abstraction of all shapes.
 *
 * @author wessles
 */
public class Shape {
	protected Shape parent = null;
	protected ArrayList<Shape> children = new ArrayList<>();

	/**
	 * All of the vertices that make up the shape.
	 */
	protected Vector2f[] vertices;

	/**
	 * The x value of the vertex top-left-most vertex.
	 */
	protected float x;

	/**
	 * The y value of the vertex top-left-most vertex.
	 */
	protected float y;

	/**
	 * The x value of the vertex bottom-right-most vertex.
	 */
	protected float x2;

	/**
	 * The y value of the vertex bottom-right-most vertex.
	 */
	protected float y2;

	/**
	 * The absolute value of the difference of x and x2.
	 */
	protected float w;

	/**
	 * The absolute value of the difference of y and y2.
	 */
	protected float h;

	/**
	 * The center of mass. Basically the mean location of all vertices.
	 */
	protected Vector2f center;

	/**
	 * The rotation angle (in degrees).
	 */
	protected float rot = 0;

	/**
	 * The scale!
	 */
	protected float scale = 1;

	/**
	 * Duplicate constructor.
	 */
	public Shape(Shape s) {
		parent = s.parent;
		children = s.children;
		vertices = new Vector2f[s.vertices.length];
		for (int _v = 0; _v < s.vertices.length; _v++)
			vertices[_v] = new Vector2f(s.vertices[_v].x, s.vertices[_v].y);
		x = s.x;
		y = s.y;
		x2 = s.x2;
		y2 = s.y2;
		w = s.w;
		h = s.h;
		center = s.center;
		rot = s.rot;

		// Just to be safe
		regen();
	}

	/**
	 * @param coordinates The coordinates of all vertices in the shape. Will be parsed for every 2 values into a Vec2.
	 */
	protected Shape(float... coordinates) {
		this(getVector2fs(coordinates));
	}

	/**
	 * Sorts an even number of float values into 2 dimensional vectors.
	 *
	 * @param coordinates The x/y pattern of floats
	 * @return An array of 2 dimensional vectors based on coordinates
	 */
	protected static Vector2f[] getVector2fs(float... coordinates) {
		if (coordinates.length % 2 != 0)
			throw new IllegalArgumentException("Vertex coordinates must be even!");

		Vector2f[] vectors = new Vector2f[coordinates.length / 2];

		for (int v = 0; v < coordinates.length; v += 2)
			vectors[v / 2] = new Vector2f(coordinates[v], coordinates[v + 1]);

		return vectors;
	}

	/**
	 * @param vertices All vertices in the shape.
	 */
	protected Shape(Vector2f... vertices) {
		this.vertices = vertices;
		regen();
	}

	/**
	 * Returns Whether s intersects with this shape.
	 */
	public boolean intersects(Shape s) {
		for (Shape child : children)
			if (child.intersects(s))
				return true;

		// More vertices will cause more lag. Beware.

		// Loop through all of the vertices!
		for (int vertex0 = 0; vertex0 < vertices.length; ) {
			// The 'line1vertex1' and 'line1vertex2'
			//
			// For the second point, we want to make sure
			// that we are not doing
			// twice the work for a line, which is not a
			// closed shape.
			Vector2f l1v1 = vertices[vertex0], l1v2 = vertices.length > 2 ? vertices[++vertex0 % vertices.length] : vertices[++vertex0];
			Line l1 = new Line(l1v1, l1v2);

			// If it is a line, the next bit of code breaks.
			if (s instanceof Line) {
				Line l2 = (Line) s;

				if (l2.intersects(l1))
					return true;

				continue;
			}

			// Now, for each line in this shape, we need to
			// test all lines in
			// the other shape.
			for (int vertex1 = 0; vertex1 < s.vertices.length; ) {
				Vector2f l2v1 = s.vertices[vertex1], l2v2 = s.vertices.length > 2 ? s.vertices[++vertex1 % s.vertices.length] : s.vertices[++vertex1];
				Line l2 = new Line(l2v1, l2v2);

				// Now we test.
				if (l1.intersects(l2))
					return true;
			}
		}

		return false;
	}

	/**
	 * Returns Whether all vertices of s is inside of this shape.
	 */
	public boolean contains(Shape s) {
		for (Vector2f v : s.vertices)
			if (contains(v))
				return true;

		return false;
	}

	/**
	 * Returns Whether v is inside of this shape.
	 */
	public boolean contains(Vector2f v) {
		for (Shape child : children)
			if (child.contains(v))
				return true;

		float sumAngle = 0;

		for (Vector2f v2 : vertices) {
			float dx = v.x - v2.x, dy = v.y - v2.x;
			float angle = (float) Math.atan2(dy, dx);

			sumAngle += angle;
		}

		return sumAngle == 360;
	}

	/**
	 * Moves all vertices by x and y.
	 *
	 * @param x The amount every vertex should move on x.
	 * @param y The amount every vertex should move on y. Returns The Shape.
	 */
	public Shape translate(float x, float y) {
		for (Vector2f vertex : vertices) {
			vertex.x += x;
			vertex.y += y;
		}

		regen();

		for (Shape s : children)
			s.translate(x, y);

		return this;
	}

	/**
	 * Moves all vertices to x and y.
	 *
	 * @param x Where every vertex should move relative to the nearest point of the shape on x.
	 * @param y Where every vertex should move relative to the nearest point of the shape on y. Returns The Shape.
	 */
	public Shape translateTo(float x, float y) {
		return translate(x - this.x, y - this.y);
	}

	/**
	 * Rotate the object relative to a origin.
	 *
	 * @param originX The origin's x.
	 * @param originY The origin's y.
	 * @param angle   The angle by which the object will rotate relative to the origin. Returns The Shape.
	 */
	public Shape rotate(float originX, float originY, float angle) {
		if (angle == 0 || angle == 2 * Math.PI)
			return this;

		for (Vector2f vertex : vertices) {
			float s = (float) Math.sin(angle);
			float c = (float) Math.cos(angle);

			vertex.x -= originX;
			vertex.y -= originY;

			float xNew = vertex.x * c - vertex.y * s;
			float yNew = vertex.x * s + vertex.y * c;

			vertex.x = xNew + originX;
			vertex.y = yNew + originY;
		}

		rot += angle;

		regen();

		for (Shape s : children)
			s.rotate(originX, originY, angle);

		return this;
	}

	/**
	 * Rotate the object relative to the center of the object.
	 *
	 * @param angle The angle of rotation. Returns The Shape.
	 */
	public Shape rotate(float angle) {
		return rotate(center.x, center.y, angle);
	}

	/**
	 * Rotate the object to a point in rotation relative to a origin.
	 *
	 * @param originX The origin's x.
	 * @param originY The origin's y.
	 * @param angle   The angle by which the object will rotate to relative to the origin. Returns The Shape.
	 */
	public Shape setRotation(float originX, float originY, float angle) {
		return rotate(originX, originY, angle - rot);
	}

	/**
	 * Rotate the object to a point in rotation relative to the center of the object.
	 *
	 * @param angle The angle of rotation that the object will rotate to. Returns The Shape.
	 */
	public Shape setRotation(float angle) {
		return setRotation(center.x, center.y, angle);
	}

	/**
	 * Scales a shape from a point.
	 * Returns The Shape.
	 */
	public Shape scale(Vector2f point, float scale) {
		translate(-point.x, -point.y);

		for (Vector2f v : vertices) {
			v.x *= scale;
			v.y *= scale;
		}

		translate(point.x, point.y);

		this.scale *= scale;

		regen();

		for (Shape c : children)
			c.scale(point, scale);

		return this;
	}

	/**
	 * Scales a shape from the center of the shape.
	 * Returns The Shape.
	 */
	public Shape scale(float scale) {
		return scale(getCenter(), scale);
	}

	/**
	 * Scales a shape from a point.
	 * Returns The Shape.
	 */
	public Shape setScale(Vector2f point, float scale) {
		return scale(point, scale / this.scale);
	}

	/**
	 * Scales a shape from the center of the shape.
	 * Returns The Shape.
	 */
	public Shape setScale(float scale) {
		return setScale(getCenter(), scale);
	}

	/**
	 * Returns A VERY rough estimate of area.
	 */
	public float getArea() {
		return getWidth() * getHeight();
	}

	/**
	 * Returns The x of the nearest vertex.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns The y of the nearest vertex.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returns The x of the farthest vertex.
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * Returns The y of the farthest vertex.
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * Returns The difference between the nearest x and the farthest x.
	 */
	public float getWidth() {
		return Math.abs(x2 - x);
	}

	/**
	 * Returns The difference between the nearest y and the farthest y.
	 */
	public float getHeight() {
		return Math.abs(y2 - y);
	}

	/**
	 * Returns The rotation of the object.
	 */
	public float getRotation() {
		return rot;
	}

	/**
	 * Returns The scale of the object.
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Returns The center of the object.
	 */
	public Vector2f getCenter() {
		return center;
	}

	/**
	 * This method will regenerate all of the values after you directly modify things.
	 */
	public void regen() {
		x = vertices[0].x;
		y = vertices[0].y;
		x2 = x;
		y2 = y;

		// Center x and y
		float cx = 0, cy = 0;

		for (Vector2f vertex : vertices) {
			cx += vertex.x;
			cy += vertex.y;

			x = Math.min(vertex.x, x);
			y = Math.min(vertex.y, y);
			x2 = Math.max(vertex.x, x2);
			y2 = Math.max(vertex.y, y2);
		}

		cx /= vertices.length;
		cy /= vertices.length;

		center = new Vector2f(cx, cy);

		w = Math.abs(x2 - x);
		h = Math.abs(y2 - y);
	}

	/**
	 * Returns All vertices of the object.
	 */
	public Vector2f[] getVertices() {
		return vertices;
	}

	/**
	 * Adds a child shape.
	 * Returns The Shape.
	 */
	public Shape addChild(Shape... child) {
		for (Shape s : child) {
			s.parent = this;

			children.add(s);
		}

		return this;
	}

	/**
	 * Returns All child shapes.
	 */
	public ArrayList<Shape> getChildren() {
		return children;
	}

	/**
	 * Makes me an orphan. Parent will lose me from it's ArrayList of children.
	 * Returns The Shape.
	 */
	public Shape clearParent() {
		parent.children.remove(this);

		parent = null;

		return this;
	}

	/**
	 * Returns The parent shape of me.
	 */
	public Shape getParent() {
		return parent;
	}

	/**
	 * Sets the parent of the shape.
	 * Returns The Shape.
	 */
	public Shape setParent(Shape parent) {
		parent.addChild(this);

		return this;
	}

	/**
	 * Returns A duplicate of the shape.
	 */
	public Shape duplicate() {
		return new Shape(this);
	}

	/**
	 * Returns The shape boundaries.
	 */
	public Rectangle getBounds() {
		return (Rectangle) new Rectangle(x, y, w, h).rotate(rot);
	}
}
