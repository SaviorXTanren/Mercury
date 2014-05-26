package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Animation;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.SpriteSheet;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

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
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        sheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.stream("com/radirius/merc/test/tiles.png")), 16, 16);
        anm = new Animation(300, sheet, 0, 6);
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.setBackground(Color.marble);
        g.drawTexture(sheet.getParentTexture(), 0, 0);
        anm.render(10, 10, g);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new SpriteSheetTest("SpriteSheet Test");
    }
}