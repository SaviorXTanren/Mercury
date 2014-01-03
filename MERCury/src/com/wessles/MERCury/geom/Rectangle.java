package com.wessles.MERCury.geom;

import com.wessles.MERCury.opengl.Color;

/**
 * A rectangle shape.
 * 
 * @from MERCury in com.wessles.MERCury.geom
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Rectangle extends Shape {
  
  public Rectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
    super(x1, y1, x2, y2, x3, y3, x4, y4);
  }
  
  public Rectangle(Color[] colors, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
    super(colors, new float[] { x1, y1, x2, y2, x3, y3, x4, y4 });
  }
  
  public Rectangle(float x, float y, float w, float h) {
    super(x, y, x + w, y, x + w, y + h, x, y + h);
  }
  
  public Rectangle(Color[] colors, float x, float y, float w, float h) {
    super(colors, new float[] { x, y, x + w, y, x + w, y + h, x, y + h });
  }
  
  @Override
  public float getArea() {
    return getWidth() * getHeight();
  }
  
  @Override
  public boolean intersects(Shape s) {
    return !(nx > s.fx || fx < s.nx || ny > s.fy || fy < s.ny);
  }
  
  @Override
  public boolean contains(Vector2f v) {
    return v.x >= getX1() && v.x <= getX2() && v.y >= getY1() && v.y <= getY2();
  }
}
