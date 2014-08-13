package radirius.merc.graphics.particles;

import radirius.merc.environment.Entity;
import radirius.merc.graphics.Graphics;
import radirius.merc.math.MERCMath;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Vec2;
import radirius.merc.utilities.Wipeable;

/**
 * A class that will represent a single particle, with a parent particle system.
 * 
 * @author wessles
 */

public class Particle implements Entity, Wipeable {
    // Size of the particle
    private float size;
    
    // Position of the particle
    private Vec2 pos;
    // Bounds of the particle
    private Rectangle bounds;
    // The rotation of the particle
    private float rot;
    // The rotational velocity of the particle
    private float rotdirection;
    // The velocity of the particle
    private Vec2 vel;
    
    // The amount of frames that the particle has experienced
    private int life;
    
    // The parent particle system
    private ParticleSystem emitter;
    
    public Particle(float x, float y, float angle, ParticleSystem emitter) {
        size = emitter.getOptions().size;
        
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        rotdirection = MERCMath.nextBoolean() ? 1 : -1;
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
        if (size <= 0)
            wipe();
        
        bounds = new Rectangle(pos.x - size / 2, pos.y - size / 2, size);
        bounds.rotateTo(rot += emitter.getOptions().rotation * rotdirection);
        
        life -= 1;
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(emitter.getOptions().color.duplicate());
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
