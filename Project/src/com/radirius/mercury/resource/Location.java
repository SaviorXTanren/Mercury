package com.radirius.mercury.resource;

import java.net.URL;

/**
 * A location that a resource can be loaded from.
 *
 * @author Jeviny
 */
public interface Location {
	/**
	 * Get a resource as a URL
	 *
	 * @param location
	 * 		The location to the resource to retrieve.
	 *
	 * @return a URL from which the resource can be read.
	 */
	public URL getResource(String location);
}
