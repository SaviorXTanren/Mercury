package com.radirius.mercury.framework;

import com.radirius.mercury.exceptions.MercuryException;
import com.radirius.mercury.framework.splash.SplashScreen;
import com.radirius.mercury.graphics.Camera;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Vector2f;
import com.radirius.mercury.scene.GameScene;
import com.radirius.mercury.utilities.TaskTiming;
import com.radirius.mercury.utilities.command.CommandList;
import com.radirius.mercury.utilities.command.CommandThread;
import com.radirius.mercury.utilities.logging.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * The heart of Mercury. Runs the Core and provides all of the various materials
 * required for your game.
 *
 * @authors wessles, Jeviny
 */
public class Runner {
    /**
     * The singleton instance of the Runner. This should be the only Runner
     * used.
     */
    private final static Runner singleton = new Runner();
    /**
     * A list of splash screens.
     */
    private final ArrayList<SplashScreen> splashes = new ArrayList<SplashScreen>();
    /**
     * A list of plugins.
     */
    private final ArrayList<Plugin> plugins = new ArrayList<Plugin>();
    /**
     * A Runnable for the console thread.
     */
    private final CommandThread consoleRunnable = new CommandThread();
    /**
     * A Thread for the console.
     */
    private final Thread consoleThread = new Thread(consoleRunnable);
    /**
     * Whether or not the Runner is running.
     */
    public boolean running = false;
    public boolean initialized = false;
    /**
     * Whether or not the game is being updated.
     */
    private boolean updating = true;
    /**
     * Whether or not the game is being rendered.
     */
    private boolean rendering = true;
    /**
     * Whether or not v-sync is enabled.
     */
    private boolean vsync;
    /**
     * The delta variable.
     */
    private int delta = 1;
    /**
     * The target framerate.
     */
    private int targetFps = 60;
    /**
     * The current framerate.
     */
    private int fps;
    /**
     * The last frame. Used for calculating the framerate.
     */
    private long lastFrame;
    /**
     * The factor by which the delta time is multiplied.
     */
    private float deltaFactor = 1;
    /**
     * A string that holds debugging data to be rendered to the screen, should
     * `showDebug` be true.
     */
    private String debugData = "";
    /**
     * Whether or not the debug data will be drawn to the screen.
     */
    private boolean showDebug = false;
    /**
     * Whether or not the extra Mercury debug data will be shown in the console.
     */
    private boolean showExtraDebug = false;
    /**
     * The Core being ran.
     */
    private Core core;
    /**
     * The graphics object.
     */
    private Graphics graphics;
    /**
     * The camera object.
     */
    private Camera camera;
    /**
     * The input object.
     */
    private Input input;
    /**
     * The current splash screen.
     */
    private int splidx = 0;

    /*
     * We don't want anybody attempting to create another Runner, there's a
     * singleton and it should be put to use.
     */
    private Runner() {
    }

    /**
     * @return The singleton instance of Runner.
     */
    public static Runner getInstance() {
        return singleton;
    }

