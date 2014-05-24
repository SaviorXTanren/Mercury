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
 * @author opiop65
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
