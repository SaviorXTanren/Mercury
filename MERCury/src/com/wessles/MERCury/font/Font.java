package com.wessles.MERCury.font;

import com.wessles.MERCury.Resource;
import com.wessles.MERCury.opengl.Texture;

/**
 * An abstraction for fonts.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public interface Font extends Resource {
  public Font deriveFont(float size);
  
  public Font deriveFont(int style);
  
  public int getHeight();
  
  public int getLineHeight();
  
  public int getWidth(char[] what);
  
  public Texture getFontTexture();
}
