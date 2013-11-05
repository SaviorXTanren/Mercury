package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;

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
	public static float SCALE = 1;

	private static boolean logfps = false;
	private static int delta = 1;
	private static int FPS_TARGET = 60, FPS;
	private static long lastframe;
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
		boot(core, WIDTH, HEIGHT, fullscreen, true, null);
	}

	public static void boot(Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, File log) {
		if (log != null) {
			Logger.setLog(log);
		}

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
		Logger.println("          by wessles of www.wessles.com");
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

		// To the main loop!

		int _FPS = 0;
		long lastfps;

		/*
		 * Initial 'last time...' Otherwise the first delta will be about
		 * 50000000.
		 */
		lastfps = lastframe = Sys.getTime() * 1000 / Sys.getTimerResolution();

		while (core.isRunning()) {
			// Set time for FPS and Delta calculations
			long time = Sys.getTime() * 1000 / Sys.getTimerResolution();

			// Calculate delta
			delta = (int) (time - lastframe);

			// Update FPS
			if (time - lastfps < 1000) {
				_FPS++;
			} else {
				lastfps = time;
				FPS = _FPS;
				_FPS = 0;
			}
			
			if(FPS == 0)
				FPS = FPS_TARGET;

			// End all time calculations.
			lastframe = time;

			// Log FPS
			if (logfps) {
				Logger.println("FPS:" + FPS);
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			Runner.core.update(delta());

			camera.pre(graphicsobject);
			Runner.core.render(graphicsobject);
			camera.post(graphicsobject);

			if (Display.isCloseRequested()) {
				Runner.core.end();
			}

			Display.update();
			Display.sync(FPS_TARGET);
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

	public static int fps() {
		return FPS;
	}

	public static void setFpsTarget(int target) {
		FPS_TARGET = target;
	}

	public static void setLogFPS(boolean logfps) {
		Runner.logfps = logfps;
	}

	public static int width() {
		return Display.getWidth();
	}

	public static float width(float numerator) {
		return width() / numerator;
	}

	public static int height() {
		return Display.getHeight();
	}

	public static float height(float numerator) {
		return height() / numerator;
	}

	public static Core core() {
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
