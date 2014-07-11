package us.radiri.merc.gui;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.SpriteSheet;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class CheckBox extends Component {
    private Texture unchecked, checked;
    private boolean ticked = false;
    
    private float boxsize;
    
    private boolean boxtoleftoftext = false;
    
    private Color textcolor;
    private Font font;
    
    public CheckBox(String txt, float x, float y) {
        this(txt, x, y, Color.DEFAULT_TEXT_COLOR);
    }
    
    public CheckBox(String txt, float x, float y, Color textcolor) {
        this(txt, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, Color textcolor, Font font) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), Math.max(
                getDefaultTextures().getTexture(1).getWidth(), getDefaultTextures().getTexture(0).getWidth()), true,
                textcolor, font);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, true,
                Color.DEFAULT_TEXT_COLOR, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, Color textcolor) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, true,
                textcolor, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext) {
        this(txt, x, y, boxsize, boxtoleftoftext, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Font font) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize,
                boxtoleftoftext, Color.DEFAULT_TEXT_COLOR, font);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Color textcolor, Font font) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize,
                boxtoleftoftext, textcolor, font);
    }
    
    public CheckBox(String txt, float x, float y, Texture unchecked, Texture checked, float boxsize,
            boolean boxtoleftoftext, Color textcolor, Font font) {
        super(txt, x, y, font.getWidth(txt.toCharArray()) + boxsize, Math.max(font.getHeight(), boxsize));
        this.unchecked = unchecked;
        this.checked = checked;
        
        this.boxsize = boxsize;
        
        this.boxtoleftoftext = boxtoleftoftext;
        
        this.textcolor = textcolor;
        this.font = font;
    }
    
    @Override
    public void update(float delta) {
        if (isClicked(bounds))
            ticked = !ticked;
    }
    
    @Override
    public void render(Graphics g) {
        if (boxtoleftoftext) {
            g.pushSetColor(textcolor);
            g.drawString(font, bounds.getX() + boxsize, bounds.getY(), content);
            
            if (!ticked)
                g.drawTexture(unchecked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize,
                        boxsize);
            else
                g.drawTexture(checked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize,
                        boxsize);
        } else {
            g.pushSetColor(textcolor);
            g.drawString(font, bounds.getX(), bounds.getY(), content);
            
            if (!ticked)
                g.drawTexture(unchecked, bounds.getX() + font.getWidth(content.toCharArray()),
                        bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
            else
                g.drawTexture(checked, bounds.getX() + font.getWidth(content.toCharArray()),
                        bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
        }
    }
    
    public boolean isTicked() {
        return ticked;
    }
    
    public void setTicked(boolean ticked) {
        this.ticked = ticked;
    }
    
    private static SpriteSheet defaulttexture;
    
    public static SpriteSheet getDefaultTextures() {
        if (defaulttexture == null)
            defaulttexture = SpriteSheet.loadSpriteSheet(
                    Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/gui/checkbox.png")), 64);
        
        return defaulttexture;
    }
}
