package com.radirius.merc.fmwk;

/**
 * Plugins are to be used for both official and 3rd party extensions of MERCury
 * game library.
 * 
 * @from merc in com.radirius.merc.fmwk
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public interface Plugin
{
    /** The method for initializing the plugin */
    public void init();
    
    /** The method for updating */
    public void update();
    
    /** The method for cleaning up the plugin */
    public void cleanup();
}
