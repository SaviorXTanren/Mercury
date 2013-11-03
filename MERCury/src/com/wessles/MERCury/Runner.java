package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.log.Logger;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.utils.Camera;

/**
 * A class that will run your core, and give out the graphics object, current
 * core, resource manager, and input staticly.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Runner {
	public static float SCALE=1;
	
	private static long lastframe;
	private static int delta = 1;
	private static float deltafactor = 1;

	private static Core core;
	private static Camera camera;
	private static Graphics graphicsobject;
	private static ResourceManager RM;
	private static Input input;

	public static void boot(Core core, int WIDTH, int HEIGHT) {
		boot(core, WIDTH, HEIGHT, true);
	}

	public static void boot(Core core, int WIDTH, int HEIGHT, boolean fullscreen) {
		boot(core, WIDTH, HEIGHT, fullscreen, true);
	}

	public static void boot(Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {

		// Init some stuffs!
		Logger.println("# MERCury Started!");
		Logger.println();
		Logger.println("  __  __ ______ _____   _____                 ");
		Logger.println(" |  \\/  |  ____|  __ \\ / ____|                ");
		Logger.println(" | \\  / | |__  | |__) | |    _   _ _ __ _   _ ");
		Logger.println(" | |\\/| |  __| |  _  /| |   | | | | '__| | | |");
		Logger.println(" | |  | | |____| | \\ \\| |___| |_| | |  | |_| |");
		Logger.println(" |_|  |_|______|_|  \\_\\_____\\__,|_| |   \\__, |");
		Logger.println("                                        __/ |");
		Logger.println("                                       |___/ ");
		Logger.println("Maitenance Enhanced and Reliable Coding Engine");
		Logger.println();

		Logger.printDateAndTime();

		Logger.println();

		Runner.core = core;
		Logger.println("#MERCury: Made Core...");
		Runner.RM = new ResourceManager();
		Logger.println("#MERCury: Initialized Resource Manager...");

		Runner.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
		Logger.println("#MERCury: Initialized Display...");
		Runner.camera = new Camera(0, 0);
		Logger.println("#MERCury: Initialized Camera...");
		Runner.graphicsobject = Runner.core.initGraphics();
		Logger.println("#MERCury: Made Graphics...");
		Runner.core.initAudio();
		Logger.println("#MERCury: Initialized Audio...");
		Runner.core.init(RM);
		Logger.println("#MERCury: Initialized Core...");

		Runner.graphicsobject.init();
		Logger.println("#MERCury: Initialized Graphics...");
		Runner.input = new Input();
		Logger.println("#MERCury: Created Input...");
		Runner.input.create();
		Logger.println("#MERCury: Initialized Input...");
		Logger.println("#MERCury: Done Initializing.");

		Logger.println("#MERCury: Starting Game Loop...");
		Logger.println("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
		Logger.println();
		Logger.println();

		while (core.isRunning()) {
			long time = ((Sys.getTime() * 1000) / Sys.getTimerResolution());
			delta = (int) (time - lastframe);
			lastframe = time;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			Runner.core.update(delta());

			camera.pre(graphicsobject);
			Runner.core.render(graphicsobject);
			camera.post(graphicsobject);

			if (Display.isCloseRequested())
				Runner.core.end();

			Display.update();
			Display.sync(60);
		}

		Logger.println();
		Logger.println();
		Logger.println("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
		Logger.println("#MERCury: Ending Game Loop...");

		Logger.println("#MERCury: Starting Cleanup...");
		Runner.core.cleanup(RM);
		Logger.println("#MERCury: MERCury Shutting down...");
		Logger.printDateAndTime();
		Logger.cleanup();
	}

	public static int width() {
		return Display.getWidth();
	}
	
	public static float width(float numerator) {
		return width()/numerator;
	}
	
	public static int height() {
		return Display.getHeight();
	}
	
	public static float height(float numerator) {
		return height()/numerator;
	}
	
	public static Core getCore() {
		return Runner.core;
	}

	public static float delta() {
		return delta * deltafactor;
	}

	public static void setDeltaFactor(float factor) {
		deltafactor = factor;
	}

	public static Camera camera() {
		return camera;
	}

	public static ResourceManager resourceManager() {
		return RM;
	}

	public static Graphics graphics() {
		return graphicsobject;
	}

	public static Input input() {
		return input;
	}
}
