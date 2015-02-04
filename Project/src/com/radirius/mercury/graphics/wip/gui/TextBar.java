package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.utilities.Clipboard;

/**
 * A text bar that can be typed into. Clipboards and space-movement are included.
 *
 * @author wessles
 */
public class TextBar extends TextField {
	public TextBar(String message, float maxWidth) {
		super(message);

		focusable = true;
		width = maxWidth;
		singleLine = true;
	}

	public void update() {
		super.update();

		if (!isFocused())
			return;

		if (cursorIndex == -1)
			cursorIndex = text.length() - 1;


		float contentWidth = width == fitText ? Float.MAX_VALUE : getContentWidth();

		// Remove cursor
		char nextCharacter = Input.getNextCharacter();

		if (nextCharacter != 0 /* No character typed */) {
			if (nextCharacter != 8 /* Backspace */) {
				if (font.getWidth(text.toString() + nextCharacter + cursor) <= contentWidth) {
					text.insert(cursorIndex, nextCharacter);
					cursorIndex++;
				}
			} else if (text.length() != 0)
				if (Input.keyDown(Input.KEY_LCONTROL)) {
					int lastSpace = text.substring(0, cursorIndex).lastIndexOf(" ");
					lastSpace = lastSpace == -1 ? 0 : lastSpace;
					text.replace(lastSpace, cursorIndex, "");
					cursorIndex = lastSpace;
				} else if (cursorIndex > 0) {
					text.deleteCharAt(cursorIndex - 1);
					cursorIndex = Math.max(0, cursorIndex - 1);
				}
		}

		if (Input.keyDown(Input.KEY_LCONTROL)) {
			if (Input.keyClicked(Input.KEY_LEFT))
				cursorIndex = text.substring(0, cursorIndex).lastIndexOf(' ');
			else if (Input.keyClicked(Input.KEY_RIGHT)) {
				cursorIndex = text.indexOf(" ", cursorIndex + 1);
				if (cursorIndex < 0)
					cursorIndex = text.length();
			} else if (Input.keyClicked(Input.KEY_V)) {
				String clipData = Clipboard.fetch();
				while (font.getWidth(text.toString() + clipData + cursor) > contentWidth && clipData.length() > 0)
					clipData = clipData.substring(0, clipData.length() - 1);
				text.insert(cursorIndex-1, clipData);
				cursorIndex += clipData.length();
			}
		} else {
			if (Input.keyClicked(Input.KEY_LEFT))
				cursorIndex = cursorIndex - 1;
			else if (Input.keyClicked(Input.KEY_RIGHT))
				cursorIndex = cursorIndex + 1;
		}

		cursorIndex = Math.max(0, Math.min(text.length(), cursorIndex));
	}

	@Override
	public void render(Graphics g) {
		if (isFocused())
			// Add cursor
			text.insert(cursorIndex, cursor);

		super.render(g);

		if (isFocused())
			// Remove cursor
			text.deleteCharAt(cursorIndex);
	}

	/**
	 * The index of the cursor
	 */
	protected int cursorIndex = 0;
	/**
	 * The cursor character.
	 */
	protected char cursor = '|';

	/**
	 * Sets the cursor character.
	 *
	 * @param cursor The new cursor
	 * @return This text bar
	 */
	public TextBar setCursor(char cursor) {
		this.cursor = cursor;
		return this;
	}
}
