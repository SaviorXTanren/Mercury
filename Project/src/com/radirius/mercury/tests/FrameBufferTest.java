package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;
import org.lwjgl.opengl.GL11;

/**
 * @author wessles
 */
public class FrameBufferTest extends Core {
	int f = 0;

	Rectangle rect;

	FrameBuffer frameBuffer;

	public FrameBufferTest(CoreSetup setup) {
		super(setup);
	}

	public static void main(String[] args) {
		CoreSetup setup = new CoreSetup("Framebuffer Test");
        setup.vSync = true;
        setup.showDebug = true;

		new FrameBufferTest(setup).start();
	}

	@Override
	public void init() {
		frameBuffer = FrameBuffer.getFrameBuffer();

		rect = new Rectangle(100, 100, 500, 500);
	}

	@Override
	public void update() {
		f++;
	}

	@Override
	public void render(Graphics g) {
		frameBuffer.bind();

		g.setColor(Color.CRIMSON);
		g.drawRectangle(100, 100, 100, 100);
		g.setColor(Color.CLOUDS);
		g.drawString("This is rendered to a framebuffer!", 0, 0);

		frameBuffer.release();

		g.drawTexture(frameBuffer.getTextureObject(), rect);
		rect.rotate((float) Math.toRadians(1));

		addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		addDebugData("Frame", "" + getFps());
	}

	@Override
	public void cleanup() {
		frameBuffer.cleanup();
	}

}
