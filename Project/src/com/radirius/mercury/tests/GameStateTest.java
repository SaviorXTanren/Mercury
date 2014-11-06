package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.GameState;
import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.input.Input;

class GameStateTest extends Core {
	public GameStateTest() {
		super("Game State Test", 800, 600);
	}
	
	GameState gs0 = new GameState() {
		public void render(Graphics g) {
			g.setBackground(Color.BLUE);
		}
	};
	
	GameState gs1 = new GameState() {
		public void render(Graphics g) {
			g.setBackground(Color.RED);
		}
	};
	
	public void init() {}
	
	public void update(float delta) {
		if (Runner.getInstance().getInput().keyClicked(Input.KEY_RETURN)) {
			if (getCurrentState() == gs0)
				switchGameState(gs1);
			else
				switchGameState(gs0);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Mash Enter.", Runner.getInstance().getCenterX() - TrueTypeFont.OPENSANS_REGULAR.getWidth("Mash Enter.") / 2, 128);
		
	}
	
	public void cleanup() {
		
	}
	
	public static void main(String[] args) {
		new GameStateTest().start();
	}
}
