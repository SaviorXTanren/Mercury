package radirius.merc.particles;

import radirius.merc.environment.Entity;
import radirius.merc.geometry.Rectangle;
import radirius.merc.geometry.Vec2;
import radirius.merc.graphics.Graphics;
import radirius.merc.maths.MercMath;
import radirius.merc.utils.Wipeable;

/**
 * A class that will represent a single particle, with a parent emitter.
 * 
 * @author wessles
 */

public class Particle implements Entity, Wipeable {
    private float size;
    
    private Vec2 pos;
    private Rectangle bounds;
    private float rot;
    private float rotdirection;
    private Vec2 vel;
    
    private int life;
    
    private ParticleEmitter emitter;
    
    public Particle(float angle, ParticleEmitter emitter) {
        size = emitter.getOptions().size;
        
        Rectangle emitterbounds = new Rectangle(emitter.getEmitterBounds().getX(), emitter.getEmitterBounds().getY(),
                emitter.getEmitterBounds().getWidth(), emitter.getEmitterBounds().getHeight());
        float x = (float) MercMath.random(emitterbounds.getX(), emitterbounds.getX2()), y = (float) MercMath.random(
                emitterbounds.getY(), emitterbounds.getY2());
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        rotdirection = MercMath.nextBoolean() ? 1 : -1;
        vel.scale(emitter.getOptions().speed);
        
        bounds = new Rectangle(pos.x, pos.y, size);
        
        life = emitter.getOptions().lifeinframes;
        
        this.emitter = emitter;
    }
    
    @Override
    public void update(float delta) {
        if (life < 0)
            wipe();
        
        pos.add(new Vec2(vel.x * delta, vel.y * delta));
        vel.add(emitter.getOptions().gravity);
        vel.scale(emitter.getOptions().acceleration);
        
        size *= emitter.getOptions().growth;
        
        bounds = new Rectangle(pos.x - size / 2, pos.y - size / 2, size);
        bounds.rotateTo(rot += emitter.getOptions().rotation * rotdirection);
        
        life -= 1;
    }
    
    @Override
    public void render(Graphics g) {
        g.pushSetColor(emitter.getOptions().color.duplicate());
        if (emitter.getOptions().texture == null)
            g.drawRect(bounds);
        else
            g.drawTexture(emitter.getOptions().texture, bounds);
    }
    
    boolean wiped = false;
    
    @Override
    public void wipe() {
        wiped = true;
    }
    
    @Override
    public boolean wiped() {
        return wiped;
    }
}
