package com.radirius.merc.tuts;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;

/**
 * @author wessles
 */

public class Blackbox extends Core {
    Runner rnr = Runner.getInstance();
    
    public Blackbox(String name) {
        super(name);
        // Make a game window that is 500x500
        rnr.init(this, 500, 500);
        // Now run it!
        rnr.run();
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void update(float delta) {
        rnr.sleep(5000);
        rnr.end();
    }
    
    @Override
    public void render(Graphics g) {
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new Blackbox("This is the title of the window!");
    }
    
}
