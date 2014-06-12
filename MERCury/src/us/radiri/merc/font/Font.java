package us.radiri.merc.font;

import us.radiri.merc.gfx.Texture;
import us.radiri.merc.res.Resource;

/**
 * An abstraction for fonts.
 * 
 * @author wessles
 */
public interface Font extends Resource {
    /**
     * Derive another differently sized instance of this font. Very resource
     * heavy, so only call this once (NOT every single frame)
     * 
     * @return A newly sized font!
     */
    public Font deriveFont(float size);
    
    /**
     * Derive another differently sized instance of this font. Very resource
     * heavy, so only call this once (NOT every single frame)
     * 
     * @return A newly sized font!
     */
    public Font deriveFont(int style);
    
    /**
     * @return The height of the font.
     */
    public int getHeight();
    
    /**
     * @return The height of the font.
     */
    public int getLineHeight();
    
    /**
     * @return The width of a given character array.
     */
    public int getWidth(char[] what);
    
    /**
     * @return The overall texture used for rendering the font.
     */
    public Texture getFontTexture();
}
