package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Shader;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.spl.SplashScreen;

/**
 * An object version of shaders. Does all of the tedius stuff for you and lets
 * you use the shader easily.
 * 
 * @from MERCury in com.radirius.merc.test
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class ShaderTest extends Core
{

    Runner rnr = Runner.getInstance();
    Shader program;

    public ShaderTest()
    {
        super("MERCury Shader Test");
        rnr.init(this, 800, 600);
        rnr.run();
    }

    Texture tex;

    @Override
    public void init(ResourceManager RM) throws IOException
    {
        rnr.getGraphics().scale(4);

        program = Shader.getDefaultShader();
        tex = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/torch.png"));

        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }

    @Override
    public void update(float delta)
    {
    }

    @Override
    public void render(Graphics g)
    {
        g.setBackground(Color.cyan);
        g.useShader(program);
        g.drawTexture(tex, 0, 0);
        g.drawRect(new Rectangle(100, 100, 50, 50));
        g.releaseShaders();
    }

    @Override
    public void cleanup(ResourceManager RM)
    {
    }

    public static void main(String[] args)
    {
        new ShaderTest();
    }
}
