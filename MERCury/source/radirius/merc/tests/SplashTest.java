package radirius.merc.tests;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Graphics;
import radirius.merc.maths.MercMath;
import radirius.merc.splash.SplashScreen;

/**
 * @author wessles
 */

public class SplashTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public SplashTest() {
        super("Splash Screen Test!");
        rnr.init(this, 800, 600);
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
        
        if (!rnr.inited)
            return;
        
        for (int trolol = 0; trolol < 100; trolol++)
            g.drawString((float) MercMath.random(-50, rnr.getWidth()), (float) MercMath.random(0, rnr.getHeight()),
                    "LEL,                      This is a GAME!                dilll with it!");
    }
    
    @Override
    public void cleanup() {
        
    }
    
}
