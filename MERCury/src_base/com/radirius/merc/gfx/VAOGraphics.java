package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glScalef;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.radirius.merc.font.Font;
import com.radirius.merc.font.TrueTypeFont;
import com.radirius.merc.geo.Circle;
import com.radirius.merc.geo.Ellipse;
import com.radirius.merc.geo.Point;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.TexturedRectangle;
import com.radirius.merc.geo.TexturedTriangle;
import com.radirius.merc.geo.Triangle;
import com.radirius.merc.geo.Vec2;
import com.radirius.merc.log.Logger;

/**
 * An object used for graphics. It will draw just about anything for you.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class VAOGraphics implements Graphics
{
    private VAOBatcher batcher;

    private Vec2 scale;
    private Font current_font;
    private Color background_color;
    private Color current_color;

    // drawMode(false) = filled, drawMode(true) = line

    @Override
    public void init()
    {
        batcher = new VAOBatcher();
        scale = new Vec2(1, 1);

        try
        {
            current_font = TrueTypeFont.loadTrueTypeFont("com/radirius/merc/gfx/OpenSans-Semibold.ttf", 20, 1, true);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (FontFormatException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        background_color = Color.DEFAULT_BACKGROUND;
        current_color = Color.DEFAULT_DRAWING;
    }

    @Override
    public void pre()
    {
        batcher.begin();

        GL11.glClearColor(background_color.r, background_color.g, background_color.b, background_color.a);
    }

    @Override
    public void post()
    {
        batcher.end();
    }

    @Override
    public void setDrawMode(int mode)
    {
        if (mode == FILLED)
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        else
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    @Override
    public void scale(float factor)
    {
        scale(factor, factor);
    }

    @Override
    public void scale(float x, float y)
    {
        glScalef(x, y, 1);
        scale.x = x;
        scale.y = y;

        Logger.debug(x + ", " + y + " | " + scale.toString());
    }

    @Override
    public Vec2 getScale()
    {
        return scale;
    }

    @Override
    public void setFont(Font font)
    {
        current_font = font;
    }

    @Override
    public Font getFont()
    {
        return current_font;
    }

    @Override
    public void setBackground(Color color)
    {
        background_color = color;
    }

    @Override
    public Color getBackground()
    {
        return background_color;
    }

    @Override
    public void setColor(Color color)
    {
        current_color = color;

        batcher.setColor(current_color);
    }

    @Override
    public Color getColor()
    {
        return current_color;
    }

    @Override
    public void useShader(Shader shad)
    {
        batcher.setShader(shad);
    }

    @Override
    public void releaseShaders()
    {
        batcher.clearShaders();
    }

    @Override
    public Batcher getBatcher()
    {
        return batcher;
    }

    @Override
    public void drawRawVertices(VAOBatcher.VertexData... verts)
    {
        if (verts.length % 3 != 0)
            throw new IllegalArgumentException("Vertices must be in multiples of 3!");

        for (VAOBatcher.VertexData vdo : verts)
            batcher.vertex(vdo);
    }

    @Override
    public void drawString(float x, float y, String what)
    {
        drawString(current_font, x, y, what);
    }

    @Override
    public void drawString(Font font, float x, float y, String what)
    {
        drawString(font, x, y, 1, what);
    }

    @Override
    public void drawString(Font font, float x, float y, float sizemult, String what)
    {
        if (font instanceof TrueTypeFont)
        {
            TrueTypeFont jf = (TrueTypeFont) font;

            int current_x = 0;

            for (int ci = 0; ci < what.toCharArray().length; ci++)
            {
                if (what.toCharArray()[ci] == '\n')
                {
                    y += jf.getLineHeight();
                    current_x = 0;
                }

                TrueTypeFont.IntObject intobj = jf.chars[what.toCharArray()[ci]];

                drawTexture(jf.font_tex, intobj.x, intobj.y, intobj.x + intobj.w, intobj.y + intobj.h, x + current_x, y, x + current_x + intobj.w * sizemult, y + intobj.h * sizemult);
                current_x += intobj.w * sizemult;
            }
        }
    }

    @Override
    public void drawTexture(Texture texture, float x, float y)
    {
        float w = texture.getTextureWidth();
        float h = texture.getTextureHeight();

        batcher.setTexture(texture);

        batcher.vertex(x, y, 0, 0);
        batcher.vertex(x + w, y, 1, 0);
        batcher.vertex(x, y + h, 0, 1);

        batcher.vertex(x + w, y + h, 1, 1);
        batcher.vertex(x + w, y, 1, 0);
        batcher.vertex(x, y + h, 0, 1);
    }

    @Override
    public void drawTexture(Texture texture, float x, float y, float w, float h)
    {
        batcher.setTexture(texture);

        batcher.vertex(x, y, 0, 0);
        batcher.vertex(x + w, y, 1, 0);
        batcher.vertex(x, y + h, 0, 1);

        batcher.vertex(x + w, y + h, 1, 1);
        batcher.vertex(x + w, y, 1, 0);
        batcher.vertex(x, y + h, 0, 1);
    }

    @Override
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y)
    {
        float w = texture.getTextureWidth();
        float h = texture.getTextureHeight();

        sx1 /= w;
        sy1 /= h;
        sx2 /= w;
        sy2 /= h;
        w *= sx2 - sx1;
        h *= sy2 - sy1;

        batcher.setTexture(texture);

        batcher.vertex(x, y, sx1, sy1);
        batcher.vertex(x + w, y, sx2, sy1);
        batcher.vertex(x, y + h, sx1, sy2);

        batcher.vertex(x + w, y + h, sx2, sy2);
        batcher.vertex(x + w, y, sx2, sy1);
        batcher.vertex(x, y + h, sx1, sy2);
    }

    @Override
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x1, float y1, float x2, float y2)
    {
        float w = texture.getTextureWidth();
        float h = texture.getTextureHeight();
        sx1 /= w;
        sy1 /= h;
        sx2 /= w;
        sy2 /= h;

        batcher.setTexture(texture);

        batcher.vertex(x1, y1, sx1, sy1);
        batcher.vertex(x2, y1, sx2, sy1);
        batcher.vertex(x1, y2, sx1, sy2);

        batcher.vertex(x2, y2, sx2, sy2);
        batcher.vertex(x2, y1, sx2, sy1);
        batcher.vertex(x1, y2, sx1, sy2);
    }

    @Override
    public void drawTexture(SubTexture texture, float x, float y)
    {
        float w = texture.getSheet().getSheetTexture().getTextureWidth() / texture.getSheet().getNumTextures();
        float h = texture.getSheet().getSheetTexture().getTextureHeight() / texture.getSheet().getNumTextures();

        batcher.setTexture(texture.getSheet().getSheetTexture());

        batcher.vertex(x, y, texture.getX(), texture.getY());
        batcher.vertex(x + w, y, texture.getX() + texture.getSize(), texture.getY());
        batcher.vertex(x, y + h, texture.getX(), texture.getY() + texture.getSize());

        batcher.vertex(x + w, y + h, texture.getX() + texture.getSize(), texture.getY() + texture.getSize());
        batcher.vertex(x + w, y, texture.getX() + texture.getSize(), texture.getY());
        batcher.vertex(x, y + h, texture.getX(), texture.getY() + texture.getSize());
    }

    @Override
    public void drawTexture(SubTexture texture, float x, float y, float w, float h)
    {
        batcher.setTexture(texture.getSheet().getSheetTexture());

        batcher.vertex(x, y, texture.getX(), texture.getY());
        batcher.vertex(x + w, y, texture.getX() + texture.getSize(), texture.getY());
        batcher.vertex(x, y + h, texture.getX(), texture.getY() + texture.getSize());

        batcher.vertex(x + w, y + h, texture.getX() + texture.getSize(), texture.getY() + texture.getSize());
        batcher.vertex(x + w, y, texture.getX() + texture.getSize(), texture.getY());
        batcher.vertex(x, y + h, texture.getX(), texture.getY() + texture.getSize());
    }

    @Override
    public void drawRect(Rectangle rectangle)
    {
        float x1 = rectangle.getVertices()[0].x;
        float y1 = rectangle.getVertices()[0].y;
        float x2 = rectangle.getVertices()[1].x;
        float y2 = rectangle.getVertices()[1].y;
        float x3 = rectangle.getVertices()[2].x;
        float y3 = rectangle.getVertices()[2].y;
        float x4 = rectangle.getVertices()[3].x;
        float y4 = rectangle.getVertices()[3].y;

        if (!(rectangle instanceof TexturedRectangle))
            batcher.clearTextures();
        else
        {
            TexturedRectangle texrect = (TexturedRectangle) rectangle;
            batcher.setTexture(texrect.getTexture());
        }

        batcher.vertex(x1, y1, 0, 0);
        batcher.vertex(x2, y2, 1, 0);
        batcher.vertex(x4, y4, 0, 1);

        batcher.vertex(x3, y3, 1, 1);
        batcher.vertex(x4, y4, 0, 1);
        batcher.vertex(x2, y2, 1, 0);
    }

    @Override
    public void drawRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
    {
        drawRect(new Rectangle(x1, y1, x2, y2, x3, y3, x4, y4));
    }

    @Override
    public void drawRects(Rectangle[] rects)
    {
        for (Rectangle rect : rects)
            drawRect(rect);
    }

    @Override
    public void drawTriangle(Triangle triangle)
    {
        float x1 = triangle.getVertices()[0].x;
        float y1 = triangle.getVertices()[0].y;
        float x2 = triangle.getVertices()[1].x;
        float y2 = triangle.getVertices()[1].y;
        float x3 = triangle.getVertices()[2].x;
        float y3 = triangle.getVertices()[2].y;

        if (!(triangle instanceof TexturedTriangle))
            batcher.clearTextures();
        else
        {
            TexturedTriangle texrect = (TexturedTriangle) triangle;
            batcher.setTexture(texrect.getTexture());
        }

        batcher.vertex(x1, y1, 0, 0);
        batcher.vertex(x3, y3, 1, 0);
        batcher.vertex(x2, y2, 0, 1);
    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        drawTriangle(new Triangle(x1, y1, x2, y2, x3, y3));
    }

    @Override
    public void drawTriangles(Triangle[] triangles)
    {
        for (Triangle triangle : triangles)
            drawTriangle(triangle);
    }

    @Override
    public void drawEllipse(Ellipse ellipse)
    {
        batcher.clearTextures();

        Vec2[] vs = ellipse.getVertices();

        for (int c = 0; c < vs.length; c++)
        {
            batcher.vertex(ellipse.getCenterX(), ellipse.getCenterY(), 0, 0);

            if (c >= vs.length - 1)
                batcher.vertex(vs[0].x, vs[0].y, 0, 0);
            else
                batcher.vertex(vs[c].x, vs[c].y, 0, 0);

            if (c >= vs.length - 1)
                batcher.vertex(vs[vs.length - 1].x, vs[vs.length - 1].y, 0, 0);
            else
                batcher.vertex(vs[c + 1].x, vs[c + 1].y, 0, 0);
        }
    }

    @Override
    public void drawEllipse(float x, float y, float radx, float rady)
    {
        drawEllipse(new Ellipse(x, y, radx, rady));
    }

    @Override
    public void drawEllipses(Ellipse[] ellipses)
    {
        for (Ellipse ellipse : ellipses)
            drawEllipse(ellipse);
    }

    @Override
    public void drawCircle(Circle circle)
    {
        drawEllipse(circle);
    }

    @Override
    public void drawCircle(float x, float y, float radius)
    {
        drawCircle(new Circle(x, y, radius));
    }

    @Override
    public void drawCircles(Circle[] circs)
    {
        for (Circle circ : circs)
            drawCircle(circ);
    }

    @Override
    public void drawPoint(Point point)
    {
        float x = point.getX1();
        float y = point.getY1();
        drawRect(new Rectangle(x, y, x + 1, y, x + 1, y + 1, x, y + 1));
    }

    @Override
    public void drawPoint(float x, float y)
    {
        drawPoint(new Point(x, y));
    }

    @Override
    public void drawPoints(Point[] points)
    {
        for (Point point : points)
            drawPoint(point);
    }
}
