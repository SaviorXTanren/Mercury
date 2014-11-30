package com.radirius.mercury.math.geometry;

import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.utilities.ArrayUtils;

import java.util.ArrayList;

/**
 * The abstraction of all shapes.
 *
 * @author wessles
 */
public class Shape {
	protected Shape parent = null;
	protected ArrayList<Shape> children = new ArrayList<Shape>();

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
	 * @param coords The coordinates of all vertices in the shape. Will be parsed for every 2 values into a Vec2.
	 */
	protected Shape(float... coords) {
		this(ArrayUtils.getVector2fs(coords));
	}

	/**
	 * @param vertices All vertices in the shape.
	 */
	protected Shape(Vector2f... vertices) {
		this.vertices = vertices;
		regen();
	}

	/**
	 * @return Whether s intersects with this shape.
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
	 * @return Whether all vertices of s is inside of this shape.
	 */
	public boolean contains(Shape s) {
		for (Vector2f v : s.vertices)
			if (contains(v))
				return true;

		return false;
	}

	/**
	 * @return Whether v is inside of this shape.
	 */
	public boolean contains(Vector2f v) {
		for (Shape child : children)
			if (child.contains(v))
				return true;

		float sumAngle = 0;

		for (Vector2f v2 : vertices) {
			float dx = v.x - v2.x, dy = v.y - v2.x;
			float angle = MathUtil.atan2(dy, dx);

			sumAngle += angle;
		}

		if (sumAngle != 360)
			return false;

		return true;
	}

	/**
	 * Moves all vertices by x and y.
	 *
	 * @param x The amount every vertex should move on x.
	 * @param y The amount every vertex should move on y.
	 * @return The Shape.
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
	 * @param y Where every vertex should move relative to the nearest point of the shape on y.
	 * @return The Shape.
	 */
	public Shape translateTo(float x, float y) {
		return translate(x - this.x, y - this.y);
	}

	/**
	 * Rotate the object relative to a origin.
	 *
	 * @param xOrigin The origin's x.
	 * @param yOrigin The origin's y.
	 * @param angle   The angle by which the object will rotate relative to the origin.
	 * @return The Shape.
	 */
	public Shape rotate(float xOrigin, float yOrigin, float angle) {
		if (angle == 0)
			return this;

		for (Vector2f vertex : vertices) {
			float s = MathUtil.sin(angle);
			float c = MathUtil.cos(angle);

			vertex.x -= xOrigin;
			vertex.y -= yOrigin;

			float xNew = vertex.x * c - vertex.y * s;
			float yNew = vertex.x * s + vertex.y * c;

			vertex.x = xNew + xOrigin;
			vertex.y = yNew + yOrigin;
		}

		rot += angle;

		regen();

		for (Shape s : children)
			s.rotate(xOrigin, yOrigin, angle);

		return this;
	}

	/**
	 * Rotate the object relative to the center of the object.
	 *
	 * @param angle The angle of rotation.
	 * @return The Shape.
	 */
	public Shape rotate(float angle) {
		return rotate(center.x, center.y, angle);
	}

	/**
	 * Rotate the object to a point in rotation relative to a origin.
	 *
	 * @param origx The origin's x.
	 * @param origy The origin's y.
	 * @param angle The angle by which the object will rotate to relative to the origin.
	 * @return The Shape.
	 */
	public Shape rotateTo(float origx, float origy, float angle) {
		return rotate(origx, origy, angle - rot);
	}

	/**
	 * Rotate the object to a point in rotation relative to the center of the object.
	 *
	 * @param angle The angle of rotation that the object will rotate to.
	 * @return The Shape.
	 */
	public Shape rotateTo(float angle) {
		return rotateTo(center.x, center.y, angle);
	}

	/**
	 * Scales a shape from a point.
	 *
	 * @return The Shape.
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
	 *
	 * @return The Shape.
	 */
	public Shape scale(float scale) {
		return scale(getCenter(), scale);
	}

	/**
	 * Scales a shape from a point.
	 *
	 * @return The Shape.
	 */
	public Shape scaleTo(Vector2f point, float scale) {
		return scale(point, scale / this.scale);
	}

	/**
	 * Scales a shape from the center of the shape.
	 *
	 * @return The Shape.
	 */
	public Shape scaleTo(float scale) {
		return scaleTo(getCenter(), scale);
	}

	/**
	 * Flips the object over the y axis, relative to the mean center.
	 *
	 * @return The Shape.
	 */
	public Shape flipX() {
		for (Vector2f v : vertices)
			v.add(new Vector2f(0, (getCenter().y - v.y) * 2));

		regen();

		return this;
	}

	/**
	 * Flips the object over the y axis, relative to the mean center.
	 *
	 * @return The Shape.
	 */
	public Shape flipY() {
		for (Vector2f v : vertices)
			v.add(new Vector2f((getCenter().x - v.x) * 2, 0));

		regen();

		return this;
	}

	/**
	 * @return A VERY rough estimate of area.
	 */
	public float getArea() {
		return getWidth() * getHeight();
	}

	/**
	 * @return The x of the nearest vertex.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return The y of the nearest vertex.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return The x of the farthest vertex.
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * @return The y of the farthest vertex.
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * @return The difference between the nearest x and the farthest x.
	 */
	public float getWidth() {
		return Math.abs(x2 - x);
	}

	/**
	 * @return The difference between the nearest y and the farthest y.
	 */
	public float getHeight() {
		return Math.abs(y2 - y);
	}

	/**
	 * @return The rotation of the object.
	 */
	public float getRotation() {
		return rot;
	}

	/**
	 * @return The scale of the object.
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @return The center of the object.
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
	 * @return All vertices of the object.
	 */
	public Vector2f[] getVertices() {
		return vertices;
	}

	/**
	 * Adds a child shape.
	 *
	 * @return The Shape.
	 */
	public Shape addChild(Shape... child) {
		for (Shape s : child) {
			s.parent = this;

			children.add(s);
		}

		return this;
	}

	/**
	 * @return All child shapes.
	 */
	public ArrayList<Shape> getChildren() {
		return children;
	}

	/**
	 * Makes me an orphan. Parent will lose me from it's ArrayList of children.
	 *
	 * @return The Shape.
	 */
	public Shape clearParent() {
		parent.children.remove(this);

		parent = null;

		return this;
	}

	/**
	 * @return The parent shape of me.
	 */
	public Shape getParent() {
		return parent;
	}

	/**
	 * Sets the parent of the shape.
	 *
	 * @return The Shape.
	 */
	public Shape setParent(Shape parent) {
		parent.addChild(this);

		return this;
	}

	/**
	 * @return A duplicate of the shape.
	 */
	public Shape duplicate() {
		return new Shape(this);
	}

	/**
	 * @return The shape boundaries.
	 */
	public Rectangle getBounds() {
		return (Rectangle) new Rectangle(x, y, w, h).rotate(rot);
	}
}
