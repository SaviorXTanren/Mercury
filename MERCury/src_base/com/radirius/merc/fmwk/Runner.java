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
    private final static Runner singleton = new Runner();

    public boolean running = false;

    private final ArrayList<SplashScreen> splashes = new ArrayList<SplashScreen>();
    private final ArrayList<Plugin> plugs = new ArrayList<Plugin>();

    private final CommandThread consolerunnable = new CommandThread();
    private final Thread consolethread = new Thread(consolerunnable);

    private boolean updatefreeze = false, renderfreeze = false;

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

    private Runner()
    {
    }

    public void init(Core core, int WIDTH, int HEIGHT)
    {
        init(core, WIDTH, HEIGHT, false);
    }

    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen)
    {
        init(core, WIDTH, HEIGHT, fullscreen, false, true);
    }

    public void init(Core core, boolean fullscreen, boolean vsync)
    {
        init(core, Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), fullscreen, vsync, true);
    }

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
        Logger.log("Maitenance Enhanced and Reliable Coding Engine");
        Logger.log("             Designed by Radirius");
        Logger.log("                                             ");
        Logger.log("             You may read the list of contributors");
        Logger.log("             On the official MERCury website:");
        Logger.log("             http://weslgames.github.io/MERCury/");
        Logger.log("                                             ");
        Logger.log("             Email wessles@wessles.com for support");
        Logger.log("             Go to www.reddit.com/r/MERCuryGameEngine for community");
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
            Logger.debug("     Initializing " + plug.getClass().getSimpleName() + "...");
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
            public void run(String... args)
            {
                end();
                Logger.console("Ending MERCury...");
            }
        });
        cmdlmerc.addCommand(new Command("togglefreeze", "Toggles the freezing of the update and the freezing of the rendering.")
        {
            public void run(String... args)
            {
                setUpdateFreeze(!updatefreeze);
                setRenderFreeze(!renderfreeze);
                Logger.console("Update Freeze changed to " + updatefreeze + ", and Render Freeze changed to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("setFpsTarget", "merc setFpsTarget [Fps Target]\nTargets for, or caps the framerate at a given height.")
        {
            public void run(String... args)
            {
                setFpsTarget(Integer.parseInt(args[0]));
                Logger.console("Framerate targeted for " + FPS_TARGET);
            }
        });
        cmdlmerc.addCommand(new Command("setMouseGrab", "merc setMouseGrab [True/False]\nLocks or releases the mouse from the window.")
        {
            public void run(String... args)
            {
                setMouseGrab(Boolean.valueOf(args[0]));
                Logger.console("Mouse " + (Boolean.valueOf(args[0]) ? "grabbed" : "released") + ".");
            }
        });
        cmdlmerc.addCommand(new Command("setVsync", "Sets whether or not there is Vertical Sync.")
        {
            public void run(String... args)
            {
                setVsync(Boolean.valueOf(args[0]));
                Logger.console("Vsync set to " + args[0]);
            }
        });
        cmdlmerc.addCommand(new Command("setTitle", "Sets the title of the window.")
        {
            public void run(String... args)
            {
                setTitle(args[0]);
                Logger.console("Window title set to '" + args[0] + ".'");
            }
        });
        cmdlmerc.addCommand(new Command("setDeltaFactor", "Sets the delta factor, or, the number by which delta is multiplied by, to a given number.")
        {
            public void run(String... args)
            {
                setDeltaFactor(Float.valueOf(args[0]));
                Logger.console("Delta factor set to " + args[0]);
            }
        });
        cmdlmerc.addCommand(new Command("setUpdateFreeze", "Sets the freezing of the update.")
        {
            public void run(String... args)
            {
                setUpdateFreeze(Boolean.valueOf(args[0]));
                Logger.console("Set update freeze to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("setRenderFreeze", "Sets the freezing of the render.")
        {
            public void run(String... args)
            {
                setRenderFreeze(Boolean.valueOf(args[0]));
                Logger.console("Set render freeze to " + renderfreeze);
            }
        });
        cmdlmerc.addCommand(new Command("echo", "Echos your every word.")
        {
            public void run(String... args)
            {
                for (String s : args)
                    Logger.console(s);
            }
        });
        cmdlmerc.addVariable(new Variable("fps")
        {
            public String get(String... args)
            {
                return String.valueOf(getFps());
            }
        });

        // Add in the finished CommandList
        CommandList.addCommandList(cmdlmerc);

        Logger.debug("Booting Developers Console Thread...");
        consolethread.start();

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
                    try
                    {
                        core.update(getDelta());
                    } catch (MERCuryException e)
                    {
                        e.printStackTrace();
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
        Logger.debug("MERCury Game Engine shutting down...");
    }

    public int getFps()
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

    public int getWidth()
    {
        return Display.getWidth();
    }

    public int getHeight()
    {
        return Display.getHeight();
    }

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

    public void sleep(long milliseconds) throws InterruptedException
    {
        Thread.sleep(milliseconds);
    }

    public void setMouseGrab(boolean grab)
    {
        Mouse.setGrabbed(grab);
    }

    public void setVsync(boolean vsync)
    {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }

    public boolean isFocused()
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

        getCore().initDisplay(getWidth(), getHeight(), Display.isFullscreen(), vsync);

        graphicsobject = getCore().initGraphics();
    }

    public void end()
    {
        running = false;
    }

    public float getDelta()
    {
        return delta * deltafactor;
    }

    public void setDeltaFactor(float factor)
    {
        deltafactor = factor;
    }

    public Core getCore()
    {
        return core;
    }

    public void setUpdateFreeze(boolean freeze)
    {
        updatefreeze = freeze;
    }

    public Graphics getGraphics()
    {
        return graphicsobject;
    }

    public void setRenderFreeze(boolean freeze)
    {
        renderfreeze = freeze;
    }

    public ResourceManager getResourceManager()
    {
        return RM;
    }

    public Input getInput()
    {
        return input;
    }

    public Camera getCamera()
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
