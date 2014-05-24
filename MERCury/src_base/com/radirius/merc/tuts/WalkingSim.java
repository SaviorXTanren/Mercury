package com.radirius.merc.tuts;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.res.ResourceManager;

/**
 * @author wessles
 */

public class WalkingSim extends Core {
    Runner rnr = Runner.getInstance();
    
    public WalkingSim(String name) {
        super(name);
        // Make a game window that is 500x500
        rnr.init(this, 500, 500);
        // Now run it!
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        rnr.getGraphics().setBackground(Color.blue);
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
        
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.setColor(Color.green);
        g.drawRect(new Rectangle(0, 0, 100, 100));
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new WalkingSim("Blank title!");
    }
    
}
