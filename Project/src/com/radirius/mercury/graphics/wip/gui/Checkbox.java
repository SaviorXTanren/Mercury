package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.input.Input;

/**
 * @author wessles
 */
public class Checkbox extends Component {
	/**
	 * @param message         The content text
	 * @param tickedInitially Initially ticked checkbox?
	 */
	public Checkbox(String message, boolean tickedInitially) {
		super(message);

		focusable = true;
		componentType = ComponentType.div;

		tickBox = new TickBox(font.getHeight(message));

		addChild(tickBox);

		if (tickedInitially)
			tick();
		else
			unTick();
	}

	/**
	 * @param message The content text
	 */
	public Checkbox(String message) {
		this(message, false);
	}

	protected boolean isTicked = false;

	@Override
	public void update() {
		super.update();
		if (isFocused())
			if (Input.keyClicked(Input.KEY_SPACE))
				onMouseClick();
	}

	@Override
	public void onMouseClick() {
		if (isTicked = !isTicked)
			onTick();
		else
			onUnTick();
	}


	/**
	 * Called when the checkbox is ticked.
	 */
	public void onTick() {
		getTickBox().backgroundColor = Color.BLACK;
	}

	/**
	 * Called when the checkbox is un-ticked.
	 */
	public void onUnTick() {
		getTickBox().backgroundColor = Color.WHITE;
	}

	/**
	 * Ticks the box.
	 */
	public void tick() {
		isTicked = true;
		onTick();
	}

	/**
	 * Un-ticks the box.
	 */
	public void unTick() {
		isTicked = false;
		onUnTick();
	}

	/**
	 * Returns whether the checkbox has been ticked.
	 */
	public boolean isTicked() {
		return isTicked;
	}

	protected TickBox tickBox;

	/**
	 * Returns the tick-box sub-component
	 */
	public Component getTickBox() {
		return tickBox;
	}

	public static class TickBox extends Component {
		public TickBox(float tickHeight) {
			width = height = tickHeight / 2f;
			setMargin(tickHeight / 4f);

			border = BorderType.border;
			borderThickness = 2;
		}
	}
}
