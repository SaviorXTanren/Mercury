package com.radirius.mercury.framework;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.misc.Renderable;
import com.radirius.mercury.utilities.misc.Updatable;

/**
 * A class that allows the dividing of a game into different 'states' or
 * 'screens'.
 * 
 * @author wessles
 */

public class GameState implements Updatable, Renderable {
	boolean auto = true;
	
	/**
	 * Executed when the GameState is switched to.
	 */
	public void onEnter() {}

	/**
	 * Executed when a different GameState is switched to from this one.
	 */
	public void onLeave() {}

	@Override
	public void update(float delta) {}

	@Override
	public void render(Graphics g) {}

	/**
	 * Sets whether the updating and rendering should automatically be called.
	 */
	public void setAutomaticUpdateAndRender(boolean auto) {
		this.auto = auto;
	}
}