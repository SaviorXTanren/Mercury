package com.radirius.merc.fmwk;

/**
 * Plugins are to be used for both official and 3rd party extensions of MERCury
 * game library.
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
