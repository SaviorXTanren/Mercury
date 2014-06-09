package com.radirius.merc.test;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Animation;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.SpriteSheet;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;

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
        sheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.stream("com/radirius/merc/test/tiles.png")), 16, 16);
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
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new SpriteSheetTest("SpriteSheet Test");
    }
}