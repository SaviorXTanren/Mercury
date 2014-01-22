package com.teama.merc.geo;

/**
 * A triangle shape; 3 sides.
 * 
 * @from merc in com.teama.merc.geo
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Triangle extends Shape
{
    
    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        super(new float[]
        {
                x1, y1, x2, y2, x3, y3
        });
    }
    
    @Override
    public float getArea()
    {
        float base = (float) Math.sqrt(Math.abs(vertices[2].x - vertices[0].x) * Math.abs(vertices[2].y - vertices[0].y));
        Vector2f midpoint = new Vector2f((vertices[2].x + vertices[0].x) / 2, (vertices[2].y + vertices[0].y) / 2);
        float base_to_top_height = (float) Math.sqrt(Math.abs(midpoint.x - vertices[1].x) * Math.abs(midpoint.y - vertices[1].y));
        
        // The whistle was blown,
        // t'was the code as follows
        // that made me revise,
        // this perfect little snippet
        //
        // return (getWidth() getHeight()) / 2;
        //
        // He said base != width-ippit
        //
        // He unfortunately went missing in an eppit.
        // Well here is a quite big tippet.
        // Don't ask too many questions, source lurkits...
        // Not one too many... Beware... crickets...
        //
        // But the saddest little ippet
        // is that this snippet of code, may still not be right.
        // Double, triple, quadtippet your code, younglings.
        //
        // Simply signed, The Loomy Narty.
        
        return base * base_to_top_height / 2;
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
