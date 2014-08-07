package org.radirius.merc.gui;

import org.radirius.merc.font.Font;
import org.radirius.merc.font.TrueTypeFont;
import org.radirius.merc.gfx.Color;
import org.radirius.merc.gfx.Texture;

/**
 * @author Jeviny
 */
public class TextButton extends TextBar implements Button {
    protected boolean wasactive;
    
    public TextButton(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Font textfont) {
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
        super.update(delta);
        
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
