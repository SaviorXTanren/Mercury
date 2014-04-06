package com.radirius.merc.gfx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.TexturedRectangle;
import com.radirius.merc.res.Resource;
import com.radirius.merc.util.TextureFactory;

/**
 * An easy to use animation class. Just render, and watch the moving picture.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class Animation implements Resource
{
    private Texture[] texs;
    private int frame = 0, frameratemillis;
    private long framemillis = 0, lastframemillis;
    private float w, h;

    public Animation(int frameratemillis, Texture... texs)
    {
        if (texs.length == 0)
            throw new IllegalArgumentException("Must be at least 1 texture!");

        this.frameratemillis = frameratemillis;
        this.texs = texs;
        w = 0;
        h = 0;

        for (Texture tex : this.texs)
        {
            if (tex.getTextureWidth() > w)
                w = tex.getTextureWidth();
            if (tex.getTextureHeight() > h)
                h = tex.getTextureHeight();
        }

        frame = 0;
    }

    public Animation(float w, float h, int frameratemillis, Texture... texs)
    {
        if (texs.length == 0)
            throw new IllegalArgumentException("Must be at least 1 texture!");

        this.frameratemillis = frameratemillis;
        this.texs = texs;
        this.w = w;
        this.h = h;

        frame = 0;
    }

    public void reverse()
    {
        for (int f = 0; f < texs.length; f++)
            texs[texs.length - 1 - f] = texs[f];
        frame = texs.length - 1 - frame;
    }

    public boolean render(float x, float y, Graphics g)
    {
        return render(x, y, getAnimationWidth(), getAnimationHeight(), g);
    }

    public boolean render(float x, float y, float w, float h, Graphics g)
    {
        return render(x, y, x + w, y, x + w, y + h, x, y + h, g);
    }

    /**
     * @return whether or not this is the last frame.
     */
    public boolean render(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Graphics g)
    {
        g.drawRect(new TexturedRectangle(new Rectangle(x1, y1, x2, y2, x3, y3, x4, y4), texs[frame]));

        framemillis = System.currentTimeMillis();

        if (framemillis - lastframemillis >= frameratemillis)
        {

            if (frame < texs.length - 1)
                frame++;
            else
                frame = 0;

            lastframemillis = System.currentTimeMillis();
            return frame == 0;
        }
        return false;
    }

    /**
     * @return whether or not this is the last frame.
     */
    public boolean render(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float origx, float origy, float rot, Graphics g)
    {
        TexturedRectangle render = new TexturedRectangle(new Rectangle(x1, y1, x2, y2, x3, y3, x4, y4), texs[frame]);
        render.rotate(origx, origy, rot);
        g.drawRect(render);

        framemillis = System.currentTimeMillis();

        if (framemillis - lastframemillis >= frameratemillis)
        {

            if (frame < texs.length - 1)
                frame++;
            else
                frame = 0;

            lastframemillis = System.currentTimeMillis();
            return frame == 0;
        }
        return false;
    }

    public void setFrame(int frame)
    {
        this.frame = frame;
    }

    public int getFrame()
    {
        return frame;
    }

    public int getLength()
    {
        return texs.length;
    }

    public int getAnimationWidth()
    {
        return getTextures()[0].getTextureWidth();
    }

    public int getAnimationHeight()
    {
        return getTextures()[0].getTextureHeight();
    }

    public boolean isLastFrame()
    {
        return frame == texs.length - 1;
    }

    public Texture[] getTextures()
    {
        return texs;
    }

    public static Animation loadAnimationFromStrip(InputStream in, int divwidth, int frameratemillis) throws FileNotFoundException, IOException
    {
        return new Animation(frameratemillis, TextureFactory.getTextureStrip(in, divwidth));
    }

    public static Animation loadAnimationFromGrid(InputStream in, int divwidth, int divheight, int frameratemillis) throws FileNotFoundException, IOException
    {
        Texture[][] texs_g = TextureFactory.getTextureGrid(in, divwidth, divheight);
        Texture[] texs_s = new Texture[texs_g.length * texs_g[0].length];
        int cnt = 0;

        for (Texture[] element : texs_g)
            for (int y = 0; y < texs_g[0].length; y++)
            {
                texs_s[cnt] = element[y];
                cnt++;
            }

        return new Animation(frameratemillis, texs_s);
    }

    @Override
    public void clean()
    {
        for (Texture tex : texs)
            tex.clean();
        texs = null;
    }
}
