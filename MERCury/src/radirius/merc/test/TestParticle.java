package radirius.merc.test;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.particles.ParticleSystem;
import radirius.merc.graphics.particles.ParticleSystem.ParticleSetup;
import radirius.merc.math.geometry.Polygon;
import radirius.merc.math.geometry.Vec2;

/**
 * @author wessles
 */

public class TestParticle extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestParticle() {
        super("Particle Test");
        rnr.init(this, 1600, 600, false);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new TestParticle();
    }
    
    public Polygon bullet;
    public float vel = 1;
    public ParticleSystem emitter;
    
    @Override
    public void init() {
        bullet = new Polygon(64, rnr.getHeight() / 2 - 16, 32, 5);
        
        ParticleSetup pes = new ParticleSetup();
        pes.color = Color.gray;
        pes.speed = 1;
        pes.acceleration = 0.5f;
        pes.size = 32;
        pes.growth = 0.9f;
        pes.validangle = new Vec2(170, 190);
        pes.rotation = 2;
        emitter = new ParticleSystem(pes);
    }
    
    @Override
    public void update(float delta) {
        bullet.translate(Math.abs(vel) < 20 ? vel *= 1.01f : vel, 0).rotate(vel / 2).setScale(1f + (float) (0.2f * Math.sin(rnr.getMillis() / 50)));
        emitter.getOptions().speed = -vel * 0.02f;
        emitter.getOptions().size = Math.min(Math.abs(vel), 16);
        
        emitter.update(delta);
        emitter.generateParticle(20, bullet.getCenter());
        
        if (bullet.getX2() > rnr.getWidth() || bullet.getX() < 0)
            vel *= -1;
    }
    
    @Override
    public void render(Graphics g) {
        emitter.render(g);
        
        g.setColor(Color.red);
        g.drawPolygon(bullet);
        g.setColor(Color.turquoise);
        g.tracePolygon(bullet);
    }
    
    @Override
    public void cleanup() {
        
    }
}
