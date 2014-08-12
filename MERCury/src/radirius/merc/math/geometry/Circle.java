package radirius.merc.math.geometry;

/**
 * An ellipse in which the length and width are the same.
 * 
 * @author wessles
 */
public class Circle extends Ellipse {
    
    public Circle(float x, float y, float radius) {
        super(x, y, radius, radius);
    }
    
    @Override
    public boolean intersects(Shape s) {
        if (s instanceof Circle) {
            float dist = s.getCenter().distance(getCenter());
            if (dist <= ((Circle) s).radx + radx)
                return true;
        } else
            return super.intersects(s);
        
        return false;
    }
}
