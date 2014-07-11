package us.radiri.merc.gui;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Texture;

/**
 * @author Jeviny
 */
public class TextButton extends TextBar implements Button {
    private boolean wasactive;
    
    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor,
            Font textfont) {
        super(txt, left, right, body, x, y, textcolor, textfont);
    }
    
    public TextButton(String txt, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, null, null, null, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor) {
        this(txt, left, right, body, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.black);
    }
    
    public TextButton(String txt, float x, float y) {
        this(txt, x, y, Color.black, Color.white);
    }
    
    @Override
    public void update(float delta) {
        if (isClicked(bounds))
            wasactive = true;
    }
    
    @Override
    public boolean wasActive() {
        boolean _waspressed = wasactive;
        wasactive = false;
        return _waspressed;
    }
}
