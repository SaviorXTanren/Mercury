package com.radirius.merc.gfx;

import com.radirius.merc.font.Font;
import com.radirius.merc.geo.Circle;
import com.radirius.merc.geo.Ellipse;
import com.radirius.merc.geo.Point;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.Triangle;
import com.radirius.merc.geo.Vec2;

/**
 * An abstraction for all graphics.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public interface Graphics
{
    public static final int LINE = 0, FILLED = 1;
    
    /** Initializes the graphics object. */
    public void init();
    
    /** Prepares the graphics object for rendering. */
    public void pre();
    
    /** Cleans up the graphics object at the end of rendering. */
    public void post();
    
    /** Sets the drawing mode. */
    public void setDrawMode(int mode);
    
    /** Scales the matrix. */
    public void scale(float factor);
    
    /**
     * Scales the matrix.
     * 
     * @param x
     *            The scale factor of x
     * @param y
     *            The scale factor of y
     */
    public void scale(float x, float y);
    
    /** @return The last scale that was set in the graphics object. */
    public Vec2 getScale();
    
    /** Sets the font. */
    public void setFont(Font font);
    
    /** @return The last set font. */
    public Font getFont();
    
    /** Sets the background color. */
    public void setBackground(Color col);
    
    /** @return The background color. */
    public Color getBackground();
    
    /** Sets the color. */
    public void setColor(Color color);
    
    /** @return The last set color. */
    public Color getColor();
    
    /** Uses a shader. */
    public void useShader(Shader shader);
    
    /** Releases shaders. */
    public void releaseShaders();
    
    /** @return The batcher. */
    public Batcher getBatcher();
    
    /** Pushes raw vertices to the batcher. */
    public void drawRawVertices(VAOBatcher.VertexData... verts);
    
    /** Pushes data in batcher to OGL. */
    public void flush();
    
    /** Draws msg at x and y. */
    public void drawString(float x, float y, String msg);
    
    /** Draws msg at x and y with font. */
    public void drawString(Font font, float x, float y, String msg);
    
    /** Draws texture at x and y. */
    public void drawTexture(Texture texture, float x, float y);
    
    /** Draws texture at x and y, at a width and height. */
    public void drawTexture(Texture texture, float x, float y, float w, float h);
    
    /** Draws a portion of the texture at x and y. */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y);
    
    /** Draws a portion of the texture at x1 and y1, to x2 and y2. */
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x1, float y1, float x2, float y2);
    
    /** Draws a subtexture at x and y. */
    public void drawTexture(SubTexture texture, float x, float y);
    
    /** Draws a subtexture at x and y with a size. */
    public void drawTexture(SubTexture texture, float x, float y, float size);
    
    /** Draws a subtexture at x and y with a width and height. */
    public void drawTexture(SubTexture texture, float x, float y, float w, float h);
    
    /** Draws a rectangle. */
    public void drawRect(Rectangle rectangle);
    
    /** Makes and draws a rectangle. */
    public void drawRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
    
    /** Draws multiple rectangles. */
    public void drawRects(Rectangle[] rects);
    
    /** Draws a triangle. */
    public void drawTriangle(Triangle triangle);
    
    /** Makes and draws a triangle. */
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3);
    
    /** Draws multiple triangles. */
    public void drawTriangles(Triangle[] triangles);
    
    /** Draws an ellipse. */
    public void drawEllipse(Ellipse ellipse);
    
    /** Makes and draws an ellipse. */
    public void drawEllipse(float x, float y, float radx, float rady);
    
    /** Draws multiple ellipses. */
    public void drawEllipses(Ellipse[] ellipses);
    
    /** Draws a circle. */
    public void drawCircle(Circle circle);
    
    /** Makes and draws a circle. */
    public void drawCircle(float x, float y, float radius);
    
    /** Draws multiple circles. */
    public void drawCircles(Circle[] circles);
    
    /** Draws a point. */
    public void drawPoint(Point point);
    
    /** Makes and draws a point. */
    public void drawPoint(float x, float y);
    
    /** Draws multiple points. */
    public void drawPoints(Point[] points);
}
