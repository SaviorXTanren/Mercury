package radirius.merc.graphics;

/**
 * A class for a color that will hold R, G, B, and A values.
 *
 * @authors wessles, Jeviny
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

	public static final Color CLEAR = new Color(0, 0, 0, 0);
	public static final Color GRAY = GREY.duplicate();

	public static final Color DEFAULT_BACKGROUND = BLACK;
	public static final Color DEFAULT_DRAWING = WHITE;
	public static final Color DEFAULT_TEXTURE_COLOR = BLACK;
	public static final Color DEFAULT_TEXT_COLOR = WHITE;

	/** Red, Green, Blue, and Alpha variables. */
	public float r = 0, g = 0, b = 0, a = 0;

	/**
	 * Creates a color with all 4 RGBA components.
	 *
	 * @param r
	 *            The Red Component.
	 * @param g
	 *            The Green Component.
	 * @param b
	 *            The Blue Component.
	 * @param a
	 *            The Alpha Component.
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
	 * @param r
	 *            The Red Component.
	 * @param g
	 *            The Green Component.
	 * @param b
	 *            The Blue Component.
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	/**
	 * Creates a color with all 4 RGBA components in integers.
	 *
	 * @param r
	 *            The Red Component.
	 * @param g
	 *            The Green Component.
	 * @param b
	 *            The Blue Component.
	 * @param a
	 *            The Alpha Component.
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
	 * @param r
	 *            The Red Component.
	 * @param g
	 *            The Green Component.
	 * @param b
	 *            The Blue Component.
	 */
	public Color(int r, int g, int b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = 1f;
	}

	/**
	 * Creates a color with a hex notation.
	 *
	 * @param value
	 *            The integer to take the color from.
	 */
	public Color(int value) {
		r = (value >> 16) & 0xff;
		g = (value >> 8) & 0xff;
		b = (value) & 0xff;
		a = 0xff;
	}

	/** @return The red component. */
	public float getRed() {
		return r;
	}

	/** @return The green component. */
	public float getGreen() {
		return g;
	}

	/** @return The blue component. */
	public float getBlue() {
		return b;
	}

	/** @return The alpha component. */
	public float getAlpha() {
		return a;
	}

	/** Sets the red component. */
	public void setRed(float r) {
		this.r = r;
	}

	/** Sets the green component. */
	public void setGreen(float g) {
		this.g = g;
	}

	/** Sets the blue component. */
	public void setBlue(float b) {
		this.b = b;
	}

	/** Sets the alpha component. */
	public void setAlpha(float a) {
		this.a = a;
	}

	/** Sets the color with RGBA values in integers. */
	public void set(int r, int g, int b, int a) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
	}

	/** Sets the color with RGB values in integers. */
	public void set(int r, int g, int b) {
		set(r, g, b, 1f);
	}

	/** Sets the color with RGBA values. */
	public void set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/** Sets the color with RGB values. */
	public void set(float r, float g, float b) {
		set(r, g, b, 255);
	}

	/** Duplicates the color. */
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
