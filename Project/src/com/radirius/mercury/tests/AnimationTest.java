package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.resource.*;

/**
 * @author wessles
 */
public class AnimationTest extends Core {
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
		Loader.pushLocation(new ClasspathLocation());
		spriteSheet = SpriteSheet.loadSpriteSheet(Texture.loadTexture(Loader.getResourceAsStream("com/radirius/mercury/tests/animationTest.png")), 4);
		Loader.popLocation();
		animation = new Animation(spriteSheet, 20, true);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		g.drawAnimation(animation, 64, 64, 256, 256);
		animation.passFrame();
	}

	@Override
	public void cleanup() {
	}
}
