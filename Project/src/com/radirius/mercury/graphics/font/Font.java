package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.utilities.misc.Cleanable;

/**
 * An abstraction for all font types.
 *
 * @author wessles
 */
public interface Font extends Cleanable {

	/**
	 * The bare-minimum amount of characters.
	 */
	public final static int STANDARD_CHARACTERS = 256;

	/**
	 * A margin for preventing texture bleeding.
	 */
	public static int ANTI_TEXTURE_BLEEDING_MARGIN = 2;

	/**
	 * @return the height of the font
	 */
	public float getHeight();

	/**
	 * @param message
	 * 		The string to find the height of
	 *
	 * @return the height of the string in the font
	 */
	public float getHeight(String message);

	/**
	 * @param message
	 * 		The string to find the width of
	 *
	 * @return the width of the string in the font
	 */
	public float getWidth(String message);

	/**
	 * @param length
	 * 		The length of a string
	 *
	 * @return the maximum width that a string of a length could be
	 */
	public float getMaxWidth(int length);

	/**
	 * @param length
	 * 		The length of a string
	 *
	 * @return the average width of all number/letter characters, multiplied by a certain length
	 */
	public float getAverageWidth(int length);

	/**
	 * @return the overall texture used for rendering the font
	 */
	public Texture getFontTexture();

	/**
	 * @return the spritesheet of the characters in the font
	 */
	public SpriteSheet getFontSpriteSheet();
}
