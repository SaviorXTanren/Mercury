package radirius.merc.framework;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import radirius.merc.command.CommandList;
import radirius.merc.command.CommandThread;
import radirius.merc.exceptions.MERCuryException;
import radirius.merc.graphics.Camera;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.input.Input;
import radirius.merc.logging.Logger;
import radirius.merc.splash.SplashScreen;
import radirius.merc.utils.TaskTiming;

/**
 * A class that will run your core, and give out the graphics object, current
 * core, resource manager, and input ly.
 * 
 * @author wessles
 */

public class Runner {
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
    
    /** Whether or not we are vsyncing */
    private boolean vsync;
    /** The delta variable */
    private int delta = 1;
    /** The target fps */
    private int FPS_TARGET = 60;
    /** The current fps */
    private int FPS;
    /** The last frame; used for calculating fps */
    private long lastframe;
    /** The factor by which delta is multiplied */
    private float deltafactor = 1;
    
    /**
     * The string to be rendered every frame to the screen, assuming that
     * `showdebug` is true.
     */
    private String debugdata = "";
    /** Whether or not the debugdata will be drawn to the screen. */
    private boolean showdebug = false;
    
    /** The core being ran */
    private Core core;
    
    /** The graphics object */
    private Graphics graphicsobject;
    
    /** The camera */
    private Camera camera;
    /** The input node */
    private Input input;
    
    // We don't want ANYONE attempting to create another. There is a singleton,
    // and you must use it.
    private Runner() {
    }
    
    /**
     * An object that will be used for initializing the Runner with default
     * values that can be modified.
     */
    @SuppressWarnings("unused")
    private static class InitSetup {
        public InitSetup(Core core, int WIDTH, int HEIGHT) {
            this.core = core;
            this.WIDTH = WIDTH;
            this.HEIGHT = HEIGHT;
        }
        
        /** The core we shall run */
        public Core core;
        /** The width of the display */
        public int WIDTH;
        /** The height of the display */
        public int HEIGHT;
        /** Whether or not the display is fullscreen */
        public boolean fullscreen;
        /** Whether or not we are vsynced */
        public boolean vsync;
        /** Whether or not to initialize the Core on a seperate thread */
        public boolean initonseperatethread;
        /** Whether or not we are enabling the developers console */
        public boolean devconsole;
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
    public void init(Core core, int WIDTH, int HEIGHT) {
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
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen) {
        init(core, WIDTH, HEIGHT, fullscreen, false, false, true);
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
    public void init(Core core, boolean fullscreen, boolean vsync) {
        init(core, Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), fullscreen, vsync, false, true);
    }
    
    /**
     * Initializes the library
     * 
     * @param iniset
     *            The initialization setup filled with information to initialize
     *            with.
     */
    public void init(InitSetup iniset) {
        init(iniset.core, iniset.WIDTH, iniset.HEIGHT, iniset.fullscreen, iniset.vsync, iniset.initonseperatethread, iniset.devconsole);
    }
    
