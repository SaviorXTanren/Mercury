package radirius.merc.geometry;

import radirius.merc.maths.MercMath;

/**
 * @author wessles
 */

public class Polygon extends Shape {
    public Polygon(float centerx, float centery, float radius, int numberofsides) {
        this(centerx, centery, radius, radius, numberofsides);
    }
    
    public Polygon(float centerx, float centery, float radiusx, float radiusy, int numberofsides) {
        super(getTrigVerts(centerx, centery, radiusx, radiusy, numberofsides));
    }
    
    /**
     * @return Basically the vertices for a whole bunch of triangles 'slices'
     *         that make up a 'pie.'
     */
    protected static Vec2[] getTrigVerts(float x, float y, float radiusx, float radiusy, int numberofsides) {
        if (numberofsides < 3)
            throw new IllegalArgumentException("Polygon must have at least 3 sides!");
        
        Vec2[] verts = new Vec2[numberofsides];
        
        // start at 270, so that we have an upwards-facing polygon. More fun
        // that way.
        float angle = 270, step = 360 / numberofsides;
        
        for (int a = 0; a < numberofsides; a++) {
            if (angle > 360)
                angle %= 360;
            verts[a] = new Vec2(x + MercMath.cos(angle) * radiusx, y + MercMath.sin(angle) * radiusy);
            angle += step;
        }
        
        return verts;
    }
}
