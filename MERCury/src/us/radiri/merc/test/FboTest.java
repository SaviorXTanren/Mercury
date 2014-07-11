package us.radiri.merc.test;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.graphics.FrameBuffer;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Shader;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class FboTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public FboTest() {
        super("FBO Test!");
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    Texture cuteface;
    Shader shad;
    FrameBuffer fbo;
    
    @Override
    public void init() {
        Runner.getInstance().getGraphics().setScale(1.1f);
        
        cuteface = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"), 45, GL_NEAREST);
        shad = Shader.getShader(Loader.streamFromClasspath("us/radiri/merc/test/distort.fs"), Shader.FRAGMENT_SHADER);
        fbo = FrameBuffer.getFrameBuffer();
    }
    
    @Override
    public void update(float delta) {
    }
    
    float x = 0;
    
    @Override
    public void render(Graphics g) {
        fbo.use();
        {
            g.drawTexture(cuteface, x++, x++);
            
            g.getBatcher().flush();
        }
        fbo.release();
        
        g.useShader(shad);
        {
            g.drawTexture(fbo.getTextureObject(), 0, 0);
        }
        g.releaseShaders();
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new FboTest();
    }
}
