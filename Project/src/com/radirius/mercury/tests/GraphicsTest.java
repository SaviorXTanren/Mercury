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

		setDebugDisplays(true);
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
	public void update(float delta) {
		f++;
	}

	@Override
	public void render(Graphics g) {
		g.translate((float) Math.random() * 5, (float) Math.random() * 5);

		g.setColor(Color.CRIMSON);
		g.setBackground(Color.ASBESTOS);

		Runner.getInstance().addDebugData("OpenGL", GL11.glGetString(GL11.GL_VERSION));
		Runner.getInstance().addDebugData("Frame", "" + f);

		rect.rotate(1);
		g.drawRectangle(rect);

		g.drawShape(circ);


		Runner.getInstance().getCamera().setOrigin(new Vector2f(100, 100));

		Runner.getInstance().getCamera().rotate(5);
	}

	@Override
	public void cleanup() {
	}

}
