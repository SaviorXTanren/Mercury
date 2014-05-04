package com.radirius.merc.test;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.FrameBuffer;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Shader;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury in package com.radirius.merc.test;
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Apr 30, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class FboTest extends Core
{
    Runner rnr = Runner.getInstance();
    
    public FboTest()
    {
        super("FBO Test!");
        rnr.init(this, 700, 700);
        rnr.run();
    }
    
    Texture cuteface;
    Shader shad;
    FrameBuffer fbo;
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException
    {
        Runner.getInstance().getGraphics().scale(1.1f);
        
        cuteface = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/dAWWWW.png"), 45, GL11.GL_NEAREST);
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
