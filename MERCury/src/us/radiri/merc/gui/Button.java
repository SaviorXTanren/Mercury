package us.radiri.merc.gui;

import us.radiri.merc.fmwk.Runner;
import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.geo.Vec2;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;
import us.radiri.merc.in.Input;

/**
 * @author Jeviny
 */
public abstract class Button extends TextBar {
    
    public Button(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Color backgroundcolor, Font textfont) {
        super(txt, left, right, body, x, y, textcolor, backgroundcolor, textfont);
        addDefaultActionCheck();
    }
    
    public Button(String txt, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, null, null, null, x, y, textcolor, backgroundcolor, TrueTypeFont.OPENSANS_REGULAR);
        addDefaultActionCheck();
    }
    
    public Button(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, left, right, body, x, y, textcolor, backgroundcolor, TrueTypeFont.OPENSANS_REGULAR);
        addDefaultActionCheck();
    }
    
    public Button(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.black, Color.white);
        addDefaultActionCheck();
    }
    
    public Button(String txt, float x, float y) {
        this(txt, x, y, Color.black, Color.white);
        addDefaultActionCheck();
    }
    
    private void addDefaultActionCheck() {
        addActionCheck(new ActionCheck() {
            @Override
            public void noAct() {
                ((Button) parent).noAct();
            }
            
            @Override
            public boolean isActed() {
                return ((Button) parent).isActed();
            }
            
            @Override
            public void act() {
                ((Button) parent).act();
            }
        });
    }
    
    /**
     * To be ran when the isActed() returns true.
     */
    public abstract void act();
    
    /**
     * @return Whether or not the button has been activated.
     */
    public boolean isActed() {
        return isClicked(bounds);
    }
    
    /**
     * To be ran when the isActed() returns false.
     */
    public abstract void noAct();
    
    public static boolean isClicked(Rectangle bounds) {
        Graphics g = Runner.getInstance().getGraphics();
        Input in = Runner.getInstance().getInput();
        Vec2 mousepos = in.getAbsoluteMousePosition().toVec2();
        mousepos.div(g.getScaleDimensions());
        if (bounds.contains(mousepos))
            if (in.mouseClicked(0))
                return true;
        return false;
    }
}
