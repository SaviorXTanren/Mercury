package com.radirius.merc.res;

import java.io.File;
import java.util.HashMap;

/**
 * An object that will hold, handle, and load all resources, so that one
 * resource will only have one instance.
 * 
 * With this class, you may load an object; loading means that you will input a
 * resource, and it will be stored with a given key.
 * 
 * You may also get an object; getting it means that you give the key and it
 * returns the resource associated with that key.
 * 
 * @from merc in com.radirius.merc.res
 * @authors wessles, opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class ResourceManager {
    private final HashMap<String, Resource> resources = new HashMap<String, Resource>();
    
    /** Loads a resource by the key key. */
    public void loadResource(Resource res, String key) {
        resources.put(key, res);
    }
    
    /**
     * Loads multiple resources, simply concatinating "_n" to the key with each
     * new one, n being the number of resources processed.
     */
    public void loadResources(Resource[] resources, String key) {
        for (int t = 0; t < resources.length; t++)
            this.resources.put(key + "_" + t, resources[t]);
    }
    
    /** @return The resource of the key key. */
    public Resource retrieveResource(String key) {
        return resources.get(key);
    }
    
    /** Removes the resource of the key key. */
    public void clearResource(String key) {
        resources.remove(key);
    }
    
    /**
     * A method for releasing anything that needs to be released, for it is the
     * end!
     */
    public void cleanup() {
        resources.clear();
    }
    
    /** @return The user's directory. */
    public String getUserDirectory() {
        return System.getProperty("user.dir");
    }
    
    /** @return The seperating character. */
    public char seperatorChar() {
        return File.separatorChar;
    }
}