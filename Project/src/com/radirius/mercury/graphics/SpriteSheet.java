package com.radirius.mercury.graphics;

import com.radirius.mercury.resource.Resource;

/**
 * A class for spritesheets.
 *
 * @author wessles, opiop65, Jeviny
 */
public class SpriteSheet implements Resource {
	private Texture baseTexture;
	private SubTexture[] subTextures;

	private SpriteSheet(Texture baseTexture, SubTexture... subTextures) {
		this.baseTexture = baseTexture;
		this.subTextures = subTextures;
	}

	/** @return The number of subtextures. */
	public int getNumberOfSubTextures() {
		return subTextures.length;
	}

	/**
	 * @return The texture corresponding to the texnum.
	 */
	public SubTexture getTexture(int numTextures) {
		return subTextures[numTextures];
	}

	/** @return The base texture for all SubTextures. */
	public Texture getBaseTexture() {
		return baseTexture;
	}

	/**
	 * Slices the Texture tex up, cutting vertically every divWidth length.
	 */
	public static SpriteSheet loadSpriteSheet(Texture tex, int divWidth) {
		return loadSpriteSheet(tex, divWidth, tex.getHeight());
	}

	/**
	 * Slices the Texture tex up, cutting vertically every divwidth length, and
	 * cutting horizontally every divHeight length. The subtextures are counted
	 * reading left to right.
	 */
	public static SpriteSheet loadSpriteSheet(Texture texture, int divWidth, int divHeight) {
		SubTexture texture0 = (SubTexture) texture;

		if (texture0.getWidth() % divWidth != 0)
			throw new ArithmeticException("The width of the Texture must be divisible by the division width!");

		int numx = texture0.getWidth() / divWidth;
		int numy = texture0.getHeight() / divHeight;

		SubTexture[] subtexs = new SubTexture[numx * numy];

		for (int y = 0; y < numy; y++)
			for (int x = 0; x < numx; x++)
				subtexs[x + y * numx] = new SubTexture(texture, x * divWidth, y * divHeight, (x + 1) * divWidth, (y + 1) * divHeight);

		return new SpriteSheet(texture0, subtexs);
	}

	/** @return A spritesheet based off of Texture tex, with SubTextures subtexs. */
	public static SpriteSheet loadSpriteSheet(Texture tex, SubTexture... subtexs) {
		return new SpriteSheet(tex, subtexs);
	}

	@Override
	public void clean() {
	}
}