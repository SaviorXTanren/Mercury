package radirius.merc.graphics;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferTexture2DEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenFramebuffersEXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT_BIT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GLContext.getCapabilities;
import radirius.merc.framework.Runner;
import radirius.merc.utilities.logging.Logger;

/**
 * An object that allows you to 'draw to a texture.' Good for post-processing
 * effects.
 * 
 * @author wessles
 */

public class FrameBuffer {
    private final int fboid;
    private final Texture fbotex;
    
    private FrameBuffer(int fboid, int texid, int width, int height) {
        this.fboid = fboid;
        
        fbotex = Texture.createTextureObject(texid, width, height);
    }
    
    /** Creates a new frame buffer object. */
    public static FrameBuffer getFrameBuffer() {
        if (!isSupported()) {
            Logger.warn("Framebuffers not supported!");
            return null;
        }
        
        int fboid = glGenFramebuffersEXT();
        int texid = glGenTextures();
        
        // Bind
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboid);
        
        // Texture stuffs!
        glBindTexture(GL_TEXTURE_2D, texid);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        int width = (int) Runner.getInstance().getCamera().getWidth(), height = (int) Runner.getInstance().getCamera().getHeight();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_INT, (java.nio.ByteBuffer) null);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, texid, 0);
        
        // Unbind
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        Texture.unbindTextures();
        
        return new FrameBuffer(fboid, texid, width, height);
    }
    
    /** Begins recording of the FBO to the texture object. */
    public void use() {
        Texture.unbindTextures();
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboid);
        glPushAttrib(GL_VIEWPORT_BIT);
        glViewport(0, 0, (int) Runner.getInstance().getCamera().getWidth(), (int) Runner.getInstance().getCamera().getHeight());
        glClear(GL_COLOR_BUFFER_BIT);
    }
    
    /** Finalizes the recording of the FBO, and releases it. */
    public void release() {
        releaseFrameBuffers();
        glPopAttrib();
    }
    
    /** @return The last recorded texture. */
    public Texture getTextureObject() {
        return fbotex;
    }
    
    /** Staticly 'use().' */
    public static void useFrameBuffer(FrameBuffer fbo) {
        fbo.use();
    }
    
    /** Returns to original frame buffer (window). */
    public static void releaseFrameBuffers() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }
    
    /** @return If FBOs are supported */
    public static boolean isSupported() {
        return getCapabilities().GL_EXT_framebuffer_object;
    }
}
