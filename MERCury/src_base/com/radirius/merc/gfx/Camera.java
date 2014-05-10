package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Vec2;

/**
 * An object for the camera.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class Camera {
    /** The position on its respective axis */
    private float x, y;
    /** The point on the screen that anchors the camera to the world. */
    private Vec2 origin = new Vec2(0, 0);
    
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
        glTranslatef(x - origin.x, y - origin.y, 0);
        
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
    public void setOrigin(Vec2 origin) {
        this.origin = origin;
    }
    
    /** @return The origin point (on screen). */
    public Vec2 getOrigin() {
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
        g.scale(zoom);
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
     * @return The real world width of the camera.
     */
    public float getWidth() {
        return Runner.getInstance().getWidth() / Runner.getInstance().getGraphics().getScale().x;
    }
    
    /**
     * @return The real world height of the camera.
     */
    public float getHeight() {
        return Runner.getInstance().getHeight() / Runner.getInstance().getGraphics().getScale().y;
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
    public Vec2 getPosition() {
        return new Vec2(Runner.getInstance().getWidth() - x, Runner.getInstance().getHeight() - y);
    }
}
