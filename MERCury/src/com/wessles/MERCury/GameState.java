package com.wessles.MERCury;

import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public abstract class GameState {
	public final int id;
	
	public GameState(int id) {
		this.id = id;
	}
	
	public abstract void update(float delta);
	public abstract void render(Graphics g);
}
