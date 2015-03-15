package com.radirius.mercury.resource;

import java.io.*;
import java.net.URL;

/**
 * A resource loading path that searches somewhere on the classpath.
 *
 * @author Kevin Glass
 * @author Jeviny
 */
public class SystemLocation implements Location {
	private File root;

	/**
	 * Create a new resource path based on the file system.
	 *
	 * @param root
	 * 		The root of the file system to search.
	 */
	public SystemLocation(File root) {
		this.root = root;
	}

	@Override
	public URL getResource(String path) {
		try {
			File file = new File(root, path);

			return file.toURI().toURL();
		} catch (IOException e) {
			return null;
		}
	}
}
