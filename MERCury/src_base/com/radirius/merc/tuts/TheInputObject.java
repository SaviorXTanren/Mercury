package com.radirius.merc.tuts;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;

/**
 * @author wessles
 */

public class TheInputObject extends Core {
    Runner rnr = Runner.getInstance();
    
    public TheInputObject(String name) {
        super(name);
        // Make a game window that is 500x500
        rnr.init(this, 500, 500);
        // Now run it!
        rnr.run();
    }
    
    @Override
    public void init() {
        rnr.getGraphics().setBackground(Color.blue);
    }
    
    @Override
    public void update(float delta) {
        // Get the instance from the Runner
        Input in = rnr.getInput();
        // Check if spacebar is down
        if (in.keyDown(Keyboard.KEY_SPACE))
            rnr.getGraphics().setBackground(Color.green);
        else
            rnr.getGraphics().setBackground(Color.blue);
    }
    
    @Override
    public void render(Graphics g) {
        
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new TheInputObject("Next Gen Input!!!");
    }
    
}
