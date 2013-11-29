package com.wessles.MERCury.test;

import com.wessles.MERCury.Core;
import com.wessles.MERCury.ResourceManager;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class Test extends Core {
	Runner rnr = Runner.getInstance();

	public Test(String name) {
		super(name);
		rnr.init(this, 100, 200);
		rnr.run();
	}

	@Override
	public void init(ResourceManager RM) {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Graphics g) {
		g.drawRect(0, 0, 10, 0, 10, 10, 0, 10);
	}

	@Override
	public void cleanup(ResourceManager RM) {
	}

	public static void main(String[] args) {
		new Test("Test123");
	}

}
