package com.radirius.merc.gui;

import com.radirius.merc.font.Font;
import com.radirius.merc.font.TrueTypeFont;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.log.Logger;

/**
 * @author wessles, Jeviny(quite postponed, to be honest)
 */
public class TextBar extends Component {
    public Texture left, right, body;
    public Color textcolor, backgroundcolor;
    public Font textfont;
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Color backgroundcolor, Font textfont) {
        super(txt, x, y, textfont.getWidth(txt.toCharArray()) + (left != null ? left.getTextureWidth() : 0) + (right != null ? right.getTextureWidth() : 0), body != null ? body.getTextureHeight() : textfont.getHeight());
        
        if (txt.contains("\n"))
            Logger.warn("Text Bars will not display new lines correctly, so beware!");
        
        this.textcolor = textcolor;
        this.backgroundcolor = backgroundcolor;
        this.textfont = textfont;
        
        this.left = left;
        this.right = right;
        this.body = body;
    }
    
    public TextBar(String txt, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, null, null, null, x, y, textcolor, backgroundcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Color backgroundcolor) {
        this(txt, left, right, body, x, y, textcolor, backgroundcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.black, Color.white);
    }
    
    public TextBar(String txt, float x, float y) {
        this(txt, x, y, Color.black, Color.white);
    }
    
    @Override
    public void render(Graphics g) {
        if (left == null && right == null && body == null) {
            g.pushSetColor(backgroundcolor);
            g.drawRect(new Rectangle(bounds.getX(), bounds.getY(), g.getFont().getWidth(content.toCharArray()), g.getFont().getHeight()));
        }
        
        if (left != null) {
            g.pushSetColor(backgroundcolor);
            g.drawTexture(left, bounds.getX(), bounds.getY());
        }
        
        if (body != null) {
            for (float i = 0; i < contentWidth(); i += body.getTextureWidth()) {
                i = i > contentWidth() - body.getTextureWidth() ? contentWidth() : i;
                float fit = i / body.getTextureWidth();
                float overflow = body.getTextureWidth() * (fit % 1);
                boolean last = overflow != 0;
                Rectangle bounds = new Rectangle(this.bounds.getX() + i - overflow + left.getTextureWidth(), this.bounds.getY(), last ? overflow : body.getTextureWidth(), body.getTextureHeight());
                
                g.pushSetColor(backgroundcolor);
                g.drawTexture(body, 0, 0, last ? overflow : body.getTextureWidth(), body.getTextureHeight(), bounds);
            }
        }
        
        if (right != null) {
            g.pushSetColor(backgroundcolor);
            g.drawTexture(right, bounds.getX() + left.getTextureWidth() + contentWidth(), bounds.getY());
        }
        
        renderContent(g);
    }
    
    @Override
    public void renderContent(Graphics g) {
        g.pushSetColor(textcolor);
        
        if (left != null)
            g.drawString(bounds.getX() + left.getTextureWidth(), bounds.getY() + bounds.getHeight() / 2 - textfont.getHeight() / 2, content);
        else
            g.drawString(bounds.getX(), bounds.getY(), content);
    }
    
    public float contentWidth() {
        return textfont.getWidth(content.toCharArray());
    }
}
