package com.teama.merc.test;

import java.awt.Toolkit;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Rectangle;

import static com.teama.merc.gfx.Color.*;

import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.res.ResourceManager;

/**
 * Colors got screwed up... So this should be a good tool for the future and now.
 * 
 * @from MERCury in com.teama.merc.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 23, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ColorTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public ColorTest()
    {
        super("Color Test");
        rnr.init(this, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 100);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new ColorTest();
    }
    
    @Override
    public void init(ResourceManager RM)
    {
        rnr.graphics().setBackground(Color.black);
        rnr.graphics().scale(8);
    }
    
    @Override
    public void update(float delta)
    {
    }
    
    @Override
    public void render(Graphics g)
    {
        test(red, g);
        test(orange, g);
        test(yellow, g);
        test(springgreen, g);
        test(green, g);
        test(turquoise, g);
        test(cyan, g);
        test(ocean, g);
        test(blue, g);
        test(violet, g);
        test(magenta, g);
        test(rasberry, g);
        
        test(black, g);
        
        test(white, g);
        test(marble, g);
        test(gray, g);
        test(coal, g);
        test(black, g);
        colidx = 0;
    }
    
    int colidx;
    
    public void test(Color color, Graphics g)
    {
        g.setColor(color);
        g.drawRect(new Rectangle(colidx * 4, 0, 4, 8));
        colidx++;
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
    }
    
}
