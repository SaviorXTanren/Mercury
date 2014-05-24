package com.radirius.merc.test;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.font.TrueTypeFont;
import com.radirius.merc.gfx.FrameBuffer;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Shader;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @author wessles
 */

public class FboTest extends Core
{
    Runner rnr = Runner.getInstance();

    public FboTest()
    {
        super("FBO Test!");
        rnr.init(this, 500, 500);
        rnr.run();
    }

    Texture cuteface;
    Shader shad;
    FrameBuffer fbo;

    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException
    {
        Runner.getInstance().getGraphics().scale(1.1f);

        cuteface = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/dAWWWW.png"), 45, GL_NEAREST);
        shad = Shader.getShader(Loader.streamFromClasspath("com/radirius/merc/test/distort.fs"), Shader.FRAGMENT_SHADER);
        fbo = FrameBuffer.getFrameBuffer();
    }

    @Override
    public void update(float delta) throws MERCuryException
    {
    }

    float x = 0;

    @Override
    public void render(Graphics g) throws MERCuryException
    {
        fbo.use();
        {
            TrueTypeFont f = (TrueTypeFont) g.getFont();
            g.drawTexture(f.font_tex, 0, x);
            g.drawTexture(cuteface, x++, x);

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
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException
    {
    }

    public static void main(String[] args)
    {
        new FboTest();
    }
}
