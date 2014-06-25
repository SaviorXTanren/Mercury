package us.radiri.merc.test;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class StressTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public StressTest() {
        super("MERCury Stress Test");
        rnr.init(this, 400, 400);
        rnr.run();
    }
    
    Texture dAWWWW;
    
    @Override
    public void init() {
        rnr.getGraphics().scale(2);
        
        dAWWWW = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.drawTexture(dAWWWW, new Rectangle(0, 0, rnr.getWidth()/2, rnr.getHeight()/2));
        // Renders 427,890 vertices to OGL. That is a lot.
        for (float x = 0; x < rnr.getWidth(); x += 1.5)
            for (float y = 0; y < rnr.getHeight(); y += 1.5)
                g.drawRect(new Rectangle(x, y, 0.5f));
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new StressTest();
    }
}
