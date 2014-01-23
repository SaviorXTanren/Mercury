package com.teama.merc.test;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Vec2;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.part.ParticleEmitter;
import com.teama.merc.res.ResourceManager;

/**
 * @from MERCury in com.teama.merc.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ParticleTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    ParticleEmitter emitter;
    
    public ParticleTest()
    {
        super("Particle Test");
        rnr.init(this, 600, 600);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new ParticleTest();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        emitter = new ParticleEmitter(rnr.width(2), rnr.height(2), new Vec2(0, 360), 1f, Color.yellow, 3, 3, new Vec2(0, 0.2f), 90);
    }
    
    @Override
    public void update(float delta)
    {
        emitter.update(delta);
        emitter.validangle.y -= 0.5f;
    }
    
    @Override
    public void render(Graphics g)
    {
        emitter.render(g);
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
    }
    
}
