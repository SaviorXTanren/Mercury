package radirius.merc.tutorials;

import java.io.InputStream;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.resource.Loader;

/**
 * @author wessles
 */

public class TutorialTexturing extends Core {
    Runner runner = Runner.getInstance();
    
    public TutorialTexturing() {
        super("Vermeer");
        runner.init(this, 500, 500);
        runner.run();
    }
    
    public static void main(String[] args) {
        new TutorialTexturing();
    }
    
    Texture vermeer;
    Rectangle rectangle;
    
    @Override
    public void init() {
        InputStream stream = Loader.streamFromClasspath("radirius/merc/tutorials/vermeer.png");
        vermeer = Texture.loadTexture(stream);
        rectangle = new Rectangle(10, 10, vermeer.getWidth(), vermeer.getHeight());
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    @Override
    public void render(Graphics g) {
        g.drawTexture(vermeer, rectangle);
        rectangle.rotate(2);
        rectangle.translate(1, 1);
        rectangle.scale(0.99f);
    }
    
    @Override
    public void cleanup() {
        
    }
}
