package com.radirius.mercury.tests;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.*;
import org.lwjgl.opengl.GL11;

public class GraphicsTest extends Core {
	int f = 0;

	Rectangle rect;
	Circle circ;

	public GraphicsTest() {
		super("Graphics Test", 800, 600);
		showExtraDebug = true;
	}

	public static void main(String[] args) {
		new GraphicsTest().start();
	}

	@Override
	public void init() {
		rect = new Rectangle(100, 100, 100, 100);
		circ = new Circle(500, 500, 100);
	}

	@Override
	public void update() {
		f++;
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.CRIMSON);
		g.setBackground(Color.ASBESTOS);

		addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		addDebugData("Frame", "" + f);

		rect.rotate(1);
		g.drawRectangle(rect);

		g.drawShape(circ);

		getCamera().setOrigin(Window.getCenter());
		getCamera().rotate(0.1f);
	}

	@Override
	public void cleanup() {
	}

}