    /**
     * Initializes the library
     *
     * @param core        The Core to be ran.
     * @param width       The width of the display.
     * @param height      The height of the display.
     * @param fullscreen  Whether or not fullscreen is enabled.
     * @param vsync       Whether or not v-sync is enabled.
     * @param multithread Whether or not the Core is initialized on a separate thread.
     * @param devConsole  Whether or not the developers console is enabled.
     * @param targetFps   The target framerate.
     */
    public void init(final Core core, int width, int height, boolean fullscreen, boolean vsync, boolean multithread, boolean devConsole, boolean showDebug, boolean showExtraDebug, int targetFps) {
        if (showExtraDebug) {
            System.out.println("Mercury Game Library (In-Dev)\n" + "Designed by Radirius\n" + "Website: http://mercurylib.com/");
            System.out.println("-------------------------------");
        }

        Logger.warn("You're running a non-stable build of Mercury!\nIf you run into any issues, please leave an issue on GitHub or make a post on the forum.");

        if (showExtraDebug) {
            Logger.info("Mercury Starting:");
            Logger.info("Making Core...");
            this.core = core;
            this.vsync = vsync;

            Logger.info("Making Display & Graphics...");
            this.core.initDisplay(width, height, fullscreen, vsync);

            graphics = this.core.initGraphics();
            Logger.info("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
            Logger.info("Display Mode: " + Display.getDisplayMode());

            Logger.info("Starting Graphics...");
            graphics.init();

            Logger.info("Making Audio...");
            this.core.initAudio();

            Logger.info("Making Camera...");
            camera = new Camera(0, 0);

            Logger.info("Making Input...");
            input = new Input();
            input.create();

            Logger.info("Making Plugins...");
            for (Plugin plugin : plugins) {
                Logger.info("\tInitializing " + plugin.getName() + "...");

                plugin.init();
            }

            Logger.info("Starting Core" + (multithread ? " (On Separate Thread)" : "") + "...");

            if (multithread) {
                Runnable initthread_run = new Runnable() {
                    @Override
                    public void run() {
                        core.init();

                        initialized = true;
                    }
                };

                Thread initthread = new Thread(initthread_run);

                initthread.run();
            }

            Logger.info("Making Default CommandList 'mercury...'");
            CommandList.addCommandList(CommandList.getDefaultCommandList());

            Logger.info("Starting Developer Console Thread...");
            consoleThread.setName("mercury_devConsole");
            consoleThread.start();

            this.showDebug = showDebug;
            this.showExtraDebug = showExtraDebug;
            this.targetFps = targetFps;

            Logger.info("Ready to begin game loop. Awaiting permission from Core...");
        } else {
            this.core = core;
            this.vsync = vsync;
            this.core.initDisplay(width, height, fullscreen, vsync);

            graphics = this.core.initGraphics();
            graphics.init();

            this.core.initAudio();

            camera = new Camera(0, 0);

            input = new Input();
            input.create();

            for (Plugin plugin : plugins) {
                Logger.info("\tInitializing " + plugin.getName() + "...");

                plugin.init();
            }

            if (multithread) {
                Runnable initthread_run = new Runnable() {
                    @Override
                    public void run() {
                        core.init();

                        initialized = true;
                    }
                };

                Thread initthread = new Thread(initthread_run);

                initthread.run();
            }

            CommandList.addCommandList(CommandList.getDefaultCommandList());

            consoleThread.setName("mercury_devConsole");
            consoleThread.start();

            this.showDebug = showDebug;
            this.showExtraDebug = showExtraDebug;
            this.targetFps = targetFps;
        }
    }

    /**
     * The main game loop.
     */
    public void run() {
        if (showExtraDebug) {
            Logger.info("Starting Game Loop...");
            Logger.newLine();
        }

        core.init();
        GameScene.init();

        initialized = true;

        running = true;

        int FPS1 = 0;
        long lastfps;

		/*
         * Initial 'last time...' Otherwise the first delta will be about
		 * 50000000.
		 */
        lastfps = lastFrame = Sys.getTime() * 1000 / Sys.getTimerResolution();

        while (running) {
            // Set time for FPS and Delta calculations
            long time = Sys.getTime() * 1000 / Sys.getTimerResolution();

            // Calculate delta
            delta = (int) (time - lastFrame);

            // Update FPS
            if (time - lastfps < 1000)
                FPS1++;
            else {
                lastfps = time;
                fps = FPS1;
                FPS1 = 0;
            }

            if (fps == 0)
                fps = targetFps;

            lastFrame = time;

            input.poll();

            if (rendering)
                glClear(GL_COLOR_BUFFER_BIT);

            if (updating) {
                core.update(getDelta());

                if (core.currentGameState != null)
                    if (!core.currentGameState.auto)
                        core.currentGameState.update(getDelta());

                GameScene.update(getDelta());
            }

            // Update timing
            TaskTiming.update();

            if (rendering) {
                // Pre-Render Camera
                camera.pre(graphics);

                // Render Game
                if (showSplashScreens(graphics)) {
                    core.render(graphics);

                    if (core.currentGameState != null)
                        if (core.currentGameState.auto)
                            core.currentGameState.render(graphics);

                    GameScene.render(graphics);
                }

                // Debug
                if (showDebug) {
                    addDebugData("FPS", String.valueOf(getFps()));

                    graphics.setFont(TrueTypeFont.ROBOTO_REGULAR);
                    graphics.setColor(Color.WHITE);
                    graphics.drawString(debugData, 1 / graphics.getScale(), 8, 4);
                    debugData = "";
                }

                // Post-Render Camera
                camera.post(graphics);
            }

            // Close the window if the window is closed.
            if (Display.isCloseRequested())
                end();

            // Update and sync the FPS.
            Display.update();
            Display.sync(targetFps);
        }

        glClear(GL_COLOR_BUFFER_BIT);

        Display.update();

        Logger.newLine();

        if (showExtraDebug) {
            Logger.info("Ending Game Loop...");
            Logger.info("Beginning Clean Up:");

            Logger.info("Cleaning Up Developers Console...");
            consoleThread.interrupt();

            Logger.info("Cleaning Up Core & Plugins...");
            GameScene.cleanup();
            core.cleanup();

            for (Plugin plugin : plugins) {
                Logger.info("     Cleaning Up '" + plugin.getName() + "' Plugin...");

                plugin.cleanup();
            }

            Logger.info("Clean Up Complete.");
            Logger.info("Mercury Shutting Down...");
        } else {
            consoleThread.interrupt();

            GameScene.cleanup();
            core.cleanup();

            for (Plugin plugin : plugins) {
                Logger.info("\tCleaning Up '" + plugin.getName() + "' Plugin...");

                plugin.cleanup();
            }
        }
    }

    /**
     * @return The framerate.
     */
    public int getFps() {
        return fps;
    }

    /**
     * @return The vertices rendered in the last rendering frame.
     */
    public int getVerticesLastRendered() {
        return getGraphics().getBatcher().getVerticesLastRendered();
    }

    /**
     * @param targetFps The new FPS target
     */
    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
    }

