package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.math.MercMath;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury_git in com.radirius.merc.test
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Mar 27, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class LineTest extends Core {
    
    Runner rnr = Runner.getInstance();
    
    public LineTest(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.setDrawMode(Graphics.LINE);
        g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0, 255)));
        g.drawRect(new Rectangle(rnr.getWidth() / 2, rnr.getHeight() / 2, 50, 50));
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new LineTest("Line Test");
    }
}
