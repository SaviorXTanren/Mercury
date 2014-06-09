package com.radirius.merc.test;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Graphics;
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
        rnr.init(this, true, true);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new FullscreenTest();
    }
    
    @Override
    public void init() {
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
        
        g.drawString(0, 0, "You have made it to fullscreen.\nPress <ESCAPE> to leave.\nThat square is testing for vsync-y stuffs.\n\nGoodbye, and godspeed to you.\n\n\n-Radirius");
    }
    
    @Override
    public void cleanup() {
    }
    
}
