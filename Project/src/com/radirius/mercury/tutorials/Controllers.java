package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.*;

/**
 * @author wessles
 */
public class Controllers extends Core {
	public Controllers(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Controllers");
		coreSetup.width = 800;
		coreSetup.height = 600;

		Controllers controllers = new Controllers(coreSetup);
		controllers.start();
	}

	public void init() {
		Input.initControllers();
	}

	Rectangle rectangle = new Rectangle(0,0,100,100);

	public void update() {
		// The 0 is referring to the controller of the analog stick.
		int controllerIndex = 0;

		Vector2f leftValue = Input.getLeftAnalogStick(controllerIndex);

		rectangle.translate(leftValue.x, leftValue.y);


		int buttonIndex = 9; // Commonly the start button, will end the game.

		if(Input.controllerButtonClicked(buttonIndex, controllerIndex))
			end();

		if(Input.controllerButtonDown(0, controllerIndex))
			getGraphics().setBackground(Color.BLACK);
		else
			getGraphics().setBackground(Color.WHITE);
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawShape(rectangle);
	}

	public void cleanup() {
	}
}
