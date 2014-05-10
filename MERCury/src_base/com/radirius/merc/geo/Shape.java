package com.radirius.merc.geo;

import com.radirius.merc.math.MercMath;
import com.radirius.merc.util.ArrayUtils;

/**
 * The abstraction of all shapes.
 * 
 * @from merc in com.radirius.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public abstract class Shape {
    /** The mean average of all vertices; the center. */
    protected Vec2 center;
    /** All vertices that make up the shape. */
    protected Vec2[] vertices;
    /** The near coordinates (lowest values of all vertices). */
    protected float nx, ny;
    /** The far coordinates (highest value of all vertices). */
    protected float fx, fy;
    /** The radius! */
    protected float radius;
    
    /** The rotation angle (in degrees, COS screw freaking radians) */
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
            vertex.x = Math.abs(vertex.x - nx + origx + y);
            vertex.y = Math.abs(vertex.x - ny + origy + x);
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
    }
    
    /**
     * Rotate the object relative to the center of the object.
     * 
     * @param angle
     *            The angle of rotation.
     */
    public void rotate(float angle) {
        rotate(nx + getWidth() / 2, ny + getHeight() / 2, angle);
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
        rotateTo(nx + getWidth() / 2, ny + getHeight() / 2, angle);
    }
    
    /** @return A VERY rough estimate of area. */
    public float getArea() {
        return getWidth() * getHeight();
    }
    
    /** @return The x of the nearest vertex. */
    public float getX1() {
        regen();
        return nx;
    }
    
    /** @return The y of the nearest vertex. */
    public float getY1() {
        regen();
        return ny;
    }
    
    /** @return The x of the farthest vertex. */
    public float getX2() {
        regen();
        return fx;
    }
    
    /** @return The y of the farthest vertex. */
    public float getY2() {
        regen();
        return fy;
    }
    
    public Point getPoint1() {
        regen();
        return new Point(nx, ny);
    }
    
    public Point getPoint2() {
        regen();
        return new Point(fx, fy);
    }
    
    /** @return The difference between the nearest x and the farthest x. */
    public float getWidth() {
        regen();
        return Math.abs(fx - nx);
    }
    
    /** @return The difference between the nearest y and the farthest y. */
    public float getHeight() {
        regen();
        return Math.abs(fy - ny);
    }
    
    /** @return The mean average of all vertices (the center). */
    public Vec2 getCenterPoint() {
        regen();
        return center;
    }
    
    /** @return The x of getCenterPoint() */
    public float getCenterX() {
        return getCenterPoint().x;
    }
    
    /** @return The y of getCenterPoint() */
    public float getCenterY() {
        return getCenterPoint().y;
    }
    
    private void regen() {
        nx = vertices[0].x;
        ny = vertices[0].y;
        fx = nx;
        fy = ny;
        
        for (Vec2 vertex : vertices) {
            nx = Math.min(vertex.x, nx);
            ny = Math.min(vertex.y, ny);
            fx = Math.max(vertex.x, fx);
            fy = Math.max(vertex.y, fy);
        }
        
        center = new Vec2((nx + fx) / 2, (ny + fy) / 2);
        radius = Math.abs(fx - nx > fy - ny ? fx - nx : fy - ny);
    }
    
    /** @return All vertices of the object. */
    public Vec2[] getVertices() {
        return vertices;
    }
    
    @Override
    public String toString() {
        return "Shape with: x1:" + nx + ", y1:" + ny + ", x2:" + fx + ", y2:" + fy;
    }
}
