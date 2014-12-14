package com.radirius.mercury.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A utility for resource loading from different locations (the classpath and
 * file system).
 *
 * @author wessles, Jeviny
 */
public class Loader {

	/**
	 * Loads a URL of a resource from the classpath.
	 *
	 * @param path The path of the resource Returns The URL of a resource from
	 *        the classpath
	 */
	public static URL loadFromClasspath(String path) {
		path = path.replace('\\', '/');

		return Loader.class.getClassLoader().getResource(path);
	}

	/**
	 * Loads a URL of a resource from the file system.
	 *
	 * @param path The path of the resource Returns The URL of a resource from
	 *        the file system
	 */
	public static URL loadFromSystem(String path) {
		path = path.replace('\\', '/');

		try {
			return new URL("file:" + path);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Streams a resource from the classpath.
	 *
	 * @param path The path of the resource Returns The InputStream of a
	 *        resource from the classpath
	 */
	public static InputStream streamFromClasspath(String path) {
		return streamFromUrl(loadFromClasspath(path));
	}

	/**
	 * Streams a resource from the file system.
	 *
	 * @param path The path of the resource Returns The InputStream of a
	 *        resource from the file system
	 */
	public static InputStream streamFromSystem(String path) {
		return streamFromUrl(loadFromSystem(path));
	}

	/**
	 * Loads a URL of a resource from the file system (if null, then from the
	 * classpath). This is useful for modding a game without going into the
	 * classpath.
	 *
	 * @param path The path of the resource Returns A URL of the resource from
	 *        either the classpath or file system
	 */
	public static URL load(String path) {
		URL filesystem = loadFromSystem(path);
		URL classpath = loadFromClasspath(path);

		try {
			if (filesystem != null)
				return filesystem;
		}
		// If it is not from the system, then it may be from the classpath.
		catch (NullPointerException e) {
			try {
				if (classpath != null)
					return classpath;
			}
			// If it is not from the system or classpath, then it probably
			// doesn't exist.
			catch (NullPointerException e2) {
				return null;
			}
		}

		return null;
	}

	/**
	 * Streams a resource from the file system (if null, then from the
	 * classpath). This is useful for modding a game without going into the
	 * classpath.
	 *
	 * @param path The path of the resource Returns An InputStream of the
	 *        resource from either the classpath or file system
	 */
	public static InputStream stream(String path) {
		return streamFromUrl(load(path));
	}

	/**
	 * Converts a URL into an InputStream.
	 *
	 * @param url The URL to convert Returns The converted InputStream
	 */
	public static InputStream streamFromUrl(URL url) {
		try {
			return new BufferedInputStream(url.openStream());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
