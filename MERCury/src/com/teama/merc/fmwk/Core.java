package com.teama.merc.fmwk;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_SCALE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.VAOGraphics;
import com.teama.merc.res.ResourceManager;

/**
 * The {@code Core} that will host the game. It is ran above by the {@code Runner} class.
 * 
<<<<<<< HEAD
 * @from merc in com.teama.merc.fmwk
 * @authors wessles
=======
 * @from merc in com.teama.merc.fwk
 * @by wessles
>>>>>>> branch 'master' of https://github.com/weslgames/MERCury.git
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public abstract class Core
{
    public final String name;
    
    public Core(String name)
    {
        this.name = name;
    }
    
    /**
     * Called first (after {@code initDisplay}, {@code initGraphics}, and {@code initAudio}), used to initialize all resources, and for whatever you wish to do for initialization.
     */
    public abstract void init(ResourceManager RM);
    
    /**
     * Called once every frame, and used to handle all logic.
     */
    public abstract void update(float delta);
    
    /**
     * Called once every frame, and used to render everything, via {@code Graphics g}.
     */
    public abstract void render(Graphics g);
    
    /**
     * Called when the Runner is done
     */
    public abstract void cleanup(ResourceManager RM);
    
    public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync)
    {
        try
        {
            DisplayMode dm = new DisplayMode(WIDTH, HEIGHT);
            
            if (fullscreen)
            {
                Display.setFullscreen(fullscreen);
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                
                for (DisplayMode mode : modes)
                    if (mode.getWidth() >= WIDTH && mode.getHeight() >= HEIGHT && mode.isFullscreenCapable())
                        dm = mode;
            }
            
            Display.setDisplayMode(dm);
            Display.setTitle(name);
            Display.setVSyncEnabled(vsync);
            Display.create();
        } catch (LWJGLException e)
        {
            e.printStackTrace();
        }
    }
    
    public Graphics initGraphics()
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        glEnable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_SCALE);
        glDepthMask(true);
        glDepthFunc(GL_LEQUAL);
        
        glAlphaFunc(GL_GREATER, 0.1f);
        
        return new VAOGraphics();
    }
}
