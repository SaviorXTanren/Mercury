package com.radirius.mercury.framework;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Shader;
import com.radirius.mercury.graphics.VAOGraphics;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.GraphicsUtils;
import com.radirius.mercury.utilities.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

/**
 * The Core that will host the game with help from the
 * Runner class.
 *
 * @author wessles, Jeviny, Sri Harsha Chilakapati
 */
public abstract class Core {
    private final Runner runner = Runner.getInstance();
    private final GameState defaultGameState = new GameState();
    GameState currentGameState = defaultGameState;
    public String name;
    public int width;
    public int height;
    public boolean fullscreen;
    public boolean vsync;
    public boolean multithread;
    public boolean devConsole;
    public boolean showDebug;
    public boolean showExtraDebug;
    public int targetFps;

    public Core() {
        this("Untitled Window", 1024, 640);
    }

    public Core(String name) {
        this(name, 1024, 640);
    }

    public Core(int width, int height) {
        this("Untitled Window", width, height);
    }

    public Core(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.fullscreen = false;
        this.vsync = true;
        this.multithread = false;
        this.devConsole = true;
        this.showDebug = false;
        this.showExtraDebug = false;
        this.targetFps = 60;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    public void setMultithreadedCore(boolean multithread) {
        this.multithread = multithread;
    }

    public void setDevelopersConsole(boolean devConsole) {
        this.devConsole = devConsole;
    }

    public void setTargetFps(int targetFps) {
        this.targetFps = targetFps;
    }

    public void setDebugDisplays(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public void setExtraDebugDisplays(boolean showExtraDebug) {
        this.showExtraDebug = showExtraDebug;
    }

    /**
     * Switches to a GameState and after alerting the
     * current GameState.
     *
     * @param currentgamestate The GameState to switch to.
     */
    public void switchGameState(GameState currentgamestate) {
        this.currentGameState.onLeave();
        this.currentGameState = currentgamestate;
        this.currentGameState.onEnter();
    }

    /**
     * @return The current GameState.
     */
    public GameState getCurrentState() {
        return currentGameState;
    }

    /**
     * Initializes and then runs the Runner.
     */
    public void start() {
        runner.init(this, width, height, fullscreen, vsync, multithread, devConsole, showDebug, showExtraDebug, targetFps);
        runner.run();
    }

    /**
     * @return The core's runner.
     */
    public Runner getRunner() {
        return runner;
    }

    /**
     * Used to initialize all resources, and for whatever
     * you wish to do for initialization. May run on a
     * seperate thread, while the main Thread continues to
     * the game loop.
     */
    public abstract void init();

    /**
     * Called once every frame and used to handle all game
     * logic.
     *
     * @param delta The delta time.
     */
    public abstract void update(float delta);

    /**
     * Called once every frame and used to render graphics.
     *
     * @param g The Graphics object for rendering.
     */
    public abstract void render(Graphics g);

    /**
     * Called when the game loop is ended.
     */
    public abstract void cleanup();

    /**
     * Initializes the display.
     *
     * @param width      The width of the display.
     * @param height     The height of the display.
     * @param fullscreen Whether or not fullscreen is enabled.
     * @param vsync      Whether or not v-sync is used.
     */
    public void initDisplay(int width, int height, boolean fullscreen, boolean vsync) {
        try {
            Display.setVSyncEnabled(vsync);

            DisplayMode dimensions = new DisplayMode(width, height);

            boolean matchedDimensions = false;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();

                for (DisplayMode mode : modes) {
                    if (mode.getWidth() == width && mode.getHeight() == height && mode.isFullscreenCapable()) {
                        dimensions = mode;
                        matchedDimensions = true;
                    }
                }

                if (!matchedDimensions)
                    Logger.warn("Dimensions " + width + "x" + height + " is not supported! Disabling fullscreen.");
                else
                    Display.setFullscreen(true);
            }

            Display.setDisplayMode(dimensions);
            Display.setTitle(name);

            PixelFormat format = new PixelFormat();
            ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true);

            Display.create(format, contextAttribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        Runner.getInstance().setIcon(Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x16.png"),
                Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x32.png"),
                Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x64.png"));
    }

    /**
     * Initializes graphics & handle OpenGL initialization
     * calls.
     */
    public Graphics initGraphics() {
        GraphicsUtils.getProjectionMatrix().initOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        VAOGraphics graphicsObject = new VAOGraphics();

        Shader.loadDefaultShaders();
        Shader.releaseShaders();

        return graphicsObject;
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
