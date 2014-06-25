package us.radiri.merc.tutorials;

import org.lwjgl.input.Keyboard;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.input.Input;

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
