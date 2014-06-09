package com.radirius.merc.geo;

import com.radirius.merc.math.MercMath;

/**
 * An ellipse that can have a length and width.
 * 
 * @author wessles
 */
public class Ellipse extends Shape {
    /** The radius in the respective axis. */
    public float radx, rady;
    /**
     * Maximum amount of vertices that can be rendered when rendering an ellipse
     */
    public static final int MAX_VERTS = 60;
    
    /**
     * @param x
     *            The x position of the center.
     * @param y
     *            The y position of the center.
     * @param radx
     *            The radius of the circle in the x axis.
     * @param rady
     *            The radius of the circle in the y axis.
     */
    public Ellipse(float x, float y, float radx, float rady) {
        super(getTrigVerts(x, y, radx, rady));
        this.radx = radx;
        this.rady = rady;
    }
    
    /**
     * @return Basically the vertices for a whole bunch of triangles 'slices'
     *         that make up a circle, or 'pie.'
     */
    public static Vec2[] getTrigVerts(float x, float y, float radx, float rady) {
        radx = Math.abs(radx);
        rady = Math.abs(rady);
        
        Vec2[] verts = new Vec2[MAX_VERTS];
        
        float angle = 0, step = 360 / MAX_VERTS;
        
        for (int a = 0; a < MAX_VERTS; a++) {
            verts[a] = new Vec2(x + MercMath.cos(angle) * radx, y + MercMath.sin(angle) * rady);
            angle += step;
        }
        
        return verts;
    }
    
    @Override
    public boolean intersects(Shape s) {
        // Center positions.
        float cx = getX() + getWidth() / 2;
        float cy = getY() + getHeight() / 2;
        
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
    public boolean contains(Vec2 v) {
        // Center positions.
        float cx = getX() + getWidth() / 2;
        float cy = getY() + getHeight() / 2;
        
        float x1 = cx - radx / 2;
        float y1 = cy - rady / 2;
        
        float x3 = cx + radx / 2;
        float y3 = cy + rady / 2;
        
        return v.x > x1 && v.x < x3 && v.y > y1 && v.y < y3;
    }
}
