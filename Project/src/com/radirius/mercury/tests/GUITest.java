package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.graphics.gui.Button;
import com.radirius.mercury.graphics.gui.Label;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.scene.GameScene;

class GUITest extends Core {
	private GameScene gameScene;
	
	public GUITest(CoreSetup setup) {
		super(setup);
	}
	
	Font test1, test2;
	
	Button button;
	Label label;
	
	Color backgroundCol = Color.BLACK;

	@Override
	public void init() {
		gameScene = new GameScene();

		test1 = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/tests/test1.ttf"), 48f);
		test2 = TrueTypeFont.loadTrueTypeFont(Loader.getResourceAsStream("com/radirius/mercury/tests/test1.ttf"), 22f);
		
		button = new Button("Click Me!", 72, 72) {
			@Override
			public void act() {
				if (backgroundCol == Color.BLACK)
					backgroundCol = Color.RED;
				else
					backgroundCol = Color.BLACK;
			}

			@Override
			public void noAct() {}
		};
		
		label = new Label("A Mercury Label.", test1, 164, 128);
		
		gameScene.add(button, label);
		gameScene.init();
	}

	@Override
	public void update() {
		gameScene.update();
	}

	@Override
	public void render(Graphics g) {
		g.setBackground(backgroundCol);
		
		gameScene.render(g);
		
		g.setFont(test2);
		g.drawString("*muffled 'ayy lmao' in the distance*", 640, 480);
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
