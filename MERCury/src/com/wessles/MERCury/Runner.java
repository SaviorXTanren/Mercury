package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.opengl.Graphics;

/**
 * A class that will run your core, and give out a delta variable staticly.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Runner {
	private static long lastframe;
	private static int delta = 1;
	
	private static Core core;
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
		Runner.core = core;
		Runner.RM = new ResourceManager();
		
		Runner.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
		Runner.graphicsobject = Runner.core.initGraphics();
		Runner.core.initAudio();
		Runner.core.init(RM);
		
		Runner.graphicsobject.init();
		Runner.input = new Input(); 
		Runner.input.create();
				
		while (core.isRunning()) {
			long time = ((Sys.getTime() * 1000) / Sys.getTimerResolution());
			delta = (int) (time - lastframe);
			lastframe = time;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			Runner.core.update(getDelta());
			
			graphicsobject.pre();
			Runner.core.render(graphicsobject);
			graphicsobject.post();


			if(Display.isCloseRequested())
				Runner.core.end();
			
			Display.update();
			Display.sync(60);
		}

		Runner.core.cleanup(RM);
	}

	public Core getCore() {
		return Runner.core;
	}

	public static float getDelta(float factor) {
		return getDelta() * factor;
	}

	public static int getDelta() {
		return delta;
	}

	public static ResourceManager getResourceManager() {
		return RM;
	}
	
	public static Graphics getGraphicsObject() {
		return graphicsobject;
	}
	
	public static void setGraphicsObject(Graphics graphicsobject) {
		Runner.graphicsobject = graphicsobject;
		graphicsobject.init();
	}
	
	public static Input getInput() {
		return input;
	}
}
