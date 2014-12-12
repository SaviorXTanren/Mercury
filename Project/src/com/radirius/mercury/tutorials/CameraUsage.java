package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;

/**
 * @author wessles
 */
public class CameraUsage extends Core {
	public CameraUsage(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Camera Usage");
		coreSetup.width = 800;
		coreSetup.height = 600;

		CameraUsage cameraUsage = new CameraUsage(coreSetup);
		cameraUsage.start();
	}

	public void init() {
		getCamera().setOrigin(Window.getCenter());
	}

	Rectangle rectangle = new Rectangle(100, 200, 100, 100);

	public void update() {
	}

	public void render(Graphics g) {
		Camera camera = g.getCamera();

		if (!Input.keyDown(Input.KEY_LSHIFT)) {
			if (Input.keyDown(Input.KEY_UP))
				camera.translate(0, -4);
			if (Input.keyDown(Input.KEY_DOWN))
				camera.translate(0, 4);
			if (Input.keyDown(Input.KEY_LEFT))
				camera.translate(-4, 0);
			if (Input.keyDown(Input.KEY_RIGHT))
				camera.translate(4, 0);
		} else {
			if (Input.keyDown(Input.KEY_UP))
				camera.scale(0.1f);
			if (Input.keyDown(Input.KEY_DOWN))
				camera.scale(-0.1f);
			if (Input.keyDown(Input.KEY_LEFT))
				camera.rotate(-4);
			if (Input.keyDown(Input.KEY_RIGHT))
				camera.rotate(4);
		}

		g.drawShape(rectangle);
	}

	public void cleanup() {
	}
}
