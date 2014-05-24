package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Spritesheet;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @author opiop65
 */

public class SpritesheetTest extends Core {
    
    private Runner rnr = Runner.getInstance();
    private Spritesheet sheet;
    
    public SpritesheetTest(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        sheet = Spritesheet.loadSheet(Loader.loadFromClasspath("com/radirius/merc/test/tiles.txt"), Loader.streamFromClasspath("com/radirius/merc/test/tiles.png"));
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.setBackground(Color.marble);
        g.drawTexture(sheet.getTexture("Grass"), 10, 10, 128, 128);
        g.drawTexture(sheet.getTexture("Void"), 200, 10, 128, 128);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new SpritesheetTest("Spritesheet Test");
    }
}