    public boolean inited = false;
    
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
     * @param initonseperatethread
     *            Whether or not to initialize the Core on a seperate thread
     * @param devconsole
     *            Whether or not we are enabling the developers console
     */
    public void init(final Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, boolean initonseperatethread, boolean devconsole) {
        System.out.print("  _   _   _   _   _   _   _  \n" + " / \\ / \\ / \\ / \\ / \\ / \\ / \\\n" + "( M | E | R | C | U | R | Y ) Started\n" + " \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \n\n");
        
        Logger.debug("Making Core...");
        
        this.core = core;
        this.vsync = vsync;
        
        Logger.debug("Initializing Display...");
        
        this.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
        
        Logger.debug("Making Graphics...");
        
        graphicsobject = this.core.initGraphics();
        Logger.debug("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
        
        Logger.debug("Making Audio...");
        
        this.core.initAudio();
        
        Logger.debug("Initializing Camera...");
        
        camera = new Camera(0, 0);
        
        Logger.debug("Initializing Graphics...");
        
        graphicsobject.init();
        
        Logger.debug("Creating Input...");
        
        input = new Input();
        
        Logger.debug("Initializing Input...");
        
        input.create();
        
        Logger.debug("Starting plugins...");
        
        for (Plugin plug : plugs) {
            Logger.debug("\tInitializing " + plug.getClass().getSimpleName() + "...");
            plug.init();
        }
        
        Logger.debug("Initializing Core " + (initonseperatethread ? "on seperate Thread" : "") + "...");
        
        if (initonseperatethread) {
            Runnable initthread_run = new Runnable() {
                @Override
                public void run() {
                    core.init();
                    inited = true;
                }
            };
            Thread initthread = new Thread(initthread_run);
            initthread.run();
        } else {
            core.init();
            inited = true;
        }
        
        Logger.debug("Making and adding default CommandList 'merc...'");
        
        CommandList.addCommandList(CommandList.getDefaultCommandList());
        
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
    public void run() {
        Logger.debug("Starting Game Loop...");
        Logger.line();
        
        running = true;
        
        // To the main loop!
        
        int _FPS = 0;
        long lastfps;
        
        /*
         * Initial 'last time...' Otherwise the first delta will be about
         * 50000000.
         */
        lastfps = lastframe = Sys.getTime() * 1000 / Sys.getTimerResolution();
        
        while (running) {
            // Set time for FPS and Delta calculations
            long time = Sys.getTime() * 1000 / Sys.getTimerResolution();
            
            // Calculate delta
            delta = (int) (time - lastframe);
            
            // Update FPS
            if (time - lastfps < 1000)
                _FPS++;
            else {
                lastfps = time;
                FPS = _FPS;
                _FPS = 0;
            }
            
            if (FPS == 0)
                FPS = FPS_TARGET;
            
            // End all time calculations.
            lastframe = time;
            
            // Take in information from input.
            input.poll();
            
            if (!renderfreeze)
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if (!updatefreeze)
                core.update(getDelta());
            
            int verticeslastrendered = getVerticesLastRendered();
            
            if (!renderfreeze) {
                camera.pre(graphicsobject);
                core.render(graphicsobject);
                
                if (showdebug) {
                    addDebugData("FPS", getFPS() + "");
                    addDebugData("Vertices", verticeslastrendered + "");
                    
                    graphicsobject.drawString(debugdata, 1 / graphicsobject.getScale(), 0, 0);
                    debugdata = "";
                }
                
                camera.post(graphicsobject);
            }
            
            // Close the window if the window is x'd out.
            if (Display.isCloseRequested())
                end();
            
            Display.update();
            Display.sync(FPS_TARGET);
        }
        
        Logger.line();
        Logger.debug("Game Loop ended.");
        
        Logger.debug("Starting Cleanup...");
        
        Logger.debug("Cleaning up Developers Console");
        consolethread.interrupt();
        
        Logger.debug("Cleaning up Task Timing Thread...");
        
        TaskTiming.cleanup();
        
        Logger.debug("Cleaning up Core...");
        core.cleanup();
        Logger.debug("Cleaning up plugins...");
        for (Plugin plug : plugs) {
            Logger.debug("     Cleaning up " + plug.getClass().getSimpleName() + "...");
            plug.cleanup();
        }
        Logger.debug("Cleanup complete.");
        Logger.debug("MERCury Game Library shutting down...");
    }
    
    /** @return The fps */
    public int getFPS() {
        return FPS;
    }
    
    /** @return The vertices rendered in the last rendering frame. */
    public int getVerticesLastRendered() {
        return getGraphics().getBatcher().getVerticesLastRendered();
    }
    
    /**
     * @param target
     *            The new FPS target
     */
    public void setFpsTarget(int target) {
        FPS_TARGET = target;
    }
    
    /** Sets whether or not debug data should be displayed. */
    public void showDebug(boolean showdebug) {
        this.showdebug = showdebug;
    }
    
    /**
     * Adds information to the debugdata. Debug data is wiped every single
     * update frame, so this is to be called every frame.
     */
    public void addDebugData(String name, String value) {
        name.trim();
        value.trim();
        debugdata += name + " " + value + "\n";
    }
    
    /** @return The Width of the display */
    public int getWidth() {
        return Display.getWidth();
    }
    
    /** @return The Height of the display */
    public int getHeight() {
        return Display.getHeight();
    }
    
    /** @return The aspect ratio of the display WIDTH/HEIGHT */
    public float getAspectRatio() {
        return getWidth() / getHeight();
    }
    
    /** @return Time in milliseconds */
    public float getMillis() {
        return System.currentTimeMillis();
    }
    
    /** @return Time in seconds */
    public float getSeconds() {
        return System.currentTimeMillis() / 1000f;
    }
    
    /**
     * Sleeps the thread for a few milliseconds
     * 
     * @param milliseconds
     *            The milliseconds to wait
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param grab
     *            Whether or not to grab the mouse
     */
    public void setMouseGrab(boolean grab) {
        Mouse.setGrabbed(grab);
    }
    
    /**
     * @param vsync
     *            Whether or not to vsync
     */
    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }
    
    /** @return Whether or not the window has the focus */
    public boolean isFocused() {
        return Display.isActive();
    }
    
    /**
     * @param title
     *            The title of the window
     */
    public void setTitle(String title) {
        Display.setTitle(title);
    }
    
    /**
     * Sets the icon for given size(s). Reccomended sizes that you should put in
     * are x16, x32, and x64.
     */
    public void setIcon(InputStream... icons) {
        ArrayList<ByteBuffer> bufs = new ArrayList<ByteBuffer>();
        
        for (InputStream is : icons)
            if (is != null)
                try {
                    bufs.add(Texture.convertBufferedImageToBuffer(ImageIO.read(is)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        
        ByteBuffer[] bufarray = new ByteBuffer[bufs.size()];
        bufs.toArray(bufarray);
        Display.setIcon(bufarray);
    }
    
    /**
     * @param resizable
     *            The resizability of the window
     */
    public void setResizable(boolean resizable) {
        Display.setResizable(resizable);
        
        remakeDisplay();
    }
    
    /** Remakes the display */
    private void remakeDisplay() {
        Display.destroy();
        
        getCore().initDisplay(getWidth(), getHeight(), Display.isFullscreen(), vsync);
        
        graphicsobject = getCore().initGraphics();
    }
    
    /** Ends the loop */
    public void end() {
        running = false;
    }
    
    /** @return The delta time variable */
    public float getDelta() {
        return delta * deltafactor;
    }
    
    /**
     * @param factor
     *            The new delta factor
     */
    public void setDeltaFactor(float factor) {
        deltafactor = factor;
    }
    
    /** @return The core being ran */
    public Core getCore() {
        return core;
    }
    
    /**
     * @param freeze
     *            Whether or not to freeze the updating
     */
    public void setUpdateFreeze(boolean freeze) {
        updatefreeze = freeze;
    }
    
    /** @return The graphics object */
    public Graphics getGraphics() {
        return graphicsobject;
    }
    
    /**
     * @param freeze
     *            Whether or not to freeze the rendering
     */
    public void setRenderFreeze(boolean freeze) {
        renderfreeze = freeze;
    }
    
    /** @return The input node */
    public Input getInput() {
        return input;
    }
    
    /** @return The camera */
    public Camera getCamera() {
        return camera;
    }
    
    // The current splash screen.
    private int splidx = 0;
    
    public boolean showSplashScreens(Graphics g) {
        if (splidx > splashes.size() - 1)
            return true;
        
        if (!splashes.get(splidx).show(g))
            splidx++;
        
        return false;
    }
    
    /**
     * @param splash
     *            The splash screen to add
     */
    public void addSplashScreen(SplashScreen splash) {
        splashes.add(splash);
    }
    
    /**
     * @param plugin
     *            The plugin to add
     */
    public void addPlugin(Plugin plugin) {
        plugs.add(plugin);
    }
    
    /**
     * @param name
     *            The name of the plugin you want
     * @return The plugin corresponding to name
     */
    public Plugin getPlugin(String name) throws MERCuryException {
        for (Plugin plug : plugs)
            if (plug.getClass().getSimpleName().equalsIgnoreCase(name))
                return plug;
        throw new MERCuryException("Plugin '" + name + "' not found!");
    }
    
    /** @return The singleton instance of Runner */
    public static Runner getInstance() {
        return singleton;
    }
}
