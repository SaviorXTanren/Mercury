package com.teama.merc.res;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @from MERCury in com.teama.merc.res
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 25, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Loader
{
    public static URL URLFromClasspath(String loc)
    {
        loc = loc.replace('\\', '/');
        return Loader.class.getClassLoader().getResource(loc);
    }
    
    public static URL URLFromFileSys(String loc)
    {
        loc = loc.replace('\\', '/');
        return Loader.class.getResource(loc);
    }
    
    public static InputStream streamFromClasspath(String loc)
    {
        try
        {
            return new BufferedInputStream(URLFromClasspath(loc).openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static InputStream streamFromSys(String loc)
    {
        try
        {
            return new BufferedInputStream(URLFromFileSys(loc).openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
