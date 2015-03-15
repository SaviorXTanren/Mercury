package com.radirius.mercury.resource;

import com.radirius.mercury.utilities.logging.Logger;

import java.io.*;
import java.net.URL;
import java.util.Stack;

/**
 * A utility for resource loading from different locations.
 *
 * @author wessles
 * @author Jeviny
 * @author Kevin Glass
 */
public class Loader {
	private static Stack<Location> locationStack = new Stack<>();

	/**
	 * Pushes the location onto the location stack.
	 *
	 * @param location
	 * 		The new location
	 */
	public static void pushLocation(Location location) {
		locationStack.push(location);
	}

	/**
	 * Pops the location stack.
	 */
	public static void popLocation() {
		locationStack.pop();
	}

	/**
	 * Clears the location stack.
	 */
	public static void clearLocations() {
		locationStack.clear();
	}

	/**
	 * @return the location stack.
	 */
	public static Stack<Location> getLocationStack() {
		return locationStack;
	}

	/**
	 * Loads a URL of a resource. If the resource is not found in the top location of the stack, then the next location
	 * will be checked until either the resource is found, or there are no more locations.
	 *
	 * @param path
	 * 		The path of the resource
	 *
	 * @return the URL of a resource
	 */
	public static URL getResource(String path) {
		URL url = locationStack.peek().getResource(path);

		int i = locationStack.size();
		while (i > 0 && url == null) url = locationStack.get(i--).getResource(path);

		if (url == null)
			Logger.warn("Resource not found: " + path);

		return url;
	}

	/**
	 * Loads a File resource.
	 *
	 * @param path
	 * 		The path of the resource
	 *
	 * @return the File resource
	 */
	public static File getResourceAsFile(String path) {
		return new File(getResource(path).getFile().replaceAll("%20", " "));
	}

	/**
	 * Streams a resource from the url of {@link #getResource(String path)}.
	 *
	 * @param path
	 * 		The path of the resource
	 *
	 * @return the InputStream of a resource.
	 */
	public static InputStream getResourceAsStream(String path) {
		return streamFromUrl(getResource(path));
	}

	/**
	 * Converts a URL into an InputStream.
	 *
	 * @param url
	 * 		The URL to convert
	 *
	 * @return the converted InputStream.
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
