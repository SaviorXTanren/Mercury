package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.*;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.resource.Loader;

import java.io.InputStream;

/**
 * @author wessles
 */
public class UsingTextures extends Core {
	public UsingTextures(CoreSetup coreSetup) {
		super(coreSetup);
	}

	public static void main(String[] args) {
		CoreSetup coreSetup = new CoreSetup("Using Textures");
		coreSetup.width = 800;
		coreSetup.height = 600;

		UsingTextures usingTextures = new UsingTextures(coreSetup);
		usingTextures.start();
	}

	Texture texture;

	@Override
	public void init() {
		// Creates an InputStream for the Texture.
		InputStream stream = Loader.getResourceAsStream("com/radirius/mercury/tutorials/monaLisa.png");

		// Loads Texture from stream.
		texture = Texture.loadTexture(stream);
	}

	@Override
	public void update() {
	}

	// A 100x100 rectangle at (10, 10)
	Rectangle rectangle = new Rectangle(10, 10, 100, 100);

	@Override
	public void render(Graphics g) {
		if (!Input.keyDown(Input.KEY_SPACE))
			return;
		rectangle.translate(4f, 2f);
		rectangle.rotate(0.1f);
		rectangle.scale(1.01f);
		g.drawTexture(texture, rectangle);
	}

	@Override
	public void cleanup() {
	}
}
