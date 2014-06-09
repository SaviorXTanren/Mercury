package com.radirius.merc.test;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.Triangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.gui.Button;
import com.radirius.merc.math.MercMath;
import com.radirius.merc.res.Loader;

/** @author opiop65, wessles */

public class LineTest extends Core {
    
    Runner rnr = Runner.getInstance();
    
    public LineTest(String name) {
        super(name);
        rnr.init(this, 800, 600);
        rnr.run();
    }
    
    Button toggle_btn;
    
    @Override
    public void init() {
        Texture tbl, tbr, tbm;
        tbl = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/button_left.png"));
        tbr = Texture.loadTexture(tbl.getSourceImage(), true, false);
        tbm = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/button_body.png"));
        
        toggle_btn = new Button("Toggle It!", tbl, tbr, tbm, 70, 500, Color.white.duplicate(), Color.black.duplicate()) {
            @Override
            public void noAct() {
                toggle_btn.backgroundcolor.r+=0.1f;
                toggle_btn.backgroundcolor.g+=0.1f;
                toggle_btn.backgroundcolor.b+=0.1f;
            }
            
            @Override
            public void act() {
                backgroundcolor = Color.black.duplicate();
                modeline = !modeline;
            }
        };
    }
    
    @Override
    public void update(float delta) {
        toggle_btn.update();
    }
    
    float linewidth = 1;
    boolean modeline = true;
    
    @Override
    public void render(Graphics g) {
        g.setLineWidth(linewidth);
        
        g.setDrawMode(modeline ? Graphics.MODE_LINE : Graphics.MODE_FILL);
        g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0, 255)));
        g.drawRect(new Rectangle(100, 100, 100));
        g.drawCircle(100, 100, 50);
        
        g.setDrawMode(modeline ? Graphics.MODE_FILL : Graphics.MODE_LINE);
        g.setColor(new Color((int) MercMath.random(0, 255), (int) MercMath.random(0, 255), (int) MercMath.random(0, 255)));
        g.drawRect(new Rectangle(650, 100, 50));
        g.drawCircle(600, 200, 50);
        g.drawTriangle(new Triangle(600,200,610,200,600,190));
        
        g.setDrawMode(Graphics.MODE_FILL);
        g.setColor(Color.white);
        
        toggle_btn.render(g);
        
        g.drawLine(600, 200, 100, 100);
        
        g.drawString(300, 400, "This is rendering in GL_LINES, which is pretty chill.");
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new LineTest("Line Test");
    }
}
