package com.radirius.mercury.graphics.wip.gui;

/**
 * @author wessles
 */
public abstract class Button extends TextBar {
	public Button(String message) {
		super(message);
	}

	public abstract void onMouseClick();

	public abstract void onMouseHover();

	public abstract void onNoMouseHover();
}
