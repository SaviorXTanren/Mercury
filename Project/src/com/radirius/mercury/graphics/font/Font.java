package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Resource;

/**
 * An abstraction for fonts.
 *
 * @author wessles
 */
public interface Font extends Resource {
	/**
	 * Derive another differently sized instance of this font; very resource heavy.
	 *
	 * @return A newly sized font
	 */
	public Font deriveFont(float size);

	/**
	 * Derive another differently sized instance of this font; very resource heavy.
	 *
	 * @return A newly sized font
	 */
	public Font deriveFont(int style);

	/**
	 * @return The size of the font
	 */
	public float getSize();

	/**
	 * @return The height of the font
	 */
	public float getHeight();

	/**
	 * @param message The string to find the height of
	 * @return The height of the string in the font
	 */
	public float getHeight(String message);

	/**
	 * @param message The string to find the width of
	 * @return The width of the string in the font
	 */
	public float getWidth(String message);

	/**
	 * @param length The length of a string
	 * @return The maximum width that a string of a length could be
	 */
	public float getMaxWidth(int length);

	/**
	 * @param length The length of a string
	 * @return The average width of all number/letter characters, multiplied by a certain length
	 */
	public float getAverageWidth(int length);

	/**
	 * @return The overall texture used for rendering the font
	 */
	public Texture getFontTexture();
}
