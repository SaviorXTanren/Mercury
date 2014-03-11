package com.teama.merc.res;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.teama.merc.gfx.Texture;
import com.teama.merc.log.Logger;
import com.teama.merc.paulscodeaud.Audio;

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
 * @from merc in com.teama.merc.res
 * @authors wessles, opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
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

	public void autoLoadExternal(String path)
	{
		path = path.replace('/', '\\');
		loadExternal(path);
	}
	
	public void autoLoadInternal(String path, String jarname)
	{
		path = path.replace('/', '\\');
		loadInternal(path, jarname);
	}

	private void loadExternal(String path)
	{
		Logger.debug("Auto loading resources from path: " + path);
		File folder = new File(path);
		if (folder.exists())
		{
			if (folder.isDirectory())
			{
				for (File currentFile : folder.listFiles())
				{
					Logger.debug("Loading: " + currentFile.getName());
					loadFile(currentFile);
				}
			} else
			{
				Logger.debug(path + " is not a valid dir ectory!");
			}
		} else
		{
			Logger.debug(path + " is not a valid location!");
		}
	}

	private void loadInternal(String path, String jarname)
	{
		Logger.debug("Auto loading resources from path: " + path + seperatorChar() + jarname);
		JarFile jar = null;
		try
		{
			jar = new JarFile(path + seperatorChar() + jarname);
		} catch (IOException e)
		{
			Logger.debug("Could not open: "  + jarname + " at location: " + path);
			e.printStackTrace();
		}
		for(Enumeration<JarEntry> files = jar.entries(); files.hasMoreElements();)
		{
			JarEntry entry = files.nextElement();
			Logger.debug("Loading: " + entry.getName());
			loadFileFromJar(entry);
		}
	}

	private void loadFileFromJar(JarEntry entry)
	{
	}
	
	private void loadFile(File file)
	{
		String[] filename = file.getName().split("\\.");
		switch (filename[1])
		{
		case "png":
			try
			{
				loadResource(Texture.loadTexture(Loader.streamFromSys(file.getAbsoluteFile().toString())), filename[0]);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		case "jpg":
			try
			{
				loadResource(Texture.loadTexture(Loader.streamFromSys(file.getAbsoluteFile().toString())), filename[0]);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		case "ogg":
			loadResource(new Audio(file.getAbsoluteFile().toString()), filename[0]);
			break;
		case "wav":
			loadResource(new Audio(file.getAbsoluteFile().toString()), filename[0]);
			break;
		}
	}

	public String getUserDirectory()
	{
		return System.getProperty("user.dir");
	}

	public char seperatorChar()
	{
		return File.separatorChar;
	}
}