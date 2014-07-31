package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;

/**
 * @author wessles
 */

public class ManipulatingGeometry extends Core {
    Runner runner = Runner.getInstance();
    
    public ManipulatingGeometry() {
        super("Manipulative Shapes");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new ManipulatingGeometry();
    }
    
    Rectangle rectangle;
    
    public void init() {
        rectangle = new Rectangle(0, 0, 100, 100);
    }
    
    public void update(float delta) {
        // Move the rectangle 1 over and 1 down every frame
        rectangle.translate(1, 1);
        // Rotate the rectangle by 2 degrees every frame
        rectangle.rotate(2);
        // Slightly shrink the rectangle every frame
        rectangle.scale(0.996f);
    }
    
    public void render(Graphics g) {
        g.drawRect(rectangle);
    }
    
    public void cleanup() {
        
    }
}
