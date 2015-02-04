package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.wip.gui.*;
import com.radirius.mercury.scene.GameScene;

/**
 * @author wessles
 */
public class GUITest extends Core {
	private GameScene gameScene;

	public GUITest(CoreSetup setup) {
		super(setup);
	}

	Panel panel;

	@Override
	public void init() {
		gameScene = new GameScene();

		panel = new Panel();
		panel.x = 100;
		panel.y = 100;

		TextField title = new TextField("Mercury GUI");
		title.componentType = Component.ComponentType.div;

		final TextBar firstName = new TextBar("", 200);
		final TextBar lastName = new TextBar("", 200);
		final Checkbox reportBugsCheckbox = new Checkbox("Report Bugs");

		Button button = new Button("Submit Configuration") {
			@Override
			public void onMouseClick() {
				super.onMouseClick();

				reportBugsCheckbox.unTick();
				firstName.getText().delete(0, firstName.getText().length());
				lastName.getText().delete(0, lastName.getText().length());
			}
		};

		panel.addChild(title, "Sign up for the beta today!\n", "\nFirst name ", firstName, "\nLast name ", lastName, "\n", reportBugsCheckbox, "\n", button);

		gameScene.init();
	}

	@Override
	public void update() {
		gameScene.update();
		panel.update();
	}

	@Override
	public void render(Graphics g) {
		panel.render(g);

		gameScene.render(g);
	}

	@Override
	public void cleanup() {
		gameScene.cleanup();
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("GUI Test");
		setup.width = 1280;
		setup.height = 720;

		new GUITest(setup).start();
	}
}
