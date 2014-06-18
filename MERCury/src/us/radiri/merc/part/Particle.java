package us.radiri.merc.part;

import us.radiri.merc.env.Entity;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.geo.Vec2;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.math.MercMath;
import us.radiri.merc.util.Wipeable;

/**
 * A class that will represent a single particle, with a parent emitter.
 * 
 * @author wessles
 */

public class Particle implements Entity, Wipeable {
    public float size;
    
    public Vec2 pos;
    public Vec2 vel;
    
    public int life;
    
    public ParticleEmitter emitter;
    
    public Particle(float angle, ParticleEmitter emitter) {
        this.size = emitter.getOptions().size;
        
        float x = (float) MercMath.random(emitter.getEmitterBounds().getX(), emitter.getEmitterBounds().getX2()), y = (float) MercMath.random(emitter.getEmitterBounds().getY(), emitter.getEmitterBounds().getY2());
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        vel.scale(emitter.getOptions().speed);
        
        life = emitter.getOptions().lifeinframes;
        
        this.emitter = emitter;
    }
    
    @Override
    public void update(float delta) {
        if (life < 0)
            wipe();
        
        pos.add(new Vec2(vel.x * delta, vel.y * delta));
        vel.add(emitter.getOptions().gravity);
        vel.scale(emitter.getOptions().growth);
        
        size *= emitter.getOptions().growth;
        
        life -= 1;
    }
    
    @Override
    public void render(Graphics g) {
        g.pushSetColor(new Color(emitter.getOptions().color.r, emitter.getOptions().color.g, emitter.getOptions().color.b, life));
        if (emitter.getOptions().texture == null)
            g.drawRect(new Rectangle(pos.x-size/2, pos.y-size/2, size, size));
        else
            g.drawTexture(emitter.getOptions().texture, new Rectangle(pos.x-size/2, pos.y-size/2, size));
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
