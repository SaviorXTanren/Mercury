package com.radirius.mercury.graphics;

/**
 * A class for sub-textures. These objects are just like regular textures, but
 * represent a portion of them.
 *
 * @author wessles
 */
public class SubTexture extends Texture {
	private Texture parent;
	private int x, y, x2, y2;

	public SubTexture(Texture parent, int x, int y, int x2, int y2) {
		super(parent.getTextureId(), parent.getWidth(), parent.getHeight(), parent.getSourceImage(), parent.getBuffer());

		this.parent = parent;

		if (parent instanceof SubTexture) {
			SubTexture sb = (SubTexture) parent;
			width = sb.getParentWidth();
			height = sb.getParentHeight();
		}

		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * This is useful for when you want the functionality of a real Texture from
	 * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
	 * <p/>
	 * Returns A Texture version of the SubTexture.
	 */
	public Texture convertToTexture() {
		return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()));
	}

	/**
	 * Returns Returns the parent Texture.
	 */
	public Texture getParent() {
		return parent;
	}

	/**
	 * Returns The x location of the subtexture on the parent texture.
	 */
	public int getSubX() {
		return x;
	}

	/**
	 * Returns The y location of the subtexture on the parent texture.
	 */
	public int getSubY() {
		return y;
	}

	/**
	 * Returns The second x location of the subtexture on the parent texture.
	 */
	public int getSubX2() {
		return x2;
	}

	/**
	 * Returns The second y location of the subtexture on the parent texture.
	 */
	public int getSubY2() {
		return y2;
	}

	/**
	 * Returns The width of the subtexture of the parent texture.
	 */
	@Override
	public int getWidth() {
		return x2 - x;
	}

	/**
	 * Returns The height of the subtexture of the parent texture.
	 */
	@Override
	public int getHeight() {
		return y2 - y;
	}

	/**
	 * Returns The width of the parent texture.
	 */
	public int getParentWidth() {
		return width;
	}

	/**
	 * Returns The width of the parent texture.
	 */
	public int getParentHeight() {
		return height;
	}
}
