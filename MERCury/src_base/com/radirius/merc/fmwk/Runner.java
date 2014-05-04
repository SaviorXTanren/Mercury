package com.radirius.merc.fmwk;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.radirius.merc.cmd.Command;
import com.radirius.merc.cmd.CommandList;
import com.radirius.merc.cmd.CommandThread;
import com.radirius.merc.cmd.Variable;
import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.exc.PluginNotFoundException;
import com.radirius.merc.gfx.Camera;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;
import com.radirius.merc.log.Logger;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.spl.SplashScreen;
import com.radirius.merc.util.TaskTiming;

/**
 * A class that will run your core, and give out the graphics object, current
 * core, resource manager, and input ly.
 * 
 * @from merc in com.radirius.merc.fmwk
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http//www.wtfpl.net/about/.
 */

public class Runner
{
    /** The singleton instance of the Runner. This should be the ONLY Runner. */
    private final static Runner singleton = new Runner();
    
    /** Whether or not the library is running */
    public boolean running = false;
    
    /** A list of splash screens we have */
    private final ArrayList<SplashScreen> splashes = new ArrayList<SplashScreen>();
    /** A list of plugins we have */
    private final ArrayList<Plugin> plugs = new ArrayList<Plugin>();
    
    /** A Runnable for the console thread */
    private final CommandThread consolerunnable = new CommandThread();
    /** A Thread for the console */
    private final Thread consolethread = new Thread(consolerunnable);
    
    /** Whether or not we are updating or not */
    private boolean updatefreeze = false;
    /** Whether or not we are rendering or not */
    private boolean renderfreeze = false;
    
    /** Whether or not we have 'splashed' the splash screens */
    private boolean splashed = false;
    /** Whether or not we are vsyncing */
    private boolean vsync;
    /** Whether or not we are logging the FPS */
    private boolean logfps = false;
    /** The delta variable */
    private int delta = 1;
    /** The target fps */
    private int FPS_TARGET = 60;
    /** The current fps */
    private int FPS;
    /** The current splash screen */
    private int splashidx = 0;
    /** The last frame; used for calculating fps */
    private long lastframe;
    /** The factor by which delta is multiplied */
    private float deltafactor = 1;
    
    /** The core being ran */
    private Core core;
    
    /** The graphics object */
    private Graphics graphicsobject;
    
    /** The resource manager */
    private ResourceManager RM;
    
    /** The camera */
    private Camera camera;
    /** The input node */
    private Input input;
    
    // We don't want ANYONE attempting to create another. There is a singleton,
    // and you must use it.
    private Runner()
    {
    }
    
    /**
     * Initializes the library
     * 
     * @param core
     *            The core we shall run
     * @param WIDTH
     *            The width of the display
     * @param HEIGHT
     *            The height of the display
     */
    public void init(Core core, int WIDTH, int HEIGHT)
    {
        init(core, WIDTH, HEIGHT, false);
    }
    
