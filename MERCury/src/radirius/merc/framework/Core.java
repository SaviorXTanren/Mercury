package radirius.merc.framework;

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

import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Shader;
import radirius.merc.graphics.VAOGraphics;
import radirius.merc.resource.Loader;
import radirius.merc.utilities.logging.Logger;

/**
 * The Core that will host the game with help from the Runner class.
 * 
 * @author wessles
 */

public abstract class Core {
	public final String name;
	private final Runner runner = Runner.getInstance();
	
	public Core(String name, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, boolean initonseparatethread, boolean devconsole) {
		this.name = name;
		
		runner.init(this, WIDTH, HEIGHT, fullscreen, vsync, initonseparatethread, devconsole);
	}
	
	public Core(String name, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		this(name, WIDTH, HEIGHT, fullscreen, vsync, false, true);
	}
	
	public Core(String name, int WIDTH, int HEIGHT, boolean fullscreen) {
		this(name, WIDTH, HEIGHT, fullscreen, true, false, true);
	}
	
	public Core(String name, int WIDTH, int HEIGHT) {
		this(name, WIDTH, HEIGHT, false, true);
	}
	
	/**
	 * Runs the runner.
	 */
	public void run() {
		runner.run();
	}
	
	public Runner getRunner() {
		return runner;
	}

	/**
	 * Used to initialize all resources, and for whatever you wish to do for
	 * initialization. May run on a seperate thread, while the main Thread
	 * continues to the game loop.
	 */
	public abstract void init();

	/**
	 * Called once every frame and used to handle all game logic.
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
	 * @param WIDTH
	 *            The width of the display.
	 * @param HEIGHT
	 *            The height of the display.
	 * @param fullscreen
	 *            Whether or not fullscreen is enabled.
	 * @param vsync
	 *            Whether or not v-sync is used.
	 */
	public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		try {
			Display.setVSyncEnabled(vsync);

			DisplayMode dm = new DisplayMode(WIDTH, HEIGHT);
			
			boolean screendimmatched = false;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();

				for (DisplayMode mode : modes) {
					if (mode.getWidth() == WIDTH && mode.getHeight() == HEIGHT && mode.isFullscreenCapable()) {
						dm = mode;
						screendimmatched = true;
					}
				}

				if (!screendimmatched)
					Logger.warn("Dimensions " + WIDTH + "x" + HEIGHT + " is not supported! Disabling Fullscreen.");
				else
					Display.setFullscreen(true);
			}

			Display.setDisplayMode(dm);
			Display.setTitle(name);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Runner.getInstance().setIcon(Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x16.png"), Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x32.png"), Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x64.png"));
	}

	/**
	 * Initializes graphics.
	 */
	public Graphics initGraphics() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		VAOGraphics graphicsobject = new VAOGraphics();

		Shader.loadDefaultShaders();
		Shader.releaseShaders();

		return graphicsobject;
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
