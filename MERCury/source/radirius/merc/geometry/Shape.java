package radirius.merc.geometry;

import java.util.ArrayList;

import radirius.merc.maths.MercMath;
import radirius.merc.utils.ArrayUtils;

/**
 * The abstraction of all shapes.
 * 
 * @author wessles
 */

public class Shape {
    protected Shape parent = null;
    protected ArrayList<Shape> children = new ArrayList<Shape>();
    
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
    
    /** The center of mass. Basically the mean location of all vertices. */
    protected Vec2 center;
    
    /** The rotation angle (in degrees, COS screw radians) */
    protected float rot = 0;
    
    /** Duplicate constructor. */
    public Shape(Shape s) {
        this.parent = s.parent;
        this.children = s.children;
        this.vertices = s.vertices;
        this.x = s.x;
        this.y = s.y;
        this.x2 = s.x2;
        this.y2 = s.y2;
        this.w = s.w;
        this.h = s.h;
        this.center = s.center;
        this.rot = s.rot;
        
        // Just to be safe :).
        regen();
    }
    
    /**
     * @param coords
     *            The coordinates of all vertices in the shape. Will be parsed
     *            for every 2 values into a Vec2.
     */
    protected Shape(float... coords) {
        this(ArrayUtils.getVector2fs(coords));
    }
    
    /**
     * @param vertices
     *            All vertices in the shape.
     */
    protected Shape(Vec2... vertices) {
        this.vertices = vertices;
        regen();
    }
    
