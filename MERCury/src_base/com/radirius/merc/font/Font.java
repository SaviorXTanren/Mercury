package com.radirius.merc.font;

import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Resource;

/**
 * An abstraction for fonts.
 * 
 * @from merc in com.radirius.merc.fnt
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
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
