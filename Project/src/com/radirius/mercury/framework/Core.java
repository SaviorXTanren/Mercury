package com.radirius.mercury.framework;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Shader;
import com.radirius.mercury.graphics.VAOGraphics;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.logging.Logger;

/**
 * The Core that will host the game with help from the
 * Runner class.
 *
 * @author wessles, Jeviny
 */
public abstract class Core {
	private final Runner runner = Runner.getInstance();

	public final String name;
	public final int width;
	public final int height;
	public final boolean fullscreen;
	public final boolean vsync;
	public final boolean multithread;
	public final boolean devconsole;

	public Core(String name, int width, int height, boolean fullscreen, boolean vsync, boolean multithread, boolean devconsole) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		this.vsync = vsync;
		this.multithread = multithread;
		this.devconsole = devconsole;
	}

	public Core(String name, int width, int height, boolean fullscreen, boolean vsync) {
		this(name, width, height, fullscreen, vsync, false, false);
	}

	public Core(String name, int width, int height, boolean fullscreen) {
		this(name, width, height, fullscreen, true, false, false);
	}

	public Core(String name, int width, int height) {
		this(name, width, height, false, true);
	}

	/**
	 * Initializes and then runs the Runner.
	 */
	public void start() {
		runner.init(this, width, height, fullscreen, vsync, multithread, devconsole);
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
	 * @param delta
	 *            The delta time.
	 */
	public abstract void update(float delta);

	/**
	 * Called once every frame and used to render graphics.
	 *
	 * @param g
	 *            The Graphics object for rendering.
	 */
	public abstract void render(Graphics g);

	/**
	 * Called when the game loop is ended.
	 */
	public abstract void cleanup();

	/**
	 * Initializes the display.
	 *
	 * @param width
	 *            The width of the display.
	 * @param height
	 *            The height of the display.
	 * @param fullscreen
	 *            Whether or not fullscreen is enabled.
	 * @param vsync
	 *            Whether or not v-sync is used.
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
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Runner.getInstance().setIcon(Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x16.png"), Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x32.png"), Loader.streamFromClasspath("com/radirius/mercury/framework/res/merc_mascot_x64.png"));
	}

	/**
	 * Initializes graphics & handle OpenGL initialization
	 * calls.
	 */
	public Graphics initGraphics() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

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
