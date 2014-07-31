package radirius.merc.tutorials;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;
import radirius.merc.input.Input;

/**
 * @author wessles
 */

public class TakingInput extends Core {
    Runner runner = Runner.getInstance();
    
    public TakingInput() {
        super("Taking Input");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new TakingInput();
    }
    
    Rectangle rectangle;
    
    public void init() {
        rectangle = new Rectangle(0, 0, 10, 10);
    }
    
    public void update(float delta) {
        Input in = Runner.getInstance().getInput();
        
        if(in.keyDown(Input.KEY_UP))
            rectangle.translate(0, -5);
        if(in.keyDown(Input.KEY_DOWN))
            rectangle.translate(0, 5);
        if(in.keyDown(Input.KEY_LEFT))
            rectangle.translate(-5, 0);
        if(in.keyDown(Input.KEY_RIGHT))
            rectangle.translate(5, 0);
    }
    
    public void render(Graphics g) {
        g.drawRect(rectangle);
    }
    
    public void cleanup() {
        
    }
}