package org.radirius.merc.test;

import static org.radirius.merc.gfx.Color.black;
import static org.radirius.merc.gfx.Color.blue;
import static org.radirius.merc.gfx.Color.brown;
import static org.radirius.merc.gfx.Color.coal;
import static org.radirius.merc.gfx.Color.cyan;
import static org.radirius.merc.gfx.Color.gray;
import static org.radirius.merc.gfx.Color.green;
import static org.radirius.merc.gfx.Color.magenta;
import static org.radirius.merc.gfx.Color.marble;
import static org.radirius.merc.gfx.Color.ocean;
import static org.radirius.merc.gfx.Color.orange;
import static org.radirius.merc.gfx.Color.rasberry;
import static org.radirius.merc.gfx.Color.red;
import static org.radirius.merc.gfx.Color.springgreen;
import static org.radirius.merc.gfx.Color.turquoise;
import static org.radirius.merc.gfx.Color.violet;
import static org.radirius.merc.gfx.Color.white;
import static org.radirius.merc.gfx.Color.yellow;

import org.radirius.merc.geom.Rectangle;
import org.radirius.merc.gfx.Color;
import org.radirius.merc.gfx.Graphics;
import org.radirius.merc.main.Core;
import org.radirius.merc.main.Runner;

/**
 * Colors got screwed up... So this should be a good tool for the future and
 * now.
 * 
 * @author wessles
 */

public class TestColor extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestColor() {
        super("Color Test");
        rnr.init(this, 640, 480);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new TestColor();
    }
    
    @Override
    public void init() {
        rnr.getGraphics().setBackground(Color.black);
        rnr.getGraphics().setScale(8);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        test(red, g);
        test(orange, g);
        test(brown, g);
        test(yellow, g);
        test(springgreen, g);
        test(green, g);
        test(turquoise, g);
        test(cyan, g);
        test(ocean, g);
        test(blue, g);
        test(violet, g);
        test(magenta, g);
        test(rasberry, g);
        
        test(black, g);
        
        test(white, g);
        test(marble, g);
        test(gray, g);
        test(coal, g);
        test(black, g);
        colidx = 0;
    }
    
    int colidx;
    
    public void test(Color color, Graphics g) {
        g.setColor(color);
        g.drawRect(new Rectangle(colidx * 4, 0, 4, 8));
        colidx++;
    }
    
    @Override
    public void cleanup() {
    }
    
}
