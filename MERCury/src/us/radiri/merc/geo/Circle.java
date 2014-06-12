package us.radiri.merc.geo;

/**
 * An ellipse in which the length and width are the same.
 * 
 * @author wessles
 */
public class Circle extends Ellipse {
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
    public Circle(float x, float y, float radius) {
        super(x, y, radius, radius);
    }
    
    @Override
    public boolean intersects(Shape s) {
        if (s instanceof Circle) {
            float dist = s.getCenter().distance(getCenter());
            if(dist <= ((Circle) s).radx+radx)
                return true;
        } else
            return super.intersects(s);
        
        return false;
    }
}
