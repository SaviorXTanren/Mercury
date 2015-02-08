package com.radirius.mercury.graphics.wip.gui;

/**
 * A component with a main text subject.
 *
 * @author wessles
 */
public class TextField extends Component {

	protected StringBuilder text;

	/**
	 * @param message The content text
	 */
	public TextField(String message) {
		this.text = new StringBuilder(message);
		addChild(this.text);
	}

	/**
	 * Returns the text of the field.
	 */
	public StringBuilder getText() {
		return text;
	}

	/**
	 * Sets the text of the field.
	 *
	 * @param text The new text
	 * @return This component
	 */
	public TextField setText(String text) {
		this.text.replace(0, Math.max(0, text.length() - 1), text);
		return this;
	}
}
