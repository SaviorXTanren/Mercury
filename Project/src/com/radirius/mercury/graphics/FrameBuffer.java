package com.radirius.mercury.graphics;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.framework.*;
import com.radirius.mercury.utilities.misc.*;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * An object that allows you to 'draw to a texture.' Good for end-processing effects.
 *
 * @author wessles
 * @author Sri Harsha Chilakapati
 */
public class FrameBuffer implements Cleanable, Bindable {
	private final int fboId;
	private final Texture fboTexture;
	private final int width, height;
	private Color backgroundColor;

	private FrameBuffer(int fboId, int texId, int width, int height, Color backgroundColor) {
		this.fboId = fboId;
		fboTexture = Texture.createTextureObject(texId, width, height);
		this.width = width;
		this.height = height;
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer() {
		return getFrameBuffer(Color.CLEAR);
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @param background
	 * 		The background color (can be transparent)
	 *
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer(Color background) {
		return getFrameBuffer(GL_NEAREST, background);
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @param filtering
	 * 		The GL texture filter of the frame
	 * @param background
	 * 		The background color (can be transparent)
	 *
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer(int filtering, Color background) {
		return getFrameBuffer(Window.getWidth(), Window.getHeight(), filtering, background);
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @param width
	 * 		The width of the frame
	 * @param height
	 * 		The height of the frame
	 * @param filtering
	 * 		The GL texture filter of the frame
	 * @param background
	 * 		The background color (can be transparent)
	 *
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer(int width, int height, int filtering, Color background) {
		glEnable(GL_MULTISAMPLE);

		int texId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filtering);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filtering);
		int fboId = glGenFramebuffers();
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboId);
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texId, 0);
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			try {
				throw new MercuryException("Error in Framebuffer");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		return new FrameBuffer(fboId, texId, width, height, background);
	}

	/**
	 * Sets the background color.
	 *
	 * @param color
	 * 		The color to switch to
	 */
	public void setBackground(Color color) {
		backgroundColor = color;
	}

	/**
	 * @return the color of the background
	 */
	public Color getBackground() {
		return backgroundColor;
	}

	/**
	 * Begins recording of the FBO to the texture object.
	 */
	@Override
	public void bind() {
		bind(true, true);
	}

	/**
	 * Begins recording of the FBO to the texture object.
	 */
	public void bind(boolean flush, boolean clear) {
		if (flush) {
			// Flush previous data
			Core.getCurrentCore().getBatcher().flush();
		}
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboId);
		glViewport(0, 0, width, height);
		if (clear) {
			glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
			glClear(GL_COLOR_BUFFER_BIT);
		}
	}

	/**
	 * Finalizes the recording of the FBO, and releases it.
	 */
	@Override
	public void release() {
		releaseFrameBuffers();
		glViewport(0, 0, Window.getWidth(), Window.getHeight());
		Color background = Core.getCurrentCore().getGraphics().getBackground();
		glClearColor(background.r, background.g, background.g, background.a);
	}

	/**
	 * Returns to original frame buffer.
	 */
	public static void releaseFrameBuffers() {
		// Flush the data to the frame buffer
		Core.getCurrentCore().getBatcher().flush();
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void cleanup() {
		glDeleteFramebuffers(fboId);
	}

	/**
	 * Returns The last recorded texture.
	 */
	public Texture getTextureObject() {
		return fboTexture;
	}
}