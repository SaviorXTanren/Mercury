package com.radirius.mercury.scene;

import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.math.geometry.Vector2f;

/**
 * A base for all Entities that have a function in a Mercury
 * game's environment.
 *
 * @author Jeviny
 */
public class BasicEntity extends GameObject {
    private float x, y, rotation;
    private Rectangle bounds;

    /**
     * Creates a new entity.
     *
     * @param x The x position.
     * @param y The y position.
     * @param w The width.
     * @param h The height.
     */
    public BasicEntity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;

        bounds = new Rectangle(x, y, w, h);
    }

    /**
     * @return The entity's boundaries.
     */
    public Rectangle getBounds() {
        return (Rectangle) bounds.rotateTo(rotation);
    }

    /**
     * Sets the entity's boundaries.
     *
     * @param bounds The boundaries.
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    /**
     * @return The entity's name.
     */
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * @return The entity's position.
     */
    public Vector2f getPosition() {
        return new Vector2f(x, y);
    }

    /**
     * Sets the position of the entity.
     *
     * @param position The position.
     */
    public void setPosition(Vector2f position) {
        x = position.x;
        y = position.y;
    }

    /**
     * Sets the position of the entity.
     *
     * @param x The x position.
     * @param y The y position.
     */
    public void setPosition(float x, float y) {
        setPosition(new Vector2f(x, y));
    }

    /**
     * @return The entity's rotation.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the entity.
     *
     * @param rotation The rotation.
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
