package us.radiri.merc.geo;

import us.radiri.merc.math.MercMath;
import us.radiri.merc.util.ArrayUtils;

/**
 * The abstraction of all shapes.
 * 
 * @author wessles
 */

public abstract class Shape {
    /** All vertices that make up the shape. */
    protected Vec2[] vertices;
    
    /** The x value of the vertex top-left-most vertex. */
    protected float x;
    /** The y value of the vertex top-left-most vertex. */
    protected float y;
    /** The x value of the vertex bottom-right-most vertex. */
    protected float x2;
    /** The y value of the vertex bottom-right-most vertex. */
    protected float y2;
    /** The absolute value of the difference of x and x2. */
    protected float w;
    /** The absolute value of the difference of y and y2. */
    protected float h;
    
    /** The rotation angle (in degrees, COS screw radians) */
    protected float rot = 0;
    
    /**
     * @param coords
     *            The coordinates of all vertices in the shape. Will be parsed
     *            for every 2 values into a Vec2.
     */
    public Shape(float... coords) {
        this(ArrayUtils.getVector2fs(coords));
    }
    
    /**
     * @param vertices
     *            All vertices in the shape.
     */
    public Shape(Vec2... vertices) {
        this.vertices = vertices;
        regen();
    }
    
    /** @return Whether s intersects with this shape. */
    public boolean intersects(Shape s) {
        // This method is VERY inefficient! You are supposed to use this as a
        // base, and then make specific-cases that save processing time where
        // you can.
        //
        // Let's go over the key points of what this method does: it will loop
        // through all vertices of this object, make lines between them, and
        // test that line against the lines of the other shape. This is a great
        // idea, included even in Slick2D's code[1]. There is one flaw to it
        // though: if a smaller object is inside of a bigger object, and none of
        // the lines are touching, then it is not colliding by this algorithm.
        //
        // To (roughly) fix this, we will test to see that the center of the
        // smaller shape does not contain the center point of the larger shape.
        
        // Loop through all of the vertices!
        for (int v_ = 0; v_ < vertices.length; v_++) {
            // The amount we have to step to get to the second vertex on a line.
            // The reason this can't be just '1' is since the last vertex needs
            // to connect to the bottom! In that case, v_+1 will not cut it.
            int step1 = v_ != vertices.length - 1 ? 1 : -(vertices.length - 1);
            // The 'line1vertex1' and 'line1vertex2'
            Vec2 l1v1 = vertices[v_], l1v2 = vertices[v_ + step1];
            
            // Now, for each line in this shape, we need to test all lines in
            // the other shape.
            for (int v2_ = 0; v2_ < s.vertices.length; v2_++) {
                int step2 = v2_ != s.vertices.length - 1 ? 1 : -(s.vertices.length - 1);
                Vec2 l2v1 = s.vertices[v2_], l2v2 = s.vertices[v2_ + step2];
                
                // Now we test!
                if (linesIntersect(l1v1, l1v2, l2v1, l2v2))
                    return true;
            }
        }
        
        // Now to do the center test!
        
        if (getArea() > s.getArea())
            return contains(new Vec2(s.getX() + s.getWidth() / 2, s.getY() + s.getHeight() / 2));
        else
            return s.contains(new Vec2(getX() + getWidth() / 2, getY() + getHeight() / 2));
    }
    
    /**
     * @param l1_1
     *            Point 1 of line 1.
     * @param l1_2
     *            Point 2 of line 1.
     * @param l2_1
     *            Point 1 of line 2.
     * @param l2_2
     *            Point 2 of line 2.
     * @return If the given lines intersect.
     */
    private boolean linesIntersect(Vec2 l1_1, Vec2 l1_2, Vec2 l2_1, Vec2 l2_2) {
        float UA = ((l2_2.x - l2_1.x) * (l1_1.y - l2_1.y) - (l2_2.y - l2_1.y) * (l1_1.x - l2_1.x)) / ((l2_2.y - l2_1.y) * (l1_2.x - l1_1.x) - (l2_2.x - l2_1.x) * (l1_2.y - l1_1.y));
        float UB = ((l1_2.x - l1_1.x) * (l1_1.y - l2_1.y) - (l1_2.y - l1_1.y) * (l1_1.x - l2_1.x)) / ((l2_2.y - l2_1.y) * (l1_2.x - l1_1.x) - (l2_2.x - l2_1.x) * (l1_2.y - l1_1.y));
        
        if (UA >= 0 && UA <= 1 && UB >= 0 && UB <= 1)
            return true;
        
        return false;
    }
    
    /** @return Whether all vertices of s is inside of this shape. */
    public boolean contains(Shape s) {
        // Loop through all vertices and test them.
        for (Vec2 v : s.vertices)
            if (contains(v))
                return true;
        
        return false;
    }
    
