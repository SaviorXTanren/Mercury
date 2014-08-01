package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;

/**
 * @author wessles
 */

public class TutorialBasicGraphics extends Core {
    Runner runner = Runner.getInstance();
    
    public TutorialBasicGraphics() {
        super("Basic Graphics in MERCury");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new TutorialBasicGraphics();
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        // Makes a rectangle at (0,0) with a 100x100 size.
        Rectangle rectangle = new Rectangle(0, 0, 100, 100);
        // Draws the rectangle
        g.drawRect(rectangle);
        
        // Trace a blue rectangle
        g.setColor(Color.blue);
        // Makes a rectangle at (100,100) with a 100x100 size.
        Rectangle traced_rectangle = new Rectangle(100, 100, 100, 100);
        // Traces the rectangle
        g.traceRect(traced_rectangle);
    }
    
    @Override
    public void cleanup() {
    }
    
}
