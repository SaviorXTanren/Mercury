package org.radirius.merc.geom;

/**
 * A line class.
 * 
 * @author wessles
 */

public class Line extends Shape {
    private float slope, xintercept, yintercept;
    
    public Line(float x1, float y1, float x2, float y2) {
        this(new Vec2(x1, y1), new Vec2(x2, y2));
    }
    
    public Line(Vec2 p1, Vec2 p2) {
        super(p1, p2);
    }
    
    public float getSlope() {
        return slope;
    }
    
    public float getYIntercept() {
        return yintercept;
    }
    
    public float getXIntercept() {
        return xintercept;
    }
    
    @Override
    public boolean intersects(Shape s) {
        if (s instanceof Line) {
            Vec2 l1_1 = vertices[0];
            Vec2 l1_2 = vertices[1];
            Vec2 l2_1 = s.vertices[0];
            Vec2 l2_2 = s.vertices[1];
            
            float UA = ((l2_2.x - l2_1.x) * (l1_1.y - l2_1.y) - (l2_2.y - l2_1.y) * (l1_1.x - l2_1.x)) / ((l2_2.y - l2_1.y) * (l1_2.x - l1_1.x) - (l2_2.x - l2_1.x) * (l1_2.y - l1_1.y));
            float UB = ((l1_2.x - l1_1.x) * (l1_1.y - l2_1.y) - (l1_2.y - l1_1.y) * (l1_1.x - l2_1.x)) / ((l2_2.y - l2_1.y) * (l1_2.x - l1_1.x) - (l2_2.x - l2_1.x) * (l1_2.y - l1_1.y));
            
            if (UA >= 0 && UA <= 1 && UB >= 0 && UB <= 1)
                return true;
        } else
            return super.intersects(s);
        
        return false;
    }
    
    @Override
    public boolean contains(Vec2 p) {
        // Plug the point into this formula, and see if it checks out.
        // ----- b = y-mx
        
        return p.y - slope * p.x == yintercept;
    }
    
    @Override
    public float getArea() {
        // Lines always have an area of 1.
        // Proof:
        // http://math.stackexchange.com/questions/256803/is-the-area-of-a-line-1
        return 1;
    }
    
    @Override
    public void regen() {
        // Get our two points.
        Vec2 p1 = vertices[0], p2 = vertices[1];
        
        // ----- y = mx + b
        // or,
        // ----- b = y-mx
        // Where:
        // ----- y is the y value of a point on the line
        // ----- x is the x value of said point on the line
        // ----- m is the slope of the line
        // ----- b is the y intercept of the line
        
        slope = (p2.y - p2.y) / (p2.x - p1.x);
        yintercept = p1.y - slope * p1.x;
        // And then we have the y intercept, just because.
        xintercept = -yintercept / slope;
    }
}
