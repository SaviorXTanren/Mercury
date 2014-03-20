package com.teama.merc.test;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Graphics;
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
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new SplashTest();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta)
    {
        
    }
    
    @Override
    public void render(Graphics g)
    {
        for (int trolol = 0; trolol < 10; trolol++)
            g.drawString((float) MercMath.random(0, rnr.getWidth()), (float) MercMath.random(0, rnr.getHeight()), "LEL,                      This is a GAME!                deal with it!");
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
        
    }
    
}
