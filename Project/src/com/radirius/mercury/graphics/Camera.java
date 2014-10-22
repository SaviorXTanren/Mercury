package com.radirius.mercury.graphics;

import static org.lwjgl.opengl.GL11.*;

import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.math.geometry.*;

/**
 * An object for the camera.
 *
 * @author wessles
 */
public class Camera {
	/** The position on its respective axis */
	float x, y;
	/**
	 * The point on the screen that anchors the camera to
	 * the world.
	 */
	private Vector2f origin = new Vector2f(0, 0);

	/** The scaling / zoom of the camera */
	Vector2f scale = new Vector2f(1, 1);
	/** Rotation of the camera */
	float rot = 0;

	/**
	 * @param x
	 *            The x position.
	 * @param y
	 *            The y position.
	 */
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Prepares the camera for each frame.
	 */
	public void pre(Graphics g) {
		glPushMatrix();

		glTranslatef(origin.x, origin.y, 0);
		glScalef(scale.x, scale.y, 1);
		glRotatef(rot, 0, 0, 1);
		glTranslatef(x, y, 0);

		g.pre();
	}

	/**
	 * Applies changes to OGL at the end of each frame.
	 */
	public void post(Graphics g) {
		g.post();

		glPopMatrix();
	}

	/**
	 * @param origin
	 *            The point to set the origin to (on
	 *            screen).
	 */
	public void setOrigin(Vector2f origin) {
		this.origin = origin;
	}

	/** @return The origin point (on screen). */
	public Vector2f getOrigin() {
		return origin;
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param x
	 *            The value by which to scale horizontally.
	 * @param y
	 *            The value by which to scale vertically.
	 */
	public void scale(float x, float y) {
		scale.x += x;
		scale.y += y;
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param x
	 *            The value to set the horizontal scaling.
	 * @param y
	 *            The value to set the vertical scaling.
	 */
	public void setScale(float x, float y) {
		scale.x = x;
		scale.y = y;
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param scale
	 *            The value by which to scale.
	 */
	public void scale(float scale) {
		this.scale.x += scale;
		this.scale.y += scale;
	}

	/**
	 * Zooms the camera in / scales the scene.
	 *
	 * @param scale
	 *            The value to set the scaling.
	 */
	public void setScale(float scale) {
		this.scale.x = scale;
		this.scale.y = scale;
	}

	/**
	 * Rotates the camera on its origin.
	 * 
	 * @param rot
	 *            The amount by which to rotate
	 */
	public void rotate(float rot) {
		this.rot += rot;
	}

	/**
	 * Sets the camera rotation on its origin.
	 * 
	 * @param rot
	 *            The amount of rotation.
	 */
	public void setRotation(float rot) {
		this.rot = rot;
	}

	/**
	 * Translates the camera by x and y.
	 *
	 * @param x
	 *            The x movement.
	 * @param y
	 *            The y movement.
	 */
	public void translate(float x, float y) {
		this.x -= x;
		this.y -= y;
	}

	/**
	 * Translates the camera to x and y.
	 *
	 * @param x
	 *            The x position.
	 * @param y
	 *            The y position.
	 */
	public void translateTo(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The real world width of the camera.
	 */
	public float getWidth() {
		return Runner.getInstance().getWidth() / Runner.getInstance().getGraphics().getScaleDimensions().x;
	}

	/**
	 * @return The real world height of the camera.
	 */
	public float getHeight() {
		return Runner.getInstance().getHeight() / Runner.getInstance().getGraphics().getScaleDimensions().y;
	}

	/**
	 * @return The real world x position of the camera.
	 */
	public float getPositionX() {
		return getPosition().x;
	}

	/**
	 * @return The real world y position of the camera.
	 */
	public float getPositionY() {
		return getPosition().y;
	}

	/**
	 * @return The scaling / zoom of the camera per axis.
	 */
	public Vector2f getScaleDimensions() {
		return scale;
	}

	/**
	 * @return The scaling / zoom of the camera.
	 */
	public float getScale() {
		// Return the average
		return (scale.x + scale.y) / 2;
	}

	/**
	 * @return The rotation of the camera by its origin.
	 */
	public float getRotation() {
		return rot;
	}

	/**
	 * @return The real world position of the camera.
	 */
	public Vector2f getPosition() {
		return new Vector2f(-x, -y);
	}

	/**
	 * Returns an in-game rectangle that represents where
	 * the camera lies.
	 */
	public Rectangle getBounds() {
		Rectangle camera = new Rectangle(getPositionX() - origin.x / scale.x, getPositionY() - origin.y / scale.y, getWidth(), getHeight());
		camera.rotate(getPositionX(), getPositionY(), -rot);
		return camera;
	}
}