    /**
     * Sets whether or not debug data should be displayed.
     *
     * @param showDebug Whether or not debug info is to be shown onscreen.
     */
    public void showDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    /**
     * Adds information to the debugdata. Debug data is wiped every single
     * update frame, so this is to be called every frame.
     *
     * @param name  The name of the debug information.
     * @param value The value of the debug information.
     */
    public void addDebugData(String name, String value) {
        name.trim();
        value.trim();

        debugData += name + ": " + value + "\n";
    }

    /**
     * @return The width of the display.
     */
    public int getWidth() {
        return Display.getWidth();
    }

    /**
     * @return The height of the display.
     */
    public int getHeight() {
        return Display.getHeight();
    }

    /**
     * @return The horizontal center of the display.
     */
    public int getCenterX() {
        return Display.getWidth() / 2;
    }

    /**
     * @return The vertical center of the display.
     */
    public int getCenterY() {
        return Display.getHeight() / 2;
    }

    /**
     * @return The center of the display.
     */
    public Vector2f getCenter() {
        return new Vector2f(getCenterX(), getCenterY());
    }

    /**
     * @return The aspect ratio of the display.
     */
    public float getAspectRatio() {
        return (float) getWidth() / (float) getHeight();
    }

    /**
     * @return Time in milliseconds.
     */
    public float getMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @return Time in seconds.
     */
    public float getSeconds() {
        return getMillis() / 1000f;
    }

    /**
     * Sleeps the thread for a few milliseconds.
     *
     * @param milliseconds The milliseconds to wait.
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
     * @param grab Whether or not to grab the mouse.
     */
    public void enableMouseGrab(boolean grab) {
        Mouse.setGrabbed(grab);
    }

