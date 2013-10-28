package com.wessles.MERCury;

import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public abstract class GameState {
	public abstract void update(float delta);
	public abstract void render(Graphics g);
}
