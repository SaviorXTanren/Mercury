package us.radiri.merc.test;

import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.math.MercMath;
import us.radiri.merc.spl.SplashScreen;

/**
 * @author wessles
 */

public class SplashTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public SplashTest() {
        super("Splash Screen Test!");
        rnr.init(this, 1024, 768);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new SplashTest();
    }
    
    @Override
    public void init() {
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    @Override
    public void render(Graphics g) {
        if (!rnr.showSplashScreens(g))
            return;
        
        if(!rnr.inited)
            return;
        
        for (int trolol = 0; trolol < 100; trolol++)
            g.drawString((float) MercMath.random(-50, rnr.getWidth()), (float) MercMath.random(0, rnr.getHeight()), "LEL,                      This is a GAME!                dilll with it!");
    }
    
    @Override
    public void cleanup() {
        
    }
    
}
