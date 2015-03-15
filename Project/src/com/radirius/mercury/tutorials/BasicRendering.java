package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author wessles
 */
public class BasicRendering extends Core {
	public BasicRendering(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Basic Rendering");
		coreSetup.width = 800;
		coreSetup.height = 600;

		BasicRendering basicRendering = new BasicRendering(coreSetup);
		basicRendering.start();
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics g) {
		// A 100x100 rectangle at (10, 10)
		Rectangle rectangle = new Rectangle(10, 10, 100, 100);
		// Set the color of the rectangle
		g.setColor(Color.GREEN);
		// Draw the solid green rectangle
		g.drawFigure(rectangle);

		// A 100x100 rectangle at (500, 500)
		Rectangle rectangle2 = new Rectangle(500, 500, 100, 100);
		// Set the color of the rectangle
		g.setColor(Color.BLUE);
		// Trace the green rectangle
		g.traceFigure(rectangle2);
	}

	@Override
	public void cleanup() {
	}
}