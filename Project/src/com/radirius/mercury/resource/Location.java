package com.radirius.mercury.resource;

import java.io.InputStream;
import java.net.URL;

/**
 * A location that a resource can be loaded from.
 * 
 * @author Jeviny
 */
public interface Location {
	/**
	 * Get a resource as an input stream
	 *
	 * @param location The location to the resource to retrieve.
	 * @return A stream from which the resource can be read or null if the
	 *         resource can't be found in this location.
	 */
	public InputStream getResourceAsStream(String location);

	/**
	 * Get a resource as a URL
	 *
	 * @param location The location to the resource to retrieve.
	 * @return A URL from which the resource can be read.
	 */
	public URL getResource(String location);
}
