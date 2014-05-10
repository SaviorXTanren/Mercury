package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.gui.Button;
import com.radirius.merc.gui.TextBar;
import com.radirius.merc.gui.TextBox;
import com.radirius.merc.gui.TextPanel;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury in package com.radirius.merc.test;
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Mar 2, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class GUITest extends Core {
    Runner rnr = Runner.getInstance();
    
    TextBar tbar0;
    TextBox tbox0;
    TextPanel tpan1;
    Button button0;
    
    public GUITest() {
        super("GUI Test!");
        
        rnr.init(this, 1280, 800);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        Texture left = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/side_tbar.png"));
        Texture right = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/side_tbar.png"), true, false);
        Texture body = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/body_tbar.png"));
        
        tbar0 = new TextBar("Progressive stupidity, a poem by wesslas", left, right, body, 10, 10);
        tbox0 = new TextBox("The main reason f0r the c1v1|_ w4r w45 d4 n0 5c0p35 63771n h1 1n d15 c|_uB Y0 1 h4z m0n3Y$ 0m6 m8 u 637 r3k7 50 h4rd, y0 m4mman 357 570094d", Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/border_tbox_hor.png")), Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/border_tbox_vert.png")), 10, 100, 300, 180, 16, Color.white);
        
        Texture background = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/panel_main.png"));
        
        // Texture bleft =
        // Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/button_left.png"));
        // Texture bbody =
        // Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/button_body.png"));
        
        float tb1w = 800;
        float tb1h = 600;
        
        tpan1 = new TextPanel("Welcome!", "Welcome to MERCury Game Library! This is a demonstration of the GUI System, currently being developed by both Jeviny and Wessles. How was your day? Mine has been great. I've had to waste all of it because I'm writing this stupid-ass text just so I can test panels!", background, rnr.getWidth() - (tb1w + 32), 16, tb1w, tb1h, 64, Color.black);
        
        // tpan1.addButton("Click Me!", bleft, bbody, GridLocation.BOTTOM_LEFT);
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
        
    }
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        g.setBackground(Color.gray);
        
        tpan1.render(g);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
        
    }
    
    public static void main(String[] args) {
        new GUITest();
    }
}
