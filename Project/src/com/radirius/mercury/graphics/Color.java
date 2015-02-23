package com.radirius.mercury.graphics;

/**
 * A class for a color that will hold R, G, B, and A values.
 *
 * @author wessles
 * @author Jeviny
 * @author opiop65
 * @author KevGlass
 */
public class Color {
	public static final Color TURQUOISE = new Color(26, 188, 156);
	public static final Color GREEN_SEA = new Color(22, 160, 133);
	public static final Color GREEN = new Color(46, 204, 113);
	public static final Color DARK_GREEN = new Color(39, 174, 96);
	public static final Color BLUE = new Color(52, 152, 219);
	public static final Color OCEAN_BLUE = new Color(41, 128, 185);
	public static final Color PURPLE = new Color(155, 89, 182);
	public static final Color VIOLET = new Color(142, 68, 173);
	public static final Color CHARCOAL = new Color(52, 73, 94);
	public static final Color ASPHALT = new Color(44, 62, 80);
	public static final Color YELLOW = new Color(241, 196, 15);
	public static final Color ORANGE = new Color(243, 156, 18);
	public static final Color CARROT = new Color(230, 126, 34);
	public static final Color PUMPKIN = new Color(211, 84, 0);
	public static final Color RED = new Color(231, 76, 60);
	public static final Color CRIMSON = new Color(192, 57, 43);

	public static final Color CLOUDS = new Color(236, 240, 241);
	public static final Color SILVER = new Color(189, 195, 199);
	public static final Color CONCRETE = new Color(149, 165, 166);
	public static final Color ASBESTOS = new Color(127, 140, 141);

	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color MARBLE = new Color(188, 188, 188);
	public static final Color GREY = new Color(125, 125, 125);
	public static final Color COAL = new Color(63, 63, 63);
	public static final Color BLACK = new Color(0, 0, 0);

	public static final Color DEFAULT_DRAWING = WHITE;
	public static final Color DEFAULT_BACKGROUND = BLACK;
	public static final Color DEFAULT_TEXTURE = BLACK;

	public static final Color CLEAR = new Color(0, 0, 0, 0);
	public static final Color GRAY = GREY.duplicate();

	/**
	 * Red, Green, Blue, and Alpha variables.
	 */
	public float r = 0, g = 0, b = 0, a = 0;

	/**
	 * Creates a color with all 4 RGBA components.
	 *
	 * @param r The Red Component.
	 * @param g The Green Component.
	 * @param b The Blue Component.
	 * @param a The Alpha Component.
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Creates a color with 3 RGB components.
	 *
	 * @param r The Red Component.
	 * @param g The Green Component.
	 * @param b The Blue Component.
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		a = 1f;
	}

	/**
	 * Creates a color with all 4 RGBA components in integers.
	 *
	 * @param r The Red Component.
	 * @param g The Green Component.
	 * @param b The Blue Component.
	 * @param a The Alpha Component.
	 */
	public Color(int r, int g, int b, int a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
	}

	/**
	 * Creates a color with 3 RGB components in integers.
	 *
	 * @param r The Red Component.
	 * @param g The Green Component.
	 * @param b The Blue Component.
	 */
	public Color(int r, int g, int b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		a = 1f;
	}

	/**
	 * Creates a color with a hex notation.
	 *
	 * @param value The integer to take the color from.
	 */
	public Color(int value) {
		r = value >> 16 & 0xff;
		g = value >> 8 & 0xff;
		b = value & 0xff;
		a = 0xff;
	}

	/**
	 * Returns The red component.
	 */
	public float getRed() {
		return r;
	}

	/**
	 * Sets the red component.
	 *
	 * @return This color
	 */
	public Color setRed(float r) {
		this.r = r;
		return this;
	}

	/**
	 * Returns The green component.
	 */
	public float getGreen() {
		return g;
	}

	/**
	 * Sets the green component.
	 *
	 * @return This color
	 */
	public Color setGreen(float g) {
		this.g = g;
		return this;
	}

	/**
	 * Returns The blue component.
	 */
	public float getBlue() {
		return b;
	}

	/**
	 * Sets the blue component.
	 *
	 * @return This color
	 */
	public Color setBlue(float b) {
		this.b = b;
		return this;
	}

	/**
	 * Returns The alpha component.
	 */
	public float getAlpha() {
		return a;
	}

	/**
	 * Sets the alpha component.
	 *
	 * @return This color
	 */
	public Color setAlpha(float a) {
		this.a = a;
		return this;
	}

	/**
	 * Sets the color with RGBA values in integers.
	 *
	 * @return This Color
	 */
	public Color set(int r, int g, int b, int a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;

		return this;
	}

	/**
	 * Sets the color with RGB values in integers.
	 *
	 * @return This Color
	 */
	public Color set(int r, int g, int b) {
		set(r, g, b, 1f);
		return this;
	}

	/**
	 * Sets the color with RGBA values.
	 *
	 * @return This Color
	 */
	public Color set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	/**
	 * Sets the color with RGB values.
	 *
	 * @return This Color
	 */
	public Color set(float r, float g, float b) {
		set(r, g, b, 255);
		return this;
	}

	/**
	 * Adds to the color values (not including alpha).
	 *
	 * @param adder The value to add
	 * @return This color
	 */
	public Color add(float adder) {
		this.r += adder;
		this.g += adder;
		this.b += adder;

		return this;
	}

	/**
	 * Multiplies the color values (not including alpha).
	 *
	 * @param factor The factor to multiply by
	 * @return This color
	 */
	public Color multiply(float factor) {
		this.r *= factor;
		this.g *= factor;
		this.b *= factor;

		return this;
	}

	/**
	 * Duplicates the color.
	 */
	public Color duplicate() {
		return new Color(r, g, b, a);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Color) {
			Color color = (Color) object;

			if (color.r == r && color.g == g && color.b == b && color.a == a)
				return true;
		}

		return false;
	}
}
