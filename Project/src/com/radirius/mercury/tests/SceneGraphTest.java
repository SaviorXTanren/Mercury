package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.scene.*;

class SceneGraphTest extends Core {
	public SceneGraphTest() {
		super("Scene Graph Test", 800, 600);
	}

	public static void main(String[] args) {
		new SceneGraphTest().start();
	}

	public void init() {
		GameScene.addObject(new TestEntity(64, 64), new TestEntity(226, 150));
	}

	public void update(float delta) {
	}

	public void render(Graphics g) {
	}

	public void cleanup() {
	}

	private class TestEntity extends BasicEntity {
		float t = 0;

		public TestEntity(float x, float y) {
			super(x, y, 128, 128);
		}

		public void render(Graphics g) {
			g.setColor(Color.WHITE);
			g.drawRectangle((Rectangle) getBounds().rotate(t++));
		}
	}
}
