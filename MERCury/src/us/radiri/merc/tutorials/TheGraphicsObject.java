package us.radiri.merc.tutorials;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

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
        tex = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
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
