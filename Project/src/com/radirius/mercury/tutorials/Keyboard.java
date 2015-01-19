package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author wessles
 */
public class Keyboard extends Core {
	public Keyboard(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Keyboard");
		coreSetup.width = 800;
		coreSetup.height = 600;

		Keyboard keyboard = new Keyboard(coreSetup);
		keyboard.start();
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	// A 100x100 rectangle at (10, 10)
	Rectangle rectangle = new Rectangle(10, 10, 100, 100);

	@Override
	public void render(Graphics g) {
		// If space is down, render.
		if (!Input.keyDown(Input.KEY_SPACE))
			return;

		// If left control is tapped, grow by 100%.
		if (Input.keyClicked(Input.KEY_LCONTROL))
			rectangle.scale(2f);

		g.drawShape(rectangle);
	}

	@Override
	public void cleanup() {
	}
}
