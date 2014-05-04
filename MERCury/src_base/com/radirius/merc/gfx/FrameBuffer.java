package com.radirius.merc.gfx;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GLContext.*;

import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.log.Logger;

/**
 * @from MERCury in package com.radirius.merc.gfx;
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Apr 30, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class FrameBuffer
{
    private final int fboid;
    private final Texture fbotex;
    
    private FrameBuffer(int fboid, int texid, int width, int height)
    {
        this.fboid = fboid;
        
        this.fbotex = Texture.createTextureObject(texid, width, height);
    }
    
    public static FrameBuffer getFrameBuffer()
    {
        if (!isSupported())
        {
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
    
    public void use()
    {
        Texture.unbindTextures();
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fboid);
        glPushAttrib(GL_VIEWPORT_BIT);
        glViewport(0, 0, (int)Runner.getInstance().getCamera().getWidth(), (int)Runner.getInstance().getCamera().getHeight());
        glClear(GL_COLOR_BUFFER_BIT);
    }
    
    public void release()
    {
        releaseFrameBuffers();
        glPopAttrib();
    }
    
    public Texture getTextureObject()
    {
        return fbotex;
    }
    
    public static void useFrameBuffer(FrameBuffer fbo)
    {
        fbo.use();
    }
    
    public static void releaseFrameBuffers()
    {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }
    
    public static boolean isSupported()
    {
        return getCapabilities().GL_EXT_framebuffer_object;
    }
}
