package com.radirius.merc.test;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Circle;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;

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
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    Rectangle r1 = new Rectangle(10, 0, 10);
    Rectangle r2 = new Rectangle(15, 30, 15);
    Circle c1 = new Circle(50, 0, 5);
    Circle c2 = new Circle(50.5f, 30, 7.5f);
    
    @Override
    public void render(Graphics g) {
        g.setDrawMode(Graphics.MODE_LINE);
        
        g.drawRect(r1);
        g.drawRect(r2);
        
        r1.translate(0, 0.1f);
        r2.translate(0, -0.1f);
        
        g.pushSetColor(Color.blue);
        g.drawString(0, 200, r1.intersects(r2) ? "Yup, intersection at rectangles." : "Nope, no intersection at rectangles.");
        
        
        g.drawCircle(c1);
        g.drawCircle(c2);
        
        c1.translate(0, 0.1f);
        c2.translate(0, -0.2f);

        g.pushSetColor(Color.blue);
        g.drawString(0, 100, c1.intersects(c2) ? "Yup, intersection at circles." : "Nope, no intersection at circles.");
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new CollisionTest();
    }
}
