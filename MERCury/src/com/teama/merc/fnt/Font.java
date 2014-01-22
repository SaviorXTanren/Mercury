package com.teama.merc.fnt;

import com.teama.merc.gfx.Texture;
import com.teama.merc.res.Resource;

/**
 * An abstraction for fonts.
 * 
 * @from MERCury in com.wessles.MERCury.font
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public interface Font extends Resource
{
    public Font deriveFont(float size);
    
    public Font deriveFont(int style);
    
    public int getHeight();
    
    public int getLineHeight();
    
    public int getWidth(char[] what);
    
    public Texture getFontTexture();
}
