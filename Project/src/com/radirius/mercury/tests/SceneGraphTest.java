package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.scene.*;

/**
 * @author wessles
 */
public class SceneGraphTest extends Core {
	GameScene gameScene = new GameScene();

	public SceneGraphTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("Scene Graph Test");

		new SceneGraphTest(setup).start();
	}

	@Override
	public void init() {
		gameScene.add(new TestEntity(64, 64), new TestEntity(226, 150));
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void cleanup() {
	}

	private class TestEntity extends BasicEntity {
		float t = 0;

		public TestEntity(float x, float y) {
			super(x, y, 128, 128);
		}

		@Override
		public void render(Graphics g) {
			g.setColor(Color.WHITE);
			g.drawRectangle((Rectangle) getBounds().rotate(t++));
		}
	}
}
