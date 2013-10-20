package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.*;

import javax.swing.JOptionPane;

import kuusisto.tinysound.TinySound;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.VAOGraphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public abstract class Core {
	private boolean running = true;

	public Core() {
		this(500, 500);
	}

	public Core(int WIDTH, int HEIGHT) {
		this(WIDTH, HEIGHT, true);
	}

	public Core(int WIDTH, int HEIGHT, boolean vsync) {
		this(WIDTH, HEIGHT, false, vsync);
	}

	public Core( int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		Runner.boot(this, WIDTH, HEIGHT, fullscreen, vsync);
	}

	public abstract void init(ResourceManager RM);

	public abstract void update(float delta);

	public abstract void render(Graphics g);

	public abstract void cleanup(ResourceManager RM);

	public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		try {
			Display.setTitle(getClass().getSimpleName());
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setFullscreen(fullscreen);
			Display.setVSyncEnabled(vsync);
			Display.create();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
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
	
	public final boolean isRunning() {
		return running;
	}

	public final void end() {
		running = false;
	}
}
