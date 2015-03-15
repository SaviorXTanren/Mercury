package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.BitmapFont;
import com.radirius.mercury.math.RandomUtil;
import com.radirius.mercury.resource.*;
import org.lwjgl.opengl.GL11;

/**
 * @author wessles
 */
public class GraphicsTest extends Core {

	public GraphicsTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("Graphics Test");
		setup.vSync = false;
		setup.showDebug = true;

		new GraphicsTest(setup).start();
	}

	@Override
	public void init() {
		Loader.pushLocation(new ClasspathLocation());
		getGraphics().setFont(BitmapFont.loadBitmapFont(Loader.getResourceAsStream("com/radirius/mercury/tests/bitmap.png"), 16, 16));
		Loader.popLocation();
	}

	@Override
	public void update() {
	}

	int msgProgress = 0;
	String fullMsg = "Testing BitMap fonts..\nI think it's working!";

	@Override
	public void render(Graphics g) {
		addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		addDebugData("Vertices Last Rendered", "" + getBatcher().getVerticesLastRendered());

		g.drawString(fullMsg.substring(0, msgProgress += msgProgress < fullMsg.length() ? (RandomUtil.chance(0.01f) ? 1 : 0) : 0), 100, 100);

		getCamera().setOrigin(Window.getCenter());
		getCamera().rotate(0.01f);
	}

	@Override
	public void cleanup() {
	}
}
