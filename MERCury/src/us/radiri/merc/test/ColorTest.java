package us.radiri.merc.test;

import static us.radiri.merc.graphics.Color.black;
import static us.radiri.merc.graphics.Color.blue;
import static us.radiri.merc.graphics.Color.coal;
import static us.radiri.merc.graphics.Color.cyan;
import static us.radiri.merc.graphics.Color.gray;
import static us.radiri.merc.graphics.Color.green;
import static us.radiri.merc.graphics.Color.magenta;
import static us.radiri.merc.graphics.Color.marble;
import static us.radiri.merc.graphics.Color.ocean;
import static us.radiri.merc.graphics.Color.orange;
import static us.radiri.merc.graphics.Color.rasberry;
import static us.radiri.merc.graphics.Color.red;
import static us.radiri.merc.graphics.Color.springgreen;
import static us.radiri.merc.graphics.Color.turquoise;
import static us.radiri.merc.graphics.Color.violet;
import static us.radiri.merc.graphics.Color.white;
import static us.radiri.merc.graphics.Color.yellow;
import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;

/**
 * Colors got screwed up... So this should be a good tool for the future and
 * now.
 * 
 * @author wessles
 */

public class ColorTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public ColorTest() {
        super("Color Test");
        rnr.init(this, 640, 480);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new ColorTest();
    }
    
    @Override
    public void init() {
        rnr.getGraphics().setBackground(Color.black);
        rnr.getGraphics().scale(8);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        test(red, g);
        test(orange, g);
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
        g.pushSetColor(color);
        g.drawRect(new Rectangle(colidx * 4, 0, 4, 8));
        colidx++;
    }
    
    @Override
    public void cleanup() {
    }
    
}
