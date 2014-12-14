package com.radirius.mercury.scene;

import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.math.geometry.Vector2f;

/**
 * A base for all Entities that have a function in a Mercury game's environment.
 *
 * @author Jeviny
 */
public class BasicEntity extends GameObject {
	private Rectangle bounds;

	/**
	 * Creates a new entity.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public BasicEntity(float x, float y, float w, float h) {
		bounds = new Rectangle(x, y, w, h);
	}

	/**
	 * Returns The entity's boundaries
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Sets the entity's boundaries.
	 *
	 * @param bounds The boundaries
	 */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	/**
	 * Moves in the direction of the current rotation.
	 *
	 * @param speed The speed of the translation
	 */
	public void move(float speed) {
		translate(speed * MathUtil.cos(bounds.getRotation()), speed * MathUtil.sin(bounds.getRotation()));
	}

	/**
	 * Moves by <x, y>.
	 *
	 * @param x The amount to move on x
	 * @param y The amount to move on y
	 */
	public void translate(float x, float y) {
		bounds.translate(x, y);
	}

	/**
	 * Moves to (x, y).
	 *
	 * @param x Where to move to on x
	 * @param y Where to move to on y
	 */
	public void translateTo(float x, float y) {
		bounds.translateTo(x, y);
	}

	/**
	 * Rotate the object relative to the center of the object.
	 *
	 * @param angle The angle of rotation
	 */
	public void rotate(float angle) {
		bounds.rotate(angle);
	}

	/**
	 * Rotate the object to a point in rotation relative to the center of the
	 * object.
	 *
	 * @param angle The angle of rotation that the object will rotate to
	 */
	public void rotateTo(float angle) {
		bounds.rotateTo(angle);
	}

	/**
	 * Returns The top-right most position of the bounds
	 */
	public Vector2f getPosition() {
		return new Vector2f(bounds.getX(), bounds.getY());
	}

	/**
	 * Returns The center of the bounds
	 */
	public Vector2f getCenter() {
		return bounds.getCenter();
	}

	/**
	 * Returns The current rotation of the object
	 */
	public float getRotation() {
		return bounds.getRotation();
	}
}
