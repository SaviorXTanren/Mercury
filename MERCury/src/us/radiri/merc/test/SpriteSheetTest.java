package us.radiri.merc.test;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.graphics.Animation;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.SpriteSheet;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author opiop65 (base code), wessles (modification)
 */

public class SpriteSheetTest extends Core {
    
    private Runner rnr = Runner.getInstance();
    private SpriteSheet sheet;
    private Animation anm;
    
    public SpriteSheetTest(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    @Override
    public void init() {
        sheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.stream("us/radiri/merc/test/tiles.png")), 16, 16);
        anm = new Animation(300, sheet, 0, 6);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        g.setBackground(Color.marble);
        g.drawTexture(sheet.getParentTexture(), 0, 0);
        anm.render(10, 10, g);
        anm.nextFrame();
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new SpriteSheetTest("SpriteSheet Test");
    }
}