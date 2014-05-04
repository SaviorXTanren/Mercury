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

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.math.MercMath;
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
    private static final int BYTES_PER_PIXEL = 4;
    private static Texture BLANK_TEXTURE;
    
    private final int textureid, width, height;
    private final BufferedImage buf;
    
    /**
     * Make a texture of the textureid, with a width and height, based off of
     * bufferedimage buf.
     * 
     * @param textureid
     *            The id of the texture.
     * @param width
     *            The width of the texture.
     * @param height
     *            The height of the texture.
     * @param buf
     *            The source of the image, the bufferedimage.
     */
    public Texture(int textureid, int width, int height, BufferedImage buf)
    {
        this.textureid = textureid;
        this.width = width;
        this.height = height;
        this.buf = buf;
    }
    
    /** Binds the texture. */
    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, textureid);
    }
    
    /** Unbinds the texture. */
    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /** @return The texture's width. */
    public int getTextureWidth()
    {
        return width;
    }
    
    /** @return The texture's height. */
    public int getTextureHeight()
    {
        return height;
    }
    
    /** @return The texture's id. */
    public int getTextureId()
    {
        return textureid;
    }
    
    /** @return The source image. */
    public BufferedImage getSourceImage()
    {
        if (buf == null)
            try
            {
                throw new MERCuryException("No source image given.");
            } catch (MERCuryException e)
            {
                e.printStackTrace();
            }
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
    
    /** Staticly 'bind().' */
    public static void bindTexture(Texture tex)
    {
        tex.bind();
    }
    
    /** Unbinds textures */
    public static void unbindTextures()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /** Loads a texture from in */
    public static Texture loadTexture(InputStream in)
    {
        return loadTexture(in, false, false, GL_NEAREST);
    }
    
    /** Loads a texture from bi */
    public static Texture loadTexture(BufferedImage bi)
    {
        return loadTexture(bi, false, false);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert)
    {
        return loadTexture(in, fliphor, flipvert, GL_NEAREST);
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert)
    {
        return loadTexture(bi, fliphor, flipvert, GL_NEAREST);
    }
    
    /**
     * Loads a texture from in, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, int filter) throws IOException
    {
        return loadTexture(in, false, false, filter);
    }
    
    /**
     * Loads a texture from bi, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, int filter)
    {
        return loadTexture(bi, false, false, filter);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert, int filter)
    {
        return loadTexture(in, fliphor, flipvert, 0, filter);
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int filter)
    {
        return loadTexture(bi, fliphor, flipvert, 0, filter);
    }
    
    /**
     * Loads a texture from in, rotating it by rot, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, int rot, int filter)
    {
        return loadTexture(in, false, false, rot, filter);
    }
    
    /**
     * Loads a texture from bi, rotating it by rot, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, int rot, int filter)
    {
        return loadTexture(bi, false, false, rot, filter);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically, rotated by rot, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert, int rot, int filter)
    {
        try
        {
            return loadTexture(ImageIO.read(in), fliphor, flipvert, rot, filter);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically, rotated by rot, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int rot, int filter)
    {
        rot *= -1;
        rot -= 90;
        AffineTransform transform = new AffineTransform();
        transform.rotate(MercMath.toRadians(rot), bi.getWidth() / 2, bi.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        bi = op.filter(bi, null);
        
        int[][] pixels = new int[bi.getWidth()][bi.getHeight()];
        
        for (int y = 0; y < bi.getHeight(); y++)
            for (int x = 0; x < bi.getWidth(); x++)
                pixels[x][y] = bi.getRGB(x, y);
        
        ByteBuffer buffer = BufferUtils.createByteBuffer(bi.getWidth() * bi.getHeight() * BYTES_PER_PIXEL);
        
        rot %= 3;
        
        for (int y = flipvert ? 0 : bi.getHeight() - 1; flipvert ? y < bi.getHeight() : y > -1; y += flipvert ? 1 : -1)
            for (int x = fliphor ? bi.getWidth() - 1 : 0; fliphor ? x > -1 : x < bi.getWidth(); x += fliphor ? -1 : 1)
            {
                int pixel = pixels[y][x];
                
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
        
        return new Texture(textureid, bi.getWidth(), bi.getHeight(), bi);
    }
    
    /** @return A texture object with no data or source image. */
    public static Texture createTextureObject(int textureid, int width, int height)
    {
        return new Texture(textureid, width, height, null);
    }
    
    /** @return A blank, 4x4 white texture. */
    public static Texture getEmptyTexture()
    {
        if (BLANK_TEXTURE == null)
            BLANK_TEXTURE = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/gfx/empty.png"));
        
        return BLANK_TEXTURE;
    }
}