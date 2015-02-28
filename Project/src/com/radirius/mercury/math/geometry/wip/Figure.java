package com.radirius.mercury.math.geometry.wip;

/**
 * An interface for figures, or geometric objects. All figures should be able to preform basic transformations.
 *
 * @author wessles
 */
public abstract class Figure {

	/**
	 * To be called after any changes to the geometry. Updates various items in the object according to the changed geometry.
	 */
	public abstract void regenerate();

	/**
	 * Translates the figure.
	 */
	public abstract void translate(float x, float y);

	/**
	 * The rotation of the figure by no particular origin (usually the center).
	 */
	public float rotation = 0;

	/**
	 * Rotates the figure by a certain radian angle from an origin.
	 */
	public abstract void rotate(float originX, float originY, float angle);

	/**
	 * Rotates the figure by a certain radian angle from the center.
	 */
	public void rotate(float angle) {
		Point center = getCenter();
		rotate(center.x, center.y, angle);
	}

	/**
	 * The dilation of the figure by no particular origin (usually the center).
	 */
	public float dilation = 0;

	/**
	 * Dilates the figure from an origin by a certain factor.
	 */
	public abstract void dilate(float originX, float originY, float scaleFactor);

	/**
	 * Dilates the figure from the center by a certain factor.
	 */
	public void dilate(float scaleFactor) {
		Point center = getCenter();
		dilate(center.x, center.y, scaleFactor);
	}

	/**
	 * Returns if this figure intersects with another.
	 */
	public abstract boolean intersects(Figure figure);

	/**
	 * Returns if this figure contains another.
	 */
	public abstract boolean contains(Figure figure);

	/**
	 * The mean average of all vertices.
	 */
	protected Point meanCenter;

	/**
	 * Returns the mean average of all vertices.
	 */
	public Point getCenter() {
		return meanCenter;
	}
}
