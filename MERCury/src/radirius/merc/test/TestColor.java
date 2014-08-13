package radirius.merc.test;

import static radirius.merc.graphics.Color.black;
import static radirius.merc.graphics.Color.blue;
import static radirius.merc.graphics.Color.brown;
import static radirius.merc.graphics.Color.coal;
import static radirius.merc.graphics.Color.cyan;
import static radirius.merc.graphics.Color.gray;
import static radirius.merc.graphics.Color.green;
import static radirius.merc.graphics.Color.magenta;
import static radirius.merc.graphics.Color.marble;
import static radirius.merc.graphics.Color.ocean;
import static radirius.merc.graphics.Color.orange;
import static radirius.merc.graphics.Color.rasberry;
import static radirius.merc.graphics.Color.red;
import static radirius.merc.graphics.Color.springgreen;
import static radirius.merc.graphics.Color.turquoise;
import static radirius.merc.graphics.Color.violet;
import static radirius.merc.graphics.Color.white;
import static radirius.merc.graphics.Color.yellow;
import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.Rectangle;

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
