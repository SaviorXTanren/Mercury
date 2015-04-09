package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.resource.*;
import com.radirius.mercury.utilities.logging.Logger;

import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * A font type for .TTFs and .OTFs.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 * @author kevglass
 */
public class TrueTypeFont implements Font {
	/**
	 * The default Roboto Bold font.
	 */
	public static final TrueTypeFont ROBOTO_BOLD;
	/**
	 * The default Roboto Regular font.
	 */
	public static final TrueTypeFont ROBOTO_REGULAR;

	static {
		Loader.pushLocation(new ClasspathLocation());
		ROBOTO_BOLD = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/graphics/font/res/Roboto-Bold.ttf"), 22f, true);
		ROBOTO_REGULAR = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/graphics/font/res/Roboto-Regular.ttf"), 22f, true);
		Loader.popLocation();
	}


	/**
	 * All data for all characters.
	 */
	public SpriteSheet characters;

	/**
	 * Defines whether or not the text is smoothed.
	 */
	private boolean smooth;

	/**
	 * The size of the font
	 */
	private float fontSize;

	/**
	 * The height of the font
	 */
	private float fontHeight;

	/**
	 * The maximum number/letter character width
	 */
	private float fontMaxWidth;

	/**
	 * The average number/letter character width
	 */
	private float fontAverageWidth;

	/**
	 * Some AWT jargon for fonts.
	 */
	private java.awt.Font font;

