package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.graphics.wip.gui.*;
import com.radirius.mercury.scene.GameScene;
import com.radirius.mercury.utilities.logging.Logger;

/**
 * @author wessles
 */
public class GUITest extends Core {
	private GameScene gameScene;

	public GUITest(CoreSetup setup) {
		super(setup);
	}

	TextBar title;

	@Override
	public void init() {
		gameScene = new GameScene();

		title = new TextBar("This is a title!", Color.OCEAN_BLUE);

		TextBar textBar0 = new TextBar("This is a little sub-text. See that button below?");
		textBar0.font = ((TrueTypeFont) title.font).deriveFont(15f);

		TextBar textBar1 = new TextBar("More sub-text. And guess what lies below my magnificance?");
		textBar1.font = ((TrueTypeFont) title.font).deriveFont(15f);

		title.addChild(textBar0, textBar1);
		textBar0.addChild(new Button("Press me!") {
			@Override
			public void onMouseClick() {
				getGraphics().setBackground(getGraphics().getBackground() == Color.YELLOW ? Color.RED : Color.YELLOW);
			}

			@Override
			public void onMouseHover() {
				this.backgroundColor = Color.GREEN;
			}

			@Override
			public void onNoMouseHover() {
				this.backgroundColor = Color.CHARCOAL;
			}
		});
		textBar1.addChild(new Button("Press me!") {
			@Override
			public void onMouseClick() {
				getGraphics().setBackground(getGraphics().getBackground() == Color.YELLOW ? Color.RED : Color.YELLOW);
			}

			@Override
			public void onMouseHover() {
				this.backgroundColor = Color.GREEN;
			}

			@Override
			public void onNoMouseHover() {
				this.backgroundColor = Color.CHARCOAL;
			}
		});

		gameScene.init();
	}

	@Override
	public void update() {
		gameScene.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawRectangle(0,0,
				100,100);

		title.update();
		title.render(g, 100, 100);

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
