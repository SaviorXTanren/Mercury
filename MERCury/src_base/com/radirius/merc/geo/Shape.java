package com.radirius.merc.geo;

import com.radirius.merc.math.MercMath;
import com.radirius.merc.util.ArrayUtils;

/**
 * The abstraction of all shapes.
 * 
 * @from merc in com.radirius.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public abstract class Shape
{
    protected Vec2 center;
    protected Vec2[] vertices;
    protected float nx, ny, fx, fy;
    protected float radius;
    
    private float rot = 0;
    
    public Shape(float... coords)
    {
        this(ArrayUtils.getVector2fs(coords));
    }
    
    public Shape(Vec2... vertices)
    {
        this.vertices = vertices;
        regen();
    }
    
    public abstract boolean intersects(Shape s);
    
    public abstract boolean contains(Vec2 v);
    
    public void translate(float x, float y)
    {
        for (Vec2 vertex : vertices)
        {
            vertex.x += x;
            vertex.y += y;
        }
    }
    
    public void translateTo(float x, float y)
    {
        translateTo(x, y, 0, 0);
    }
    
    public void translateTo(float x, float y, float origx, float origy)
    {
        for (Vec2 vertex : vertices)
        {
            vertex.x = Math.abs(vertex.x - nx + origx + y);
            vertex.y = Math.abs(vertex.x - ny + origy + x);
        }
        regen();
    }
    
    public void rotate(float origx, float origy, float angle)
    {
        for (Vec2 p : vertices)
        {
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
    
    public void rotate(float angle)
    {
        rotate(nx + getWidth() / 2, ny + getHeight() / 2, angle);
    }
    
    public void rotateTo(float origx, float origy, float angle)
    {
        rotate(origx, origy, rot - angle);
    }
    
    public void rotateTo(float angle)
    {
        rotateTo(nx + getWidth() / 2, ny + getHeight() / 2, angle);
    }
    
    public float getArea()
    {
        return getWidth() * getHeight();
    }
    
    public float getX1()
    {
        regen();
        return nx;
    }
    
    public float getY1()
    {
        regen();
        return ny;
    }
    
    public float getX2()
    {
        regen();
        return fx;
    }
    
    public float getY2()
    {
        regen();
        return fy;
    }
    
    public Point getPoint1()
    {
        regen();
        return new Point(nx, ny);
    }
    
    public Point getPoint2()
    {
        regen();
        return new Point(fx, fy);
    }
    
    public float getWidth()
    {
        regen();
        return Math.abs(fx - nx);
    }
    
    public float getHeight()
    {
        regen();
        return Math.abs(fy - ny);
    }
    
    public Vec2 getCenterPoint()
    {
        regen();
        return center;
    }
    
    public float getCenterX()
    {
        regen();
        return center.x;
    }
    
    public float getCenterY()
    {
        regen();
        return center.y;
    }
    
    private void regen()
    {
        nx = vertices[0].x;
        ny = vertices[0].y;
        fx = nx;
        fy = ny;
        
        for (Vec2 vertex : vertices)
        {
            nx = Math.min(vertex.x, nx);
            ny = Math.min(vertex.y, ny);
            fx = Math.max(vertex.x, fx);
            fy = Math.max(vertex.y, fy);
        }
        
        center = new Vec2((nx + fx) / 2, (ny + fy) / 2);
        radius = Math.abs(fx - nx > fy - ny ? fx - nx : fy - ny);
    }
    
    public Vec2[] getVertices()
    {
        return vertices;
    }
    
    @Override
    public String toString()
    {
        return "Shape with: x1:" + nx + ", y1:" + ny + ", x2:" + fx + ", y2:" + fy;
    }
}
