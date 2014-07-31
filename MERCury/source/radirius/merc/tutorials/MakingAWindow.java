package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Graphics;

/**
 * @author wessles
 */

public class MakingAWindow extends Core {
    // Grab the instance of Runner
    Runner rnr = Runner.getInstance();

    public MakingAWindow() {
        // The title of the window
        super("My First MERCury Game");
        // Initializes a 500x500 window based off of MyCore ('this')
        rnr.init(this, 500, 500);
        // Runs the game loop
        rnr.run();
    }

    public static void main(String[] args) {
        // Runs code in the MakingAWindow constructor
        new MakingAWindow();
    }

    public void init() {
    }

    public void update(float delta) {
    }

    public void render(Graphics g) {
        // Draws a string of text at (10,10)
        g.drawString("Lorem ipsum dolor sit amet.", 10, 10);
    }

    public void cleanup() {
    }
}