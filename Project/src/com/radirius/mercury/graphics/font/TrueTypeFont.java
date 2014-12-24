package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.resource.Loader;

import java.awt.Color;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * A font type for .TTF's and .OTF's.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
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
		ROBOTO_BOLD = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/graphics/font/res/Roboto-Bold.ttf"), 22f, true);
		ROBOTO_REGULAR = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/graphics/font/res/Roboto-Regular.ttf"), 22f, true);
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

	private int baseWidth;
	private int baseHeight;

	/**
	 * Some AWT jargon for fonts.
	 */
	private java.awt.Font font;

	private TrueTypeFont(java.awt.Font font, boolean smooth) {
		this.font = font;
		this.smooth = smooth;

		fontSize = font.getSize();

		createSet();
	}

	/**
	 * Loads a font.
	 *
	 * @param isUrl The URL for the stream for the font.
	 * @param size  The size of the font.
	 */
	public static TrueTypeFont loadTrueTypeFont(URL isUrl, float size) {
		return loadTrueTypeFont(isUrl, size, true);
	}

	/**
	 * Loads a font.
	 *
	 * @param is   The stream for the font.
	 * @param size The size of the font.
	 */
	public static TrueTypeFont loadTrueTypeFont(InputStream is, float size) {
		return loadTrueTypeFont(is, size, true);
	}

	/**
	 * Loads a font.
	 *
	 * @param isUrl  The URL for the stream for the font.
	 * @param size   The size of the font.
	 * @param smooth Whether or not the text is smoothed.
	 */
	public static TrueTypeFont loadTrueTypeFont(URL isUrl, float size, boolean smooth) {
		return loadTrueTypeFont(Loader.streamFromUrl(isUrl), size, smooth);
	}

	/**
	 * Loads a font.
	 *
	 * @param is     The stream for the font.
	 * @param size   The size of the font.
	 * @param smooth Whether or not the text is smoothed.
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
	 * @param font   The base awt font.
	 * @param smooth Whether or not the text is smoothed.
	 */

	public static TrueTypeFont loadTrueTypeFont(java.awt.Font font, boolean smooth) {
		return new TrueTypeFont(font, smooth);
	}

	private void createSet() {
		for (int i = 0; i < STANDARD_CHARACTERS; i++) {
			char ch = (char) i;

			BufferedImage fontimg = getFontImage(ch);

			baseWidth += fontimg.getWidth();
			baseHeight = Math.max(fontimg.getHeight(), baseHeight);
		}

		baseWidth /= 8;
		baseHeight *= 8;

		// Make a graphics object for the buffered image.
		BufferedImage imgTemp = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		// Set the color to transparent
		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, baseWidth, baseHeight);

		// Initialize temporary variables.
		float positionX = 0;
		float positionY = 0;

		int subXs[] = new int[STANDARD_CHARACTERS], subYs[] = new int[STANDARD_CHARACTERS], subWidths[] = new int[STANDARD_CHARACTERS], subHeights[] = new int[STANDARD_CHARACTERS];

		// Loop through all standard characters (256 of them)
		for (int i = 0; i < STANDARD_CHARACTERS; i++) {
			char ch = (char) i;

			// BufferedImage for the character
			BufferedImage fontImage = getFontImage(ch);

			// Go to next row if there is no room on x axis
			if (positionX + fontImage.getWidth() >= baseWidth) {
				positionX = 0;
				positionY += getHeight();
			}

			g.setColor(Color.BLACK);
			// Draw the character onto the font image
			g.drawImage(fontImage, (int) positionX, (int) positionY, null);

			// Set the parameters of the character at i
			subXs[i] = (int) positionX;
			subYs[i] = (int) positionY;

			// Next position on x axis.
			positionX += fontImage.getWidth();

			// Set the parameters of the character at i
			subWidths[i] = fontImage.getWidth();
			subHeights[i] = fontImage.getHeight();
		}

		// Load texture and spritesheet.
		Texture fontTexture = Texture.loadTexture(imgTemp, Texture.FILTER_NEAREST);

		SubTexture[] characterSubs = new SubTexture[STANDARD_CHARACTERS];
		for (int i = 0; i < characterSubs.length; i++)
			characterSubs[i] = new SubTexture(fontTexture, subXs[i], subYs[i], subXs[i] + subWidths[i], subYs[i] + subHeights[i]);

		this.characters = SpriteSheet.loadSpriteSheet(fontTexture, characterSubs);
	}

	private BufferedImage getFontImage(char ch) {
		// Make and init graphics for character image.
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();

		if (smooth)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(font);

		// Font preperation.
		FontMetrics fontMetrics = g.getFontMetrics();

		float charwidth = fontMetrics.charWidth(ch);
		// Safety guards just in case.
		if (charwidth <= 0)
			charwidth = 1;

		if (Character.isLetterOrDigit(ch)) {
			fontMaxWidth = Math.max(fontMaxWidth, charwidth);
			fontAverageWidth += charwidth / STANDARD_CHARACTERS;
		}

		// Height!
		fontHeight = fontMetrics.getHeight();

		// Now to the actual image!
		BufferedImage fontImage = new BufferedImage((int) charwidth, (int) getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) fontImage.getGraphics();

		if (smooth)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);

		// Set the text color to white, set x and y, and return to font image.
		g.setColor(Color.WHITE);
		g2d.drawString(String.valueOf(ch), 0, fontMetrics.getAscent());

		return fontImage;
	}

	/**
	 * Derive another differently sized instance of this font; very resource heavy.
	 *
	 * @return A newly sized font
	 */
	public TrueTypeFont deriveFont(float size) {
		return new TrueTypeFont(font.deriveFont(size), smooth);
	}

	/**
	 * Returns the size of the font
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
			SubTexture characterSub = null;

			if (element < STANDARD_CHARACTERS)
				characterSub = characters.getTexture(element);

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
