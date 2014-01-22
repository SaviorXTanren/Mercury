package com.teama.merc.util;

import com.teama.merc.gfx.Color;

/**
 * A utilities class for Color.
 * 
 * @from merc in com.teama.merc.util
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class ColorUtils
{
    public static final Color DEFAULT_BACKGROUND = Color.black;
    public static final Color DEFAULT_DRAWING = Color.white;
    public static final Color DEFAULT_TEXTURE_COLOR = Color.white;
    
    public static Color[] getColorArray(Color col, int length)
    {
        Color[] cols = new Color[length];
        
        for (int j = 0; j < length; j++)
            cols[j] = col;
        
        return cols;
    }
}
