package radirius.merc.resource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A utility for resource management to load different resources from specific
 * roots.
 * 
 * @author wessles, Jeviny
 */

public class Loader {
	/** @return The URL from a classpath. */
	public static URL loadFromClasspath(String path) {
		path = path.replace('\\', '/');

		return Loader.class.getClassLoader().getResource(path);
	}

	/** @return The URL from a file system. */
	public static URL loadFromSystem(String path) {
		path = path.replace('\\', '/');
		
		try {
			return new URL("file:" + path);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/** @return The InputStream from a classpath. */
	public static InputStream streamFromClasspath(String path) {
		try {
			return new BufferedInputStream(loadFromClasspath(path).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/** @return The InputStream from a file system. */
	public static InputStream streamFromSystem(String path) {
		try {
			return new BufferedInputStream(loadFromSystem(path).openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @return The URL from a file system. If null, from the classpath. This is
	 *         for easier modding.
	 */
	public static URL load(String path) {
		URL filesystem = loadFromSystem(path);
		URL classpath = loadFromClasspath(path);

		if (filesystem != null)
			return filesystem;
		else if (classpath != null)
			return classpath;

		return null;
	}

	/**
	 * @return The InputStream from a file system. If null, from the classpath.
	 *         This is for easier modding.
	 */
	public static InputStream stream(String path) {
		InputStream filesystem = streamFromSystem(path);
		InputStream classpath = streamFromClasspath(path);

		if (filesystem != null)
			return filesystem;
		else if (classpath != null)
			return classpath;

		return null;
	}
}
