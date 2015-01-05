package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.wip.gui.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.scene.GameScene;

/**
 * @author wessles
 */
public class GUITest extends Core {
	private GameScene gameScene;

	public GUITest(CoreSetup setup) {
		super(setup);
	}

	TextBar textBar;
	Button button;

	@Override
	public void init() {
		gameScene = new GameScene();

		textBar = new TextBar("");

		TextBar title = new TextBar("This is a title!", Color.RED);

		button = new Button("Press me!") {
			@Override
			public void onMouseClick() {
				getGraphics().setBackground(getGraphics().getBackground() == Color.YELLOW ? Color.RED : Color.YELLOW);
			}

			@Override
			public void onMouseHover() {
				button.backgroundColor = Color.GREEN;
			}

			@Override
			public void onNoMouseHover() {
				button.backgroundColor = Color.CHARCOAL;
			}
		};

		textBar.addChild(title, button);

		gameScene.init();
	}

	@Override
	public void update() {
		gameScene.update();
	}

	@Override
	public void render(Graphics g) {
		textBar.update();
		textBar.render(g, 100, 100);

		char nextChar = Input.getNextCharacter();
		if (nextChar != 0)
			textBar.message += nextChar;

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
		setup.showDebug = true;

		new GUITest(setup).start();
	}
}
