package radirius.merc.tests;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Polygon;
import radirius.merc.geometry.Rectangle;
import radirius.merc.geometry.Vec2;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.particles.ParticleEmitter;
import radirius.merc.particles.ParticleEmitter.ParticleSetup;

/**
 * @author wessles
 */

public class ParticleTest extends Core {
  Runner rnr = Runner.getInstance();
  
  public ParticleTest() {
    super("Particle Test");
    rnr.init(this, 1600, 600, false);
    rnr.run();
  }
  
  public static void main(String[] args) {
    new ParticleTest();
  }
  
  public Polygon bullet;
  public float vel = 1;
  public ParticleEmitter emitter;
  
  @Override
  public void init() {
    bullet = new Polygon(64, rnr.getHeight() / 2 - 16, 32, 5);
    
    ParticleSetup pes = new ParticleSetup();
    pes.color = Color.gray;
    pes.speed = 1;
    pes.acceleration = 0.5f;
    pes.size = 4;
    pes.growth = 0.9f;
    pes.validangle = new Vec2(170, 190);
    emitter = new ParticleEmitter((Rectangle) new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight()).scale(0.4f), pes);
    bullet.addChild(emitter.getEmitterBounds());
  }
  
  @Override
  public void update(float delta) {
    bullet.translate(Math.abs(vel) < 10 ? vel *= 1.01f : vel, 0).rotate(vel / 2).setScale(1f + (float) (0.2f * Math.sin(rnr.getTime() / 50)));
    emitter.getOptions().speed = -vel * 0.02f;
    emitter.getOptions().size = Math.min(Math.abs(vel), 16);
    
    emitter.update(delta);
    emitter.generateParticle(20);
    
    if (bullet.getX2() > rnr.getWidth() || bullet.getX() < 0)
      vel *= -1;
  }
  
  @Override
  public void render(Graphics g) {
    emitter.render(g);
    
    g.setColor(Color.red);
    g.drawShape(bullet);
    g.setColor(Color.turquoise);
    g.traceShape(bullet);
  }
  
  @Override
  public void cleanup() {
    
  }
}
