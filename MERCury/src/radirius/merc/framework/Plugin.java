package radirius.merc.framework;

/**
 * An abstraction for plugins.
 * 
 * @author wessles, Jeviny
 */

public abstract class Plugin {
	/** The method for initializing the plugin. */
	public abstract void init();

	/** The method for updating. */
	public abstract void update();

	/** The method for cleaning up the plugin. */
	public abstract void cleanup();
	
	/** @return The name of the plugin. */
	public String getName() {
		return getClass().getSimpleName();
	}
}
