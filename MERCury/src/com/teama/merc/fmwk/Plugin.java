package com.teama.merc.fmwk;

/**
 * Plugins are to be used for both official and 3rd party extensions of MERCury game engine.
 * 
 * @from merc in com.teama.merc
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public interface Plugin
{
    public void init();
    
    public void update();
    
    public void cleanup();
}
