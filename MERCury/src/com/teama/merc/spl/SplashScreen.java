package com.teama.merc.spl;

import java.util.Timer;
import java.util.TimerTask;

import com.teama.merc.fwk.Runner;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.TexturedRectangle;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

/**
 * @from MERCury in com.wessles.MERCury.gui
 * @by wessles
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
        texrect = center ? new TexturedRectangle(new Rectangle(Runner.getInstance().width() / 2 - WIDTH / 2, Runner.getInstance().height() / 2 - HEIGHT / 2, WIDTH, HEIGHT), tex) : new TexturedRectangle(new Rectangle(0, 0, WIDTH, HEIGHT), tex);
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
                @SuppressWarnings("deprecation")
                @Override
                public void run()
                {
                    _return_ = false;
                    Thread t = Thread.currentThread();
                    t.stop();
                }
            }, showtimemillis);
            showing = true;
        }
        g.drawRect(texrect);
        return _return_;
    }
}
