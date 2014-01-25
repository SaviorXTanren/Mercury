package com.teama.merc.part;

import com.teama.merc.env.AdvancedMercEntity;
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

public class ParticleEmitter extends AdvancedMercEntity
{
    private WipingArrayList<Particle> parts;
    
    private Vec2 validangle;
    private float percentchance;
    
    private Color color;
    private float size;
    private boolean shrink;
    private float speed;
    private Vec2 grav;
    private int lifeinframes;
    
    public ParticleEmitter(Rectangle spawnarea, Vec2 validangle, Vec2 grav, float percentchance, Color color, float size, boolean shrink, float speed, int lifeinframes)
    {
        super(spawnarea.getX1(), spawnarea.getY1(), spawnarea.getWidth(), spawnarea.getHeight());
        
        parts = new WipingArrayList<Particle>();
        
        this.validangle = validangle;
        this.percentchance = percentchance;
        
        this.color = color;
        this.size = size;
        this.shrink = shrink;
        
        this.speed = speed;
        this.grav = grav;
        this.lifeinframes = lifeinframes;
    }
    
    public ParticleEmitter(float x, float y, Vec2 validangle, float percentchance, Color color, float size, boolean shrink, float speed, Vec2 grav, int lifeinframes)
    {
        this(new Rectangle(x, y, 1, 1), validangle, grav, percentchance, color, size, shrink, speed, lifeinframes);
    }
    
    @Override
    public void update(float delta)
    {
        if (MercMath.chance(percentchance))
        {
            float angle = (float) MercMath.random(validangle.x, validangle.y);
            float x = (float) MercMath.random(getBounds().getX1(), getBounds().getX2()), y = (float) MercMath.random(getBounds().getY1(), getBounds().getY2());
            parts.add(new Particle(color, size, shrink, x, y, angle, speed, lifeinframes, this));
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
    
    public WipingArrayList<Particle> getParticles()
    {
        return parts;
    }
    
    public Vec2 getValidAngles()
    {
        return validangle;
    }
    
    public Vec2 getGravity()
    {
        return grav;
    }
    
    public float getPercentChance()
    {
        return percentchance;
    }
    
    public Color getParticleColor()
    {
        return color;
    }
    
    public float getParticleSize()
    {
        return size;
    }
    
    public float getParticleSpeed()
    {
        return speed;
    }
    
    public boolean getParticleShrinking()
    {
        return shrink;
    }
    
    public int getParticleLifeInFrames()
    {
        return lifeinframes;
    }
    
    public void setValidAngles(Vec2 validangle)
    {
        this.validangle = validangle;
    }
    
    public void setGravity(Vec2 grav)
    {
        this.grav = grav;
    }
    
    public void setPercentChance(float percentchance)
    {
        this.percentchance = percentchance;
    }
    
    public void setParticleColor(Color color)
    {
        this.color = color;
    }
    
    public void setParticleSize(float size)
    {
        this.size = size;
    }
    
    public void setParticleSpeed(float speed)
    {
        this.speed = speed;
    }
    
    public void setParticleShrinking(boolean shrinking)
    {
        shrink = shrinking;
    }
    
    public void setParticleLifeInFrames(int lifeinframes)
    {
        this.lifeinframes = lifeinframes;
    }
    
    @Override
    public boolean isValidPosition(float x, float y)
    {
        return true;
    }
    
}
