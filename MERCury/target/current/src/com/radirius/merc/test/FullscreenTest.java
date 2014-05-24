package com.radirius.merc.test;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.spl.SplashScreen;

/**
 * @author wessles
 */

public class FullscreenTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public FullscreenTest() {
        super("ga");
        /**
         * Will choose lowest resolution, since near no monitor will view
         * fullscreen at these dimensions.
         */
        rnr.init(this, 500, 500, true, true, true);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new FullscreenTest();
    }
    
    @Override
    public void init(ResourceManager RM) {
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta) {
        if (rnr.getInput().keyDown(Keyboard.KEY_ESCAPE))
            rnr.end();
    }
    
    float x, y;
    
    @Override
    public void render(Graphics g) {
        /** Testing for vsync stuffs */
        g.drawRect(new Rectangle(x += 1, y += 3, 100, 100));
    }
    
    @Override
    public void cleanup(ResourceManager RM) {
    }
    
}
