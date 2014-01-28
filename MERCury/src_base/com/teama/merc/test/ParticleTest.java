package com.teama.merc.test;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.Vec2;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;
import com.teama.merc.in.Input;
import com.teama.merc.part.ParticleEmitter;
import com.teama.merc.res.Loader;
import com.teama.merc.res.ResourceManager;
import com.teama.merc.spl.SplashScreen;

/**
 * @from MERCury in com.teama.merc.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ParticleTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    ParticleEmitter emitter2, emitter1;
    
    public ParticleTest()
    {
        super("Particle Test");
        rnr.init(this, 600, 600);
        rnr.setMouseGrab(true);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new ParticleTest();
    }
    
    Vec2 torchpos = new Vec2();
    
    @Override
    public void init(ResourceManager RM)
    {
        try
        {
            RM.loadResource(Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/test/torch.png")), "torch");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        rnr.getGraphics().scale(4);
        
        emitter1 = new ParticleEmitter(new Rectangle(0, 0, 10, 10), new Vec2(265, 275), new Vec2(0, -0.01f), 1f, Color.red, 2, true, 0.2f, 100);
        emitter2 = new ParticleEmitter(new Rectangle(0, 0, 10, 10), new Vec2(255, 285), new Vec2(0, -0.03f), 1f, Color.yellow, 4, true, 0.3f, 100);
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta)
    {
        Input in = rnr.getInput();
        
        if (in.keyDown(Keyboard.KEY_ESCAPE))
            rnr.setMouseGrab(false);
        
        emitter1.move(in.getAbsoluteMouseX() / 4 - emitter1.getBounds().getX1(), in.getAbsoluteMouseY() / 4 - emitter1.getBounds().getY1());
        emitter2.move(in.getAbsoluteMouseX() / 4 - emitter2.getBounds().getX1(), in.getAbsoluteMouseY() / 4 - emitter2.getBounds().getY1());
        torchpos.set(emitter1.getBounds().getX1() + 2.5f, emitter1.getBounds().getY1());
        
        emitter1.update(delta);
        emitter2.update(delta);
    }
    
    @Override
    public void render(Graphics g)
    {
        g.drawTexture((Texture) rnr.getResourceManager().retrieveResource("torch"), torchpos.x, torchpos.y);
        emitter2.render(g);
        emitter1.render(g);
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
    }
    
}
