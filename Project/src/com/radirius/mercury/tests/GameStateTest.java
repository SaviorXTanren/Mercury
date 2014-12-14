package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.input.Input;

class GameStateTest extends Core {
	GameState gs0 = new GameState() {
		@Override
		public void render(Graphics g) {
			g.setBackground(Color.BLUE);
		}
	};
	GameState gs1 = new GameState() {
		@Override
		public void render(Graphics g) {
			g.setBackground(Color.RED);
		}
	};

	public GameStateTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("GameState Test");

		new GameStateTest(setup).start();
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		if (Input.keyClicked(Input.KEY_RETURN)) {
			if (getCurrentState() == gs0)
				switchGameState(gs1);
			else
				switchGameState(gs0);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Mash Enter.", Window.getCenterX() - TrueTypeFont.ROBOTO_REGULAR.getWidth("Mash Enter.") / 2, 128);

	}

	@Override
	public void cleanup() {

	}
}
