package com.radirius.mercury.framework;

/**
 * A setup class for the Core.
 *
 * @author wessles
 * @author Jeviny
 */
public class CoreSetup {
	/**
	 * The name of the window.
	 */
	public String name;

	/**
	 * The width of the window.
	 */
	public int width = 800;

	/**
	 * The height of the window.
	 */
	public int height = 600;

	/**
	 * The target framerate.
	 */
	public int targetFps = 60;

	/**
	 * Whether or not to update multiple times while making up for the fixed time step.
	 */
	public boolean makeupTimeStepWithUpdates = false;

	/**
	 * Whether the window will attempt fullscreen.
	 */
	public boolean fullScreen = false;

	/**
	 * Whether or not the debug data will be drawn to the screen.
	 */
	public boolean showDebug = false;

	/**
	 * Whether or not the extra Mercury debug data will be shown in the console.
	 */
	public boolean showConsoleDebug = false;

	/**
	 * Whether or not v-sync is enabled.
	 */
	public boolean vSync = true;

	/**
	 * Create the CoreSetup
	 *
	 * @param name The name of the window.
	 */
	public CoreSetup(String name) {
		this.name = name;
	}
}
