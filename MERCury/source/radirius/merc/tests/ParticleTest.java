package radirius.merc.tests;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;

/**
 * @author wessles
 */

public class ParticleTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public ParticleTest() {
        super("Particle Test");
        rnr.init(this, 800, 600, false);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new ParticleTest();
    }
    
    public Rectangle bullet;
    public float vel = 10;
    
    @Override
    public void init() {
        bullet = new Rectangle(0, rnr.getHeight() / 2 - 4, 8);
    }
    
    @Override
    public void update(float delta) {
        
        bullet.translate(vel, 0).rotate(vel / 2);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawRect(bullet);
    }
    
    @Override
    public void cleanup() {
        
    }
}
