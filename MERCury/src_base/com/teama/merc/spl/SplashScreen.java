package com.teama.merc.spl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Vec2;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;
import com.teama.merc.res.Loader;

/**
 * @from merc in com.teama.merc.spl
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class SplashScreen
{
    public boolean showing = false;
    private boolean _return_ = true;
    
    public long showtimemillis;
    public Texture tex;
    
    public SplashScreen(Texture tex, long showtimemillis)
    {
        this.showtimemillis = showtimemillis;
        this.tex = tex;
    }
    
    public boolean show(Graphics g)
    {
        if (!showing)
        {
            // Evil timer
            final Timer timertodestruction = new Timer();
            
            timertodestruction.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    _return_ = false;
                    timertodestruction.cancel();
                    timertodestruction.purge();
                }
            }, showtimemillis);
            
            showing = true;
        }
        
        Vec2 scale = Runner.getInstance().getGraphics().getScale();
        int scrw = (int) (Runner.getInstance().getWidth() / scale.x), scrh = (int) (Runner.getInstance().getHeight() / scale.y);
        float width = tex.getTextureWidth(), height = tex.getTextureHeight();
        float aspect = width / height;
        
        if (scrw > scrh)
        {
            width = scrw;
            height = width / aspect;
        }
        else
        {
            width = scrw;
            height = width / aspect;
        }
        
        g.drawTexture(tex, 0, 0, tex.getTextureWidth(), tex.getTextureHeight(), 0, scrh/2-height/2, width, scrh/2-height/2+height);
        return _return_;
    }
    
    /**
     * Show some love for MERCury and give some credit!
     * 
     * @return The love of all developers from MERCury, unless you are a child murderer. Even if you code you can't get anybody's love. Sicko
     */
    public static SplashScreen getMERCuryDefault()
    {
        Texture tex = null;
        try
        {
            tex = Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/spl/splash.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new SplashScreen(tex, 3000);
    }
}
