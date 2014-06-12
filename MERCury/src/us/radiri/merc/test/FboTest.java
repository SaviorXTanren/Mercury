package us.radiri.merc.test;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import us.radiri.merc.fmwk.Core;
import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.gfx.FrameBuffer;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Shader;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.res.Loader;

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
        Runner.getInstance().getGraphics().scale(1.1f);
        
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
            TrueTypeFont f = (TrueTypeFont) g.getFont();
            g.drawTexture(f.font_tex, 0, x++);
            
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