    /**
     * Initializes the library
     * 
     * @param core
     *            The core we shall run
     * @param WIDTH
     *            The width of the display
     * @param HEIGHT
     *            The height of the display
     * @param fullscreen
     *            Whether or not the display is fullscreen
     */
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen)
    {
        init(core, WIDTH, HEIGHT, fullscreen, false, true);
    }
    
    /**
     * Initializes the library
     * 
     * @param core
     *            The core we shall run
     * @param fullscreen
     *            Whether or not the display is fullscreen
     * @param vsync
     *            Whether or not we are vsynced
     */
    public void init(Core core, boolean fullscreen, boolean vsync)
    {
        init(core, Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), fullscreen, vsync, true);
    }
    
    /**
     * Initializes the library
     * 
     * @param core
     *            The core we shall run
     * @param WIDTH
     *            The width of the display
     * @param HEIGHT
     *            The height of the display
     * @param fullscreen
     *            Whether or not the display is fullscreen
     * @param vsync
     *            Whether or not we are vsynced
     * @param devconsole
     *            Whether or not we are enabling the developers console
     */
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, boolean devconsole)
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
        Logger.log("Maitenance Enhanced and Reliable Coding Library");
        Logger.log("             Designed by Radirius");
        Logger.log("                                             ");
        Logger.log("             You may read the list of contributors");
        Logger.log("             On the official MERCury website:");
        Logger.log("             http://weslgames.github.io/MERCury/");
        Logger.log("                                             ");
        Logger.log("             Email wessles@wessles.com for support");
        Logger.log("             Go to www.reddit.com/r/MERCuryGameLibrary for community");
        Logger.log("                        Copyright www.wessles.com 2013-20XX");
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
            Logger.debug("\tInitializing " + plug.getClass().getSimpleName() + "...");
            plug.init();
        }
        
        Logger.debug("Initializing Core...");
        
        try
        {
            this.core.init(RM);
        } catch (IOException | MERCuryException e)
        {
            e.printStackTrace();
        }
        
        Logger.debug("Making and adding default CommandList 'merc...'");
        // Make default CommandList
        CommandList cmdlmerc = new CommandList("merc", "This is the default Command List for MERCury Developer Console. In it, you will find core functions to MERCury Developer Console that will allow you to modify projects within the runtime.");
        
        // Add in all the commands.
        cmdlmerc.addCommand(new Command("end", "Ends the program.")
        {
            @Override
            public void run(String... args)
            {
                end();
                Logger.console("Ending MERCury...");
            }
        });
        cmdlmerc.addCommand(new Command("togglefreeze", "Toggles the freezing of the update and the freezing of the rendering.")
        {
            @Override
            public void run(String... args)
            {
                setUpdateFreeze(!updatefreeze);
                setRenderFreeze(!renderfreeze);
                Logger.console("Update Freeze changed to " + updatefreeze + ", and Render Freeze changed to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("setFpsTarget", "merc setFpsTarget [Fps Target]\nTargets for, or caps the framerate at a given height.")
        {
            @Override
            public void run(String... args)
            {
                setFpsTarget(Integer.parseInt(args[0]));
                Logger.console("Framerate targeted for " + FPS_TARGET);
            }
        });
        cmdlmerc.addCommand(new Command("setMouseGrab", "merc setMouseGrab [True/False]\nLocks or releases the mouse from the window.")
        {
            @Override
            public void run(String... args)
            {
                setMouseGrab(Boolean.valueOf(args[0]));
                Logger.console("Mouse " + (Boolean.valueOf(args[0]) ? "grabbed" : "released") + ".");
            }
        });
        cmdlmerc.addCommand(new Command("setVsync", "Sets whether or not there is Vertical Sync.")
        {
            @Override
            public void run(String... args)
            {
                setVsync(Boolean.valueOf(args[0]));
                Logger.console("Vsync set to " + args[0]);
            }
        });
        cmdlmerc.addCommand(new Command("setTitle", "Sets the title of the window.")
        {
            @Override
            public void run(String... args)
            {
                setTitle(args[0]);
                Logger.console("Window title set to '" + args[0] + ".'");
            }
        });
        cmdlmerc.addCommand(new Command("setDeltaFactor", "Sets the delta factor, or, the number by which delta is multiplied by, to a given number.")
        {
            @Override
            public void run(String... args)
            {
                setDeltaFactor(Float.valueOf(args[0]));
                Logger.console("Delta factor set to " + args[0]);
            }
        });
        cmdlmerc.addCommand(new Command("setUpdateFreeze", "Sets the freezing of the update.")
        {
            @Override
            public void run(String... args)
            {
                setUpdateFreeze(Boolean.valueOf(args[0]));
                Logger.console("Set update freeze to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("setRenderFreeze", "Sets the freezing of the render.")
        {
            @Override
            public void run(String... args)
            {
                setRenderFreeze(Boolean.valueOf(args[0]));
                Logger.console("Set render freeze to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("echo", "Echos your every word.")
        {
            @Override
            public void run(String... args)
            {
                for (String s : args)
                    Logger.console(s);
            }
        });
        cmdlmerc.addVariable(new Variable("fps")
        {
            @Override
            public String get(String... args)
            {
                return String.valueOf(getFps());
            }
        });
        
        // Add in the finished CommandList
        CommandList.addCommandList(cmdlmerc);
        
        Logger.debug("Booting Developers Console Thread...");
        consolethread.setName("merc_devconsole");
        consolethread.start();
        
        Logger.debug("Starting Task Timing Thread...");
        
        TaskTiming.init();
        
        Logger.debug("Ready to begin game loop. Awaiting permission from Core...");
    }
    
    /**
     * The main game loop of the library.
     */
    public void run()
    {
        Logger.debug("Run permission granted by Core...");
        Logger.debug("Starting Game Loop...");
        
        running = true;
        
        Logger.line();
        Logger.log("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
        Logger.line();
        Logger.line();
        
        if (splashes.size() == 0)
        {
            splashed = true;
            
            Logger.debug("No splashes loaded by Core.");
        }
        
        // To the main loop!
        
        int _FPS = 0;
        long lastfps;
        
        /*
         * Initial 'last time...' Otherwise the first delta will be about
         * 50000000.
         */
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
            
            if (!renderfreeze)
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if (!updatefreeze)
                if (splashed)
                {
                    try
                    {
                        core.update(getDelta());
                    } catch (MERCuryException e)
                    {
                        e.printStackTrace();
                    }
                    input.poll();
                }
            
            if (!renderfreeze)
            {
                camera.pre(graphicsobject);
                {
                    if (splashed)
                        try
                        {
                            core.render(graphicsobject);
                        } catch (MERCuryException e)
                        {
                            e.printStackTrace();
                        }
                    else if (!splashes.get(splashidx).show(graphicsobject))
                        if (splashidx < splashes.size() - 1)
                            splashidx++;
                        else
                            splashed = true;
                }
                camera.post(graphicsobject);
            }
            
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
        
        Logger.debug("Cleaning up Developers Console");
        consolethread.interrupt();
        
        Logger.debug("Cleaning up Task Timing Thread...");
        
        TaskTiming.cleanup();
        
        Logger.debug("Cleaning up Core...");
        try
        {
            core.cleanup(RM);
        } catch (IOException | MERCuryException e)
        {
            e.printStackTrace();
        }
        Logger.debug("Cleaning up ResourceManager...");
        RM.cleanup();
        Logger.debug("Cleaning up plugins...");
        for (Plugin plug : plugs)
        {
            Logger.debug("     Cleaning up " + plug.getClass().getSimpleName() + "...");
            plug.cleanup();
        }
        Logger.debug("Cleanup complete.");
        Logger.debug("MERCury Game Library shutting down...");
    }
    
    /** @return The fps */
    public int getFps()
    {
        return FPS;
    }
    
    /**
     * @param target
     *            The new FPS target
     */
    public void setFpsTarget(int target)
    {
        FPS_TARGET = target;
    }
    
    /**
     * @param logfps
     *            Whether or not to log the FPS in the log
     */
    public void setLogFPS(boolean logfps)
    {
        this.logfps = logfps;
    }
    
    /** @return The Width of the display */
    public int getWidth()
    {
        return Display.getWidth();
    }
    
    /** @return The Height of the display */
    public int getHeight()
    {
        return Display.getHeight();
    }
    
    /** @return The aspect ratio of the display WIDTH/HEIGHT */
    public float getAspectRatio()
    {
        return getWidth() / getHeight();
    }
    
    /**
     * @return Time in milliseconds
     */
    public float getTime()
    {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
    
    /**
     * Sleeps the thread for a few milliseconds
     * 
     * @param milliseconds
     *            The milliseconds to wait
     */
    public void sleep(long milliseconds) throws InterruptedException
    {
        Thread.sleep(milliseconds);
    }
    
    /**
     * @param grab
     *            Whether or not to grab the mouse
     */
    public void setMouseGrab(boolean grab)
    {
        Mouse.setGrabbed(grab);
    }
    
    /**
     * @param vsync
     *            Whether or not to vsync
     */
    public void setVsync(boolean vsync)
    {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }
    
    /** @return Whether or not the window has the focus */
    public boolean isFocused()
    {
        return Display.isActive();
    }
    
    /**
     * @param title
     *            The title of the window
     */
    public void setTitle(String title)
    {
        Display.setTitle(title);
    }
    
    /**
     * @param resizable
     *            The resizability of the window
     */
    public void setResizable(boolean resizable)
    {
        Display.setResizable(resizable);
        
        remakeDisplay();
    }
    
    /** Remakes the display */
    private void remakeDisplay()
    {
        Display.destroy();
        
        getCore().initDisplay(getWidth(), getHeight(), Display.isFullscreen(), vsync);
        
        graphicsobject = getCore().initGraphics();
    }
    
    /** Ends the loop */
    public void end()
    {
        running = false;
    }
    
    /** @return The delta time variable */
    public float getDelta()
    {
        return delta * deltafactor;
    }
    
    /**
     * @param factor
     *            The new delta factor
     */
    public void setDeltaFactor(float factor)
    {
        deltafactor = factor;
    }
    
    /** @return The core being ran */
    public Core getCore()
    {
        return core;
    }
    
    /**
     * @param freeze
     *            Whether or not to freeze the updating
     */
    public void setUpdateFreeze(boolean freeze)
    {
        updatefreeze = freeze;
    }
    
    /** @return The graphics object */
    public Graphics getGraphics()
    {
        return graphicsobject;
    }
    
    /**
     * @param freeze
     *            Whether or not to freeze the rendering
     */
    public void setRenderFreeze(boolean freeze)
    {
        renderfreeze = freeze;
    }
    
    /** @return The resource manager */
    public ResourceManager getResourceManager()
    {
        return RM;
    }
    
    /** @return The input node */
    public Input getInput()
    {
        return input;
    }
    
    /** @return The camera */
    public Camera getCamera()
    {
        return camera;
    }
    
    /**
     * @param splash
     *            The splash screen to add
     */
    public void addSplashScreen(SplashScreen splash)
    {
        splashes.add(splash);
    }
    
    /**
     * @param plugin
     *            The plugin to add
     */
    public void addPlugin(Plugin plugin)
    {
        plugs.add(plugin);
    }
    
    /**
     * @param name
     *            The name of the plugin you want
     * @return The plugin corresponding to name
     */
    public Plugin getPlugin(String name) throws PluginNotFoundException
    {
        for (Plugin plug : plugs)
            if (plug.getClass().getSimpleName().equalsIgnoreCase(name))
                return plug;
        throw new PluginNotFoundException("Plugin '" + name + "' not found!");
    }
    
    /** @return The singleton instance of Runner */
    public static Runner getInstance()
    {
        return singleton;
    }
}
