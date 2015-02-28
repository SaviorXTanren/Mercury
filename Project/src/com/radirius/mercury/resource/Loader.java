package com.radirius.mercury.resource;

import com.radirius.mercury.utilities.logging.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * A utility for resource loading from different locations.
 *
 * @author wessles
 * @author Jeviny
 * @author Kevin Glass
 */
public class Loader {
	private static ArrayList<Location> locations = new ArrayList<>();

	static {
		locations.add(new ClasspathLocation());
		locations.add(new SystemLocation(new File(".")));
	}

	/**
	 * Add a location that will be searched for resources
	 *
	 * @param location The location that will be searched for resources
	 */
	public static void addLocation(Location location) {
		locations.add(location);
	}

	/**
	 * Remove a location that will be no longer be searched for resources
	 *
	 * @param location The location that will be removed from the search list
	 */
	public static void removeLocation(Location location) {
		locations.remove(location);
	}

	/**
	 * Remove all the locations, no resources will be found until new locations
	 * have been added
	 */
	public static void removeAllLocations() {
		locations.clear();
	}

	/**
	 * Loads a URL of a resource from the file system.
	 *
	 * @param path The path of the resource
	 * @return The URL of a resource from the file system
	 */
	public static URL getResource(String path) {
		URL url = null;

		for (Location location : locations) {
			url = location.getResource(path);

			if (url != null)
				break;
		}

		if (url == null)
			Logger.warn("Resource not found: " + path);

		return url;
	}

	/**
	 * Loads a File resource from the file system.
	 *
	 * @param path The path of the resource
	 * @return The File resource from the file system
	 */
	public static File getResourceAsFile(String path) {
		File file = new File(getResource(path).getFile().replaceAll("%20", " "));

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Streams a resource from the file system.
	 *
	 * @param path The path of the resource Returns The InputStream of a
	 *             resource from the file system.
	 */
	public static InputStream getResourceAsStream(String path) {
		return streamFromUrl(getResource(path));
	}

	/**
	 * Converts a URL into an InputStream.
	 *
	 * @param url The URL to convert Returns The converted InputStream.
	 */
	public static InputStream streamFromUrl(URL url) {
		try {
			return new BufferedInputStream(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
