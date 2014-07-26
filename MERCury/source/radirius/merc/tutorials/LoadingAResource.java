package radirius.merc.tutorials;

import java.io.InputStream;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.resource.Loader;

/**
 * @author wessles
 */

public class LoadingAResource extends Core {
  Runner rnr = Runner.getInstance();
  
  public LoadingAResource() {
    super("Loading a Resource!");
    rnr.init(this, 500, 500);
    rnr.run();
  }
  
  Texture test_texture;
  
  @Override
  public void init() {
    // The path is loading a Texture from the classpath, and thus we should
    // reference it like we would a class, except the '.'s turn into '/'s.
    String path = "radirius/merc/tests/dAWWWW.png";
    // The stream that represents the path we just made.
    InputStream is = Loader.streamFromClasspath(path);
    // The Texture that is derived from the stream that we just made with
    // the path.
    test_texture = Texture.loadTexture(is);
    
    // Set the background so it contrasts with the texture.
    rnr.getGraphics().setBackground(Color.white);
  }
  
  @Override
  public void update(float delta) {
  }
  
  @Override
  public void render(Graphics g) {
    g.drawTexture(test_texture, 0, 0, 500, 500);
  }
  
  @Override
  public void cleanup() {
  }
  
  public static void main(String[] args) {
    new LoadingAResource();
  }
}
