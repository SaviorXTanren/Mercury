package com.wessles.MERCury.geom;

import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * A textured version of a rectangle.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class TexturedRectangle extends Rectangle implements Textured {
	private Texture texture;

	public TexturedRectangle(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Texture texture) {
		super(x1, y1, x2, y2, x3, y3, x4, y4);
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}
}
