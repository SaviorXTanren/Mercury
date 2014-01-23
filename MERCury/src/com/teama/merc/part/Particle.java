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
    
    public Vec2 pos;
    public Vec2 vel;
    public Vec2 grav;
    
    public int lifeinframes;
    
    public Particle(Color color, float size, float x, float y, float angle, float speed, Vec2 grav, int lifeinframes)
    {
        this.color = color;
        this.size = size;
        
        pos = new Vec2(x, y);
        vel = new Vec2(angle);
        vel.scale(speed);
        this.grav = grav;
        
        this.lifeinframes = lifeinframes;
    }
    
    @Override
    public void update(float delta)
    {
        if (lifeinframes < 0)
            wipe();
        
        pos.add(vel);
        vel.add(grav);
        
        lifeinframes -= 1;
    }
    
    @Override
    public void render(Graphics g)
    {
        g.setColor(color);
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
