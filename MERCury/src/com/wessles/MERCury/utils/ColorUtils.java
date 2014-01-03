package com.wessles.MERCury.utils;

import com.wessles.MERCury.opengl.Color;

/**
 * A utilities class for Color.
 * 
 * @from MERCury in com.wessles.MERCury.utils
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class ColorUtils {
  public static final Color DEFAULT_BACKGROUND = Color.black;
  public static final Color DEFAULT_DRAWING = Color.white;
  public static final Color DEFAULT_TEXTURE_COLOR = Color.white;
  
  public static Color[] getColorArray(Color col, int length) {
    Color[] cols = new Color[length];
    for (int j = 0; j < length; j++)
      cols[j] = col;
    
    return cols;
  }
}
