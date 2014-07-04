package us.radiri.merc.gui;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Texture;

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
    }
    
    public Button(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, left, right, body, x, y, textcolor, backgroundcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public Button(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.black, Color.white);
    }
    
    public Button(String txt, float x, float y) {
        this(txt, x, y, Color.black, Color.white);
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
}
