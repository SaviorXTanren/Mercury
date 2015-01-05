package com.radirius.mercury.graphics;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.framework.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * An object that allows you to 'draw to a texture.' Good for post-processing effects.
 *
 * @author wessles
 * @author Sri Harsha Chilakapati
 */
public class FrameBuffer {
	private final int fboId;
	private final Texture fboTexture;
	private final int width, height;

	private FrameBuffer(int fboId, int texId, int width, int height) {
		this.fboId = fboId;
		fboTexture = Texture.createTextureObject(texId, width, height);
		this.width = width;
		this.height = height;
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer() {
		return getFrameBuffer(Window.getWidth(), Window.getHeight());
	}

	/**
	 * Creates a new frame buffer object.
	 *
	 * @param width  The width of the frame
	 * @param height The height of the frame
	 * @return A new frame buffer object
	 */
	public static FrameBuffer getFrameBuffer(int width, int height) {
		int fboId = glGenFramebuffers();
		int texId = glGenTextures();

		glBindFramebuffer(GL_FRAMEBUFFER, fboId);
		Texture.bindTexture(texId);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texId, 0);
		glDrawBuffer(GL_COLOR_ATTACHMENT0);

		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			try {
				throw new MercuryException("Error in Framebuffer");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		Texture.unbindTextures();

		return new FrameBuffer(fboId, texId, width, height);
	}

	/**
	 * Returns to original frame buffer.
	 */
	public static void releaseFrameBuffers() {
		Core.getCurrentCore().getBatcher().flush();
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	/**
	 * Begins recording of the FBO to the texture object.
	 */
	public void use() {
		fboTexture.bind();
		glBindFramebuffer(GL_FRAMEBUFFER, fboId);
		glViewport(0, 0, width, height);
		glClear(GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Finalizes the recording of the FBO, and releases it.
	 */
	public void release() {
		releaseFrameBuffers();
		Texture.unbindTextures();
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
