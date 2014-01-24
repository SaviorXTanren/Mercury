package com.teama.merc.tuts;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.res.ResourceManager;

/**
 * All you will need to get started on a MERCury project
 * 
 * @from merc in com.teama.merc.tuts
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 19, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class BoilerPlate extends Core
{
    /**
     * ALWAYS USE Runner.getInstance()! CANNOT STRESS ENOUGH! ALWAYS USE Runner.getInstance()!
     */
    Runner rnr = Runner.getInstance();
    
    public BoilerPlate()
    {
        /** Title for the window */
        super("Yo, world. Ya got da stuffs?");
        /** Initialize the window, and engine */
        rnr.init(this, 300, 100);
        /** Run the engine */
        rnr.run();
    }
    
    /**
     * Calls an instance of BoilerPlate, which extends Core
     */
    public static void main(String[] args)
    {
        new BoilerPlate();
    }
    
    /** Not used here, but will be used later for graphical settings, resource loading, etc. */
    @Override
    public void init(ResourceManager RM)
    {
    }
    
    /** Used for logic. Here, you handle input, data, etc. */
    @Override
    public void update(float delta)
    {
    }
    
    /** Used for everything the user can sense. Lets just draw some lel */
    @Override
    public void render(Graphics g)
    {
        g.drawString(0, 0, "Ya, we got da stuffs. You got da monez?");
    }
    
    /** Used for cleaning up and finalizing everything before the engine closes */
    @Override
    public void cleanup(ResourceManager RM)
    {
    }
}
