package com.radirius.merc.geo;

/**
 * A point shape. No sides, just a point.
 * 
 * @author wessles
 */

public class Point extends Shape {
    
    /**
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     */
    public Point(float x, float y) {
        super(x, y);
    }
    
    @Override
    public String toString() {
        return "Point at " + nx + ", " + ny;
    }
    
    @Override
    public float getArea() {
        return 1;
    }
    
    /** @return The point in the form of a vector. */
    public Vec2 toVector2f() {
        return new Vec2(nx, ny);
    }
    
    @Override
    public boolean intersects(Shape s) {
        return s.contains(new Vec2(getX1(), getY1()));
    }
    
    @Override
    public boolean contains(Vec2 v) {
        return v.x == getX1() && v.y == getY1();
    }
}
