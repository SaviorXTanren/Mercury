package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.resource.Loader;

/**
 * @author wessles
 */

public class TheGraphicsObject extends Core {
    Runner rnr = Runner.getInstance();
    
    public TheGraphicsObject(String name) {
        super(name);
        // Make a game window that is 500x500
        rnr.init(this, 500, 500);
        // Now run it!
        rnr.run();
    }
    
    Texture tex;
    
    @Override
    public void init() {
        tex = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/tests/dAWWWW.png"));
        rnr.getGraphics().setBackground(Color.blue);
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(new Rectangle(0, 0, 100, 100));
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new TheGraphicsObject("Next Gen Graphics!!!");
    }
    
}