    /** @return Whether s intersects with this shape. */
    public boolean intersects(Shape s) {
        for (Shape child : children)
            if (child.intersects(s))
                return true;
        
        // This method is VERY inefficient! You are supposed to use this as a
        // base, and then make specific-cases that save processing time where
        // you can. This is not to say that this is not to EVER be used, but
        // just to say that it is not the magical solution for ALL collision
        // problems.
        //
        // Let's go over the key points of what this method does: it will loop
        // through all vertices of this object, make lines between them, and
        // test that line against the lines of the other shape. This is a great
        // idea, included even in Slick2D's code. There is one flaw to it
        // though: if a smaller object is inside of a bigger object, and none of
        // the lines are touching, then it is not colliding by this algorithm.
        //
        // To (roughly) fix this, we will test to see that the center of the
        // smaller shape does not contain the center point of the larger shape.
        
        // Loop through all of the vertices!
        for (int v_ = 0; v_ < vertices.length;) {
            // The 'line1vertex1' and 'line1vertex2'
            //
            // For the second point, we want to make sure that we are not doing
            // twice the work for a line, which is not a closed shape.
            Vec2 l1v1 = vertices[v_], l1v2 = vertices.length > 2 ? vertices[++v_ % vertices.length] : vertices[++v_];
            Line l1 = new Line(l1v1, l1v2);
            
            // Now, for each line in this shape, we need to test all lines in
            // the other shape.
            for (int v2_ = 0; v2_ < s.vertices.length;) {
                Vec2 l2v1 = s.vertices[v2_], l2v2 = s.vertices.length > 2 ? s.vertices[++v2_ % vertices.length]
                        : vertices[++v2_];
                Line l2 = new Line(l2v1, l2v2);
                
                // Now we test!
                if (l1.intersects(l2))
                    return true;
            }
        }
        
        // Now to do the center test!
        
        if (getArea() > s.getArea())
            return contains(new Vec2(s.getX() + s.getWidth() / 2, s.getY() + s.getHeight() / 2));
        else
            return s.contains(new Vec2(getX() + getWidth() / 2, getY() + getHeight() / 2));
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
        for (Shape child : children)
            if (child.contains(v))
                return true;
        
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
     * @return Me
     * */
    public Shape translate(float x, float y) {
        for (Vec2 vertex : vertices) {
            vertex.x += x;
            vertex.y += y;
        }
        
        regen();
        
        for (Shape s : children)
            s.translate(x, y);
        
        return this;
    }
    
    /**
     * Moves all vertices to x and y.
     * 
     * @param x
     *            Where every vertex should move relative to the nearest point
     *            of the shape on x.
     * @param y
     *            Where every vertex should move relative to the nearest point
     *            of the shape on y.
     * @return Me
     */
    public Shape translateTo(float x, float y) {
        return translate(x - this.x, y - this.y);
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
     * @return Me
     */
    public Shape rotate(float origx, float origy, float angle) {
        if (angle == 0)
            return this;
        
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
        
        rot += angle;
        
        regen();
        
        for (Shape s : children)
            s.rotate(origx, origy, angle);
        
        return this;
    }
    
    /**
     * Rotate the object relative to the center of the object.
     * 
     * @param angle
     *            The angle of rotation.
     * @return Me
     */
    public Shape rotate(float angle) {
        return rotate(center.x, center.y, angle);
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
     * @return Me
     */
    public Shape rotateTo(float origx, float origy, float angle) {
        return rotate(origx, origy, rot - angle);
    }
    
    /**
     * Rotate the object to a point in rotation relative to the center of the
     * object.
     * 
     * @param angle
     *            The angle of rotation that the object will rotate to.
     * @return Me
     */
    public Shape rotateTo(float angle) {
        return rotateTo(center.x, center.y, angle);
    }
    
    /**
     * Flips the object over the y axis, relative to the mean center.
     * 
     * @return Me
     */
    public Shape flipX() {
        for (Vec2 v : vertices)
            v.add(new Vec2(0, (getCenter().y - v.y) * 2));
        
        regen();
        
        return this;
    }
    
    /**
     * Flips the object over the y axis, relative to the mean center.
     * 
     * @returns Me
     */
    public Shape flipY() {
        for (Vec2 v : vertices)
            v.add(new Vec2((getCenter().x - v.x) * 2, 0));
        
        regen();
        
        return this;
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
    
    /** @return The rotation of the object. */
    public float getRotation() {
        return rot;
    }
    
    /** @return The center of the object. */
    public Vec2 getCenter() {
        return center;
    }
    
    /** This method will regenerate all of the values after you change things! */
    protected void regen() {
        x = vertices[0].x;
        y = vertices[0].y;
        x2 = x;
        y2 = y;
        
        // Center x and y
        float cx = 0, cy = 0;
        
        for (Vec2 vertex : vertices) {
            cx += vertex.x;
            cy += vertex.y;
            
            x = Math.min(vertex.x, x);
            y = Math.min(vertex.y, y);
            x2 = Math.max(vertex.x, x2);
            y2 = Math.max(vertex.y, y2);
        }
        
        cx /= vertices.length;
        cy /= vertices.length;
        center = new Vec2(cx, cy);
        
        w = Math.abs(x2 - x);
        h = Math.abs(y2 - y);
    }
    
    /** @return All vertices of the object. */
    public Vec2[] getVertices() {
        return vertices;
    }
    
    /**
     * Adds a child shape.
     * 
     * @returns Me
     */
    public Shape addChild(Shape... child) {
        for (Shape s : child) {
            s.parent = this;
            children.add(s);
        }
        
        return this;
    }
    
    /** @return All child shapes. */
    public ArrayList<Shape> getChildren() {
        return children;
    }
    
    /**
     * Sets the parent of the shape.
     * 
     * @returns Me
     */
    public Shape setParent(Shape parent) {
        parent.addChild(this);
        
        return this;
    }
    
    /**
     * Makes me an orphan. Parent will lose me from it's arraylist of children,
     * so there is no trace of my previous life ;(.
     * 
     * @returns Me
     */
    public Shape clearParent() {
        parent.children.remove(this);
        this.parent = null;
        
        return this;
    }
    
    /** @returns The parent shape of me. */
    public Shape getParent() {
        return parent;
    }
}
