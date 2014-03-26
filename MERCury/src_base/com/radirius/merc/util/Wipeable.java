package com.radirius.merc.util;

/**
 * An abstraction for objects that can 'wipe' themselves, or self destruct.
 * 
 * @from MERCury in com.radirius.merc.util
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 22, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public interface Wipeable
{
    public void wipe();
    
    public boolean wiped();
}
