package com.wessles.MERCury.geom;

import com.wessles.MERCury.maths.MercMath;

/**
 * An ellipse that can have a length and width.
 * 
 * @from MERCury in com.wessles.MERCury.geom
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class Ellipse extends Shape
{
    public float radx, rady;
    public static final int MAX_VERTS = 60;
    
    public Ellipse(float x, float y, float radx, float rady)
    {
        super(getTrigVerts(x, y, radx, rady));
        this.radx = radx;
        this.rady = rady;
    }
    
    public static Vector2f[] getTrigVerts(float x, float y, float radx, float rady)
    {
        radx = Math.abs(radx);
        rady = Math.abs(rady);
        
        Vector2f[] verts = new Vector2f[MAX_VERTS];
        
        float angle = 0, step = 360 / MAX_VERTS;
        
        for (int a = 0; a < MAX_VERTS; a++)
        {
            verts[a] = new Vector2f(x + MercMath.cos(angle) * radx, y + MercMath.sin(angle) * rady);
            angle += step;
        }
        
        return verts;
    }
    
    @Override
    public boolean intersects(Shape s)
    {
        float cx = getCenterX();
        float cy = getCenterY();
        
        float x1 = cx - radx / 2;
        float y1 = cy - rady / 2;
        
        float x2 = cx + radx / 2;
        float y2 = cy - rady / 2;
        
        float x3 = cx + radx / 2;
        float y3 = cy + rady / 2;
        
        float x4 = cx - radx / 2;
        float y4 = cy + rady / 2;
        
        return new Rectangle(x1, y1, x2, y2, x3, y3, x4, y4).intersects(s);
    }
    
    @Override
    public boolean contains(Vector2f v)
    {
        float cx = getCenterX();
        float cy = getCenterY();
        
        float x1 = cx - radx / 2;
        float y1 = cy - rady / 2;
        
        float x3 = cx + radx / 2;
        float y3 = cy + rady / 2;
        
        return v.x > x1 && v.x < x3 && v.y > y1 && v.y < y3;
    }
}
