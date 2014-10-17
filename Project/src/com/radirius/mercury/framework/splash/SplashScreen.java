package com.radirius.mercury.framework.splash;

import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.*;
import com.radirius.mercury.utilities.TaskTiming.Task;
import com.radirius.mercury.utilities.easing.*;

/**
 * A base for all splash screens.
 *
 * @author wessles, Jeviny
 */
public class SplashScreen {
	private static final Runner runner = Runner.getInstance();

	private boolean showing = false;
	private boolean returned = true;

	private long showTimeMillis;
	private Texture splashTexture;
	private boolean fitToScreen;

	/**
	 * @param splashTexture
	 *            The texture of the splash screen.
	 * @param showTimeMillis
	 *            The time that the splash screen is shown.
	 * @param fitToScreen
	 *            Whether or not to fit the image to the screen while still
	 *            maintaining the aspect ratio.
	 */
	public SplashScreen(Texture splashTexture, long showTimeMillis, boolean fitToScreen) {
		this.showTimeMillis = showTimeMillis;
		this.splashTexture = splashTexture;
		this.fitToScreen = fitToScreen;
	}

	/**
	 * @param splashTexture
	 *            The texture of the splash screen.
	 * @param showTimeMillis
	 *            The time that the splash screen is shown.
	 */
	public SplashScreen(Texture splashTexture, long showTimeMillis) {
		this(splashTexture, showTimeMillis, (splashTexture.width <= runner.getWidth() && splashTexture.height <= runner
				.getHeight()) ? false : true);
	}

	EasingValue easingValue;

	/**
	 * Shows the splash screen on screen, whilst checking if it is time to stop
	 * as well.
	 *
	 * @return Whether or not the splash is done.
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

		for (int c_skipbtn : skipbutton) {
			if (Runner.getInstance().getInput().keyClicked(c_skipbtn)) {
				returned = false;
				showing = false;
			}
		}

		Rectangle cameraBounds = Runner.getInstance().getCamera().getBounds();

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

	private int[] skipbutton = new int[] {};

	/**
	 * Sets the buttons (from Input) for skipping the splash screen.
	 * 
	 * @param skipbutton
	 *            The button for skipping
	 *            
	 * @return the splash screen
	 */
	public SplashScreen setSkipButton(int... skipbutton) {
		this.skipbutton = skipbutton;
		
		return this;
	}

	/**
	 * Show some love for Mercury by using a shiny Mercury splash screen!
	 *
	 * @return The love of Mercury's developers. <3
	 */
	public static SplashScreen getMercuryDefault() {
		Texture texture = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/mercury/framework/splash/res/splash.png"), Texture.FILTER_LINEAR);

		return new SplashScreen(texture, 4000).setSkipButton(Input.KEY_SPACE, Input.KEY_ENTER);
	}
}
