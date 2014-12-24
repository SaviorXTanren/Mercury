package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.utilities.misc.Cleanable;

/**
 * An abstraction for all font types.
 *
 * @author wessles
 */
public interface Font extends Cleanable {

	/** The bare-minimum amount of characters. */
	public final static int STANDARD_CHARACTERS = 256;

	/**
	 * Returns the height of the font
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
	 * Returns the overall texture used for rendering the font
	 */
	public Texture getFontTexture();

	/**
	 * Returns the spritesheet of the characters in the font
	 */
	public SpriteSheet getFontSpriteSheet();
}
