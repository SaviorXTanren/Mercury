package radirius.merc.framework;

/**
 * An abstraction for plugins.
 * 
 * @author wessles
 */

public interface Plugin {
	/** The method for initializing the plugin */
	public void init();

	/** The method for updating */
	public void update();

	/** The method for cleaning up the plugin */
	public void cleanup();
}
