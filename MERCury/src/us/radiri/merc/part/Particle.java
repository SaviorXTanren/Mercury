package us.radiri.merc.part;

import us.radiri.merc.env.MercEntity;
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

public class Particle implements MercEntity, Wipeable {
    public float size;
    private final float max_size;
    
    public Vec2 pos;
    public Vec2 vel;
    
    public int life;
    
    public ParticleEmitter emitter;
    
    public Particle(float angle, ParticleEmitter emitter) {
        this.size = emitter.size;
        max_size = size;
        
        float x = (float) MercMath.random(emitter.getBounds().getX(), emitter.getBounds().getX2()), y = (float) MercMath.random(emitter.getBounds().getY(), emitter.getBounds().getY2());
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        vel.scale(emitter.speed);
        
        life = emitter.lifeinframes;
        
        this.emitter = emitter;
    }
    
    @Override
    public void update(float delta) {
        if (life < 0)
            wipe();
        
        pos.add(vel);
        vel.add(emitter.grav);
        vel.scale(emitter.damp);
        
        if (emitter.shrink)
            size = max_size * ((float) life / (float) emitter.lifeinframes);
        
        life -= 1;
    }
    
    @Override
    public void render(Graphics g) {
        g.pushSetColor(new Color(emitter.color.r, emitter.color.g, emitter.color.b, life));
        g.drawRect(new Rectangle(pos.x, pos.y, size, size));
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
