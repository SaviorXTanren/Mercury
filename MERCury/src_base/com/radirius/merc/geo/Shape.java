package com.radirius.merc.geo;

import com.radirius.merc.math.MercMath;
import com.radirius.merc.util.ArrayUtils;

/**
 * The abstraction of all shapes.
 * 
 * @author wessles
 */

public abstract class Shape {
    /** All vertices that make up the shape. */
    protected Vec2[] vertices;
    
    /** The x value of the vertex top-left-most vertex. */
    private float x;
    /** The y value of the vertex top-left-most vertex. */
    private float y;
    /** The x value of the vertex bottom-right-most vertex. */
    private float x2;
    /** The y value of the vertex bottom-right-most vertex. */
    private float y2;
    
    /** The rotation angle (in degrees, COS screw radians) */
    private float rot = 0;
    
    /**
     * @param coords
     *            The coordinates of all vertices in the shape. Will be parsed
     *            for every 2 values into a Vec2.
     */
    public Shape(float... coords) {
        this(ArrayUtils.getVector2fs(coords));
    }
    
    /**
     * @param vertices
     *            All vertices in the shape.
     */
    public Shape(Vec2... vertices) {
        this.vertices = vertices;
        regen();
    }
    
    /** @return Whether s intersects with me. */
    public abstract boolean intersects(Shape s);
    
    /** @return Whether v is inside of me. */
    public abstract boolean contains(Vec2 v);
    
    /**
     * Moves all vertices by x and y.
     * 
     * @param x
     *            The amount every vertex should move on x.
     * @param y
     *            The amount every vertex should move on y.
     * */
    public void translate(float x, float y) {
        for (Vec2 vertex : vertices) {
            vertex.x += x;
            vertex.y += y;
        }
    }
    
    /**
     * Moves all vertices to x and y.
     * 
     * @param x
     *            Where every vertex should move relative to 0 on x.
     * @param y
     *            Where every vertex should move relative to 0 on y.
     */
    public void translateTo(float x, float y) {
        translateTo(x, y, 0, 0);
    }
    
    /**
     * Moves all vertices to x and y relative to a origin.
     * 
     * @param x
     *            Where every vertex should move relative to the origin on x.
     * @param y
     *            Where every vertex should move relative to the origin on y.
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     */
    public void translateTo(float x, float y, float origx, float origy) {
        for (Vec2 vertex : vertices) {
            vertex.x = Math.abs(vertex.x - x + origx + y);
            vertex.y = Math.abs(vertex.x - y + origy + x);
        }
        
        regen();
    }
    
    /**
     * Rotate the object relative to a origin.
     * 
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     * @param angle
     *            The angle by which the object will rotate relative to the
     *            origin.
     */
    public void rotate(float origx, float origy, float angle) {
        for (Vec2 p : vertices) {
            float s = MercMath.sin(angle);
            float c = MercMath.cos(angle);
            
            p.x -= origx;
            p.y -= origy;
            
            float xnew = p.x * c - p.y * s;
            float ynew = p.x * s + p.y * c;
            
            p.x = xnew + origx;
            p.y = ynew + origy;
        }
        rot = angle;
        
        regen();
    }
    
    /**
     * Rotate the object relative to the center of the object.
     * 
     * @param angle
     *            The angle of rotation.
     */
    public void rotate(float angle) {
        rotate(x + getWidth() / 2, y + getHeight() / 2, angle);
    }
    
    /**
     * Rotate the object to a point in rotation relative to a origin.
     * 
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     * @param angle
     *            The angle by which the object will rotate to relative to the
     *            origin.
     */
    public void rotateTo(float origx, float origy, float angle) {
        rotate(origx, origy, rot - angle);
    }
    
    /**
     * Rotate the object to a point in rotation relative to the center of the
     * object.
     * 
     * @param angle
     *            The angle of rotation that the object will rotate to.
     */
    public void rotateTo(float angle) {
        rotateTo(x + getWidth() / 2, y + getHeight() / 2, angle);
    }
    
    /** @return A VERY rough estimate of area. */
    public float getArea() {
        return getWidth() * getHeight();
    }
    
    /** @return The x of the nearest vertex. */
    public float getX() {
        regen();
        return x;
    }
    
    /** @return The y of the nearest vertex. */
    public float getY() {
        regen();
        return y;
    }
    
    /** @return The x of the farthest vertex. */
    public float getX2() {
        regen();
        return x2;
    }
    
    /** @return The y of the farthest vertex. */
    public float getY2() {
        regen();
        return y2;
    }
    
    /** @return The difference between the nearest x and the farthest x. */
    public float getWidth() {
        regen();
        return Math.abs(x2 - x);
    }
    
    /** @return The difference between the nearest y and the farthest y. */
    public float getHeight() {
        regen();
        return Math.abs(y2 - y);
    }
    
    protected void regen() {
        x = vertices[0].x;
        y = vertices[0].y;
        x2 = x;
        y2 = y;
        
        for (Vec2 vertex : vertices) {
            x = Math.min(vertex.x, x);
            y = Math.min(vertex.y, y);
            x2 = Math.max(vertex.x, x2);
            y2 = Math.max(vertex.y, y2);
        }
    }
    
    /** @return All vertices of the object. */
    public Vec2[] getVertices() {
        return vertices;
    }
    
    @Override
    public String toString() {
        return "Shape with: x1:" + x + ", y1:" + y + ", x2:" + x2 + ", y2:" + y2;
    }
}
