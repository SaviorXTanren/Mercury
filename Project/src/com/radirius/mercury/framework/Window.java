package com.radirius.mercury.framework;

import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.math.geometry.Vector2f;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.utilities.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * A class for the control of the Window.
 *
 * @author wessles
 */
public class Window {

	/**
	 * Returns The width of the display.
	 */
	public static int getWidth() {
		return Display.getWidth();
	}

	/**
	 * Returns The height of the display.
	 */
	public static int getHeight() {
		return Display.getHeight();
	}

	/**
	 * Returns The horizontal center of the display.
	 */
	public static int getCenterX() {
		return getWidth() / 2;
	}

	/**
	 * Returns The vertical center of the display.
	 */
	public static int getCenterY() {
		return getHeight() / 2;
	}

	/**
	 * Returns The center of the display.
	 */
	public static Vector2f getCenter() {
		return new Vector2f(getCenterX(), getCenterY());
	}

	/**
	 * Returns The aspect ratio of the display.
	 */
	public static float getAspectRatio() {
		return (float) getWidth() / (float) getHeight();
	}

	/**
	 * Returns Whether or not the window has the focus.
	 */
	public static boolean isFocused() {
		return Display.isActive();
	}

	/**
	 * Sets the title of the window.
	 *
	 * @param title The title of the window.
	 */
	public static void setTitle(String title) {
		Display.setTitle(title);
	}

	/**
	 * Sets the icon for given size(s). Recommended sizes that you should put in
	 * are x16, x32, and x64.
	 *
	 * @param icons Icon(s) for the game.
	 */
	public static void setIcon(InputStream... icons) {
		ArrayList<ByteBuffer> buffers = new ArrayList<>();

		for (InputStream is : icons)
			if (is != null)
				try {
					buffers.add(Texture.convertBufferedImageToBuffer(ImageIO.read(is)));
				} catch (IOException e) {
					e.printStackTrace();
				}

		ByteBuffer[] bufferArray = new ByteBuffer[buffers.size()];

		buffers.toArray(bufferArray);
		Display.setIcon(bufferArray);
	}

	/**
	 * Enables or disables mouse grabbing.
	 *
	 * @param grab Whether or not to grab the mouse.
	 */
	public static void setMouseGrabbed(boolean grab) {
		Mouse.setGrabbed(grab);
	}

	/**
	 * Sets whether or not v-sync is enabled.
	 *
	 * @param vsync Whether or not to use v-sync.
	 */
	public static void setVsync(boolean vsync) {
		Display.setVSyncEnabled(vsync);
	}

	/**
	 * Initializes the display.
	 *
	 * @param name       The name of the display.
	 * @param width      The width of the display.
	 * @param height     The height of the display.
	 * @param fullscreen Whether or not fullscreen is enabled.
	 * @param vsync      Whether or not v-sync is used.
	 */
	public static void initDisplay(String name, int width, int height, boolean fullscreen, boolean vsync) {
		try {
			Display.setVSyncEnabled(vsync);

			DisplayMode dimensions = new DisplayMode(width, height);

			boolean matchedDimensions = false;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();

				for (DisplayMode mode : modes) {
					if (mode.getWidth() == width && mode.getHeight() == height && mode.isFullscreenCapable()) {
						dimensions = mode;
						matchedDimensions = true;
					}
				}

				if (!matchedDimensions)
					Logger.warn("Dimensions " + width + "x" + height + " is not supported! Disabling fullscreen.");
				else
					Display.setFullscreen(true);
			}

			Display.setDisplayMode(dimensions);
			Display.setTitle(name);

			PixelFormat format = new PixelFormat();
			ContextAttribs contextAttribs = new ContextAttribs(3, 2).withProfileCore(true);

			Display.create(format, contextAttribs);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		setIcon(Loader.getResourceAsStream("com/radirius/mercury/framework/res/merc_mascot_x16.png"), Loader.getResourceAsStream("com/radirius/mercury/framework/res/merc_mascot_x32.png"), Loader.getResourceAsStream("com/radirius/mercury/framework/res/merc_mascot_x64.png"));
	}
}
