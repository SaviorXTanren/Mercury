package com.radirius.mercury.graphics;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.framework.Runner;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * An object that allows you to 'draw to a texture.' Good
 * for post-processing effects.
 *
 * @author wessles, Sri Harsha Chilakapati
 */
public class FrameBuffer {
    private final int fboId;
    private final Texture fboTexture;

    private FrameBuffer(int fboId, int texId, int width, int height) {
        this.fboId = fboId;

        fboTexture = Texture.createTextureObject(texId, width, height);
    }

    /**
     * Creates a new frame buffer object.
     */
    public static FrameBuffer getFrameBuffer() {
        int fboId = glGenFramebuffers();
        int texId = glGenTextures();

        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
        Texture.bindTextures(texId);

        int width = (int) Runner.getInstance().getCamera().getWidth(), height = (int) Runner.getInstance().getCamera().getHeight();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0,GL_RGB, GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
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
     * Staticly 'use().'
     */
    public static void useFrameBuffer(FrameBuffer fbo) {
        fbo.use();
    }

    /**
     * Returns to original frame buffer (window).
     */
    public static void releaseFrameBuffers() {
        Runner.getInstance().getGraphics().getBatcher().flush();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    /**
     * Begins recording of the FBO to the texture object.
     */
    public void use() {
        fboTexture.bind();
        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
        glViewport(0, 0, (int) Runner.getInstance().getCamera().getWidth(), (int) Runner.getInstance().getCamera().getHeight());
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
     * @return The last recorded texture.
     */
    public Texture getTextureObject() {
        return fboTexture;
    }
}
