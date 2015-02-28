package com.radirius.mercury.graphics;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.resource.*;
import com.radirius.mercury.utilities.misc.Bindable;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * An object version of a texture. This will store the width and height of the
 * object.
 *
 * @author wessles
 * @author Jeviny
 */
public class Texture implements Resource, Bindable {
	public static final int FILTER_NEAREST = GL_NEAREST, FILTER_LINEAR = GL_LINEAR;

	protected static final int BYTES_PER_PIXEL = 4;
	protected static Texture BLANK_TEXTURE;

	private static boolean expandToPoT = true;

	public int textureId;
	public int width;
	public int height;
	public BufferedImage bufferedImage;
	public ByteBuffer buffer;

	/**
	 * Make a texture of the textureId, with a width and height, based off of
	 * BufferedImage bufferedImage
	 *
	 * @param textureId     The id of the texture
	 * @param width         The width of the texture
	 * @param height        The height of the texture
	 * @param bufferedImage The source of the image, the BufferedImage
	 * @param buffer        The original buffer
	 */
	public Texture(int textureId, int width, int height, BufferedImage bufferedImage, ByteBuffer buffer) {
		this.textureId = textureId;
		this.width = width;
		this.height = height;

		this.bufferedImage = bufferedImage;
		this.buffer = buffer;
	}

	/**
	 * Loads a Texture
	 *
	 * @param inputStream The stream of the image to load Returns A Texture
	 *                    based off of the streamed image
	 */
	public static Texture loadTexture(InputStream inputStream) {
		return loadTexture(inputStream, FILTER_NEAREST);
	}

	/**
	 * Loads a Texture
	 *
	 * @param bufferedImage The image to load Returns A Texture based off of the
	 *                      BufferedImage
	 */
	public static Texture loadTexture(BufferedImage bufferedImage) {
		return loadTexture(bufferedImage, FILTER_NEAREST);
	}

	/**
	 * Loads a Texture
	 *
	 * @param inputStream The stream of the image to load
	 * @param filter      Both the min and mag filter Returns A Texture based off of
	 *                    the streamed image
	 */
	public static Texture loadTexture(InputStream inputStream, int filter) {
		return loadTexture(inputStream, filter, filter);
	}

	/**
	 * Loads a Texture
	 *
	 * @param bufferedImage The image to load
	 * @param filter        Both the min and mag filter Returns A Texture based off of
	 *                      the BufferedImage
	 */
	public static Texture loadTexture(BufferedImage bufferedImage, int filter) {
		return loadTexture(bufferedImage, filter, filter);
	}

