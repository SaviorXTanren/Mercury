package com.teama.merc.res;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A utility for resource management to load different resources from specific roots.
 * 
 * @from MERCury in com.teama.merc.res
 * @by wessles
 * @website www.wessles.com
 * @license (C) Jan 25, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Loader
{
    /**
     * @return The URL from a classpath.
     */
    public static URL loadFromClasspath(String loc)
    {
        loc = loc.replace('\\', '/');
        return Loader.class.getClassLoader().getResource(loc);
    }
    
    /**
     * @return The URL from a file system.
     */
    public static URL loadFromSys(String loc)
    {
        loc = loc.replace('\\', '/');
        return Loader.class.getResource(loc);
    }
    
    /**
     * @return The InputStream from a classpath.
     */
    public static InputStream streamFromClasspath(String loc)
    {
        try
        {
            return new BufferedInputStream(loadFromClasspath(loc).openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @return The InputStream from a file system.
     */
    public static InputStream streamFromSys(String loc)
    {
        try
        {
            return new BufferedInputStream(loadFromSys(loc).openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @return The URL from a file system. If null, from the classpath. This is for easier modding.
     */
    public static URL load(String loc)
    {
        URL filesys = loadFromSys(loc);
        URL classpath = loadFromClasspath(loc);
        if (filesys != null)
            return filesys;
        else if (classpath != null)
            return classpath;
        return null;
    }
    
    /**
     * @return The InputStream from a file system. If null, from the classpath. This is for easier modding.
     */
    public static InputStream stream(String loc)
    {
        InputStream filesys = streamFromSys(loc);
        InputStream classpath = streamFromClasspath(loc);
        if (filesys != null)
            return filesys;
        else if (classpath != null)
            return classpath;
        return null;
    }
}
