package radirius.merc.test;

import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.main.Core;
import radirius.merc.main.Runner;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.resource.Loader;

/**
 * @author wessles
 */

public class Test4Stress extends Core {
    Runner rnr = Runner.getInstance();
    
    public Test4Stress() {
        super("MERCury Stress Test");
        rnr.init(this, 400, 400);
        rnr.run();
    }
    
    Texture dAWWWW;
    
    @Override
    public void init() {
        rnr.getGraphics().setScale(2);
        
        dAWWWW = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/main/merc_mascot_x64.png"));
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.drawTexture(dAWWWW, new Rectangle(0, 0, rnr.getWidth() / 2, rnr.getHeight() / 2));
        // Renders 427,890 vertices to OGL. That is a lot.
        for (float x = 5 * (float) Math.cos(System.nanoTime() / 100000000f); x < rnr.getWidth(); x += 1.5)
            for (float y = 5 * (float) Math.cos(System.nanoTime() / 100000000f); y < rnr.getHeight(); y += 1.5)
                g.drawRect(new Rectangle(x, y, 0.5f));
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new Test4Stress();
    }
}