	/**
	 * Loads a Texture
	 *
	 * @param inputStream The stream of the image to load
	 * @param minFilter   The min filter
	 * @param magFilter   The mag filter Returns A Texture based off of the
	 *                    streamed image
	 */
	public static Texture loadTexture(InputStream inputStream, int minFilter, int magFilter) {
		try {
			return loadTexture(ImageIO.read(inputStream), minFilter, magFilter);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads a Texture
	 *
	 * @param bufferedImage The image to load
	 * @param minFilter     The min filter
	 * @param magFilter     The mag filter Returns A Texture based off of the
	 *                      BufferedImage
	 */
	public static Texture loadTexture(BufferedImage bufferedImage, int minFilter, int magFilter) {
		BufferedImage bufferedImage0 = processBufferedImage(bufferedImage);

		ByteBuffer buffer = convertBufferedImageToBuffer(bufferedImage0, false, true);

		int textureId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureId);

		// Set the parameters for filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);

		// Push all buffer data into the now configured OpenGL.
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bufferedImage0.getWidth(), bufferedImage0.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		glGenerateMipmap(GL_TEXTURE_2D);

		// Unbind
		glBindTexture(GL_TEXTURE_2D, 0);

		return new SubTexture(new Texture(textureId, bufferedImage0.getWidth(), bufferedImage0.getHeight(), bufferedImage, buffer), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
	}

	public static BufferedImage processBufferedImage(BufferedImage bufferedImage) {
		// Power of two stuffs. This is actually kind of a weird problem with
		// OpenGL, but it has to do with speed things.
		boolean PoT = isPoT(bufferedImage.getWidth(), bufferedImage.getHeight());

		BufferedImage bufferedImage0 = bufferedImage;

		// Power of two expansion; we will set the width and height to their
		// nearest larger PoT, since PoT is way faster
		if (!PoT && expandToPoT) {
			int newWidth = 0, newHeight = 0;

			boolean done = false;

			for (int power = 1; !done; power += 1) {
				int pot = (int) Math.pow(2, power);

				if (newWidth == 0)
					if (pot >= bufferedImage.getWidth())
						newWidth = pot;

				if (newHeight == 0)
					if (pot >= bufferedImage.getHeight())
						newHeight = pot;

				done = newWidth != 0 && newHeight != 0;
			}

			BufferedImage bufferedImage1 = new BufferedImage(newWidth, newHeight, bufferedImage.getType());

			bufferedImage1.getGraphics().drawImage(bufferedImage, 0, 0, null);
			bufferedImage0 = bufferedImage1;
		}

		return bufferedImage0;
	}

	public static ByteBuffer convertBufferedImageToBuffer(BufferedImage bufferedImage, boolean flipX, boolean flipY) {
		// A buffer to store with buffered image data and throw into OpenGL
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

	/**
	 * Returns A texture object with no data or source image.
	 */
	public static Texture createTextureObject(int textureId, int width, int height) {
		return new Texture(textureId, width, height, null, null);
	}

	/**
	 * Returns The a blank white Texture.
	 */
	public static Texture getEmptyTexture() {
		if (BLANK_TEXTURE == null)
			BLANK_TEXTURE = loadTexture(Loader.getResourceAsStream("com/radirius/mercury/graphics/res/empty.png"));

		return BLANK_TEXTURE;
	}

	/**
	 * Binds the texture.
	 */
	@Override
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, textureId);
	}

	/**
	 * Unbinds the texture.
	 */
	@Override
	public void release() {
		releaseTextures();
	}

	public static void releaseTextures() {
		BLANK_TEXTURE.bind();
	}

	/**
	 * Returns The texture's width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns The texture's height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns Whether or not the texture is PoT.
	 */
	public boolean isPoT() {
		return isPoT(getWidth(), getHeight());
	}

	/**
	 * Returns The texture's id.
	 */
	public int getTextureId() {
		return textureId;
	}

	/**
	 * Returns The source image.
	 */
	public BufferedImage getSourceImage() {
		if (bufferedImage == null)
			try {
				throw new MercuryException("No source image given.");
			} catch (MercuryException e) {
				e.printStackTrace();
			}

		return bufferedImage;
	}

	/**
	 * Returns The original buffer.
	 */
	public ByteBuffer getBuffer() {
		return buffer;
	}

	/**
	 * @param width  The width of the Texture
	 * @param height The height of the Texture Returns Whether the Texture has
	 *               power-of-two sides
	 */
	public static boolean isPoT(int width, int height) {
		return (width & width - 1) == 0 && (height & height - 1) == 0;
	}

	/**
	 * Sets whether or not all Textures will be expanded to the nearest power of
	 * two. Keep in mind before you change this that PoT Textures allow for
	 * faster rendering time.
	 */
	public static void setExpandToPowerOfTwo(boolean expandToPoT) {
		Texture.expandToPoT = expandToPoT;
	}

	/**
	 * Returns If the Texture is PoT and not a SubTexture (and in turn, can
	 * repeat).
	 */
	public boolean isRepeatable() {
		boolean capable = isPoT();

		return !(this instanceof SubTexture) && capable;

	}

	@Override
	public void clean() {
		glDeleteTextures(textureId);
	}
}
