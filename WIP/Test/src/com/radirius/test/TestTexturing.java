package com.radirius.test;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Loader;
import com.radirius.mercury.scene.Entity;
import com.radirius.mercury.scene.GameScene;

public class TestTexturing extends Core {
	public TestTexturing() {
		super("Test", 1280, 720);
	}
	
	public void init() {
		GameScene.addObject(new Thing(Texture.loadTexture(Loader.streamFromSystem("res/texture.png"), Texture.FILTER_LINEAR), getRunner().getCenterX() - 128, getRunner().getCenterY() - 128));
	}
	
	public void update(float delta) {
		
	}
	
	public void render(Graphics g) {
		g.setBackground(Color.ASPHALT);
	}
	
	public void cleanup() {
		
	}
	
	public static void main(String[] args) {
		new TestTexturing().start();
	}
	
	private class Thing extends Entity {
		private Texture tex;
		
		public Thing(Texture tex, float x, float y) {
			super(x, y, 256, 256);
			
			this.tex = tex;
		}
		
		float temp = 0;
		
		public void render(Graphics g) {
			setRotation(temp++);
			
			g.drawTexture(tex, getBounds(), Color.ASPHALT);
		}
	}
}
