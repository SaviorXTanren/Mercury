package com.teama.merc.geo;

import com.teama.merc.math.MercMath;

/**
 * An ellipse that can have a length and width.
 * 
 * @from merc in com.teama.merc.geo
 * @authors wessles
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
    
    public static Vec2[] getTrigVerts(float x, float y, float radx, float rady)
    {
        radx = Math.abs(radx);
        rady = Math.abs(rady);
        
        Vec2[] verts = new Vec2[MAX_VERTS];
        
        float angle = 0, step = 360 / MAX_VERTS;
        
        for (int a = 0; a < MAX_VERTS; a++)
        {
            verts[a] = new Vec2(x + MercMath.cos(angle) * radx, y + MercMath.sin(angle) * rady);
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
    public boolean contains(Vec2 v)
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
