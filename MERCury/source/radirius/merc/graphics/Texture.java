package radirius.merc.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import radirius.merc.exceptions.MERCuryException;
import radirius.merc.maths.MercMath;
import radirius.merc.resource.Resource;

/**
 * An object version of a texture. This will store the width and height of the
 * object.
 * 
 * @author wessles
 */

public class Texture implements Resource {
    public static final int FILTER_NEAREST = GL_NEAREST, FILTER_LINEAR = GL_LINEAR, FILTER_PIXELART = FILTER_NEAREST;
    
    protected static final int BYTES_PER_PIXEL = 4;
    protected static Texture BLANK_TEXTURE;
    
    public int textureid;
    public int width;
    public int height;
    public BufferedImage bufimage;
    public ByteBuffer buf;
    
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
     * @param bufimage
     *            The source of the image, the bufferedimage.
     * @param buf
     *            The original buffer.
     */
    public Texture(int textureid, int width, int height, BufferedImage bufimage, ByteBuffer buf) {
        this.textureid = textureid;
        this.width = width;
        this.height = height;
        
        this.bufimage = bufimage;
        this.buf = buf;
    }
    
    /** Binds the texture. */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureid);
    }
    
    /** Unbinds the texture. */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /** @return The texture's width. */
    public int getWidth() {
        return width;
    }
    
    /** @return The texture's height. */
    public int getHeight() {
        return height;
    }
    
    /** @return Whether or not the texture is PoT. */
    public boolean isPoT() {
        return isPoT(getWidth(), getHeight());
    }
    
    /** @return The texture's id. */
    public int getTextureId() {
        return textureid;
    }
    
    /** @return The source image. */
    public BufferedImage getSourceImage() {
        if (bufimage == null)
            try {
                throw new MERCuryException("No source image given.");
            } catch (MERCuryException e) {
                e.printStackTrace();
            }
        return bufimage;
    }
    
    /** @return The original buffer. */
    public ByteBuffer getBuffer() {
        return buf;
    }
    
    /** @return If the Texture is PoT and not a SubTexture. */
    public boolean fullCapabilities() {
        boolean capable = isPoT();
        if (this instanceof SubTexture) {
            SubTexture subthis = (SubTexture) this;
            capable = capable && subthis.getWidth() == subthis.getParentWidth() && subthis.getHeight() == subthis.getParentHeight();
        }
        return capable;
    }
    
    @Override
    public void clean() {
        glDeleteTextures(textureid);
    }
    
    /** Staticly 'bind().' */
    public static void bindTexture(Texture tex) {
        tex.bind();
    }
    
    /** Unbinds textures */
    public static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    
    /** Loads a texture from in */
    public static Texture loadTexture(InputStream in) {
        return loadTexture(in, false, false, GL_NEAREST);
    }
    
    /** Loads a texture from bi */
    public static Texture loadTexture(BufferedImage bi) {
        return loadTexture(bi, false, false);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert) {
        return loadTexture(in, fliphor, flipvert, GL_NEAREST);
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert) {
        return loadTexture(bi, fliphor, flipvert, GL_NEAREST);
    }
    
    /**
     * Loads a texture from in, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, int filter) {
        return loadTexture(in, false, false, filter);
    }
    
    /**
     * Loads a texture from bi, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, int filter) {
        return loadTexture(bi, false, false, filter);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert, int filter) {
        return loadTexture(in, fliphor, flipvert, 0, filter);
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int filter) {
        return loadTexture(bi, fliphor, flipvert, 0, filter);
    }
    
    /**
     * Loads a texture from in, rotating it by rot, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, int rot, int filter) {
        return loadTexture(in, false, false, rot, filter);
    }
    
    /**
     * Loads a texture from bi, rotating it by rot, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, int rot, int filter) {
        return loadTexture(bi, false, false, rot, filter);
    }
    
    /**
     * Loads a texture from in, flipping it depending on fliphor horizontally,
     * and flipvert vertically, rotated by rot, filtered through filter.
     */
    public static Texture loadTexture(InputStream in, boolean fliphor, boolean flipvert, int rot, int filter) {
        try {
            return loadTexture(ImageIO.read(in), fliphor, flipvert, rot, filter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Loads a texture from bi, flipping it depending on fliphor horizontally,
     * and flipvert vertically, rotated by rot, filtered through filter.
     */
    public static Texture loadTexture(BufferedImage bi, boolean fliphor, boolean flipvert, int rot, int filter) {
        BufferedImage _bi = processBufferedImage(bi, fliphor, flipvert, rot);
        
        ByteBuffer buffer = convertBufferedImageToBuffer(_bi, false, true);
        
        int textureid = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureid);
        
        // Set the parameters for filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
        
        // Push all buffer data into the now configured OGL.
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, _bi.getWidth(), _bi.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
        // Unbind, so that this texture is not being rendered when you want to
        // draw a square!
        unbindTextures();
        
        return new SubTexture(new Texture(textureid, _bi.getWidth(), _bi.getHeight(), bi, buffer), 0, 0, bi.getWidth(), bi.getHeight());
    }
    
    public static BufferedImage processBufferedImage(BufferedImage bi, boolean fliphor, boolean flipvert, int rot) {
        // Rotate the bufferedimage
        if (rot != 0) {
            rot *= -1;
            rot -= 90;
            AffineTransform transform = new AffineTransform();
            transform.rotate(MercMath.toRadians(rot), bi.getWidth() / 2, bi.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            bi = op.filter(bi, null);
        }
        
        // Flip the bufferedimage
        if (fliphor || flipvert) {
            AffineTransform tx = new AffineTransform();
            tx.scale(fliphor ? -1 : 1, flipvert ? -1 : 1);
            tx.translate(fliphor ? -bi.getWidth() : 0, flipvert ? -bi.getHeight() : 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bi = op.filter(bi, null);
        }
        
        // Power of two stuffs! This is actually kind of a wierd problem with
        // OpenGL, but it has to do with speedy-thingies.
        boolean PoT = isPoT(bi.getWidth(), bi.getHeight());
        
        BufferedImage _bi = bi;
        
        // Power of two expansion; we will set the width and height to their
        // nearest larger PoT, since PoT is way faster :
        if (!PoT && expand2PoT) {
            int newwidth = 0, newheight = 0;
            boolean done = false;
            for (int power = 1; !done; power += 1) {
                int pot = (int) Math.pow(2, power);
                
                if (newwidth == 0)
                    if (pot >= bi.getWidth())
                        newwidth = pot;
                
                if (newheight == 0)
                    if (pot >= bi.getHeight())
                        newheight = pot;
                
                done = newwidth != 0 && newheight != 0;
            }
            
            BufferedImage newbi = new BufferedImage(newwidth, newheight, bi.getType());
            
            newbi.getGraphics().drawImage(bi, fliphor ? newbi.getWidth() - bi.getWidth() : 0, flipvert ? newbi.getHeight() - bi.getHeight() : 0, null);
            _bi = newbi;
        }
        
        return _bi;
    }
    
    public static ByteBuffer convertBufferedImageToBuffer(BufferedImage _bi, boolean fliphor, boolean flipvert) {
        // A buffer to store with bufferedimage data and throw into LWJGL
        ByteBuffer buffer = BufferUtils.createByteBuffer(_bi.getWidth() * _bi.getHeight() * BYTES_PER_PIXEL);
        
        for (int y = flipvert ? _bi.getHeight() - 1 : 0; flipvert ? y > -1 : y < _bi.getHeight(); y += flipvert ? -1 : 1)
            for (int x = fliphor ? _bi.getWidth() - 1 : 0; fliphor ? x > -1 : x < _bi.getWidth(); x += fliphor ? -1 : 1) {
                int pixel = _bi.getRGB(x, y);
                
                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) (pixel >> 24 & 0xFF));
            }
        
        buffer.flip();
        
        return buffer;
    }
    
    public static ByteBuffer convertBufferedImageToBuffer(BufferedImage _bi) {
        return convertBufferedImageToBuffer(_bi, false, false);
    }
    
    public static boolean isPoT(int width, int height) {
        return (width & width - 1) == 0 && (height & height - 1) == 0;
    }
    
    /** @return A texture object with no data or source image. */
    public static Texture createTextureObject(int textureid, int width, int height) {
        return new Texture(textureid, width, height, null, null);
    }
    
    /** @return The default no-texture of OGL. */
    public static Texture getEmptyTexture() {
        if (BLANK_TEXTURE == null)
            BLANK_TEXTURE = new Texture(0, 0, 0, null, null);
        
        return BLANK_TEXTURE;
    }
    
    private static boolean expand2PoT = true;
    
    /**
     * Sets whether or not all Textures will be expanded to the nearest power of
     * two. Keep in mind before you change this: PoT Textures allow for great
     * things like GL_REPEAT, and faster rendering time. Certain aspect of the
     * library may break, should you choose to mess with this value. Seriously,
     * you can use the non-PoT SubTextures in much the same for the most part.
     * 
     * Mess with at your own risk.
     */
    public static void setExpandToPowerOfTwo(boolean expand2PoT) {
        Texture.expand2PoT = expand2PoT;
    }
}