package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_SCALE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.File;

import kuusisto.tinysound.TinySound;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.VAOGraphics;

/**
 * The {@code Core} that will host the game. It is ran above by the
 * {@code Runner} class.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public abstract class Core {
	private int WIDTH, HEIGHT;
	private boolean fullscreen, vsync;
	private boolean running = true;
	private File log;

	public Core(int WIDTH, int HEIGHT) {
		this(WIDTH, HEIGHT, true);
	}

	public Core(int WIDTH, int HEIGHT, boolean vsync) {
		this(WIDTH, HEIGHT, false, vsync);
	}

	public Core(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		this(WIDTH, HEIGHT, fullscreen, vsync, null);
	}
	
	public Core(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, File log) {
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.fullscreen = fullscreen;
		this.vsync = vsync;
		this.log = log;
	}

	/**
	 * Called first (after {@code initDisplay}, {@code initGraphics}, and
	 * {@code initAudio}), used to initialize all resources, and for whatever
	 * you wish to do for initialization.
	 */
	public abstract void init(ResourceManager RM);

	/**
	 * Called once every frame, and used to handle all logic.
	 */
	public abstract void update(float delta);

	/**
	 * Called once every frame, and used to render everything, via
	 * {@code Graphics g}.
	 */
	public abstract void render(Graphics g);

	/**
	 * Called when the Runner is done
	 */
	public abstract void cleanup(ResourceManager RM);

	public void run() {
		Runner.boot(this, WIDTH, HEIGHT, fullscreen, vsync, log);
	}

	public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(getClass().getSimpleName());
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(vsync);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public Graphics initGraphics() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_SCALE);
		glDepthMask(true);
		glDepthFunc(GL_LEQUAL);

		glAlphaFunc(GL_GREATER, 0.1f);

		return new VAOGraphics();
	}

	public void initAudio() {
		TinySound.init();
	}

	/**
	 * Returns whether or not the core is running.
	 */
	public final boolean isRunning() {
		return running;
	}

	/**
	 * Ends the core, setting the {@code running} variable to false.
	 */
	public final void end() {
		running = false;
	}
}
