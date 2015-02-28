package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.utilities.logging.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * A font type for loading bitmap fonts.
 *
 * @author wessles
 */
public class BitmapFont implements Font {

	/**
	 * The characters to render.
	 */
	public SpriteSheet characters;

	/**
	 * Loads a font.
	 *
	 * @param characters The sprite sheet containing all of the characters in the font.
	 */
	private BitmapFont(SpriteSheet characters) {
		if (characters.getNumberOfSubTextures() < STANDARD_CHARACTERS)
			Logger.warn("Recommended to have at least " + STANDARD_CHARACTERS + " characters in a font; may encounter issues.");

		this.characters = characters;

		processFont();
	}

	public static BitmapFont loadBitmapFont(InputStream is, int width, int height) {
		BufferedImage bitmapImage;

		try {
			bitmapImage = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// Turn black, for font rendering reasons.
		for (int x = 0; x < bitmapImage.getWidth(); x++)
			for (int y = 0; y < bitmapImage.getHeight(); y++) {
				int rgba = bitmapImage.getRGB(x, y);

				java.awt.Color col = new java.awt.Color(rgba, true);
				col = new java.awt.Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue(), col.getAlpha());

				bitmapImage.setRGB(x, y, col.getRGB());
			}

		// Get the characters
		SpriteSheet spriteSheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(bitmapImage), width, height);

		return new BitmapFont(spriteSheet);
	}

	/**
	 * The height of the characters in the font.
	 */
	public float fontHeight;

	/**
	 * The maximum width of a character in the font.
	 */
	public float maxFontWidth;

	/**
	 * The average width of a character in the font.
	 */
	public float averageFontWidth;

	private void processFont() {
		for (SubTexture subTexture : characters.getTextures()) {
			maxFontWidth = Math.max(subTexture.getWidth(), maxFontWidth);
			fontHeight = Math.max(subTexture.getHeight(), fontHeight);
			averageFontWidth += subTexture.getWidth();
		}

		averageFontWidth /= characters.getNumberOfSubTextures();
	}

	@Override
	public float getHeight() {
		return fontHeight;
	}

	@Override
	public float getHeight(String message) {
		int lines = 1;

		for (char c : message.toCharArray())
			if (c == '\n')
				lines++;

		return lines * getHeight();
	}

	@Override
	public float getWidth(String message) {
		float totalWidth = 0;
		float lineWidth = 0;

		for (char element : message.toCharArray()) {
			if (element == '\n') {
				totalWidth = Math.max(totalWidth, lineWidth);
				lineWidth = 0;
			}

			lineWidth += characters.getTexture(element).getWidth();
		}

		totalWidth = Math.max(totalWidth, lineWidth);

		return totalWidth;
	}

	@Override
	public float getMaxWidth(int length) {
		return length * maxFontWidth;
	}

	@Override
	public float getAverageWidth(int length) {
		return length * averageFontWidth;
	}

	@Override
	public Texture getFontTexture() {
		return characters.getBaseTexture();
	}

	@Override
	public SpriteSheet getFontSpriteSheet() {
		return characters;
	}

	@Override
	public void cleanup() {
		characters.clean();
	}
}
