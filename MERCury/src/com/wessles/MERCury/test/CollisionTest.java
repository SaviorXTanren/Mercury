package com.wessles.MERCury.test;

import java.io.IOException;

import com.wessles.MERCury.framework.Core;
import com.wessles.MERCury.framework.Runner;
import com.wessles.MERCury.geom.Ellipse;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.graphics.Texture;
import com.wessles.MERCury.log.Logger;
import com.wessles.MERCury.res.ResourceManager;
import com.wessles.MERCury.splash.SplashScreen;

/**
 * According to this test, the following collision events are valid: [see method render()].
 * 
 * 
 * @from MERCury in com.wessles.MERCury.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 27, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class CollisionTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public CollisionTest()
    {
        super("CollisionTest");
        rnr.init(this, 200, 200, false, false);
        rnr.run();
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
        g.scale(8);
        
        /* RECTANGLE TO RECTANGLE */
        Rectangle r1 = new Rectangle(0, 0, 10, 0, 10, 10, 0, 10);
        Rectangle r2 = new Rectangle(5, 5, 15, 5, 15, 15, 5, 15);
        
        Logger.info("RECTANGLE TO RECTANGLE");
        
        System.out.println("rect1" + ", " + r1.getX1() + ", " + r1.getY1() + ", " + r1.getX2() + ", " + r1.getY2());
        System.out.println("rect2" + ", " + r2.getX1() + ", " + r2.getY1() + ", " + r2.getX2() + ", " + r2.getY2());
        System.out.println(r1.intersects(r2));
        
        /* RECTANGLE TO CIRCLE/ELLIPSE */
        Ellipse e1 = new Ellipse(5, 5, 3, 3);
        
        Logger.info("RECTANGLE TO CIRCLE/ELLIPSE");
        
        System.out.println("rect1" + ", " + r1.getX1() + ", " + r1.getY1() + ", " + r1.getX2() + ", " + r1.getY2());
        System.out.println("circ/ellipse1" + ", " + e1.getX1() + ", " + e1.getY1() + ", " + e1.getX2() + ", " + e1.getY2());
        System.out.println(r1.intersects(e1));
        System.out.println(e1.intersects(r1));
        
        /* CIRCLE/ELLIPSE TO CIRCLE/ELLIPSE */
        Ellipse e2 = new Ellipse(2, 2, 10, 4);
        
        Logger.info("RECTANGLE TO CIRCLE/ELLIPSE");
        
        System.out.println("circ/ellipse1" + ", " + e1.getX1() + ", " + e1.getY1() + ", " + e1.getX2() + ", " + e1.getY2());
        System.out.println("circ/ellipse2" + ", " + e2.getX1() + ", " + e2.getY1() + ", " + e2.getX2() + ", " + e2.getY2());
        System.out.println(e1.intersects(e2));
        
        g.drawRect(r1);
        g.drawRect(r2);
        g.drawEllipse(e1);
        g.drawEllipse(e2);
    }
    
    @Override
    public void cleanup(ResourceManager RM)
    {
        
    }
    
    public static void main(String[] args)
    {
        new CollisionTest();
    }
}
