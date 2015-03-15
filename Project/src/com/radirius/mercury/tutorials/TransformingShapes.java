package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author wessles
 */
public class TransformingShapes extends Core {
	public TransformingShapes(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Transforming Shapes");
		coreSetup.width = 800;
		coreSetup.height = 600;

		TransformingShapes transformingShapes = new TransformingShapes(coreSetup);
		transformingShapes.start();
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
	}

	// A 100x100 rectangle at (10, 10)
	Rectangle rectangle = new Rectangle(10, 10, 100, 100);

	@Override
	public void render(Graphics g) {
		g.traceFigure(rectangle);

		if (!Input.keyDown(Input.KEY_SPACE))
			return;

		rectangle.translate(4f, 2f);
		rectangle.rotate(0.1f);
		rectangle.dilate(1.01f);
	}

	@Override
	public void cleanup() {
	}
}
