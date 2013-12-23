package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * A textured version of a triangle.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class TexturedTriangle extends Triangle implements Textured {
  private Texture texture;
  
  public TexturedTriangle(float x1, float y1, float x2, float y2, float x3, float y3, Texture texture) {
    super(x1, y1, x2, y2, x3, y3);
    this.texture = texture;
  }
  
  @Override
  public Texture getTexture() {
    return texture;
  }
}
