package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Animation;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.SpriteSheet;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.resource.Loader;

class AnimationTest extends Core {
	Animation animation;
	SpriteSheet spriteSheet;

	public AnimationTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("Animation Test");
		setup.width = 1280;
		setup.height = 720;
		
		new AnimationTest(setup).start();
	}

	@Override
	public void init() {
		spriteSheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.streamFromClasspath("com/radirius/mercury/tests/animationTest.png")), 64);
		animation = new Animation(spriteSheet, 150);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawTexture(animation.getCurrentFrame(), 64, 64, 256, 256);
		animation.passFrame();
	}

	@Override
	public void cleanup() {
	}
}
