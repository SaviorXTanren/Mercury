package us.radiri.merc.geom;

/**
 * A triangle shape; 3 sides.
 * 
 * @author wessles
 */

public class Triangle extends Shape {
    
    /** All parameters are the coordinates of each vertex in the triangle. */
    public Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        super(new float[] { x1, y1, x2, y2, x3, y3 });
    }
    
    @Override
    public float getArea() {
        return getWidth() * getHeight() / 2;
    }
    
    @Override
    public boolean contains(Vec2 v) {
        // Source:
        // http://stackoverflow.com/questions/2049582/how-to-determine-a-point-in-a-triangle
        
        boolean b1, b2, b3;
        
        b1 = sign(v, vertices[0], vertices[1]) < 0.0f;
        b2 = sign(v, vertices[1], vertices[2]) < 0.0f;
        b3 = sign(v, vertices[2], vertices[0]) < 0.0f;
        
        return b1 == b2 && b2 == b3;
    }
    
    private float sign(Vec2 p1, Vec2 p2, Vec2 p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
    }
}
