package com.teama.merc.tuts;

import java.io.IOException;

import com.teama.merc.exc.MERCuryException;
import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;
import com.teama.merc.res.Loader;
import com.teama.merc.res.ResourceManager;
import com.teama.merc.spl.SplashScreen;

/**
 * @from MERCury in com.teama.merc.tuts
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 29, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ManagingResources extends Core
{
    Runner rnr = Runner.getInstance();
    
    public ManagingResources()
    {
        super("Managing Resources");
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    public static void main(String[] args)
    {
        new ManagingResources();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException
    {
        rnr.getGraphics().setBackground(Color.cyan);
        rnr.getGraphics().scale(15.6f);
        
        RM.loadResource(Texture.loadTexture(Loader.streamFromClasspath("com/teama/merc/tuts/lard.png")), "tex_lard");
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta) throws MERCuryException
    {
        
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException
    {
        Texture tex = (Texture) rnr.getResourceManager().retrieveResource("tex_lard");
        g.drawTexture(tex, 0, 0);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException
    {
        
    }
    
}
