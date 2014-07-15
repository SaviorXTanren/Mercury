package radirius.merc.tutorials;

import org.lwjgl.input.Keyboard;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.input.Input;

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
