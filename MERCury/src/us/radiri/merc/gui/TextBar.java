package us.radiri.merc.gui;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.logging.Logger;

/**
 * @author wessles, Jeviny(quite postponed, to be honest)
 */
public class TextBar extends Component {
    private Texture left, right, body;
    private Color textcolor;
    private Font textfont;
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor,
            Font textfont) {
        super(txt, x, y, textfont.getWidth(txt.toCharArray()) + (left != null ? left.getWidth() : 0)
                + (right != null ? right.getWidth() : 0), body != null ? body.getHeight() : textfont.getHeight());
        
        if (txt.contains("\n"))
            Logger.warn("Text Bars will not display new lines correctly, so beware!");
        
        this.textcolor = textcolor;
        this.textfont = textfont;
        
        this.left = left;
        this.right = right;
        this.body = body;
    }
    
    public TextBar(String txt, float x, float y, Color textcolor) {
        this(txt, null, null, null, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor) {
        this(txt, left, right, body, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y) {
        this(txt, left, right, body, x, y, Color.black);
    }
    
    public TextBar(String txt, float x, float y) {
        this(txt, x, y, Color.black);
    }
    
    @Override
    public void render(Graphics g) {
        if (left != null)
            g.drawTexture(left, bounds.getX(), bounds.getY());
        
        if (body != null)
            for (float i = 0; i < contentWidth(); i += body.getWidth()) {
                i = i > contentWidth() - body.getWidth() ? contentWidth() : i;
                float fit = i / body.getWidth();
                float overflow = body.getWidth() * (fit % 1);
                boolean last = overflow != 0;
                Rectangle bounds = new Rectangle(this.bounds.getX() + i - overflow + left.getWidth(),
                        this.bounds.getY(), last ? overflow : body.getWidth(), body.getHeight());
                
                g.drawTexture(body, 0, 0, last ? overflow : body.getWidth(), body.getHeight(), bounds);
            }
        
        if (right != null)
            g.drawTexture(right, bounds.getX() + left.getWidth() + contentWidth(), bounds.getY());
        
        renderContent(g);
    }
    
    @Override
    public void renderContent(Graphics g) {
        g.pushSetColor(textcolor);
        
        if (left != null)
            g.drawString(textfont, bounds.getX() + left.getWidth(),
                    bounds.getY() + bounds.getHeight() / 2 - textfont.getHeight() / 2, content);
        else
            g.drawString(textfont, bounds.getX(), bounds.getY(), content);
    }
    
    public float contentWidth() {
        return textfont.getWidth(content.toCharArray());
    }
}
