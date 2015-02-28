package com.radirius.mercury.graphics;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.GraphicsUtils;

/**
 * An object for the camera.
 *
 * @author wessles
 * @author Sri Harsha Chilakapati
 */
public class Camera {
	/**
	 * The position on its respective axis
	 */
	private float x, y;

	/**
	 * The scaling / zoom of the camera
	 */
	private Vector2f scale = new Vector2f(1, 1);

	/**
	 * Rotation of the camera
	 */
	private float rot = 0;

	/**
	 * The point on the screen that anchors the camera to the world.
	 */
	private Vector2f origin = new Vector2f(0, 0);

	/**
	 * @param x The x position.
	 * @param y The y position.
	 */
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Prepares the camera for each frame.
	 */
	public void pre(Graphics g) {
		GraphicsUtils.pushMatrix();
		updateTransforms();

		g.pre();
	}

	public void updateTransforms() {
		if (Core.getCurrentCore().getBatcher().isActive())
			Core.getCurrentCore().getBatcher().flush();

		// Update the transformation matrix
		Matrix4f cm = GraphicsUtils.getCurrentMatrix();

		if (rot < 0)
			rot = (float) (2 * Math.PI + rot);
		if (rot > 2 * Math.PI)
			rot = (float) ((2 * Math.PI) % rot);

		cm.initIdentity().mul(new Matrix4f().initTranslation(origin.x, origin.y)).mul(new Matrix4f().initScale(scale.x, scale.y)).mul(new Matrix4f().initRotation(rot)).mul(new Matrix4f().initTranslation(-x, -y));
	}

	/**
	 * Applies changes to OGL at the end of each frame.
	 */
	public void post(Graphics g) {
		g.post();

		GraphicsUtils.popMatrix();
	}

	/**
	 * Returns The origin point (on screen).
	 */
	public Vector2f getOrigin() {
		return origin;
	}

	/**
	 * @param origin The point to set the origin to (on screen).
	 */
	public void setOrigin(Vector2f origin) {
		float x = this.origin.x, y = this.origin.y;
		this.origin = origin;
		translate(origin.x - x, origin.y - y);
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param x The value to set the horizontal scaling.
	 * @param y The value to set the vertical scaling.
	 */
	public void setScale(float x, float y) {
		scale.x = x;
		scale.y = y;
		updateTransforms();
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param x The value by which to scale horizontally.
	 * @param y The value by which to scale vertically.
	 */
	public void scale(float x, float y) {
		scale.x += x;
		scale.y += y;
		updateTransforms();
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param scale The value by which to scale.
	 */
	public void scale(float scale) {
		this.scale.x += scale;
		this.scale.y += scale;
		updateTransforms();
	}

	/**
	 * Returns The rotation of the camera by its origin.
	 */
	public float getRotation() {
		return rot;
	}

	/**
	 * Sets the camera rotation on its origin.
	 *
	 * @param rot The amount of rotation.
	 */
	public void setRotation(float rot) {
		this.rot = rot;

		updateTransforms();
	}

	/**
	 * Rotates the camera on its origin.
	 *
	 * @param rot The amount by which to rotate
	 */
	public void rotate(float rot) {
		this.rot += rot;

		updateTransforms();
	}

	/**
	 * Returns The scaling / zoom of the camera per axis.
	 */
	public Vector2f getScaleDimensions() {
		return scale;
	}

	/**
	 * Returns The scaling / zoom of the camera.
	 */
	public float getScale() {
		// Return the average
		return (scale.x + scale.y) / 2;
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param scale The value to set the scaling.
	 */
	public void setScale(float scale) {
		this.scale.x = scale;
		this.scale.y = scale;
		updateTransforms();
	}

	/**
	 * Returns The real world position of the camera.
	 */
	public Vector2f getPosition() {
		return new Vector2f(x, y);
	}

	/**
	 * Returns The real world x position of the camera.
	 */
	public float getPositionX() {
		return getPosition().x;
	}

	/**
	 * Returns The real world y position of the camera.
	 */
	public float getPositionY() {
		return getPosition().y;
	}

	/**
	 * Translates the camera to x and y.
	 *
	 * @param x The x position.
	 * @param y The y position.
	 */
	public void translateTo(float x, float y) {
		this.x = x;
		this.y = y;
		updateTransforms();
	}

	/**
	 * Translates the camera by x and y.
	 *
	 * @param x The x movement.
	 * @param y The y movement.
	 */
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		updateTransforms();
	}

	/**
	 * Returns The real world width of the camera.
	 */
	public float getWidth() {
		return Window.getWidth() / Core.getCurrentCore().getGraphics().getScaleDimensions().x;
	}

	/**
	 * Returns The real world height of the camera.
	 */
	public float getHeight() {
		return Window.getHeight() / Core.getCurrentCore().getGraphics().getScaleDimensions().y;
	}

	/**
	 * Returns an in-game rectangle that represents where the camera lies.
	 */
	public Rectangle getBounds() {
		Rectangle camera = new Rectangle(getPositionX() - origin.x / scale.x, getPositionY() - origin.y / scale.y, getWidth(), getHeight());
		camera.rotate(getPositionX(), getPositionY(), -rot);
		return camera;
	}
}
