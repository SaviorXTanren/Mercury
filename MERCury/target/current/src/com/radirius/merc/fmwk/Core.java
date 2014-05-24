package com.radirius.merc.fmwk;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_SCALE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POLYGON_STIPPLE;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.VAOGraphics;
import com.radirius.merc.log.Logger;
import com.radirius.merc.res.ResourceManager;

/**
 * The {@code Core} that will host the game. It is ran above by the
 * {@code Runner} class.
 * 
 * @author wessles
 */

public abstract class Core {
    public final String name;
    
    public Core(String name) {
        this.name = name;
    }
    
    /**
     * Called first (after {@code initDisplay}, {@code initGraphics}, and
     * {@code initAudio}), used to initialize all resources, and for whatever
     * you wish to do for initialization.
     */
    public abstract void init(ResourceManager RM) throws IOException, MERCuryException;
    
    /**
     * Called once every frame, and used to handle all logic.
     */
    public abstract void update(float delta) throws MERCuryException;
    
    /**
     * Called once every frame, and used to render everything, via
     * {@code Graphics g}.
     */
    public abstract void render(Graphics g) throws MERCuryException;
    
    /**
     * Called when the Runner is done
     */
    public abstract void cleanup(ResourceManager RM) throws IOException, MERCuryException;
    
    /**
     * Initializes the display.
     * 
     * @param WIDTH
     *            The width of the display
     * @param HEIGHT
     *            The height of the display
     * @param fullscreen
     *            Whether or not this is fullscreen
     * @param vsync
     *            Whether or not we should vsync
     */
    public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
        try {
            Display.setVSyncEnabled(vsync);
            
            DisplayMode dm = new DisplayMode(WIDTH, HEIGHT);
            boolean screendimmatched = false;
            
            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                
                for (int i = 0; i < modes.length; i++)
                    if (modes[i].getWidth() == WIDTH && modes[i].getHeight() == HEIGHT && modes[i].isFullscreenCapable()) {
                        dm = modes[i];
                        screendimmatched = true;
                    }
                
                if (!screendimmatched)
                    Logger.warn("Dimensions " + WIDTH + "x" + HEIGHT + " is not supported! Defaulting to non-fullscreen.");
                else
                    Display.setFullscreen(true);
            }
            
            Display.setDisplayMode(dm);
            Display.setTitle(name);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initializes graphics.
     */
    public Graphics initGraphics() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_POLYGON_STIPPLE);
        glEnable(GL_ALPHA_TEST);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_SCALE);
        
        glDepthMask(true);
        
        glDepthFunc(GL_LEQUAL);
        
        glAlphaFunc(GL_GREATER, 0.01f);
        
        return new VAOGraphics();
    }
    
    /**
     * Initializes audio.
     */
    public void initAudio() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
}
