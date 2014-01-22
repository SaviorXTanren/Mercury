package com.teama.merc.exc;

/**
 * @from MERCury in com.wessles.MERCury.exception
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 17, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

@SuppressWarnings("serial")
public class PluginNotFoundException extends Exception
{
    
    public PluginNotFoundException(String msg)
    {
        super(msg);
    }
    
}
