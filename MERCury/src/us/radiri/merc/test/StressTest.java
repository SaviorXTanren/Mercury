package us.radiri.merc.test;

import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.res.Loader;

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
        rnr.setShowFPS(true);
        rnr.setShowVerticesLastRendered(true);
        
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