    /**
     * Sets whether or not v-sync is enabled.
     *
     * @param vsync Whether or not to use v-sync.
     */
    public void enableVsync(boolean vsync) {
        this.vsync = vsync;

        Display.setVSyncEnabled(vsync);
    }

    /**
     * @return Whether or not the window has the focus.
     */
    public boolean isFocused() {
        return Display.isActive();
    }

    /**
     * Sets the title of the window.
     *
     * @param title The title of the window.
     */
    public void setTitle(String title) {
        Display.setTitle(title);
    }

    /**
     * Sets the icon for given size(s). Recommended sizes that you should put in
     * are x16, x32, and x64.
     *
     * @param icons Icon(s) for the game.
     */
    public void setIcon(InputStream... icons) {
        ArrayList<ByteBuffer> buffers = new ArrayList<ByteBuffer>();

        for (InputStream is : icons)
            if (is != null)
                try {
                    buffers.add(Texture.convertBufferedImageToBuffer(ImageIO.read(is)));
                } catch (IOException e) {
                    e.printStackTrace();
                }

        ByteBuffer[] bufferarray = new ByteBuffer[buffers.size()];

        buffers.toArray(bufferarray);
        Display.setIcon(bufferarray);
    }

    /**
     * Sets the resizability of the window.
     *
     * @param resizable The resizability of the window
     */
    public void setResizable(boolean resizable) {
        Display.setResizable(resizable);

        remakeDisplay();
    }

    /**
     * Remakes the display.
     */
    private void remakeDisplay() {
        Display.destroy();

        getCore().initDisplay(getWidth(), getHeight(), Display.isFullscreen(), vsync);

        graphics = getCore().initGraphics();
    }

    /**
     * Ends the loop.
     */
    public void end() {
        running = false;

        // Cleanup the Graphics
        graphics.cleanup();
    }

    /**
     * @return The delta time variable.
     */
    public float getDelta() {
        return delta * deltaFactor;
    }

    /**
     * Sets the factor by which the delta time will be multiplied.
     *
     * @param factor The new delta factor.
     */
    public void setDeltaFactor(float factor) {
        deltaFactor = factor;
    }

    /**
     * @return Get the Core being ran.
     */
    public Core getCore() {
        return core;
    }

    /**
     * Enables/Disables all updating.
     *
     * @param updating Whether or not to stop updating.
     */
    public void enableUpdating(boolean updating) {
        this.updating = updating;
    }

    /**
     * @return The graphics object.
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * Enables/Disables all rendering.
     *
     * @param rendering Whether or not to stop rendering.
     */
    public void enableRendering(boolean rendering) {
        this.rendering = rendering;
    }

    /**
     * @return The camera object.
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * @return The input object.
     */
    public Input getInput() {
        return input;
    }

    public void setViewport(int x, int y, int width, int height) {
        glViewport(x, y, width, height);
    }

    /**
     * Shows the current splash screen.
     *
     * @return Whether there aren't any more splash screens to be shown.
     */
    private boolean showSplashScreens(Graphics g) {
        if (splidx > splashes.size() - 1)
            return true;

        if (!splashes.get(splidx).show(g))
            splidx++;

        return false;
    }

    /*
     * @return Whether the splashes screens are being shown.
     */
    public boolean showingSplashScreens() {
        if (splidx > splashes.size() - 1)
            return false;

        return true;
    }

    /**
     * Adds a splash screen to the queue.
     *
     * @param splash The splash screen to add.
     */
    public void addSplashScreen(SplashScreen splash) {
        splashes.add(splash);
    }

    /**
     * Adds a plugin.
     *
     * @param plugin The plugin to add.
     */
    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    /**
     * @param name The name of the plugin you want.
     * @return The plugin corresponding to name.
     */
    public Plugin getPlugin(String name) throws MercuryException {
        for (Plugin plugin : plugins)
            if (plugin.getName().equalsIgnoreCase(name))
                return plugin;

        throw new MercuryException("Plugin '" + name + "' was not found!");
    }
}

// no comment
