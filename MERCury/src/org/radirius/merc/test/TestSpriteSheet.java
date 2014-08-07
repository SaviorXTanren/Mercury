package org.radirius.merc.test;

import org.radirius.merc.gfx.Animation;
import org.radirius.merc.gfx.Color;
import org.radirius.merc.gfx.Graphics;
import org.radirius.merc.gfx.SpriteSheet;
import org.radirius.merc.gfx.Texture;
import org.radirius.merc.main.Core;
import org.radirius.merc.main.Runner;
import org.radirius.merc.res.Loader;

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
        sheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/test/tiles.png")), 16, 16);
        anm = new Animation(300, sheet, 0, 6);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.setBackground(Color.marble);
        g.drawTexture(sheet.getBaseTexture(), 0, 0);
        anm.render(10, 10, g);
        anm.nextFrame();
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new TestSpriteSheet("SpriteSheet Test");
    }
}