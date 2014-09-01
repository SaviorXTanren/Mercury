package radirius.merc.graphics.gui;

import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.graphics.font.Font;
import radirius.merc.graphics.font.TrueTypeFont;
import radirius.merc.input.Input;

/**
 * @author wessles, Jeviny
 */

public class TextField extends TextBar {
	public static final int INPUT_STRING = 0, INPUT_SIGNED_INTEGER_NUM = 1, INPUT_UNSIGNED_INTEGER_NUM = 2, INPUT_INTEGER = INPUT_SIGNED_INTEGER_NUM, INPUT_SIGNED_FLOATING_POINT_NUM = 3, INPUT_UNSIGNED_FLOATING_POINT_NUM = 4, INPUT_FLOATING_POINT_NUM = INPUT_SIGNED_INTEGER_NUM;

	protected int INPUT_TYPE = INPUT_STRING;
	protected int startidx, limit;

	public TextField(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, Font textfont, int INPUT_TYPE, int limit) {
		super(txt, left, right, body, x, y, textcolor, textfont);

		this.INPUT_TYPE = INPUT_TYPE;

		startidx = txt.length();

		// +1 for the cursor!
		this.limit = limit + startidx;
	}

	public TextField(String txt, float x, float y, Color textcolor, int INPUT_TYPE, int limit) {
		this(txt, getDefaultTextures().getTexture(10), getDefaultTextures().getTexture(10).convertToTexture(true, false), getDefaultTextures().getTexture(11), x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR, INPUT_TYPE, limit);
	}

	public TextField(String txt, Texture left, Texture right, Texture body, float x, float y, Color textcolor, int INPUT_TYPE, int limit) {
		this(txt, left, right, body, x, y, textcolor, TrueTypeFont.OPENSANS_REGULAR, INPUT_TYPE, limit);
	}

	public TextField(String txt, Texture left, Texture right, Texture body, float x, float y, int INPUT_TYPE, int limit) {
		this(txt, left, right, body, x, y, Color.black, INPUT_TYPE, limit);
	}

	public TextField(String txt, float x, float y, int INPUT_TYPE, int limit) {
		this(txt, x, y, Color.black, INPUT_TYPE, limit);
	}

	public int getInputType() {
		return INPUT_TYPE;
	}

	public TextField setInputType(int type) {
		INPUT_TYPE = type;

		return this;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		// Focus thingies
		if (!hasFocus())
			return;

		// The typed character!
		char nextchar = Runner.getInstance().getInput().getNextCharacter();

		// Nothing was typed...
		if (nextchar == 0)
			return;

		// Is at the start.
		boolean isatstart = content.length() == startidx;

		// Backspaces too.
		if (nextchar == '\b' && content.length() != 0 && !isatstart) {
			content = content.substring(0, content.length() - 1);
			return;
		}

		// Nothing more can fit!
		if (content.length() == limit)
			return;

		// Check if the next character is valid!
		boolean valid = true;

		// Number input!
		if (INPUT_TYPE != INPUT_STRING)
			switch (INPUT_TYPE) {
				case INPUT_UNSIGNED_INTEGER_NUM:
					valid = Character.isDigit(nextchar);
					break;
				case INPUT_SIGNED_INTEGER_NUM:
					valid = Character.isDigit(nextchar) || nextchar == '-' && isatstart;
					break;
				case INPUT_UNSIGNED_FLOATING_POINT_NUM:
					valid = Character.isDigit(nextchar) || nextchar == '.';
					break;
				case INPUT_SIGNED_FLOATING_POINT_NUM:
					valid = Character.isDigit(nextchar) || nextchar == '.' || nextchar == '-' && isatstart;
					break;
			}

		// Validity means addition!
		if (valid)
			content += nextchar;
	}

	@Override
	public void render(Graphics g) {
		addCursor();

		super.render(g);

		removeCursor();
	}

	public String getInput() {
		String result = content.substring(startidx);

		if ((result.endsWith("-") || result.isEmpty()) && INPUT_TYPE != INPUT_STRING)
			return "0";

		return result;
	}

	/** @return Whether or not ENTER was pressed. */
	public boolean wasEntered() {
		return Runner.getInstance().getInput().keyClicked(Input.KEY_ENTER) || !hasFocus();
	}

	String cursor = "_";

	/**
	 * Sets the cursor, the character that will be put always at the end of the
	 * input.
	 */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	/** Adds cursor character. */
	protected void addCursor() {
		if (!hasFocus())
			return;

		// Add the cursor.
		content += cursor;
	}

	/** Gets rid of cursor character. */
	protected void removeCursor() {
		if (!hasFocus())
			return;

		// Get rid of the last character!
		content = content.substring(0, Math.max(startidx, content.length() - cursor.length()));
	}

	@Override
	public float contentWidth() {
		return textfont.getAverageWidth(limit + cursor.length());
	}
}
