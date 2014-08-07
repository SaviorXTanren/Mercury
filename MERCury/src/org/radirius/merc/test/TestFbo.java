package org.radirius.merc.test;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import org.radirius.merc.gfx.FrameBuffer;
import org.radirius.merc.gfx.Graphics;
import org.radirius.merc.gfx.Shader;
import org.radirius.merc.gfx.Texture;
import org.radirius.merc.main.Core;
import org.radirius.merc.main.Runner;
import org.radirius.merc.res.Loader;

/**
 * @author wessles
 */

public class TestFbo extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestFbo() {
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
        
        cuteface = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x64.png"), 45, GL_NEAREST);
        shad = Shader.getShader(Loader.streamFromClasspath("radirius/merc/tests/distort.fs"), Shader.FRAGMENT_SHADER);
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
        new TestFbo();
    }
}
