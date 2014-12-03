package com.radirius.mercury.framework;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.misc.*;

/**
 * A class that allows the dividing of a game into different 'states' or 'screens'.
 *
 * @author wessles
 */

public class GameState implements Updatable, Renderable {

	/**
	 * Executed when the GameState is switched to.
	 */
	public void onEnter() {
	}

	/**
	 * Executed when a different GameState is switched to from this one.
	 */
	public void onLeave() {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Graphics g) {
	}
}
