package us.radiri.merc.gui;

import us.radiri.merc.font.Font;
import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.SpriteSheet;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class CheckBox extends Component {
    private Font font;
    
    private Texture unchecked, checked;
    private boolean ticked = false;
    
    private float boxsize;
    
    private boolean boxtoleftoftext = false;
    
    public CheckBox(String txt, float x, float y) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), Math.max(getDefaultTextures().getTexture(1).getSubWidth(), getDefaultTextures().getTexture(0).getSubWidth()), true, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, true, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, boxtoleftoftext, TrueTypeFont.OPENSANS_REGULAR);
    }
    
    public CheckBox(String txt, float x, float y, float boxsize, boolean boxtoleftoftext, Font font) {
        this(txt, x, y, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), boxsize, boxtoleftoftext, font);
    }
    
    public CheckBox(String txt, float x, float y, Texture unchecked, Texture checked, float boxsize, boolean boxtoleftoftext, Font font) {
        super(txt, x, y, font.getWidth(txt.toCharArray()) + Math.max(unchecked.getWidth(), checked.getWidth()), Math.max(font.getHeight(), boxsize));
        this.font = font;
        this.unchecked = unchecked;
        this.checked = checked;
        
        this.boxsize = boxsize;
        
        this.boxtoleftoftext = boxtoleftoftext;
        
        this.addActionCheck(new ActionCheck() {
            @Override
            public boolean isActed() {
                return isClicked(bounds);
            }
            
            @Override
            public void act() {
                ticked = !ticked;
            }
            
            @Override
            public void noAct() {
                
            }
        });
    }
    
    @Override
    public void render(Graphics g) {
        if (boxtoleftoftext) {
            g.drawString(font, bounds.getX() + boxsize, bounds.getY(), content);
            
            if (!ticked)
                g.drawTexture(unchecked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
            else
                g.drawTexture(checked, bounds.getX(), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
        } else {
            g.drawString(font, bounds.getX(), bounds.getY(), content);
            
            if (!ticked)
                g.drawTexture(unchecked, bounds.getX() + font.getWidth(content.toCharArray()), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
            else
                g.drawTexture(checked, bounds.getX() + font.getWidth(content.toCharArray()), bounds.getY() + font.getHeight() / 2 - boxsize / 2, boxsize, boxsize);
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
            defaulttexture = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/gui/checkbox.png")), 64);
        
        return defaulttexture;
    }
}
