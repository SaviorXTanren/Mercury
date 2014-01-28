package com.teama.merc.slickaud;

import org.lwjgl.openal.AL;

import com.teama.merc.fmwk.Plugin;

/**
 * @from MERCury in com.teama.merc.slickaud
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 25, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class SlickSoundPlugin implements Plugin
{
    
    @Override
    public void init()
    {
        
    }
    
    @Override
    public void update()
    {
        
    }
    
    @Override
    public void cleanup()
    {
        AL.destroy();
    }
}
