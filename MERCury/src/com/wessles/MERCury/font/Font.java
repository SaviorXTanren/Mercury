package com.wessles.MERCury.font;

import com.wessles.MERCury.Resource;
import com.wessles.MERCury.opengl.Texture;

/**
 * An abstraction for fonts.
 * 
 * @from MERCury in com.wessles.MERCury.font
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under GPLv2.0 license. You can find the license itself at bit.ly/1eyRQJ7.
 */
public interface Font extends Resource {
  public Font deriveFont(float size);
  
  public Font deriveFont(int style);
  
  public int getHeight();
  
  public int getLineHeight();
  
  public int getWidth(char[] what);
  
  public Texture getFontTexture();
}