	private TrueTypeFont(java.awt.Font font, boolean smooth) {
		this.font = font;
		this.smooth = smooth;

		fontSize = font.getSize();

		int baseWidth = 2048;
		int baseHeight = 2048;

		nullCharactersLoop:
		while (characters == null) {
			// Make a graphics object for the buffered image.
			BufferedImage imgTemp = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) imgTemp.getGraphics();

			// Set the color to transparent
			g.setColor(new Color(255, 255, 255, 0));
			g.fillRect(0, 0, baseWidth, baseHeight);

			// Initialize temporary variables.
			float positionX = 0;
			float positionY = ANTI_TEXTURE_BLEEDING_MARGIN;

			int subXs[] = new int[STANDARD_CHARACTERS], subYs[] = new int[STANDARD_CHARACTERS], subWidths[] = new int[STANDARD_CHARACTERS], subHeights[] = new int[STANDARD_CHARACTERS];

			g.setFont(font);
			g.setColor(Color.BLACK);
			if (smooth)
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Font preparation.
			FontMetrics fontMetrics = g.getFontMetrics();
			fontHeight = fontMetrics.getHeight();

			// Loop through all standard characters (256 of them)
			for (int i = 0; i < STANDARD_CHARACTERS; i++) {
				char ch = (char) i;

				float charWidth = fontMetrics.charWidth(ch);
				// Safety guards just in case.
				if (charWidth <= 0)
					charWidth = 1;
				if (Character.isLetterOrDigit(ch)) {
					fontMaxWidth = Math.max(fontMaxWidth, charWidth);
					fontAverageWidth += charWidth / STANDARD_CHARACTERS;
				}

				// Go to next row if there is no room on x axis
				if (positionX + charWidth + ANTI_TEXTURE_BLEEDING_MARGIN * 2 >= baseWidth) {
					positionX = 0;
					positionY += getHeight() + ANTI_TEXTURE_BLEEDING_MARGIN;
					if (positionY + getHeight() + ANTI_TEXTURE_BLEEDING_MARGIN > baseHeight) {
						Logger.warn("TrueTypeFont texture exceeded " + baseWidth + "x" + baseHeight + "; retrying at " + baseWidth * 2 + "x" + baseHeight * 2 + ".");
						baseWidth *= 2;
						baseHeight *= 2;
						continue nullCharactersLoop;
					}
				}

				positionX += ANTI_TEXTURE_BLEEDING_MARGIN;

				// Draw the character onto the font image
				g.drawString(String.valueOf(ch), (int) positionX, (int) positionY + fontMetrics.getAscent());

				// Set the parameters of the character at i
				subXs[i] = (int) positionX;
				subYs[i] = (int) positionY;

				// Next position on x axis.
				positionX += charWidth + ANTI_TEXTURE_BLEEDING_MARGIN;

				// Set the parameters of the character at i
				subWidths[i] = (int) charWidth;
				subHeights[i] = (int) getHeight();
			}

			// Load texture and sprite sheet.
			Texture fontTexture = Texture.loadTexture(imgTemp, Texture.FILTER_NEAREST);

			SubTexture[] characterSubs = new SubTexture[STANDARD_CHARACTERS];
			for (int i = 0; i < characterSubs.length; i++)
				characterSubs[i] = new SubTexture(fontTexture, subXs[i], subYs[i], subXs[i] + subWidths[i], subYs[i] + subHeights[i]);

			this.characters = SpriteSheet.loadSpriteSheet(fontTexture, characterSubs);
		}
	}

	/**
	 * Loads a font.
	 *
	 * @param isUrl
	 * 		The URL for the stream for the font.
	 * @param size
	 * 		The size of the font.
	 */
	public static TrueTypeFont loadTrueTypeFont(URL isUrl, float size) {
		return loadTrueTypeFont(isUrl, size, true);
	}

	/**
	 * Loads a font.
	 *
	 * @param is
	 * 		The stream for the font.
	 * @param size
	 * 		The size of the font.
	 */
	public static TrueTypeFont loadTrueTypeFont(InputStream is, float size) {
		return loadTrueTypeFont(is, size, true);
	}

	/**
	 * Loads a font.
	 *
	 * @param isUrl
	 * 		The URL for the stream for the font.
	 * @param size
	 * 		The size of the font.
	 * @param smooth
	 * 		Whether or not the text is smoothed.
	 */
	public static TrueTypeFont loadTrueTypeFont(URL isUrl, float size, boolean smooth) {
		InputStream stream = Loader.streamFromUrl(isUrl);
		return loadTrueTypeFont(stream, size, smooth);
	}

	/**
	 * Loads a font.
	 *
	 * @param is
	 * 		The stream for the font.
	 * @param size
	 * 		The size of the font.
	 * @param smooth
	 * 		Whether or not the text is smoothed.
	 */
	public static TrueTypeFont loadTrueTypeFont(InputStream is, float size, boolean smooth) {
		java.awt.Font font;
		try {
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			font = font.deriveFont(size);

			return loadTrueTypeFont(font, smooth);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Loads a font.
	 *
	 * @param font
	 * 		The base awt font.
	 * @param smooth
	 * 		Whether or not the text is smoothed.
	 */

	public static TrueTypeFont loadTrueTypeFont(java.awt.Font font, boolean smooth) {
		return new TrueTypeFont(font, smooth);
	}

	/**
	 * Derive another differently sized instance of this font; very resource heavy.
	 *
	 * @return a newly sized font
	 */
	public TrueTypeFont deriveFont(float size) {
		return new TrueTypeFont(font.deriveFont(size), smooth);
	}

	/**
	 * @return the size of the font
	 */
	public float getSize() {
		return fontSize;
	}

	@Override
	public float getHeight() {
		return fontHeight;
	}

	@Override
	public float getHeight(String message) {
		message = message.trim();

		if (message.length() == 0)
			return 0;

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
			SubTexture characterSub = characters.getTexture(element);

			if (element == '\n') {
				totalWidth = Math.max(totalWidth, lineWidth);
				lineWidth = 0;
			}

			lineWidth += characterSub.getWidth();
		}

		totalWidth = Math.max(totalWidth, lineWidth);

		return totalWidth;
	}

	@Override
	public float getMaxWidth(int length) {
		return length * fontMaxWidth;
	}

	@Override
	public float getAverageWidth(int length) {
		return length * fontAverageWidth;
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
