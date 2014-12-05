package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import org.lwjgl.opengl.GL11;

public class GraphicsTest extends Core {

	public GraphicsTest() {
		super("Graphics Test", 800, 600);

		/* You can now just set all the extra variables here (including but not limited to the ones you see here) */

		// Disable vsync
		vsync = false;
		// Log progress into program
		showExtraDebug = true;
	}

	public static void main(String[] args) {
		new GraphicsTest().start();
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		/* Notice that functions that previously were in the Runner can now be accessed directly in the Core. */

		addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		addDebugData("Vertices Last Rendered", "" + getBatcher().getVerticesLastRendered());

		getCamera().setOrigin(Window.getCenter());
		getCamera().rotate(1f);
	}

	@Override
	public void cleanup() {
	}
}
