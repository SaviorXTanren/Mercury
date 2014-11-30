package com.radirius.mercury.graphics.font;

import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Loader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * A font type for .TTF's and .OTF's.
 *
 * @author wessles, Jeviny, Sri Harsha Chilakapati
 */
public class TrueTypeFont implements com.radirius.mercury.graphics.font.Font {
	/**
	 * The default Roboto Bold font.
	 */
	public static final TrueTypeFont ROBOTO_BOLD;
	/**
	 * The default Roboto Regular font.
	 */
	public static final TrueTypeFont ROBOTO_REGULAR;
	/**
	 * The default Roboto Thin font.
	 */
	public static final TrueTypeFont ROBOTO_THIN;
	/**
	 * The default Roboto Medium font.
	 */
	public static final TrueTypeFont ROBOTO_MEDIUM;

	static {
		ROBOTO_BOLD = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/mercury/graphics/font/res/Roboto-Bold.ttf"), 22f, 1, true);
		ROBOTO_REGULAR = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/mercury/graphics/font/res/Roboto-Regular.ttf"), 22f, 1, true);
		ROBOTO_MEDIUM = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/mercury/graphics/font/res/Roboto-Medium.ttf"), 22f, 1, true);
		ROBOTO_THIN = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/mercury/graphics/font/res/Roboto-Thin.ttf"), 22f, 1, true);
	}

	public static final int STANDARD_CHARACTERS = 256;
	/**
	 * All data for all characters.
	 */
	public final IntObject[] chars = new IntObject[STANDARD_CHARACTERS];

	/**
	 * Defines whether or not the text is anti-aliased.
	 */
	private boolean antialias;

	/**
	 * The size of the font
	 */
	private float fontSize = 0;

	/**
	 * The height of the font
	 */
	private float fontHeight = 0;

	/**
	 * The maximum number/letter character width
	 */
	private float fontMaxWidth = 0;

	/**
	 * The average number/letter character width
	 */
	private float fontAverageWidth = 0;

	/**
	 * The overall texture used for rendering the font.
	 */
	private Texture fontTexture;

	private int baseWidth = 0;
	private int baseHeight = 0;

	/**
	 * Some AWT jargon for fonts.
	 */
	private java.awt.Font font;

	/**
	 * Some more AWT jargon for fonts.
	 */
	private FontMetrics fontMetrics;

	private TrueTypeFont(java.awt.Font font, boolean antialias) {
		this.font = font;
		this.antialias = antialias;

		fontSize = font.getSize();

		createSet();
	}

	/**
	 * Let's load a font!
	 *
	 * @param is        The stream for the font.
	 * @param size      The size of the font.
	 * @param style     The style of the font.
	 * @param antialias Whether or not the text is anti-aliased.
	 */
	public static TrueTypeFont loadTrueTypeFont(InputStream is, float size, int style, boolean antialias) {
		java.awt.Font font;
		try {
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
			font = font.deriveFont(size);

			return loadTrueTypeFont(font, antialias);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Let's load a font!
	 *
	 * @param font      The base awt font.
	 * @param antialias Whether or not the text is anti-aliased.
	 */

	public static TrueTypeFont loadTrueTypeFont(java.awt.Font font, boolean antialias) {
		return new TrueTypeFont(font, antialias);
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
		g.setColor(new java.awt.Color(255, 255, 255, 0));
		g.fillRect(0, 0, baseWidth, baseHeight);

		// Initialize temporary variables.
		float positionX = 0;
		float positionY = 0;

		// Loop through all standard characters (256 of
		// them)
		for (int i = 0; i < STANDARD_CHARACTERS; i++) {
			char ch = (char) i;

			// BufferedImage for the character
			BufferedImage fontImage = getFontImage(ch);

			// New IntObject with width and height of
			// fontImage.
			IntObject newIntObject = new IntObject();
			newIntObject.w = fontImage.getWidth();
			newIntObject.h = fontImage.getHeight();

			// Go to next row if there is no room on x axis.
			if (positionX + newIntObject.w >= baseWidth) {
				positionX = 0;
				positionY += getHeight();
			}

			// Set the positions
			newIntObject.x = positionX;
			newIntObject.y = positionY;

			// Draw the character onto the font image.
			g.drawImage(fontImage, (int) positionX, (int) positionY, null);

			// Next position on x axis.
			positionX += newIntObject.w;

			// Set the IntObject of the character
			chars[i] = newIntObject;

			fontImage = null;
		}

		// Turn black, for coloring reasons.
		for (int x = 0; x < imgTemp.getWidth(); x++)
			for (int y = 0; y < imgTemp.getHeight(); y++) {
				int rgba = imgTemp.getRGB(x, y);

				Color col = new Color(rgba, true);
				col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue(), col.getAlpha());

				imgTemp.setRGB(x, y, col.getRGB());
			}

		// Load texture!
		fontTexture = Texture.loadTexture(imgTemp);
	}

	private BufferedImage getFontImage(char ch) {
		// Make and init graphics for character image.
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();

		if (antialias)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(font);

		// Font preperation.
		fontMetrics = g.getFontMetrics();

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

		if (antialias == true)
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setFont(font);

		// Set the text color to white, set x and y, and
		// return to font image.
		g.setColor(Color.WHITE);
		g2d.drawString(String.valueOf(ch), 0, fontMetrics.getAscent());

		return fontImage;
	}

	@Override
	public float getWidth(String message) {
		float totalWidth = 0;

		IntObject intObject = null;

		int currentChar = 0;

		for (char element : message.toCharArray()) {
			currentChar = element;

			if (currentChar < STANDARD_CHARACTERS)
				intObject = chars[currentChar];

			if (intObject != null)
				totalWidth += intObject.w;
		}

		return totalWidth;
	}

	@Override
	public float getMaxWidth(int len) {
		return len * fontMaxWidth;
	}

	@Override
	public float getAverageWidth(int len) {
		return len * fontAverageWidth;
	}

	@Override
	public Font deriveFont(float size) {
		return new TrueTypeFont(font.deriveFont(size), antialias);
	}

	@Override
	public Font deriveFont(int style) {
		return new TrueTypeFont(font.deriveFont(style), antialias);
	}

	@Override
	public float getSize() {
		return fontSize;
	}

	@Override
	public float getHeight() {
		return fontHeight;
	}

	@Override
	public Texture getFontTexture() {
		return fontTexture;
	}

	@Override
	public void clean() {
		fontTexture.clean();
	}

	/**
	 * An object type for storing data for each character.
	 */
	public static class IntObject {
		public float w;
		public float h;
		public float x;
		public float y;
	}
}
