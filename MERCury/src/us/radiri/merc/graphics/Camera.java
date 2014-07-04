package us.radiri.merc.graphics;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Vec2;

/**
 * An object for the camera.
 * 
 * @author wessles
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
    public Vec2 getPosition() {
        return new Vec2(-x, -y);
    }
}
