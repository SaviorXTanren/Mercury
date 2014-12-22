package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.gui.Button;

public class GUITest extends Core {
	public GUITest(CoreSetup setup) {
		super(setup);
	}
	
	Button button;
	Color backgroundCol = Color.BLUE;

	@Override
	public void init() {
		button = new Button("Click Me!", 72, 72) {
			@Override
			public void act() {
				if (backgroundCol == Color.BLUE)
					backgroundCol = Color.RED;
				else
					backgroundCol = Color.BLUE;
			}

			@Override
			public void noAct() {
				
			}
		};
	}

	@Override
	public void update() {
		button.update();
	}

	@Override
	public void render(Graphics g) {
		g.setBackground(backgroundCol);
		button.render(g);
	}

	@Override
	public void cleanup() {}
	
	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("GUI Test");
		setup.width = 1280;
		setup.height = 720;
		setup.showDebug = true;
		
		new GUITest(setup).start();
	}
}
