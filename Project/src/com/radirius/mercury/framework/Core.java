package com.radirius.mercury.framework;

import com.radirius.mercury.audio.Audio;
import com.radirius.mercury.framework.splash.SplashScreen;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.utilities.TaskTiming;
import com.radirius.mercury.utilities.logging.Logger;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * The Core initializes the game, runs the game loop, and cleans up.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public abstract class Core {
	private static Core currentCore;

	/** @return The core that is currently running. */
	public static Core getCurrentCore() {
		return currentCore;
	}

	public String name;

	public Core(String name) {
		this.name = name;
	}

	public Core(int width, int height) {
		this("Untitled Window", width, height);
	}

	public Core(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}

	/**
	 * Initializes and then runs the game loop.
	 */
	public void start() {
		run();
	}

	/**
	 * Ends the loop.
	 */
	public void end() {
		running = false;
	}

	/**
	 * Used to initialize all resources, and for whatever you wish to do for initialization. May run on a seperate
	 * thread, while the main Thread continues to the game loop.
	 */
	public abstract void init();

	/**
	 * Called once every frame and used to handle all game logic.
	 */
	public abstract void update();

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


	/* Game state. */


	private final GameState defaultGameState = new GameState();
	private GameState currentGameState = defaultGameState;

	/**
	 * Updates the current game state.
	 */
	public void updateState() {
		if (currentGameState != null)
			currentGameState.update();
	}

	/**
	 * Renders the current game state.
	 *
	 * @param g The Graphics object for rendering.
	 */
	public void renderState(Graphics g) {
		if (currentGameState != null)
			currentGameState.render(g);
	}

	/**
	 * Switches to a GameState and after alerting the current GameState.
	 *
	 * @param currentGameState The GameState to switch to.
	 */
	public void switchGameState(GameState currentGameState) {
		this.currentGameState.onLeave();
		currentGameState.onEnter();
		this.currentGameState = null;
		this.currentGameState = currentGameState;
	}

	/**
	 * @return The current GameState.
	 */
	public GameState getCurrentState() {
		return currentGameState;
	}


	/* The initialization variables. */


	/** Whether or not the extra Mercury debug data will be shown in the console. */
	protected boolean showExtraDebug = false;
	/** Whether or not the debug data will be drawn to the screen. */
	protected boolean renderDebug = true;

	/** The width of the window */
	protected int width = 1280;
	/** The height of the window */
	protected int height = 720;
	/** Whether the window will attempt fullscreen */
	protected boolean fullscreen = false;
	/** The target framerate. */
	protected int targetFps = 60;
	/** Whether or not v-sync is enabled */
	protected boolean vsync = true;

	/* Private data. */

	/** A list of splash screens */
	private final ArrayList<SplashScreen> splashes = new ArrayList<>();
	/** The current splash screen */
	private int splidx = 0;

	/** Whether or not the Runner is running */
	private boolean running = false;

	/** A string that holds debugging data to be rendered to the screen, should `renderDebug` be true */
	private String debugData = "";

    /**
     * @return The current time in milliseconds
     */
    public double getCurrentTime() {
        return Sys.getTime() * 1000.0 / Sys.getTimerResolution();
    }

	/**
	 * Initializes the library, preparing for the loop
	 */
	public void run() {
		currentCore = this;

		/* Initializing, preparing for the game loop. */


		if (showExtraDebug) {
			System.out.println("Mercury Game Library (In-Dev)\n" + "Designed by Radirius\n" + "Website: http://mercurylib.com/");
			System.out.println("-------------------------------");
		}

		Logger.warn("You're running a non-stable build of Mercury!\nIf you run into any issues, please leave an issue on GitHub or make a post on the forum.");

		if (showExtraDebug)
			Logger.info("Mercury Starting:");

		if (showExtraDebug)
			Logger.info("Making Display & Graphics...");

		Window.initDisplay(name, width, height, fullscreen, vsync);

		graphics = Graphics.initGraphics();

		if (showExtraDebug) {
			Logger.info("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
			Logger.info("Display Mode: " + Display.getDisplayMode());

			Logger.info("Initializing Graphics...");
		}

		graphics.init();

		if (showExtraDebug)
			Logger.info("Initializing Audio...");

		Audio.initAudio();

		if (showExtraDebug)
			Logger.info("Initializing Input...");

		Input.init();

		if (showExtraDebug)
			Logger.info("Initializing Core...");

		init();

		running = true;


		/* The actual game loop */


		if (showExtraDebug) {
			Logger.info("Starting Game Loop...");
			Logger.newLine();
		}

        double current;
        double previous = getCurrentTime();
        double elapsed;
        double lag      = 0;

        int    processedUpdates = 0;
        double lastFPSUpdate = 0;

        final double MS_PER_UPDATE = 1000.0 / targetFps;

        while (running) {
            if (Display.isCloseRequested())
                break;

            current  = getCurrentTime();
            elapsed  = current - previous;
            previous = current;

            lag += elapsed;

            Input.poll();

            while (lag >= MS_PER_UPDATE) {
                update();
                TaskTiming.update();

                lag -= MS_PER_UPDATE;
                processedUpdates++;

                if (current - lastFPSUpdate >= 1000)
                {
                    fps = processedUpdates;
                    processedUpdates = 0;
                    lastFPSUpdate = current;
                }
            }

            // Render
            glClear(GL_COLOR_BUFFER_BIT);

            getCamera().pre(graphics);

            if (!showingSplashScreens())
                render(graphics);
            else
                showSplashScreens(graphics);

            if (renderDebug) {
                addDebugData("FPS", String.valueOf(getFps()));

                Font tempFont = graphics.getFont();
                graphics.setFont(TrueTypeFont.ROBOTO_REGULAR);
                graphics.setColor(Color.WHITE);
                graphics.drawString(debugData, 1 / graphics.getScale(), 8, 4);
                debugData = "";
                graphics.setFont(tempFont);
            }

            getCamera().post(graphics);

			if (Display.isCloseRequested())
				end();

			Display.update();
        }

//		final float nanosInMilliSecond = 1000000.0f;
//        final float frameTime          = 1000.0f / targetFps;
//        final float maxFrameSkips      = 5;
//
//        Logger.log("Target FPS: " + targetFps);
//        Logger.log("Frame Time: " + frameTime);
//
//        long gameTime    = (long) (System.nanoTime() / nanosInMilliSecond);
//        long currentTime;
////
//        long fpsTime = 0;
//        long lastTime = 0;
////
////        int framesSkipped;
////
//        int processedFrames  = 0;
//
//		while (running) {
////            framesSkipped = 0;
////
//            currentTime = (long) (System.nanoTime() / nanosInMilliSecond);
////
//            fpsTime += currentTime - lastTime;
////
////            while (currentTime - gameTime > frameTime && framesSkipped < maxFrameSkips) {
//                Input.poll();
//
//                update();
//                TaskTiming.update();
//
//                processedFrames++;
////                framesSkipped++;
////                gameTime += frameTime;
//
//                if (fpsTime >= 1000) {
//                    fps = processedFrames;
//                    fpsTime = 0;
//                    processedFrames = 0;
//                }
////            }
//
//            // Render
//            glClear(GL_COLOR_BUFFER_BIT);
//
//            getCamera().pre(graphics);
//
//            if (!showingSplashScreens())
//                render(graphics);
//            else
//                showSplashScreens(graphics);
//
//            if (renderDebug) {
//                addDebugData("FPS", String.valueOf(getFps()));
//
//                Font tempFont = graphics.getFont();
//                graphics.setFont(TrueTypeFont.ROBOTO_REGULAR);
//                graphics.setColor(Color.WHITE);
//                graphics.drawString(debugData, 1 / graphics.getScale(), 8, 4);
//                debugData = "";
//                graphics.setFont(tempFont);
//            }
//
//            getCamera().post(graphics);
//
//			if (Display.isCloseRequested())
//				end();
//
//			Display.update();
//            Display.sync(targetFps);
//            lastTime = currentTime;
//		}


		/* Game loop ends; cleaning up. */


		if (showExtraDebug) {
			Logger.newLine();

			Logger.info("Ending Game Loop...");
			Logger.info("Beginning Clean Up:");

			Logger.info("Cleaning Up Core...");
		}

		cleanup();

		if (showExtraDebug)
			Logger.info("Cleaning up Graphics...");

		graphics.cleanup();

		if (showExtraDebug) {
			Logger.info("Clean Up Complete.");
			Logger.info("Mercury Shutting Down...");
		}


		/* Goodbye ;(. */

		currentCore = null;
	}

	/** The current framerate. */
	private int fps;

	/**
	 * @return The framerate
	 */
	public int getFps() {
		return fps;
	}

	/** The graphics object */
	private Graphics graphics;

	/**
	 * @return The graphics object
	 */
	public Graphics getGraphics() {
		return graphics;
	}

	/**
	 * @return The batcher
	 */
	public Batcher getBatcher() {
		return getGraphics().getBatcher();
	}

	/**
	 * @return The camera
	 */
	public Camera getCamera() {
		return getGraphics().getCamera();
	}

	/**
	 * Adds information to the debugdata. Debug data is wiped every single update frame, so this is to be called every
	 * frame.
	 *
	 * @param name  The name of the debug information
	 * @param value The value of the debug information
	 */
	public void addDebugData(String name, String value) {
		name.trim();
		value.trim();

		debugData += name + ": " + value + "\n";
	}


	/* Splash screen logic. */


	/**
	 * Shows the current splash screen
	 */
	private void showSplashScreens(Graphics g) {
		if (!showingSplashScreens())
			return;

		if (!splashes.get(splidx).show(g))
			splidx++;
	}

	/**
	 * @return Whether the splashes screens are being shown
	 */
	public boolean showingSplashScreens() {
		if (splidx >= splashes.size())
			return false;

		return true;
	}

	/**
	 * Adds a splash screen to the queue.
	 *
	 * @param splash The splash screen to add
	 */
	public void addSplashScreen(SplashScreen splash) {
		splashes.add(splash);
	}
}
