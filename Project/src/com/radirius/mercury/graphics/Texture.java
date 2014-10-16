package com.radirius.mercury.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.resource.*;

/**
 * An object version of a texture. This will store the width and height of the
 * object.
 *
 * @author wessles, Jeviny
 */
public class Texture implements Resource {
	public static final int FILTER_NEAREST = GL_NEAREST, FILTER_LINEAR = GL_LINEAR;

	protected static final int BYTES_PER_PIXEL = 4;
	protected static Texture BLANK_TEXTURE;

	public int textureId;
	public int width;
	public int height;
	
	public BufferedImage bufferedImage;
	public ByteBuffer buffer;

	/**
	 * Make a texture of the textureId, with a width and height, based off of
	 * bufferedimage buf.
	 *
	 * @param textureId
	 *            The id of the texture.
	 * @param width
	 *            The width of the texture.
	 * @param height
	 *            The height of the texture.
	 * @param bufferedImage
	 *            The source of the image, the BufferedImage.
	 * @param buffer
	 *            The original buffer.
	 */
	public Texture(int textureId, int width, int height, BufferedImage bufferedImage, ByteBuffer buffer) {
		this.textureId = textureId;
		this.width = width;
		this.height = height;

		this.bufferedImage = bufferedImage;
		this.buffer = buffer;
	}

