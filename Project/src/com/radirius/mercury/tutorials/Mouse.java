package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author wessles
 */
public class Mouse extends Core {

	public Mouse(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Mouse");
		coreSetup.width = 800;
		coreSetup.height = 600;

		Mouse mouse = new Mouse(coreSetup);
		mouse.start();
	}

	public void init() {
	}

	public void update() {
	}

	Rectangle cursor = new Rectangle(0, 0, 10, 10);

	public void render(Graphics g) {
		// Grows the cursor by 100% every time the left button is clicked.
		if (Input.mouseClicked(Input.MOUSE_LEFT))
			cursor.scale(2f);

		// Moves the cursor rectangle to the mouse's position.
		cursor.translateTo(Input.getAbsoluteMouseX(), Input.getAbsoluteMouseY());

		g.drawShape(cursor);
	}

	public void cleanup() {
	}
}
