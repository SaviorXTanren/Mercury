package radirius.merc.test;

import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.main.Core;
import radirius.merc.main.Runner;
import radirius.merc.main.splash.SplashScreen;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Vec2;

/**
 * @author wessles
 */

public class TestController extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestController() {
        super("Test Controller!");
        rnr.init(this, 1600, 900, true);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new TestController();
    }
    
    @Override
    public void init() {
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
        rnr.getInput().initControllers();
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    Vec2 pos = new Vec2(0, 0);
    Vec2 vel = new Vec2(0, 0);
    Rectangle bounds;
    
    @Override
    public void render(Graphics g) {
        if (!rnr.showSplashScreens(g))
            return;
        
        if (!rnr.inited)
            return;
        
        if (rnr.getInput().controllerButtonDown(2, 0))
            vel.scale(1.1f);
        
        vel.add(rnr.getInput().getLeftAnalogStick(0));
        
        bounds = new Rectangle(pos.x, pos.y, 100);
        if (pos.x + vel.x < 0 || pos.x + vel.x + bounds.getWidth() > rnr.getWidth())
            vel.x *= -1;
        if (pos.y + vel.y < 0 || pos.y + vel.y + bounds.getHeight() > rnr.getHeight())
            vel.y *= -1;
        
        pos.add(vel);
        vel.scale(0.9f);
        bounds.rotateTo(-rnr.getInput().getRightAnalogStick(0).theta());
        
        g.setColor(Color.white);
        g.drawRect(bounds);
    }
    
    @Override
    public void cleanup() {
        
    }
    
}
