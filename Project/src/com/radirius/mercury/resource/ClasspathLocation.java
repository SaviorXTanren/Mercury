package com.radirius.mercury.resource;

import java.io.InputStream;
import java.net.URL;

/**
 * A resource location that searches the classpath.
 *
 * @author Kevin Glass
 * @author Jeviny
 */
public class ClasspathLocation implements Location {
	@Override
	public URL getResource(String path) {
		String resourceLocation = path.replace('\\', '/');

		return Loader.class.getClassLoader().getResource(resourceLocation);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		String resourceLocation = path.replace('\\', '/');

		return Loader.class.getClassLoader().getResourceAsStream(resourceLocation);
	}
}
