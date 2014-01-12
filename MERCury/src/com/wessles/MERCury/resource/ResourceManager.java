package com.wessles.MERCury.resource;

import java.util.HashMap;

/**
 * An object that will hold, handle, and load all resources, so that one resource will only have one instance.
 * 
 * With this class, you may load an object; loading means that you will input a resource, and it will be stored with a given key.
 * 
 * You may also get an object; getting it means that you give the key and it returns the resource associated with that key.
 * 
 * @from MERCury in com.wessles.MERCury
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ResourceManager
{
    private final HashMap<String, Resource> resources = new HashMap<String, Resource>();
    
    public void loadResource(Resource res, String key)
    {
        resources.put(key, res);
    }
    
    public void loadResources(Resource[] resources, String key)
    {
        for (int t = 0; t < resources.length; t++)
            this.resources.put(key + "_" + t, resources[t]);
    }
    
    public Resource retrieveResource(String key)
    {
        return resources.get(key);
    }
    
    public void clearResource(String key)
    {
        resources.remove(key);
    }
    
    public void cleanup()
    {
        resources.clear();
    }
}