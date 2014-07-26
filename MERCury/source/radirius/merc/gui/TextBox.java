package radirius.merc.gui;

import java.util.ArrayList;

import radirius.merc.font.Font;
import radirius.merc.font.TrueTypeFont;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;

/**
 * @author wessles, Jeviny
 */
public class TextBox extends Component {
    public float margin;
    
    public Texture background_img;
    public Color textCol;
    
    private ArrayList<TextButton> buttons = new ArrayList<TextButton>();
    
    private Font textfont = null;
    
    public TextBox(String txt, Rectangle bounds) {
        this(txt, bounds, 0);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin) {
        this(txt, bounds, margin, Color.white);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin, Color textcolor) {
        this(txt, bounds, margin, null, TrueTypeFont.OPENSANS_REGULAR, textcolor);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin, Texture backgroundtex, Font textfont, Color textcolor) {
        super("", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        
        this.margin = margin;
        background_img = backgroundtex;
        textCol = textcolor;
        
        this.textfont = textfont;
        
        content = txt + " ";
        content = fitToBounds();
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        for (int i = 0; i < buttons.size(); i++) {
            TextButton b = buttons.get(i);
            
            b.update(delta);
        }
    }
    
    @Override
    public void render(Graphics g) {
        if (background_img != null)
            g.drawTexture(background_img, bounds);
        
        renderContent(g);
        
        for (int i = 0; i < buttons.size(); i++) {
            TextButton b = buttons.get(i);
            
            b.render(g);
        }
    }
    
    @Override
    public void renderContent(Graphics g) {
        g.setColor(textCol);
        g.drawString(textfont, bounds.getX() + margin, bounds.getY() + margin, content);
    }
    
    public void setContent(String content) {
        this.content = content + " ";
        this.content = fitToBounds();
    }
    
    public String getContent() {
        return content.trim();
    }
    
    public void setTextFont(Font fnt) {
        textfont = fnt;
    }
    
    public Font getTextFont() {
        return textfont;
    }
    
    private String fitToBounds() {
        // A line of text
        String linetxt = "";
        
        // The index character in the original string
        int idx = 0;
        int lidx = 0;
        
        // The result
        String finaltxt = "";
        
        // Begin fitting!
        while (idx < content.length()) {
            // Write character to line, and add to index
            if (content.charAt(idx) == '\n') {
                // Add line to text, and reset variables
                finaltxt += linetxt;
                if (!(idx > content.length()))
                    finaltxt += "\n";
                linetxt = "";
                lidx = idx;
            } else
                linetxt += content.charAt(idx);
            
            idx++;
            
            // If we are pushing the width limit or we are at the end of the
            // text, new line (or not)!
            if (textfont.getWidth(content.substring(lidx, idx)) > bounds.getWidth() - margin * 2
                    || idx >= content.length()) {
                // Making sure no words get cut off, or split in half.
                int lastspace = linetxt.lastIndexOf(' ') + 1;
                
                if (lastspace > 0) {
                    idx -= linetxt.length() - lastspace;
                    linetxt = linetxt.substring(0, lastspace);
                }
                
                // Add line to text, and reset variables
                finaltxt += linetxt;
                if (idx < content.length())
                    finaltxt += "\n";
                linetxt = "";
                lidx = idx;
            }
        }
        
        return finaltxt;
    }
}
