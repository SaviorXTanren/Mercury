package com.teama.merc.part;

import com.teama.merc.env.MercEntity;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.Vec2;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.math.MercMath;
import com.teama.merc.util.WipingArrayList;

/**
 * @from MERCury in com.teama.merc.part
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ParticleEmitter implements MercEntity
{
    public WipingArrayList<Particle> parts;
    
    public Vec2 validangle;
    public float percentchance;
    public Rectangle spawnarea;
    
    public Color color;
    public float size;
    public float speed;
    public Vec2 grav;
    public int lifeinframes;
    
    public ParticleEmitter(Rectangle spawnarea, Vec2 validangle, float percentchance, Color color, float size, float speed, Vec2 grav, int lifeinframes)
    {
        parts = new WipingArrayList<Particle>();
        
        this.validangle = validangle;
        this.percentchance = percentchance;
        this.spawnarea = spawnarea;
        
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.grav = grav;
        this.lifeinframes = lifeinframes;
    }
    
    public ParticleEmitter(float x, float y, Vec2 validangle, float percentchance, Color color, float size, float speed, Vec2 grav, int lifeinframes)
    {
        this(new Rectangle(x, y, 1, 1), validangle, percentchance, color, size, speed, grav, lifeinframes);
    }
    
    @Override
    public void update(float delta)
    {
        if (MercMath.chance(percentchance))
        {
            float angle = (float) MercMath.random(validangle.x, validangle.y);
            float x = (float) MercMath.random(spawnarea.getX1(), spawnarea.getX2()), y = (float) MercMath.random(spawnarea.getY1(), spawnarea.getY2());
            parts.add(new Particle(color, size, x, y, angle, speed, grav, lifeinframes));
        }
        
        for (Particle part : parts)
            part.update(delta);
        
        parts.sweep();
    }
    
    @Override
    public void render(Graphics g)
    {
        for (Object part : parts)
            ((Particle) part).render(g);
    }
    
}
