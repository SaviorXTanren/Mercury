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

import radirius.merc.exceptions.MERCuryException;
import radirius.merc.framework.splash.SplashScreen;
import radirius.merc.graphics.Camera;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.input.Input;
import radirius.merc.utilities.TaskTiming;
import radirius.merc.utilities.command.CommandList;
import radirius.merc.utilities.command.CommandThread;
import radirius.merc.utilities.logging.Logger;

/**
 * The heart of MERCury. Runs the Core and provides the various materials required for your game.
 * 
 * @author wessles
 */

public class Runner {
    /** The singleton instance of the Runner. This should be the only Runner used. */
    private final static Runner singleton = new Runner();
    
    /** Whether or not the Runner is running. */
    public boolean running = false;
    
    /** A list of splash screens. */
    private final ArrayList<SplashScreen> splashes = new ArrayList<SplashScreen>();
    /** A list of plugins. */
    private final ArrayList<Plugin> plugs = new ArrayList<Plugin>();
    
    /** A Runnable for the console thread. */
    private final CommandThread consolerunnable = new CommandThread();
    /** A Thread for the console. */
    private final Thread consolethread = new Thread(consolerunnable);
    
    /** Whether or not the game is being updated. */
    private boolean updatefreeze = false;
    /** Whether or not the game is being rendered. */
    private boolean renderfreeze = false;
    
    /** Whether or not v-sync is enabled. */
    private boolean vsync;
    /** The delta variable. */
    private int delta = 1;
    /** The target framerate. */
    private int FPS_TARGET = 120;
    /** The current framerate. */
    private int FPS;
    /** The last frame. Used for calculating the framerate. */
    private long lastframe;
    /** The factor by which the delta time is multiplied. */
    private float deltafactor = 1;
    
    /** A string that holds debugging data to be rendered to the screen, should `showdebug` be true. */
    private String debugdata = "";
    /** Whether or not the debugdata will be drawn to the screen. */
    private boolean showdebug = false;
    
    /** The core being ran. */
    private Core core;
    
    /** The graphics object. */
    private Graphics graphicsobject;
    
    /** The camera object. */
    private Camera camera;
    /** The input node. */
    private Input input;
    
    // We don't want anybody attempting to create another Runner.
    // There's a singleton and it should be put to use.
    private Runner() {
    }
    
    /**
     * An object that will be used for initializing the Runner with default
     * values that can be modified.
     */
    public static class InitSetup {
        public InitSetup(Core core, int WIDTH, int HEIGHT) {
            this.core = core;
            this.WIDTH = WIDTH;
            this.HEIGHT = HEIGHT;
        }
        
        /** The Core to be ran. */
        public Core core;
        /** The width of the display. */
        public int WIDTH;
        /** The height of the display. */
        public int HEIGHT;
        /** Whether or not fullscreen is enabled. */
        public boolean fullscreen = false;
        /** Whether or not v-sync is enabled. */
        public boolean vsync = true;
        /** Whether or not the Core is initialized on a separate thread. */
        public boolean initonseparatethread = false;
        /** Whether or not the developers console is enabled. */
        public boolean devconsole = true;
    }
    
    /**
     * Initializes MERCury.
     * 
     * @param core
     *            The Core to be ran.
     * @param WIDTH
     *            The width of the display.
     * @param HEIGHT
     *            The height of the display.
     */
    public void init(Core core, int WIDTH, int HEIGHT) {
        init(core, WIDTH, HEIGHT, false);
    }
    
