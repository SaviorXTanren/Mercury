package us.radiri.merc.test;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.geom.Triangle;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.gui.CheckBox;
import us.radiri.merc.maths.MercMath;

/** @author opiop65, wessles */

public class LineTest extends Core {
    
    Runner rnr = Runner.getInstance();
    
    public LineTest(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    CheckBox toggle_left, toggle_right;
    
    @Override
    public void init() {
        toggle_left = new CheckBox(" Wireframe", 100, 10, 32, false);
        toggle_right = new CheckBox(" Wireframe", 500, 10, 32);
    }
    
    @Override
    public void update(float delta) {
        toggle_left.update(delta);
        toggle_right.update(delta);
    }
    
    @Override
    public void render(Graphics g) {
        if (!toggle_left.isTicked()) {
            g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0,
                    255)));
            g.drawRect(new Rectangle(100, 100, 100));
            g.drawCircle(100, 100, 50);
        } else {
            g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0,
                    255)));
            g.traceRect(new Rectangle(100, 100, 100));
            g.traceCircle(100, 100, 50);
        }
        if (!toggle_right.isTicked()) {
            g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0,
                    255)));
            g.drawRect(new Rectangle(650, 100, 50));
            g.drawCircle(600, 200, 50);
            g.drawTriangle(new Triangle(600, 200, 610, 200, 600, 190));
        }
        {
            g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0,
                    255)));
            g.traceRect(new Rectangle(650, 100, 50));
            g.traceCircle(600, 200, 50);
            g.traceTriangle(new Triangle(600, 200, 610, 200, 600, 190));
        }
        
        g.setColor(Color.white);
        
        toggle_left.render(g);
        toggle_right.render(g);
        
        g.drawLine(600, 200, 100, 100);
        
        g.drawString(300, 400, "This is rendering in GL_LINES, which is pretty chill.");
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new LineTest("Line Test");
    }
}