	/** Binds the texture. */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureId);
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
		return textureId;
	}

	/** @return The source image. */
	public BufferedImage getSourceImage() {
		if (bufferedImage == null) {
			try {
				throw new MercuryException("No source image given.");
			} catch (MercuryException e) {
				e.printStackTrace();
			}
		}
		
		return bufferedImage;
	}

	/** @return The original buffer. */
	public ByteBuffer getBuffer() {
		return buffer;
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
		glDeleteTextures(textureId);
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
	public static Texture loadTexture(InputStream inputStream) {
		return loadTexture(inputStream, false, false, GL_NEAREST);
	}

	/** Loads a texture from bi */
	public static Texture loadTexture(BufferedImage bufferedImage) {
		return loadTexture(bufferedImage, false, false);
	}

	/**
	 * Loads a texture from in, flipping it depending on flipX horizontally,
	 * and flipY vertically.
	 */
	public static Texture loadTexture(InputStream inputStream, boolean flipX, boolean flipY) {
		return loadTexture(inputStream, flipX, flipY, GL_NEAREST);
	}

	/**
	 * Loads a texture from bi, flipping it depending on flipX horizontally,
	 * and flipY vertically.
	 */
	public static Texture loadTexture(BufferedImage bufferedImage, boolean flipX, boolean flipY) {
		return loadTexture(bufferedImage, flipX, flipY, GL_NEAREST);
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
	public static Texture loadTexture(BufferedImage bufferedImage, int filter) {
		return loadTexture(bufferedImage, false, false, filter);
	}

	/**
	 * Loads a texture from in, flipping it depending on flipX horizontally,
	 * and flipY vertically, filtered through filter.
	 */
	public static Texture loadTexture(InputStream in, boolean flipX, boolean flipY, int filter) {
		return loadTexture(in, flipX, flipY, 0, filter);
	}

	/**
	 * Loads a texture from bi, flipping it depending on flipX horizontally,
	 * and flipY vertically, filtered through filter.
	 */
	public static Texture loadTexture(BufferedImage bufferedImage, boolean flipX, boolean flipY, int filter) {
		return loadTexture(bufferedImage, flipX, flipY, 0, filter);
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
	public static Texture loadTexture(BufferedImage bufferedImage, int rot, int filter) {
		return loadTexture(bufferedImage, false, false, rot, filter);
	}

	/**
	 * Loads a texture from in, flipping it depending on flipX horizontally,
	 * and flipY vertically, rotated by rot, filtered through filter.
	 */
	public static Texture loadTexture(InputStream in, boolean flipX, boolean flipY, int rot, int filter) {
		try {
			return loadTexture(ImageIO.read(in), flipX, flipY, rot, filter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Loads a texture from bi, flipping it depending on flipX horizontally,
	 * and flipY vertically, rotated by rot, filtered through filter.
	 */
	public static Texture loadTexture(BufferedImage bufferedImage, boolean flipX, boolean flipY, int rot, int filter) {
		BufferedImage bufferedImage0 = processBufferedImage(bufferedImage, flipX, flipY, rot);

		ByteBuffer buffer = convertBufferedImageToBuffer(bufferedImage0, false, true);

		int textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);

		// Set the parameters for filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

		// Push all buffer data into the now configured OpenGL.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bufferedImage0.getWidth(), bufferedImage0.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		// Unbind, so that this texture is not being rendered when you want to
		// draw a square!
		unbindTextures();

		return new SubTexture(new Texture(textureId, bufferedImage0.getWidth(), bufferedImage0.getHeight(), bufferedImage, buffer), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	public static BufferedImage processBufferedImage(BufferedImage bufferedImage, boolean flipX, boolean flipY, int rot) {
		// Rotate the bufferedimage
		if (rot != 0) {
			rot *= -1;
			rot -= 90;
			
			AffineTransform transform = new AffineTransform();
			transform.rotate(MathUtil.toRadians(rot), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
			
			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			bufferedImage = op.filter(bufferedImage, null);
		}

		// Flip the bufferedimage
		if (flipX || flipY) {
			AffineTransform tx = new AffineTransform();
			tx.scale(flipX ? -1 : 1, flipY ? -1 : 1);
			tx.translate(flipX ? -bufferedImage.getWidth() : 0, flipY ? -bufferedImage.getHeight() : 0);
			
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			bufferedImage = op.filter(bufferedImage, null);
		}

		// Power of two stuffs! This is actually kind of a wierd problem with
		// OpenGL, but it has to do with speedy-thingies.
		boolean PoT = isPoT(bufferedImage.getWidth(), bufferedImage.getHeight());

		BufferedImage bufferedImage0 = bufferedImage;

		// Power of two expansion; we will set the width and height to their
		// nearest larger PoT, since PoT is way faster :
		if (!PoT && expandToPoT) {
			int newwidth = 0, newheight = 0;
			
			boolean done = false;
			
			for (int power = 1; !done; power += 1) {
				int pot = (int) Math.pow(2, power);

				if (newwidth == 0)
					if (pot >= bufferedImage.getWidth())
						newwidth = pot;

				if (newheight == 0)
					if (pot >= bufferedImage.getHeight())
						newheight = pot;

				done = newwidth != 0 && newheight != 0;
			}

			BufferedImage bufferedImage1 = new BufferedImage(newwidth, newheight, bufferedImage.getType());

			bufferedImage1.getGraphics().drawImage(bufferedImage, flipX ? bufferedImage1.getWidth() - bufferedImage.getWidth() : 0, flipY ? bufferedImage1.getHeight() - bufferedImage.getHeight() : 0, null);
			bufferedImage0 = bufferedImage1;
		}

		return bufferedImage0;
	}

	public static ByteBuffer convertBufferedImageToBuffer(BufferedImage bufferedImage, boolean flipX, boolean flipY) {
		// A buffer to store with bufferedimage data and throw into LWJGL
		ByteBuffer buffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * BYTES_PER_PIXEL);

		for (int y = flipY ? bufferedImage.getHeight() - 1 : 0; flipY ? y > -1 : y < bufferedImage.getHeight(); y += flipY ? -1 : 1) {
			for (int x = flipX ? bufferedImage.getWidth() - 1 : 0; flipX ? x > -1 : x < bufferedImage.getWidth(); x += flipX ? -1 : 1) {
				int pixel = bufferedImage.getRGB(x, y);

				buffer.put((byte) (pixel >> 16 & 0xFF));
				buffer.put((byte) (pixel >> 8 & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) (pixel >> 24 & 0xFF));
			}
		}

		buffer.flip();

		return buffer;
	}

	public static ByteBuffer convertBufferedImageToBuffer(BufferedImage bufferedImage) {
		return convertBufferedImageToBuffer(bufferedImage, false, false);
	}

	public static boolean isPoT(int width, int height) {
		return (width & width - 1) == 0 && (height & height - 1) == 0;
	}

	/** @return A texture object with no data or source image. */
	public static Texture createTextureObject(int textureId, int width, int height) {
		return new Texture(textureId, width, height, null, null);
	}

	/** @return The default no-texture of OpenGL. */
	public static Texture getEmptyTexture() {
		if (BLANK_TEXTURE == null)
			BLANK_TEXTURE = loadTexture(Loader.streamFromClasspath("radirius/merc/graphics/empty.png"));

		return BLANK_TEXTURE;
	}

	private static boolean expandToPoT = true;

	/**
	 * Sets whether or not all Textures will be expanded to the nearest power of
	 * two. Keep in mind before you change this: PoT Textures allow for great
	 * things like GL_REPEAT and faster rendering time. Certain aspect of the
	 * library may break, should you choose to mess with this value. You can use
	 * the non-PoT SubTextures in much the same for the most part.
	 *
	 * Use at your own risk.
	 */
	public static void setExpandToPowerOfTwo(boolean expandToPoT) {
		Texture.expandToPoT = expandToPoT;
	}
}