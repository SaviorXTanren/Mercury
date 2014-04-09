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
 * @from merc in com.radirius.merc.fmwk
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public abstract class Core
{
    public final String name;

    public Core(String name)
    {
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
    public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync)
    {
        try
        {
            Display.setVSyncEnabled(vsync);

            DisplayMode dm = new DisplayMode(WIDTH, HEIGHT);

            boolean fullscreen_matched = false;

            if (fullscreen)
            {
                DisplayMode[] modes = Display.getAvailableDisplayModes();

                Logger.debug("Full-screen requested; attempting to find matching fullscreen display mode...");

                for (DisplayMode mode : modes)
                    if (mode.getWidth() == dm.getWidth() && mode.getHeight() == dm.getHeight())
                    {
                        dm = mode;
                        fullscreen_matched = true;
                    }

                if (!fullscreen_matched)
                {
                    Logger.debug("No fullscreen matched. Finding lowest sized available fullscreen display mode...");

                    DisplayMode lowest_mode = modes[0];

                    for (DisplayMode mode : modes)
                        if (mode.isFullscreenCapable())
                            if (mode.getWidth() < lowest_mode.getWidth() || mode.getHeight() < lowest_mode.getHeight())
                                lowest_mode = mode;
                    dm = lowest_mode;
                }

                Logger.debug("Fullscreen matched: " + dm.toString());

                Display.setFullscreen(true);
            }

            Display.setDisplayMode(dm);
            Display.setTitle(name);
            Display.create();
        } catch (LWJGLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Initializes graphics.
     */
    public Graphics initGraphics()
    {
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
}
