package com.teama.merc.test;

import java.io.IOException;

import com.teama.merc.exc.MERCuryException;
import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;
import com.teama.merc.gui.TextBar;
import com.teama.merc.gui.TextBox;
import com.teama.merc.res.Loader;
import com.teama.merc.res.ResourceManager;

/**
 * @from MERCury in package com.teama.merc.test;
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Mar 2, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class GUITest extends Core
{
    Runner rnr = Runner.getInstance();
    
    TextBar tbar0;
    TextBox tbox0;
    
    public GUITest()
    {
        super("GUI Test!");
        rnr.init(this, 470, 350);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException
    {
        Texture left = Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/test/side_tbar.png"));
        Texture right = left.flipX();
        Texture body = Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/test/body_tbar.png"));
        tbar0 = new TextBar("Progressive stupidity, a poem by wesslas", left, right, body, 10, 10);
        tbox0 = new TextBox("The main reason f0r the c1v1|_ w4r w45 d4 n0 5c0p35 63771n h1 1n d15 c|_uB Y0 1 h4z m0n3Y$ 0m6 m8 u 637 r3k7 50 h4rd, y0 m4mman 357 570094d", Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/test/border_tbox_hor.png")), Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/test/border_tbox_vert.png")), 10, 100, 300, 180, 20);
    }
    
    @Override
    public void update(float delta) throws MERCuryException
    {
        
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException
    {
        tbar0.render(g);
        tbox0.render(g);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException
    {
    }
    
    public static void main(String[] args)
    {
        new GUITest();
    }
}
