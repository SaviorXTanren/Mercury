package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Graphics;

/**
 * @author wessles
 */

public class TutorialMakingAWindow extends Core {
    // Grab the instance of Runner
    Runner rnr = Runner.getInstance();
    
    public TutorialMakingAWindow() {
        // The title of the window
        super("My First MERCury Game");
        // Initializes a 500x500 window based off of TutorialMakingAWindow
        // ('this')
        rnr.init(this, 500, 500);
        // Runs the game loop
        rnr.run();
    }
    
    public static void main(String[] args) {
        // Runs code in the TutorialMakingAWindow constructor
        new TutorialMakingAWindow();
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void render(Graphics g) {
        // Draws a string of text at (10,10)
        g.drawString("Lorem ipsum dolor sit amet.", 10, 10);
    }
    
    @Override
    public void cleanup() {
    }
}