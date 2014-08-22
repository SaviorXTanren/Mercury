package radirius.merc.test;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Animation;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.SpriteSheet;
import radirius.merc.graphics.Texture;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.resource.Loader;

/**
 * @author opiop65 (base code), wessles (modification)
 */

public class TestSpriteSheet extends Core {
    
    private Runner rnr = Runner.getInstance();
    private SpriteSheet sheet;
    private Animation anm;
    
    public TestSpriteSheet(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    @Override
    public void init() {
        sheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/test/tiles1.png")), 16, 16);
        anm = new Animation(300, sheet, 0, 6);
    }
    
    @Override
    public void update(float delta) {
    }
    
    Rectangle bounds = new Rectangle(100, 100, 60, 60);
    
    @Override
    public void render(Graphics g) {
        g.setBackground(Color.marble);
        g.drawTexture(sheet.getBaseTexture(), 0, 0);
        g.drawTexture(anm.getCurrentFrame(), bounds);
        bounds.setScale(1f + 0.6f * (float) Math.sin(System.currentTimeMillis() / 100d));
        bounds.rotate(2);
        anm.nextFrame();
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new TestSpriteSheet("SpriteSheet Test");
    }
}