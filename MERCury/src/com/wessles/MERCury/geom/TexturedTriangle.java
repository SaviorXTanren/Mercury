package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * A textured version of a triangle.
 * 
 * @from MERCury in com.wessles.MERCury.geom
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under GPLv2.0 license. You can find the license itself at bit.ly/1eyRQJ7.
 */

public class TexturedTriangle extends Triangle implements Textured {
  private Texture texture;
  
  public TexturedTriangle(Triangle tri, Texture texture) {
    super(tri.nx, tri.ny, tri.fx, tri.ny, tri.nx, tri.fy);
    this.texture = texture;
  }
  
  @Override
  public Texture getTexture() {
    return texture;
  }
}
