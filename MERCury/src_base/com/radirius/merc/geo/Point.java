package com.radirius.merc.geo;

/**
 * A point shape. No sides, just a point.
 * 
 * @from merc in com.radirius.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Point extends Shape
{
    
    public Point(float x, float y)
    {
        super(x, y);
    }
    
    @Override
    public String toString()
    {
        return "Point at " + nx + ", " + ny;
    }
    
    @Override
    public float getArea()
    {
        return 1;
    }
    
    public Vec2 toVector2f()
    {
        return new Vec2(nx, ny);
    }
    
    public static Point getPoint(float x, float y)
    {
        return new Point(x, y);
    }
    
    public static Point getPoint(Point point)
    {
        return new Point(point.nx, point.ny);
    }
    
    @Override
    public boolean intersects(Shape s)
    {
        return s.contains(new Vec2(getX1(), getY1()));
    }
    
    @Override
    public boolean contains(Vec2 v)
    {
        return v.x == getX1() && v.y == getY1();
    }
}
