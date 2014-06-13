package us.radiri.merc.tuts;

import java.io.InputStream;

import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.res.Loader;

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
        String path = "us/radiri/merc/test/dAWWWW.png";
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
