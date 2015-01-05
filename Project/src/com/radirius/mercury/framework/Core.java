package com.radirius.mercury.framework;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.radirius.mercury.audio.Audio;
import com.radirius.mercury.framework.splash.SplashScreen;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.resource.ClasspathLocation;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.TaskTiming;
import com.radirius.mercury.utilities.logging.Logger;

/**
 * The Core initializes the game, runs the game loop, and cleans up.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public abstract class Core {
	private static Core currentCore;

	/** Returns The core that is currently running. */
	public static Core getCurrentCore() {
		return currentCore;
	}

	/** The Core's CoreSetup. */
	private CoreSetup coreSetup;

	public Core(CoreSetup coreSetup) {
		this.coreSetup = coreSetup;
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
	 * Used to initialize all resources, and for whatever you wish to do for
	 * initialization. May run on a seperate thread, while the main Thread
	 * continues to the game loop.
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
	 * Returns The current GameState.
	 */
	public GameState getCurrentState() {
		return currentGameState;
	}

	/** A list of splash screens */
	private final ArrayList<SplashScreen> splashes = new ArrayList<>();

	/** The current splash screen */
	private int splidx = 0;

	/** Whether or not the Runner is running */
	private boolean running = false;

	/**
	 * A string that holds debugging data to be rendered to the screen, should
	 * `renderDebug` be true
	 */
	private String debugData = "";

	/**
	 * Returns The current time in milliseconds
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

		System.out.println("Mercury Game Library (In-Dev)\n" + "Website: http://mercurylib.com/");
		System.out.println("-------------------------------");
		System.out.println();
		
		Loader.addLocation(new ClasspathLocation());
		
		Logger.warn("You're running a non-stable build of Mercury!\nIf you run into any issues, please leave an issue on GitHub or make a post on the forum.");

		if (coreSetup.showConsoleDebug)
			Logger.log("Mercury Starting:");

		if (coreSetup.showConsoleDebug)
			Logger.log("Making Display & Graphics...");

		Window.initDisplay(coreSetup.name, coreSetup.width, coreSetup.height, coreSetup.fullScreen, coreSetup.vSync);

		graphics = Graphics.initGraphics();

		if (coreSetup.showConsoleDebug) {
			Logger.log("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
			Logger.log("Display Mode: " + Display.getDisplayMode());

			Logger.log("Initializing Graphics...");
		}

		graphics.init();

		if (coreSetup.showConsoleDebug)
			Logger.log("Initializing Audio...");

		Audio.initAudio();

		if (coreSetup.showConsoleDebug)
			Logger.log("Initializing Input...");

		Input.init();

		if (coreSetup.showConsoleDebug)
			Logger.log("Initializing Core...");

		init();

		running = true;

		if (coreSetup.showConsoleDebug) {
			Logger.log("Starting Game Loop...");
			Logger.newLine();
		}

		double current;
		double previous = getCurrentTime();
		double elapsed;
		double lag = 0;

		int processedUpdates = 0;
		double lastFPSUpdate = 0;

		double millisPerUpdate = 1000.0 / coreSetup.targetFps;

		while (running) {
			if (Display.isCloseRequested())
				break;

			current = getCurrentTime();
			elapsed = current - previous;
			previous = current;

			lag += elapsed;

			while (lag >= millisPerUpdate) {
				Input.poll();

				if (!showingSplashScreens())
					update();

				TaskTiming.update();

				lag -= millisPerUpdate;
				processedUpdates++;

				if (current - lastFPSUpdate >= 1000) {
					fps = processedUpdates;
					processedUpdates = 0;
					lastFPSUpdate = current;
				}
			}

			glClear(GL_COLOR_BUFFER_BIT);

			getCamera().pre(graphics);

			if (!showingSplashScreens())
				render(graphics);
			else
				showSplashScreens(graphics);

			if (coreSetup.showDebug) {
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

		if (coreSetup.showConsoleDebug) {
			Logger.newLine();

			Logger.log("Ending Game Loop...");
			Logger.log("Beginning Clean Up:");

			Logger.log("Cleaning Up Core...");
		}

		cleanup();

		if (coreSetup.showConsoleDebug)
			Logger.log("Cleaning up Graphics...");

		graphics.cleanup();

		if (coreSetup.showConsoleDebug) {
			Logger.log("Clean Up Complete.");
			Logger.log("Mercury Shutting Down...");
		}

		currentCore = null;
	}

	/** The current framerate. */
	private int fps;

	/**
	 * Returns The framerate
	 */
	public int getFps() {
		return fps;
	}

	/** The graphics object */
	private Graphics graphics;

	/**
	 * Returns The graphics object
	 */
	public Graphics getGraphics() {
		return graphics;
	}

	/**
	 * Returns The batcher
	 */
	public Batcher getBatcher() {
		return getGraphics().getBatcher();
	}

	/**
	 * Returns The camera
	 */
	public Camera getCamera() {
		return getGraphics().getCamera();
	}

	/**
	 * Adds information to the debugdata. Debug data is wiped every single
	 * update frame, so this is to be called every frame.
	 *
	 * @param name The name of the debug information
	 * @param value The value of the debug information
	 */
	public void addDebugData(String name, String value) {
		name = name.trim();
		value = value.trim();

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
	 * Returns Whether the splashes screens are being shown
	 */
	public boolean showingSplashScreens() {
		return splidx < splashes.size();
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
