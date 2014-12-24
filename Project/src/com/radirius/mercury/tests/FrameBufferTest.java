package com.radirius.mercury.tests;

import org.lwjgl.opengl.GL11;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.CoreSetup;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.FrameBuffer;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.math.geometry.Rectangle;

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
		frameBuffer.use();
		{
			g.setColor(Color.CRIMSON);
			g.drawRectangle(100, 100, 100, 100);
			g.setColor(Color.CLOUDS);
			g.drawString("This is rendered to a framebuffer!", 0, 0);
		}
		frameBuffer.release();

		g.setBackground(Color.BLACK);

		g.drawTexture(frameBuffer.getTextureObject(), rect);
		rect.rotate(1);

		addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		addDebugData("Frame", "" + f);
	}

	@Override
	public void cleanup() {
		frameBuffer.cleanup();
	}

}
