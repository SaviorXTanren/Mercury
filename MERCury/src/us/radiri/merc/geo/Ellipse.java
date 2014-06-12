package us.radiri.merc.geo;

import us.radiri.merc.math.MercMath;

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
    public static int MAX_VERTS = 40;
    
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
    
    // They are round, with a lot of vertices. This isn't pixel-perfect, but at least it is more efficient than this: 
    @Override
    public boolean intersects(Shape s) {
        if (s instanceof Ellipse) {
            for (Vec2 v : s.vertices)
                if (contains(v))
                    return true;
        } 
        return false;
    }
    
    @Override
    public boolean contains(Vec2 v) {
        // Source
        // http://math.stackexchange.com/questions/76457/check-if-a-point-is-within-an-ellipse
        float test = ((v.x - getCenter().x) * (v.x - getCenter().x)) / (radx * radx) + ((v.y - getCenter().y) * (v.y - getCenter().y)) / (rady * rady);
        return test <= 1;
    }
    
    @Override
    public float getArea() {
        return 3.14f * radx * rady;
    }
}
