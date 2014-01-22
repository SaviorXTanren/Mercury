package com.teama.merc.fmwk;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.teama.merc.exc.PluginNotFoundException;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.in.Input;
import com.teama.merc.log.Logger;
import com.teama.merc.res.ResourceManager;
import com.teama.merc.spl.SplashScreen;
import com.teama.merc.util.Camera;

/**
 * A class that will run your core, and give out the graphics object, current core, resource manager, and input ly.
 * 
 * @from merc in com.teama.merc
<<<<<<< HEAD
 * @authors wessles
=======
 * @by wessles
>>>>>>> branch 'master' of https://github.com/weslgames/MERCury.git
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http//www.wtfpl.net/about/.
 */

public class Runner
{
    private final static Runner singleton = new Runner();
    
    private final ArrayList<SplashScreen> splashes = new ArrayList<SplashScreen>();
    private final ArrayList<Plugin> plugs = new ArrayList<Plugin>();
    
    private boolean running = false;
    private boolean splashed = false;
    private boolean vsync;
    private boolean logfps = false;
    private int delta = 1;
    private int FPS_TARGET = 60, FPS;
    private int splashidx = 0;
    private long lastframe;
    private float deltafactor = 1;
    
    private Core core;
    
    private Graphics graphicsobject;
    
    private ResourceManager RM;
    
    private Camera camera;
    private Input input;
    
