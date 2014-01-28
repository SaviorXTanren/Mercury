package com.teama.merc.spl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.TexturedRectangle;
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
    public TexturedRectangle texrect;
    
    public SplashScreen(Texture tex, long showtimemillis, int WIDTH, int HEIGHT, boolean center)
    {
        // Center or no center? That is the question.
        texrect = center ? new TexturedRectangle(new Rectangle(Runner.getInstance().getWidth() / 2 - WIDTH / 2, Runner.getInstance().getHeight() / 2 - HEIGHT / 2, WIDTH, HEIGHT), tex) : new TexturedRectangle(new Rectangle(0, 0, WIDTH, HEIGHT), tex);
        this.showtimemillis = showtimemillis;
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
        g.drawRect(texrect);
        return _return_;
    }
    
    /**
     * Show some love for MERCury and give some credit!
     */
    public static SplashScreen getMERCuryDefault()
    {
        try
        {
            Texture tex = Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/spl/splash.png"));
            float ratio = tex.getTextureWidth() / tex.getTextureHeight();
            int height = (int) (Runner.getInstance().getWidth() / ratio);
            SplashScreen splash = new SplashScreen(tex, 3000, Runner.getInstance().getWidth(), height, true);
            return splash;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}