    /**
     * Initializes MERCury.
     * 
     * @param core
     *            The Core to be ran.
     * @param WIDTH
     *            The width of the display.
     * @param HEIGHT
     *            The height of the display.
     * @param fullscreen
     *            Whether or not fullscreen is enabled.
     */
    public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen) {
        init(core, WIDTH, HEIGHT, fullscreen, true, false, true);
    }
    
    /**
     * Initializes MERCury.
     * 
     * @param core
     *            The Core to be ran.
     * @param fullscreen
     *            Whether or not fullscreen is enabled.
     * @param vsync
     *            Whether or not v-sync is enabled.
     */
    public void init(Core core, boolean fullscreen, boolean vsync) {
        init(core, Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), fullscreen, vsync, false, true);
    }
    
    /**
     * Initializes MERCury.
     * 
     * @param iniset
     *            The initialization setup filled with information to initialize
     *            with.
     */
    public void init(InitSetup iniset) {
        init(iniset.core, iniset.WIDTH, iniset.HEIGHT, iniset.fullscreen, iniset.vsync, iniset.initonseparatethread, iniset.devconsole);
    }
    
    public boolean inited = false;
    
    /**
     * Initializes the library
     * 
     * @param core
     *            The Core to be ran.
     * @param WIDTH
     *            The width of the display.
     * @param HEIGHT
     *            The height of the display.
     * @param fullscreen
     *            Whether or not fullscreen is enabled.
     * @param vsync
     *            Whether or not v-sync is enabled.
     * @param initonseparatethread
     *            Whether or not the Core is initialized on a separate thread.
     * @param devconsole
     *            Whether or not the developers console is enabled.
     */
    public void init(final Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, boolean initonseparatethread, boolean devconsole) {
        // Little in-code splash.
        System.out.print("  _   _   _   _   _   _   _  \n" + " / \\ / \\ / \\ / \\ / \\ / \\ / \\\n" + "( M | E | R | C | U | R | Y ) Started\n" + " \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \n\n");
        
        // Lots of initialization that is self explanatory.
        Logger.debug("Making Core...");
        this.core = core;
        this.vsync = vsync;
        
        Logger.debug("Initializing Display...");
        this.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
        
        Logger.debug("Making Graphics...");
        graphicsobject = this.core.initGraphics();
        Logger.debug("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
        Logger.debug("Display Mode:" + Display.getDisplayMode());
        
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
        
        Logger.debug("Initializing Core " + (initonseparatethread ? "on separate Thread" : "") + "...");
        if (initonseparatethread) {
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
        
        Logger.debug("Booting Developer Console Thread...");
        consolethread.setName("merc_devconsole");
        consolethread.start();
        
        Logger.debug("Starting Task Timing Thread...");
        TaskTiming.init();
        
        Logger.debug("Ready to begin game loop. Awaiting permission from Core...");
    }
    
    /**
     * The main game loop.
     */
    public void run() {
        Logger.debug("Starting Game Loop...");
        Logger.line();
        
        running = true;
        
        //
        
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
            
            // Clear OpenGL buffers
            if (!renderfreeze)
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            // If not frozen, update.
            if (!updatefreeze)
                core.update(getDelta());
            
            // Update vertices last rendered variable.
            int verticeslastrendered = getVerticesLastRendered();
            
            if (!renderfreeze) {
                // Pre-render
                camera.pre(graphicsobject);
                // Render
                core.render(graphicsobject);
                
                // Debug
                if (showdebug) {
                    addDebugData("FPS", getFPS() + "");
                    addDebugData("Vertices", verticeslastrendered + "");
                    
                    graphicsobject.drawString(debugdata, 1 / graphicsobject.getScale(), 0, 0);
                    debugdata = "";
                }
                
                // Post-render
                camera.post(graphicsobject);
            }
            
            // Close the window if the window is x'd out.
            if (Display.isCloseRequested())
                end();
            
            // Update and sync the FPS.
            Display.update();
            Display.sync(FPS_TARGET);
        }
        
        // End the loop, cleanup things.
        
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
    
    /** @return The Framerate. */
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
    
    /**
     * Sets whether or not debug data should be displayed.
     * 
     * @param showdebug
     *            Whether or not debug is to be shown onscreen.
     */
    public void showDebug(boolean showdebug) {
        this.showdebug = showdebug;
    }
    
    /**
     * Adds information to the debugdata. Debug data is wiped every single
     * update frame, so this is to be called every frame.
     * 
     * @param name
     *            The name of the debug information.
     * @param value
     *            The value of the debug information.
     */
    public void addDebugData(String name, String value) {
        name.trim();
        value.trim();
        
        debugdata += name + " " + value + "\n";
    }
    
    /** @return The width of the display. */
    public int getWidth() {
        return Display.getWidth();
    }
    
    /** @return The height of the display. */
    public int getHeight() {
        return Display.getHeight();
    }
    
    /** @return The aspect ratio of the display. */
    public float getAspectRatio() {
        return getWidth() / getHeight();
    }
    
    /** @return Time in milliseconds. */
    public float getMillis() {
        return System.currentTimeMillis();
    }
    
    /** @return Time in seconds. */
    public float getSeconds() {
        return getMillis() / 1000f;
    }
    
    /**
     * Sleeps the thread for a few milliseconds.
     * 
     * @param milliseconds
     *            The milliseconds to wait.
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Enables or disables mouse grabbing.
     * 
     * @param grab
     *            Whether or not to grab the mouse.
     */
    public void setMouseGrab(boolean grab) {
        Mouse.setGrabbed(grab);
    }
    
    /**
     * Sets whether or not v-sync is enabled.
     * 
     * @param vsync
     *            Whether or not to v-sync.
     */
    public void setVsync(boolean vsync) {
        this.vsync = vsync;
        Display.setVSyncEnabled(vsync);
    }
    
    /** @return Whether or not the window has the focus. */
    public boolean isFocused() {
        return Display.isActive();
    }
    
    /**
     * Sets the title of the window.
     * 
     * @param title
     *            The title of the window.
     */
    public void setTitle(String title) {
        Display.setTitle(title);
    }
    
    /**
     * Sets the icon for given size(s). Reccomended sizes that you should put in
     * are x16, x32, and x64.
     * 
     * @param icons
     *            Icon(s) for the game.
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
     * Sets the resizability of the window.
     * 
     * @param resizable
     *            The resizability of the window
     */
    public void setResizable(boolean resizable) {
        Display.setResizable(resizable);
        
        remakeDisplay();
    }
    
    /** Remakes the display. */
    private void remakeDisplay() {
        Display.destroy();
        
        getCore().initDisplay(getWidth(), getHeight(), Display.isFullscreen(), vsync);
        
        graphicsobject = getCore().initGraphics();
    }
    
    /** Ends the loop. */
    public void end() {
        running = false;
    }
    
    /** @return The delta time variable. */
    public float getDelta() {
        return delta * deltafactor;
    }
    
    /**
     * Sets the factor by which the delta time will be multiplied.
     * 
     * @param factor
     *            The new delta factor.
     */
    public void setDeltaFactor(float factor) {
        deltafactor = factor;
    }
    
    /** @return The core being ran. */
    public Core getCore() {
        return core;
    }
    
    /**
     * Sets the update freeze.
     * 
     * @param freeze
     *            Whether or not to freeze the updating.
     */
    public void setUpdateFreeze(boolean freeze) {
        updatefreeze = freeze;
    }
    
    /** @return The graphics object. */
    public Graphics getGraphics() {
        return graphicsobject;
    }
    
    /**
     * Sets the graphic freeze.
     * 
     * @param freeze
     *            Whether or not to freeze the rendering.
     */
    public void setRenderFreeze(boolean freeze) {
        renderfreeze = freeze;
    }
    
    /** @return The input node. */
    public Input getInput() {
        return input;
    }
    
    /** @return The camera object. */
    public Camera getCamera() {
        return camera;
    }
    
    // The current splash screen.
    private int splidx = 0;
    
    /**
     * Shows the current splash screen.
     * 
     * @return Whether there aren't any more splash screens to be shown.
     */
    public boolean showSplashScreens(Graphics g) {
        if (splidx > splashes.size() - 1)
            return true;
        
        if (!splashes.get(splidx).show(g))
            splidx++;
        
        return false;
    }
    
    /**
     * Adds a splash screen to the queue.
     * 
     * @param splash
     *            The splash screen to add.
     */
    public void addSplashScreen(SplashScreen splash) {
        splashes.add(splash);
    }
    
    /**
     * Adds a plugin.
     * 
     * @param plugin
     *            The plugin to add.
     */
    public void addPlugin(Plugin plugin) {
        plugs.add(plugin);
    }
    
    /**
     * @param name
     *            The name of the plugin you want.
     * @return The plugin corresponding to name.
     */
    public Plugin getPlugin(String name) throws MERCuryException {
        for (Plugin plug : plugs)
            if (plug.getClass().getSimpleName().equalsIgnoreCase(name))
                return plug;
        throw new MERCuryException("Plugin '" + name + "' not found!");
    }
    
    /** @return The singleton instance of Runner. */
    public static Runner getInstance() {
        return singleton;
    }
}
