package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;
import radirius.merc.input.Input;

/**
 * @author wessles
 */

public class TutorialTakingInput extends Core {
    Runner runner = Runner.getInstance();
    
    public TutorialTakingInput() {
        super("Taking Input");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new TutorialTakingInput();
    }
    
    Rectangle cursor;
    Rectangle rectangle;
    
    @Override
    public void init() {
        cursor = new Rectangle(0, 0, 15);
        rectangle = new Rectangle(0, 0, 10, 10);
    }
    
    @Override
    public void update(float delta) {
        Input in = Runner.getInstance().getInput();
        
        // Basic movement (ULDR)
        if (in.keyDown(Input.KEY_UP))
            rectangle.translate(0, -5);
        if (in.keyDown(Input.KEY_DOWN))
            rectangle.translate(0, 5);
        if (in.keyDown(Input.KEY_LEFT))
            rectangle.translate(-5, 0);
        if (in.keyDown(Input.KEY_RIGHT))
            rectangle.translate(5, 0);
        
        // Grows by 100% if the spacebar is clicked
        if (in.keyClicked(Input.KEY_SPACE))
            rectangle.setScale(rectangle.getScale() + 1);
        
        // Moves the cursor rectangle to the mouse's position
        cursor.translateTo(in.getAbsoluteMouseX(), in.getAbsoluteMouseY());
    }
    
    @Override
    public void render(Graphics g) {
        g.drawRect(cursor);
        g.drawRect(rectangle);
    }
    
    @Override
    public void cleanup() {
        
    }
}