package radirius.merc.tests;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Circle;
import radirius.merc.geometry.Rectangle;
import radirius.merc.geometry.Triangle;
import radirius.merc.geometry.Vec2;
import radirius.merc.graphics.Graphics;

/**
 * According to this test, the following collision events are valid: [see method
 * render()].
 * 
 * @author wessles
 */

public class CollisionTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public CollisionTest() {
        super("CollisionTest");
        rnr.init(this, 800, 600, false, false, true, true);
        rnr.run();
    }
    
    @Override
    public void init() {
        rnr.getGraphics().setScale(8);
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    Rectangle r1 = new Rectangle(10, 30, 10);
    Rectangle r2 = new Rectangle(12, 60, 4);
    Circle c1 = new Circle(50, 30, 10);
    Circle c2 = new Circle(50.5f, 60, 7.5f);
    Triangle t1 = new Triangle(80, 30, 80, 40, 90, 30);
    Triangle t2 = new Triangle(80, 60, 80, 70, 90, 60);
    Vec2 testcol = new Vec2(0, 35);
    
    @Override
    public void render(Graphics g) {
        if (r1.intersects(r2) || r1.contains(testcol)) {
            g.traceRect(r1);
            g.traceRect(r2);
        } else {
            g.drawRect(r1);
            g.drawRect(r2);
        }
        
        r2.translate(0, -0.1f);
        r2.rotate(0.1f);
        
        if (c1.intersects(c2) || c1.contains(testcol)) {
            g.traceCircle(c1);
            g.traceCircle(c2);
        } else {
            g.drawCircle(c1);
            g.drawCircle(c2);
        }
        
        c2.translate(0, -0.2f);
        c2.rotate(0.1f);
        
        if (t1.intersects(t2) || t1.contains(testcol)) {
            g.traceTriangle(t1);
            g.traceTriangle(t2);
        } else {
            g.drawTriangle(t1);
            g.drawTriangle(t2);
        }
        
        t2.translate(0f, -0.2f);
        t2.rotate(0.1f);
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new CollisionTest();
    }
}
