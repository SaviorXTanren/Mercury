package com.wessles.MERCury.opengl;

import org.lwjgl.opengl.GL11;

/**
 * A class for Color, that will hold the three values; r, g, and b, and will darken, brighten, multiply, etc.
 * 
 * @from MERCury in com.wessles.MERCury.opengl
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Color {
  public static final Color red = new Color(1f, 0f, 0f);
  public static final Color green = new Color(0f, 1f, 0f);
  public static final Color blue = new Color(0f, 0f, 1f);
  
  public static final Color yellow = new Color(1f, 1f, 0f);
  public static final Color purple = new Color(1f, 0f, 1f);
  public static final Color babyblue = new Color(0f, 1f, 1f);
  
  public static final Color white = new Color(1f, 1f, 1f);
  public static final Color black = new Color(0f, 0f, 0f);
  
  public static final Color gray = new Color(0.5f, 0.5f, 0.5f);
  public static final Color darkred = new Color(0.5f, 0f, 0f);
  public static final Color darkgreen = new Color(0f, 0.5f, 0f);
  public static final Color darkblue = new Color(0f, 0f, 0.5f);
  public static final Color mustard = new Color(0.5f, 0.5f, 0f);
  public static final Color darkpurple = new Color(0.5f, 0f, 0.5f);
  public static final Color darkbabyblue = new Color(0f, 0.5f, 0.5f);
  
  public float r = 0, g = 0, b = 0, a = 0;
  
  public Color(float r, float g, float b) {
    this.r = r;
    this.g = g;
    this.b = b;
    a = 1;
  }
  
  public Color(float r, float g, float b, float a) {
    this(r, g, b);
    this.a = a;
  }
  
  public Color(int r, int g, int b) {
    this.r = r / 255f;
    this.g = g / 255f;
    this.b = b / 255f;
    a = 1;
  }
  
  public Color(int r, int g, int b, int a) {
    this.r = r / 255f;
    this.g = g / 255f;
    this.b = b / 255f;
    this.a = a / 255f;
  }
  
  public Color(int value) {
    r = (value & 0x00FF0000) >> 16;
    g = (value & 0x0000FF00) >> 8;
    b = value & 0x000000FF;
    a = (value & 0xFF000000) >> 24;
    
    if (a < 0)
      a += 256;
    if (a == 0)
      a = 255;
    
    r = r / 255.0f;
    g = g / 255.0f;
    b = b / 255.0f;
    a = a / 255.0f;
  }
  
  public void bind() {
    GL11.glColor4f(r, g, b, a);
  }
  
  public void darken() {
    darken(.3f);
  }
  
  public void darken(float scale) {
    scale = 1 - scale;
    r *= scale;
    g *= scale;
    b *= scale;
  }
  
  public void brighten() {
    brighten(.2f);
  }
  
  public void brighten(float scale) {
    scale++;
    r *= scale;
    g *= scale;
    b *= scale;
  }
  
  public int getRed() {
    return (int) r * 255;
  }
  
  public int getGreen() {
    return (int) g * 255;
  }
  
  public int getBlue() {
    return (int) b * 255;
  }
  
  public int getAlpha() {
    return (int) a * 255;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Color) {
      Color col = (Color) obj;
      if (col.r == r && col.g == g && col.b == b && col.a == a)
        return true;
    }
    return false;
  }
}
