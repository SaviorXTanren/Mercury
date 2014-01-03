package com.wessles.MERCury.opengl;

import com.wessles.MERCury.font.Font;
import com.wessles.MERCury.geom.Circle;
import com.wessles.MERCury.geom.Ellipse;
import com.wessles.MERCury.geom.Point;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.geom.Triangle;

/**
 * An abstraction for all graphics.
 * 
 * @from MERCury in com.wessles.MERCury.opengl
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public interface Graphics {
  public void init();
  
  public void pre();
  
  public void post();
  
  public void scale(float factor);
  
  public void scale(float x, float y);
  
  public void setFont(Font font);
  
  public Font getFont();
  
  public void setBackground(Color col);
  
  public void setColor(Color color);
  
  public void useShader(Shader shader);
  
  public void releaseShaders();
  
  public Batcher getBatcher();
  
  public void drawRawVertices(VAOBatcher.VertexData... verts);
  
  public void drawString(float x, float y, String what);
  
  public void drawString(Font font, float x, float y, String what);
  
  public void drawString(Font font, float x, float y, float sizemult, String what);
  
  public void drawTexture(Texture texture, float x, float y);
  
  public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y);
  
  public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x1, float y1, float x2, float y2);
  
  public void drawRect(Rectangle rectangle);
  
  public void drawRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
  
  public void drawRects(Rectangle[] rects);
  
  public void drawTriangle(Triangle triangle);
  
  public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3);
  
  public void drawTriangles(Triangle[] triangles);
  
  public void drawEllipse(Ellipse ellipse);
  
  public void drawEllipse(float x, float y, float radx, float rady);
  
  public void drawEllipses(Ellipse[] ellipses);
  
  public void drawCircle(Circle circle);
  
  public void drawCircle(float x, float y, float radius);
  
  public void drawCircles(Circle[] circles);
  
  public void drawPoint(Point point);
  
  public void drawPoint(float x, float y);
  
  public void drawPoints(Point[] points);
}
