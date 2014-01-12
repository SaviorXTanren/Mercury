package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;

/**
 * A triangle shape; 3 sides.
 * 
 * @from MERCury in com.wessles.MERCury.geom
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Triangle extends Shape
{
    
    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        super(x1, y1, x2, y2, x3, y3);
    }
    
    public Triangle(Color[] colors, float x1, float y1, float x2, float y2, float x3, float y3)
    {
        super(colors, new float[]
        {
                x1, y1, x2, y2, x3, y3
        });
    }
    
    @Override
    public float getArea()
    {
        return getWidth() * getHeight() / 2;
    }
    
    @Override
    public boolean intersects(Shape s)
    {
        return new Rectangle(getX1(), getY1(), getX2() / 2, getY1(), getX2() / 2, getY2() / 2, getX1(), getY2() / 2).intersects(s);
    }
    
    @Override
    public boolean contains(Vector2f v)
    {
        return new Rectangle(getX1(), getY1(), getX2() / 2, getY1(), getX2() / 2, getY2() / 2, getX1(), getY2() / 2).contains(v);
    }
}
