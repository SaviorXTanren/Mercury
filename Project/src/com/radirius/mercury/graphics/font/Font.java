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
	 * Derive another differently sized instance of this font. Very resource
	 * heavy, so only call this once (NOT every single frame).
	 *
	 * Returns A newly sized font!
	 */
	public Font deriveFont(float size);

	/**
	 * Derive another differently sized instance of this font. Very resource
	 * heavy, so only call this once (NOT every single frame)
	 *
	 * Returns A newly sized font!
	 */
	public Font deriveFont(int style);

	/**
	 * The size of the font.
	 */
	public float getSize();

	/**
	 * Returns The height of the font.
	 */
	public float getHeight();

	/**
	 * Returns The width of a given string in I, the font.
	 */
	public float getWidth(String message);

	/**
	 * Returns The maximum width that a given string of length len could be.
	 */
	public float getMaxWidth(int length);

	/**
	 * Returns The average width of all number/letter characters, multiplied by
	 * len.
	 */
	public float getAverageWidth(int length);

	/**
	 * Returns The overall texture used for rendering the font.
	 */
	public Texture getFontTexture();
}
