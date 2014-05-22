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
import com.radirius.merc.log.Logger;
import com.radirius.merc.math.MercMath;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.Resource;

/**
 * An object version of a texture. This will store the width and height of the
 * object.
 * 
 * @author wessles
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
        if (rot != 0)
        {
            rot *= -1;
            rot -= 90;
            AffineTransform transform = new AffineTransform();
            transform.rotate(MercMath.toRadians(rot), bi.getWidth() / 2, bi.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            bi = op.filter(bi, null);
        }

        if (fliphor || flipvert)
        {
            AffineTransform tx = AffineTransform.getScaleInstance(fliphor ? -1 : 1, flipvert ? -1 : 1);
            tx.translate(-bi.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bi = op.filter(bi, null);
        }

        ByteBuffer buffer = BufferUtils.createByteBuffer(bi.getWidth() * bi.getHeight() * BYTES_PER_PIXEL);

        for (int y = 0; y < bi.getHeight(); y += 1)
            for (int x = 0; x < bi.getWidth(); x += 1)
            {
                int pixel = bi.getRGB(x, y);

                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }

        buffer.flip();

        int textureid = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureid);

        // Power of two stuffs!
        boolean PoT = false;
        PoT = ((bi.getWidth() & (bi.getWidth() - 1)) == 0);
        PoT = PoT && ((bi.getHeight() & (bi.getHeight() - 1)) == 0);

        if (!PoT)
            Logger.warn("The provided Texture is NPoT (non power of two). It is highly reccomended that you only use PoT Textures for the full potential of Textures!");

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