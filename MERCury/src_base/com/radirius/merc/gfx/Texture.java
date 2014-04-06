package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.radirius.merc.res.Loader;
import com.radirius.merc.res.Resource;

/**
 * An object version of a texture. This will store the width and height of the
 * object.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class Texture implements Resource
{
    public static final int BYTES_PER_PIXEL = 4;
    public static Texture BLANK_TEXTURE;

    private final BufferedImage buf;
    private final int textureid, filter, width, height;
    private final boolean fliphor, flipvert;

    public Texture(BufferedImage buf, int textureid, boolean fliphor, boolean flipvert, int filter, int width, int height)
    {
        this.textureid = textureid;
        this.fliphor = fliphor;
        this.flipvert = flipvert;
        this.filter = filter;
        this.width = width;
        this.height = height;
        this.buf = buf;
    }

    public Texture filter(int filter)
    {
        return loadTexture(buf, fliphor, flipvert, filter);
    }

    public Texture flipX()
    {
        return loadTexture(buf, !fliphor, flipvert, filter);
    }

    public Texture flipY()
    {
        return loadTexture(buf, fliphor, !flipvert, filter);
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, textureid);
    }

    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getTextureWidth()
    {
        return width;
    }

    public int getTextureHeight()
    {
        return height;
    }

    public int getTextureId()
    {
        return textureid;
    }

    public BufferedImage getSourceImage()
    {
        return buf;
    }

    @Override
    public void clean()
    {
        glDeleteTextures(textureid);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Texture)
        {
            Texture other = (Texture) obj;

            if (other.getTextureHeight() == getTextureHeight() && other.getTextureWidth() == getTextureWidth() && other.getTextureId() == getTextureId())
                return true;
        }

        return false;
    }

    public static void bindTexture(Texture tex)
    {
        tex.bind();
    }

    public static void unbindTextures()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static Texture loadTexture(InputStream in)
    {
        return loadTexture(in, false, false, GL_NEAREST);
    }

    public static Texture loadTexture(BufferedImage bi)
    {
        return loadTexture(bi, false, false);
    }

    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert)
    {
        return loadTexture(in, fliphor, flipvert, GL_NEAREST);
    }

    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert)
    {
        return loadTexture(bi, fliphor, flipvert, GL_NEAREST);
    }

    public static Texture loadTexture(InputStream in, int filter) throws IOException
    {
        return loadTexture(in, false, false, filter);
    }

    public static Texture loadTexture(BufferedImage bi, int filter)
    {
        return loadTexture(bi, false, false, filter);
    }

    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert, int filter)
    {
        try
        {
            return loadTexture(ImageIO.read(in), fliphor, flipvert, filter);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int filter)
    {
        int[][] pixels = new int[bi.getWidth()][bi.getHeight()];

        for (int y = 0; y < bi.getHeight(); y++)
            for (int x = 0; x < bi.getWidth(); x++)
                pixels[x][y] = bi.getRGB(x, y);

        ByteBuffer buffer = BufferUtils.createByteBuffer(bi.getWidth() * bi.getHeight() * BYTES_PER_PIXEL);

        for (int y = flipvert ? bi.getHeight() - 1 : 0; flipvert ? y > -1 : y < bi.getHeight(); y += flipvert ? -1 : 1)
            for (int x = fliphor ? bi.getWidth() - 1 : 0; fliphor ? x > -1 : x < bi.getWidth(); x += fliphor ? -1 : 1)
            {
                int pixel = pixels[x][y];

                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }

        buffer.flip();

        int textureid = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureid);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bi.getWidth(), bi.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        unbindTextures();

        return new Texture(bi, textureid, fliphor, flipvert, filter, bi.getWidth(), bi.getHeight());
    }

    public static Texture getEmptyTexture()
    {
        if (BLANK_TEXTURE == null)
            BLANK_TEXTURE = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/gfx/empty.png"));

        return BLANK_TEXTURE;
    }
}