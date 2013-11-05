package com.wessles.MERCury;

import java.io.File;
import java.util.HashMap;

import com.wessles.MERCury.opengl.Graphics;

/**
 * A sub-class of {@code Core} that will add in the capabilities to handle
 * {@code GameState}s.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public abstract class StateCore extends Core {
	public HashMap<Integer, GameState> gamestates = new HashMap<Integer, GameState>();
	private int states = 0, current_state = 0;

	public StateCore(int WIDTH, int HEIGHT) {
		this(WIDTH, HEIGHT, true);
	}

	public StateCore(int WIDTH, int HEIGHT, boolean vsync) {
		this(WIDTH, HEIGHT, false, vsync);
	}

	public StateCore(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
		this(WIDTH, HEIGHT, fullscreen, vsync, null);
	}

	public StateCore(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, File log) {
		super(WIDTH, HEIGHT, fullscreen, vsync, log);
	}

	public abstract void init(ResourceManager RM);

	public void update(float delta) {
		updateGameState(delta);
	}

	public void render(Graphics g) {
		renderGameState(g);
	}

	public abstract void cleanup(ResourceManager RM);

	public void updateGameState(float delta) {
		gamestates.get(current_state).update(delta);
	}

	public void renderGameState(Graphics g) {
		gamestates.get(current_state).render(g);
	}

	public void addState(GameState state) {
		gamestates.put(states, state);
		states++;
	}

	public void setState(int state) {
		current_state = state;
	}
}
