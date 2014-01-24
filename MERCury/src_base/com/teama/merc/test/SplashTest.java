package com.teama.merc.test;

import java.io.IOException;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;
import com.teama.merc.math.MercMath;
import com.teama.merc.res.ResourceManager;
import com.teama.merc.spl.SplashScreen;

/**
 * @from merc in com.teama.merc.test
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class SplashTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public SplashTest()
    {
        super("Splash Screen Test!");
        rnr.init(this, 640, 480);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new SplashTest();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        try
        {
            Texture tex = Texture.loadTexture("res/splash.png");
            float ratio = tex.getTextureWidth() / tex.getTextureHeight();
            int height = (int) (rnr.width() / ratio);
            SplashScreen splash = new SplashScreen(tex, 3000, rnr.width(), height, true);
            rnr.addSplashScreen(splash);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(float delta)
    {
        
    }
    
    @Override
    public void render(Graphics g)
    {
        for (int trolol = 0; trolol < 10; trolol++)
            g.drawString((float) MercMath.random(0, rnr.width()), (float) MercMath.random(0, rnr.height()), "LEL,                      This is a GAME!                deal with it!");
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
        
    }
    
}