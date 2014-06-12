package us.radiri.merc.gui;

import java.util.ArrayList;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;

/**
 * @author wessles, Jeviny
 */
public class TextBox extends Component {
    public float margin;
    
    public Texture border_hor, border_vert;
    
    public Texture background_img;
    public Color background_col;
    public Color textCol;
    
    private ArrayList<Button> buttons = new ArrayList<Button>();
    
    private Font textfont = null;
    
    private int bg_mode = 0;
    
    private int BG_TEXTURE = 0;
    private int BG_COLOUR = 1;
    
    public TextBox(String txt, Rectangle bounds) {
        this(txt, bounds, 0);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin) {
        this(txt, bounds, margin, Color.white, Color.black);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin, Color textcolor, Color backgroundcolor) {
        this(txt, bounds, margin, null, TrueTypeFont.OPENSANS_REGULAR, Color.white, Color.black);
    }
    
    public TextBox(String txt, Rectangle bounds, float margin, Texture backgroundtex, Font textfont, Color textcolor, Color backgroundcolor) {
        super("", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        
        this.margin = margin;
        background_img = backgroundtex;
        background_col = backgroundcolor;
        textCol = textcolor;
        
        content = fitToBounds();
        
        bg_mode = backgroundtex != null ? BG_TEXTURE : BG_COLOUR;
        
        this.textfont = textfont;
    }
    
    @Override
    public void render(Graphics g) {
        if (bg_mode == BG_TEXTURE)
            g.drawTexture(background_img, bounds);
        else if (bg_mode == BG_COLOUR) {
            g.pushSetColor(background_col);
            g.drawRect(bounds);
        }
        
        renderContent(g);
        
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            
            b.render(g);
        }
    }
    
    @Override
    public void renderContent(Graphics g) {
        g.setFont(textfont);
        g.pushSetColor(textCol);
        g.drawString(bounds.getX() + margin, bounds.getY() + margin, content);
    }
    
    @Override
    public void update() {
        super.update();
        
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            
            b.update();
        }
    }
    
    public void setContent(String content) {
        this.content = content + " ";
        this.content = fitToBounds();
    }
    
    public String getContent() {
        return content;
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
            if (textfont.getWidth(content.substring(lidx, idx).toCharArray()) >= bounds.getWidth() - margin * 2 || idx >= content.length()) {
                // Making sure no words get cut off, or split in half.
                int lastspace = linetxt.lastIndexOf(' ') + 1;
                
                if (lastspace > 0) {
                    idx -= linetxt.length() - lastspace;
                    linetxt = linetxt.substring(0, lastspace);
                }
                
                // Trim whitespace
                linetxt = linetxt.trim();
                
                // Add line to text, and reset variables
                finaltxt += linetxt;
                if (!(idx > content.length()))
                    finaltxt += "\n";
                linetxt = "";
                lidx = idx;
            }
        }
        
        return finaltxt;
    }
    
    public static enum GridLocation {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, MID_LEFT, MID_RIGHT;
    }
}
