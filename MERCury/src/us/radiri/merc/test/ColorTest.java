package us.radiri.merc.test;

import static us.radiri.merc.gfx.Color.black;
import static us.radiri.merc.gfx.Color.blue;
import static us.radiri.merc.gfx.Color.coal;
import static us.radiri.merc.gfx.Color.cyan;
import static us.radiri.merc.gfx.Color.gray;
import static us.radiri.merc.gfx.Color.green;
import static us.radiri.merc.gfx.Color.magenta;
import static us.radiri.merc.gfx.Color.marble;
import static us.radiri.merc.gfx.Color.ocean;
import static us.radiri.merc.gfx.Color.orange;
import static us.radiri.merc.gfx.Color.rasberry;
import static us.radiri.merc.gfx.Color.red;
import static us.radiri.merc.gfx.Color.springgreen;
import static us.radiri.merc.gfx.Color.turquoise;
import static us.radiri.merc.gfx.Color.violet;
import static us.radiri.merc.gfx.Color.white;
import static us.radiri.merc.gfx.Color.yellow;
import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;

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