    public void init(Core core, int WIDTH, int HEIGHT)
    {
        init(core, WIDTH, HEIGHT, false);
    }
    
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen)
    {
        init(core, WIDTH, HEIGHT, fullscreen, false);
    }
    
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync)
    {
        // Initialize Some Stuff! IN ORDER
        Logger.debug("MERCury Started!");
        Logger.log("  __  __ ______ _____   _____                 ");
        Logger.log(" |  \\/  |  ____|  __ \\ / ____|                ");
        Logger.log(" | \\  / | |__  | |__) | |    _   _ _ __ _   _ ");
        Logger.log(" | |\\/| |  __| |  _  /| |   | | | | '__| | | |");
        Logger.log(" | |  | | |____| | \\ \\| |___| |_| | |  | |_| |");
        Logger.log(" |_|  |_|______|_|  \\_\\_____\\__,|_| |   \\__, |");
        Logger.log("                                        __/ |");
        Logger.log("                                       |___/ ");
        Logger.log("Maitenance Enhanced and Reliable Coding Engine");
        Logger.log("             by wessles");
        Logger.log("             Email wessles@wessles.com for support");
        Logger.log("             Go to www.reddit.com/r/MERCuryGameEngine for community");
        Logger.log("                        Copyright www.wessles.com 2013");
        Logger.log("                        All Rights Reserved");
        
        Logger.debug("Making Core...");
        this.core = core;
        this.vsync = vsync;
        Logger.debug("Initializing Display...");
        this.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
        Logger.debug("Making Graphics...");
        graphicsobject = this.core.initGraphics();
        Logger.debug("Initializing Camera...");
        camera = new Camera(0, 0);
        Logger.debug("Initializing Resource Manager...");
        RM = new ResourceManager();
        
        Logger.debug("Initializing Graphics...");
        graphicsobject.init();
        Logger.debug("Creating Input...");
        input = new Input();
        Logger.debug("Initializing Input...");
        input.create();
        
        Logger.debug("Starting plugins...");
        for (Plugin plug : plugs)
        {
            Logger.debug("     Initializing " + plug.getClass().getSimpleName() + "...");
            plug.init();
        }
        
        Logger.debug("Initializing Core...");
        this.core.init(RM);
        if (splashes.size() == 0)
        {
            splashed = true;
            Logger.debug("No splashes loaded by Core.");
        }
        
        Logger.debug("Ready to begin game loop. Awaiting permission from Core...");
    }
    
    public void run()
    {
        Logger.debug("Run permission granted by Core...");
        Logger.debug("Starting Game Loop...");
        running = true;
        Logger.line();
        Logger.log("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
        Logger.line();
        Logger.line();
        
        // To the main loop!
        
        int _FPS = 0;
        long lastfps;
        
        /* Initial 'last time...' Otherwise the first delta will be about 50000000. */
        lastfps = lastframe = Sys.getTime() * 1000 / Sys.getTimerResolution();
        
        while (running)
        {
            // Set time for FPS and Delta calculations
            long time = Sys.getTime() * 1000 / Sys.getTimerResolution();
            
            // Calculate delta
            delta = (int) (time - lastframe);
            
            // Update FPS
            if (time - lastfps < 1000)
                _FPS++;
            else
            {
                lastfps = time;
                FPS = _FPS;
                _FPS = 0;
            }
            
            if (FPS == 0)
                FPS = FPS_TARGET;
            
            // End all time calculations.
            lastframe = time;
            
            // Log FPS
            if (logfps)
                Logger.debug("FPS" + FPS);
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if (splashed)
                core.update(delta());
            
            camera.pre(graphicsobject);
            {
                if (splashed)
                    core.render(graphicsobject);
                else if (!splashes.get(splashidx).show(graphicsobject))
                    if (splashidx < splashes.size() - 1)
                        splashidx++;
                    else
                        splashed = true;
            }
            camera.post(graphicsobject);
            
            if (Display.isCloseRequested())
                end();
            
            Display.update();
            Display.sync(FPS_TARGET);
        }
        
        Logger.line();
        Logger.line();
        Logger.log("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
        Logger.line();
        Logger.debug("Game Loop ended.");
        
        Logger.debug("Starting Cleanup...");
        Logger.debug("Cleaning up Core...");
        core.cleanup(RM);
        Logger.debug("Cleaning up ResourceManager...");
        RM.cleanup();
        Logger.debug("Cleaning up plugins...");
        for (Plugin plug : plugs)
        {
            Logger.debug("     Cleaning up " + plug.getClass().getSimpleName() + "...");
            plug.cleanup();
        }
        Logger.debug("Cleanup complete.");
        Logger.debug("MERCury Game Engine shutting down...");
    }
    
    public int fps()
    {
        return FPS;
    }
    
    public void setFpsTarget(int target)
    {
        FPS_TARGET = target;
    }
    
    public void setLogFPS(boolean logfps)
    {
        this.logfps = logfps;
    }
    
    public int width()
    {
        return Display.getWidth();
    }
    
    public float width(float numerator)
    {
        return width() / numerator;
    }
    
    public int height()
    {
        return Display.getHeight();
    }
    
    public float height(float numerator)
    {
        return height() / numerator;
    }
    
    public float aspectRatio()
    {
        return width() / height();
    }
    
    public float aspectRatio(float numerator)
    {
        return width(numerator) / height(numerator);
    }
    
    /**
     * @return Time in milliseconds
     */
    public float getTime()
    {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
    
    public void sleep(int milliseconds) throws InterruptedException
    {
        Thread.sleep(milliseconds);
    }
    
    public void mousegrab(boolean grab)
    {
        Mouse.setGrabbed(grab);
    }
    
    public void vsync(boolean vsync)
    {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }
    
    public boolean focused()
    {
        return Display.isActive();
    }
    
    public void setTitle(String title)
    {
        Display.setTitle(title);
    }
    
    public void setResizable(boolean resizable)
    {
        Display.setResizable(resizable);
        
        remakeDisplay();
    }
    
    private void remakeDisplay()
    {
        Display.destroy();
        core().initDisplay(width(), height(), Display.isFullscreen(), vsync);
        graphicsobject = core().initGraphics();
    }
    
    public void end()
    {
        running = false;
    }
    
    public float delta()
    {
        return delta * deltafactor;
    }
    
    public void setDeltaFactor(float factor)
    {
        deltafactor = factor;
    }
    
    public Core core()
    {
        return core;
    }
    
    public Graphics graphics()
    {
        return graphicsobject;
    }
    
    public ResourceManager resourceManager()
    {
        return RM;
    }
    
    public Input input()
    {
        return input;
    }
    
    public Camera camera()
    {
        return camera;
    }
    
    public void addSplashScreen(SplashScreen splash)
    {
        splashes.add(splash);
    }
    
    public void addPlugin(Plugin plugin)
    {
        plugs.add(plugin);
    }
    
    public Plugin getPlugin(String name) throws PluginNotFoundException
    {
        for (Plugin plug : plugs)
            if (plug.getClass().getSimpleName().equalsIgnoreCase(name))
                return plug;
        throw new PluginNotFoundException("Plugin '" + name + "' not found!");
    }
    
    public static Runner getInstance()
    {
        return singleton;
    }
}