    /** @return Whether v is inside of this shape. */
    public boolean contains(Vec2 v) {
        // This is based off of the fact that any point inside of a convex
        // shape's angles add up to 360. Thus, we will find the sum of all
        // angles of all vertices in the shape to the one point we are testing.
        
        float sumangle = 0;
        
        for (int v_ = 0; v_ < vertices.length; v_++) {
            Vec2 v2 = vertices[v_];
            
            // Find angle between vertex of shape and point.
            float dx = v.x - v2.x, dy = v.y - v2.x;
            float angle = MercMath.atan2(dy, dx);
            sumangle += angle;
        }
        
        if (sumangle != 360)
            return false;
        
        return true;
    }
    
    /**
     * Moves all vertices by x and y.
     * 
     * @param x
     *            The amount every vertex should move on x.
     * @param y
     *            The amount every vertex should move on y.
     * */
    public void translate(float x, float y) {
        for (Vec2 vertex : vertices) {
            vertex.x += x;
            vertex.y += y;
        }
        
        regen();
    }
    
    /**
     * Moves all vertices to x and y.
     * 
     * @param x
     *            Where every vertex should move relative to 0 on x.
     * @param y
     *            Where every vertex should move relative to 0 on y.
     */
    public void translateTo(float x, float y) {
        translateTo(x, y, 0, 0);
    }
    
    /**
     * Moves all vertices to x and y relative to a origin.
     * 
     * @param x
     *            Where every vertex should move relative to the origin on x.
     * @param y
     *            Where every vertex should move relative to the origin on y.
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     */
    public void translateTo(float x, float y, float origx, float origy) {
        for (Vec2 vertex : vertices) {
            vertex.x = Math.abs(vertex.x - x + origx + y);
            vertex.y = Math.abs(vertex.x - y + origy + x);
        }
        
        regen();
    }
    
    /**
     * Rotate the object relative to a origin.
     * 
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     * @param angle
     *            The angle by which the object will rotate relative to the
     *            origin.
     */
    public void rotate(float origx, float origy, float angle) {
        for (Vec2 p : vertices) {
            float s = MercMath.sin(angle);
            float c = MercMath.cos(angle);
            
            p.x -= origx;
            p.y -= origy;
            
            float xnew = p.x * c - p.y * s;
            float ynew = p.x * s + p.y * c;
            
            p.x = xnew + origx;
            p.y = ynew + origy;
        }
        
        rot = angle;
        
        regen();
    }
    
    /**
     * Rotate the object relative to the center of the object.
     * 
     * @param angle
     *            The angle of rotation.
     */
    public void rotate(float angle) {
        rotate(x + getWidth() / 2, y + getHeight() / 2, angle);
    }
    
    /**
     * Rotate the object to a point in rotation relative to a origin.
     * 
     * @param origx
     *            The origin's x.
     * @param origy
     *            The origin's y.
     * @param angle
     *            The angle by which the object will rotate to relative to the
     *            origin.
     */
    public void rotateTo(float origx, float origy, float angle) {
        rotate(origx, origy, rot - angle);
    }
    
    /**
     * Rotate the object to a point in rotation relative to the center of the
     * object.
     * 
     * @param angle
     *            The angle of rotation that the object will rotate to.
     */
    public void rotateTo(float angle) {
        rotateTo(x + getWidth() / 2, y + getHeight() / 2, angle);
    }
    
    /** @return A VERY rough estimate of area. */
    public float getArea() {
        return getWidth() * getHeight();
    }
    
    /** @return The x of the nearest vertex. */
    public float getX() {
        return x;
    }
    
    /** @return The y of the nearest vertex. */
    public float getY() {
        return y;
    }
    
    /** @return The x of the farthest vertex. */
    public float getX2() {
        return x2;
    }
    
    /** @return The y of the farthest vertex. */
    public float getY2() {
        return y2;
    }
    
    /** @return The difference between the nearest x and the farthest x. */
    public float getWidth() {
        return Math.abs(x2 - x);
    }
    
    /** @return The difference between the nearest y and the farthest y. */
    public float getHeight() {
        return Math.abs(y2 - y);
    }
    
    public Vec2 getCenter() {
        return new Vec2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }
    
    /** This method will regenerate all of the values after you change things! */
    protected void regen() {
        x = vertices[0].x;
        y = vertices[0].y;
        x2 = x;
        y2 = y;
        
        for (Vec2 vertex : vertices) {
            x = Math.min(vertex.x, x);
            y = Math.min(vertex.y, y);
            x2 = Math.max(vertex.x, x2);
            y2 = Math.max(vertex.y, y2);
        }
        
        w = Math.abs(x2-x);
        h = Math.abs(y2-y);
    }
    
    /** @return All vertices of the object. */
    public Vec2[] getVertices() {
        return vertices;
    }
}
