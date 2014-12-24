package com.radirius.mercury.framework.splash;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.Window;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.TaskTiming;
import com.radirius.mercury.utilities.TaskTiming.Task;
import com.radirius.mercury.utilities.easing.EasingUtils;
import com.radirius.mercury.utilities.easing.EasingValue;

/**
 * A base for all splash screens.
 *
 * @author wessles
 * @author Jeviny
 */
public class SplashScreen {
	EasingValue easingValue;
	private boolean showing = false;
	private boolean returned = true;
	private long showTimeMillis;
	private Texture splashTexture;
	private boolean fitToScreen;
	private int[] skipbutton = new int[] {};

	/**
	 * @param splashTexture The texture of the splash screen.
	 * @param showTimeMillis The time that the splash screen is shown.
	 * @param fitToScreen Whether or not to fit the image to the screen while
	 *        still maintaining the aspect ratio.
	 */
	public SplashScreen(Texture splashTexture, long showTimeMillis, boolean fitToScreen) {
		this.showTimeMillis = showTimeMillis;
		this.splashTexture = splashTexture;
		this.fitToScreen = fitToScreen;

		this.setSkipButton(Input.KEY_ENTER);
	}

	/**
	 * @param splashTexture The texture of the splash screen.
	 * @param showTimeMillis The time that the splash screen is shown.
	 */
	public SplashScreen(Texture splashTexture, long showTimeMillis) {
		this(splashTexture, showTimeMillis, !(splashTexture.width <= Window.getWidth() && splashTexture.height <= Window.getHeight()));
	}

	/**
	 * Show some love for Mercury by using a shiny Mercury splash screen!
	 *
	 * Returns The love of Mercury's developers. <3
	 */
	public static SplashScreen getMercuryDefault() {
		Texture texture = Texture.loadTexture(Loader.getResourceAsStream("com/radirius/mercury/framework/splash/res/splash.png"), Texture.FILTER_LINEAR);

		return new SplashScreen(texture, 4000);
	}

	/**
	 * Shows the splash screen on screen, whilst checking if it is time to stop
	 * as well.
	 *
	 * Returns Whether or not the splash is done.
	 */
	public boolean show(Graphics g) {
		if (!showing) {
			TaskTiming.addTask(new Task(showTimeMillis) {
				@Override
				public void run() {
					returned = false;
				}
			});

			easingValue = new EasingValue(EasingUtils.BOUNCING_EASE_QUINT, 0, 1, showTimeMillis);

			showing = true;
		}

		for (int c_skipbtn : skipbutton)
			if (Input.keyClicked(c_skipbtn)) {
				returned = false;
				showing = false;
			}

		Rectangle cameraBounds = Core.getCurrentCore().getCamera().getBounds();

		float width = splashTexture.getWidth();
		float height = splashTexture.getHeight();

		if (fitToScreen) {
			float scale = cameraBounds.getWidth() / splashTexture.getWidth();

			width = cameraBounds.getWidth();
			height = splashTexture.getHeight() * scale;
			scale = cameraBounds.getHeight() / height;
			height = cameraBounds.getHeight();

			width *= scale;
		}

		g.drawTexture(splashTexture, cameraBounds.getX() + cameraBounds.getWidth() / 2 - width / 2, cameraBounds.getY() + cameraBounds.getHeight() / 2 - height / 2, width, height, new Color(0, 0, 0, easingValue.get()));

		return returned;
	}

	/**
	 * Sets the buttons (from Input) for skipping the splash screen.
	 *
	 * @param skipbutton The button for skipping Returns the splash screen
	 */
	public SplashScreen setSkipButton(int... skipbutton) {
		this.skipbutton = skipbutton;

		return this;
	}
}
