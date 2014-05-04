package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.*;

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
    public static final int BYTES_PER_PIXEL = 4;
    public static Texture BLANK_TEXTURE;
    
    private final int textureid, width, height;
    private final BufferedImage buf;
    
    public Texture(int textureid, int width, int height, BufferedImage buf)
    {
        this.textureid = textureid;
        this.width = width;
        this.height = height;
        this.buf = buf;
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
        return loadTexture(in, fliphor, flipvert, 0, filter);
    }
    
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int filter)
    {
        return loadTexture(bi, fliphor, flipvert, 0, filter);
    }
    
    public static Texture loadTexture(InputStream in, int rot, int filter)
    {
        return loadTexture(in, false, false, rot, filter);
    }
    
    public static Texture loadTexture(BufferedImage bi, int rot, int filter)
    {
        return loadTexture(bi, false, false, rot, filter);
    }
    
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
    
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int rot, int filter)
    {
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
    
    public static Texture createTextureObject(int textureid, int width, int height)
    {
        return new Texture(textureid, width, height, null);
    }
    
    public static Texture getEmptyTexture()
    {
        if (BLANK_TEXTURE == null)
            BLANK_TEXTURE = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/gfx/empty.png"));
        
        return BLANK_TEXTURE;
    }
}