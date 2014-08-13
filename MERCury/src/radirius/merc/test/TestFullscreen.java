package radirius.merc.test;

import org.lwjgl.input.Keyboard;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.framework.splash.SplashScreen;
import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.Rectangle;

/**
 * @author wessles
 */

public class TestFullscreen extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestFullscreen() {
        super("ga");
        /**
         * Will choose lowest resolution, since near no monitor will view
         * fullscreen at these dimensions.
         */
        rnr.init(this, true, true);
        rnr.showDebug(false);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new TestFullscreen();
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
        g.drawString("We is success! click <ESC>, for leave.", 0, 0);
    }
    
    @Override
    public void cleanup() {
    }
    
}
