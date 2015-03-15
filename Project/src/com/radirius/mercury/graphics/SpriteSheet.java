package com.radirius.mercury.graphics;

import com.radirius.mercury.resource.Resource;

/**
 * A class for sprite sheets.
 *
 * @author wessles
 * @author opiop65
 * @author Jeviny
 */
public class SpriteSheet implements Resource {
	private Texture baseTexture;
	private SubTexture[] subTextures;

	private SpriteSheet(Texture baseTexture, SubTexture... subTextures) {
		this.baseTexture = baseTexture;
		this.subTextures = subTextures;
	}

	/**
	 * Slices the Texture baseTexture up, cutting horizontally and vertically every divSize length.
	 */
	public static SpriteSheet loadSpriteSheet(Texture baseTexture, int divSize) {
		return loadSpriteSheet(baseTexture, divSize, divSize);
	}

	/**
	 * Slices the Texture baseTexture up, cutting vertically every divWidth length, and cutting horizontally every
	 * divHeight length. The sub-textures are counted reading left to right.
	 */
	public static SpriteSheet loadSpriteSheet(Texture baseTexture, int divWidth, int divHeight) {
		SubTexture texture = (SubTexture) baseTexture;

		if (texture.getWidth() % divWidth != 0)
			throw new ArithmeticException("The width of the Texture must be divisible by the division width!");

		int xCut = texture.getWidth() / divWidth;
		int yCut = texture.getHeight() / divHeight;

		SubTexture[] subTextures = new SubTexture[xCut * yCut];

		for (int y = 0; y < yCut; y++)
			for (int x = 0; x < xCut; x++)
				subTextures[x + y * xCut] = new SubTexture(baseTexture, x * divWidth, y * divHeight, (x + 1) * divWidth, (y + 1) * divHeight);

		return new SpriteSheet(texture, subTextures);
	}

	/**
	 * @return a sprite-sheet based off of Texture baseTexture, with SubTextures subTextures.
	 */
	public static SpriteSheet loadSpriteSheet(Texture baseTexture, SubTexture... subTextures) {
		return new SpriteSheet(baseTexture, subTextures);
	}

	/**
	 * @return the number of sub textures.
	 */
	public int getNumberOfSubTextures() {
		return subTextures.length;
	}

	/**
	 * @return the texture corresponding to the texture number.
	 */
	public SubTexture getTexture(int numTextures) {
		return subTextures[numTextures];
	}

	/**
	 * @return the textures.
	 */
	public SubTexture[] getTextures() {
		return subTextures;
	}

	/**
	 * @return the base texture for all SubTextures.
	 */
	public Texture getBaseTexture() {
		return baseTexture;
	}

	@Override
	public void clean() {
	}
}
