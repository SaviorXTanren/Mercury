package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Color;

/**
 * @author wessles
 */
public class TextBar extends PlainText {
	public TextBar(String message) {
		this(message, Color.BLUE);
	}

	public TextBar(String message, Color backgroundColor) {
		super(message);

		setPadding(5f);
		setMargin(5f);

		this.background = BackgroundType.coloredBackground;
		this.backgroundColor = backgroundColor;
	}
}
