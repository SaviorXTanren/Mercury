package com.radirius.merc.geo;

/**
 * An ellipse in which the length and width are the same.
 * 
 * @from merc in com.radirius.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class Circle extends Ellipse
{
    /**
     * This is a circle. It knows how to 'get around.' It also has a radius from
     * center to rim. And it's diameter goes from side to side; now isn't that
     * simple? PI(r^2) sounds a lot like area to me. If I need a circumphrance,
     * I'll just use PI(D). Now isn't that simple?
     * 
     * https://www.youtube.com/watch?v=lWDha0wqbcI
     * 
     * @param x
     *            The x position of the center.
     * @param y
     *            The y position of the center.
     * @param radius
     *            The radius of the circle (half of the diameter).
     */
    public Circle(float x, float y, float radius)
    {
        super(x, y, radius, radius);
    }
}
