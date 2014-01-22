package com.teama.merc.tuts;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.res.ResourceManager;

/**
 * A tutorial outlining the basics of rendering in MERCury
 * 
 * @from merc in com.teama.merc.tuts
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 20, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class BasicRendering extends Core
{
    Runner rnr = Runner.getInstance();
    
    public BasicRendering()
    {
        super("Basic Rendering");
        rnr.init(this, 400, 400);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new BasicRendering();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        /** Let's initialize the graphics by scaling and all dat background infos (pun). */
        rnr.graphics().scale(2);
        rnr.graphics().setBackground(Color.blue);
    }
    
    @Override
    public void update(float delta)
    {
        
    }
    
    float x;
    
    @Override
    public void render(Graphics g)
    {
        /** Let's start with simple rectangle, placed at 10, 10 with a width of 20 and a height of 10. */
        Rectangle r = new Rectangle(10, 10, 20, 10);
        g.drawRect(r);
        /** Make it red! */
        g.setColor(Color.red);
        g.drawRect(r);
        
        /** Hows about a circle? */
        g.setColor(Color.green);
        g.drawCircle(40, 40, 10);
        
        /** Triangle? */
        g.setColor(Color.black);
        g.drawTriangle(20, 15, 20, 20, 15, 20);
        
        /** Some text? */
        g.setColor(Color.red);
        g.drawString(0, 20, "Yo, dis is a game. So gangsta.\nMuch cool");
        /** Going to look pixelated, due to the scaling we did at init() */
        
        /** A moving ellipse? ACTION! */
        g.setColor(Color.gray);
        g.drawEllipse(this.x += 0.2f, 30, 10, 10);
        /** 'x' is declared outside of the render() method. This will just add 0.2 to it every render. */
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
        
    }
    
}
