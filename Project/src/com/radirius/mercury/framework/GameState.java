package com.radirius.mercury.framework;

import com.radirius.mercury.utilities.misc.Renderable;
import com.radirius.mercury.utilities.misc.Updatable;

/**
 * A class that allows the dividing of a game into different
 * 'states' or 'screens'.
 * 
 * @author wessles
 */

public abstract class GameState implements Updatable, Renderable {
	/**
	 * Executed when the GameState is switched to.
	 */
	public void onEnter() {
	}

	/**
	 * Executed when a different GameState is switched to
	 * from this one.
	 */
	public void onLeave() {
	}
	
	boolean auto = true;
	
	/**
	 * Sets whether the updating and rendering should automatically be called.
	 */
	public void setAutomaticUpdateAndRender(boolean auto) {
		this.auto = auto;
	}
}