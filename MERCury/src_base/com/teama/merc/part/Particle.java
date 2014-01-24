package com.teama.merc.part;

import com.teama.merc.env.MercEntity;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.Vec2;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.util.Wipeable;

/**
 * @from MERCury in com.teama.merc.part
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Particle implements MercEntity, Wipeable
{
    public Color color;
    public float size;
    private final float max_size;
    public boolean shrink;
    
    public Vec2 pos;
    public Vec2 vel;
    
    public final int lifeinframes;
    public int life;
    
    public ParticleEmitter emitter;
    
    public Particle(Color color, float size, boolean shrink, float x, float y, float angle, float speed, int lifeinframes, ParticleEmitter emitter)
    {
        this.color = color;
        this.size = size;
        this.max_size = size;
        this.shrink = shrink;
        
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        vel.scale(speed);
        
        this.lifeinframes = lifeinframes;
        this.life = lifeinframes;
        
        this.emitter = emitter;
    }
    
    @Override
    public void update(float delta)
    {
        if (life < 0)
            wipe();
        
        pos.add(vel);
        vel.add(emitter.getGravity());
        
        if(shrink)
            size = max_size*((float)life/(float)lifeinframes);
        
        life -= 1;
    }
    
    @Override
    public void render(Graphics g)
    {
        g.setColor(new Color(color.r, color.g, color.b, life));
        g.drawRect(new Rectangle(pos.x, pos.y, size, size));
    }
    
    boolean wiped = false;
    
    @Override
    public void wipe()
    {
        wiped = true;
    }
    
    @Override
    public boolean wiped()
    {
        return wiped;
    }
}
