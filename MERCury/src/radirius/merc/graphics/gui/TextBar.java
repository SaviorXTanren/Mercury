package radirius.merc.graphics.gui;

import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.graphics.font.Font;
import radirius.merc.graphics.font.TrueTypeFont;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Shape;
import radirius.merc.utilities.logging.Logger;

/**
 * @author wessles, Jeviny
 */
public class TextBar extends Component {
    protected Texture left, right, body;
    protected Color textcolor;
    protected Font textfont;
    
    public TextBar(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Font textfont) {
        super(txt, x, y, textfont.getWidth(txt) + (left != null ? left.getWidth() : 0) + (right != null ? right.getWidth() : 0), body != null ? body.getHeight() : textfont.getHeight());
        
        if (txt.contains("\n"))
            Logger.warn("TextBars will not display new lines correctly. If you have any multi-lined text you should use a TextBox instead.");
        
        this.textcolor = textcolor;
        this.textfont = textfont;
        
        this.left = left;
        this.right = right;
        this.body = body;
    }
    
    public TextBar(String txt, float x, float y, Color textcolor) {
        this(txt, getDefaultTextures().getTexture(3), getDefaultTextures().getTexture(3).convertToTexture(true, false), getDefaultTextures().getTexture(4), x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
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
    public void update(float delta) {
        super.update(delta);
        
        // Fit to a new size.
        Shape parent = new Shape(bounds.getParent());
        
        bounds.clearParent();
        bounds = new Rectangle(bounds.getX(), bounds.getY(), contentWidth() + (left != null ? left.getWidth() : 0) + (right != null ? right.getWidth() : 0), bounds.getHeight());
        bounds.setParent(parent);
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
                Rectangle bounds = new Rectangle(this.bounds.getX() + i - overflow + left.getWidth(), this.bounds.getY(), last ? overflow : body.getWidth(), body.getHeight());
                
                g.drawTexture(body, 0, 0, last ? overflow : body.getWidth(), body.getHeight(), bounds);
            }
        
        if (right != null)
            g.drawTexture(right, bounds.getX() + left.getWidth() + contentWidth() - 1, bounds.getY());
        
        renderContent(g);
    }
    
    @Override
    public void renderContent(Graphics g) {
        g.setColor(textcolor);
        
        if (left != null)
            g.drawString(content, textfont, bounds.getX() + left.getWidth(), bounds.getY() + bounds.getHeight() / 2 - g.getFont().getHeight() / 2);
        else
            g.drawString(content, textfont, bounds.getX(), bounds.getY());
    }
    
    public float contentWidth() {
        return textfont.getWidth(content);
    }
}
