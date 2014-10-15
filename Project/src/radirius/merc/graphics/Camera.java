package radirius.merc.graphics;

import static org.lwjgl.opengl.GL11.*;
import radirius.merc.framework.Runner;
import radirius.merc.math.geometry.*;

/**
 * An object for the camera.
 *
 * @author wessles
 */
public class Camera {
	/** The position on its respective axis */
	float x, y;

	/** The point on the screen that anchors the camera to the world. */
	private Vector2f origin = new Vector2f(0, 0);

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
		glTranslatef(x + origin.x / g.getScaleDimensions().x, y + origin.y / g.getScaleDimensions().y, 0);

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
	 *            The point to set the origin to (on screen).
	 */
	public void setOrigin(Vector2f origin) {
		this.origin = origin;
	}

	/** @return The origin point (on screen). */
	public Vector2f getOrigin() {
		return origin;
	}

	/**
	 * Zooms the camera in.
	 *
	 * @param zoom
	 *            The value by which to zoom to.
	 * @param g
	 *            The graphics object.
	 */
	public void zoom(float zoom, Graphics g) {
		g.setScale(zoom);
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
	 * @return The real world position of the camera.
	 */
	public Vector2f getPosition() {
		return new Vector2f(-x, -y);
	}

	/** Returns an in-game rectangle that represents where the camera lies. */
	public Rectangle getBounds() {
		return new Rectangle(getPositionX() - origin.x / Runner.getInstance().getGraphics().getScaleDimensions().x, getPositionY() - origin.y / Runner.getInstance().getGraphics().getScaleDimensions().y, getWidth(), getHeight());
	}
}
