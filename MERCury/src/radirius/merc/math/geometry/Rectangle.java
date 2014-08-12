package radirius.merc.math.geometry;

/**
 * A rectangle shape.
 * 
 * @author wessles
 */

public class Rectangle extends Shape {
    
    /**
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param w
     *            The width of the rectangle.
     * @param h
     *            The height of the rectangle.
     */
    public Rectangle(float x, float y, float w, float h) {
        super(new float[] { x, y, x + w, y, x + w, y + h, x, y + h });
    }
    
    /**
     * @param x
     *            The x position.
     * @param y
     *            The y position.
     * @param s
     *            The size of the rectangle.
     */
    public Rectangle(float x, float y, float s) {
        super(new float[] { x, y, x + s, y, x + s, y + s, x, y + s });
    }
    
    /** All parameters are the coordinates of each vertex in the rectangle. */
    public Rectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        super(x1, y1, x2, y2, x3, y3, x4, y4);
    }
    
    @Override
    public float getArea() {
        return getWidth() * getHeight();
    }
    
    @Override
    public boolean contains(Vec2 v) {
        return v.x >= getX() && v.x <= getX2() && v.y >= getY() && v.y <= getY2();
    }
}
