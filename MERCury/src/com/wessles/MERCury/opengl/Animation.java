package com.wessles.MERCury.opengl;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.maths.MTrig;
import com.wessles.MERCury.utils.ArrayUtils;
import com.wessles.MERCury.utils.TextureFactory;

/**
 * An easy to use animation class. Just render, and watch the moving picture.
 * 
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class Animation {
	private Texture[] texs;
	private int frame = 0, frameratemillis;
	private long framemillis = 0, lastframemillis;
	private float w, h;

	public Animation(int frameratemillis, Texture... texs) {
		if (texs.length == 0)
			throw new IllegalArgumentException("Must be at least 1 texture!");

		this.frameratemillis = frameratemillis;
		this.texs = texs;
		this.w = 0;
		this.h = 0;

		for (Texture tex : this.texs) {
			if (tex.getTextureWidth() > this.w)
				this.w = tex.getTextureWidth();
			if (tex.getTextureHeight() > this.h)
				this.h = tex.getTextureHeight();
		}

		this.frame = 0;
	}

	public Animation(float w, float h, int frameratemillis, Texture... texs) {
		if (texs.length == 0)
			throw new IllegalArgumentException("Must be at least 1 texture!");

		this.frameratemillis = frameratemillis;
		this.texs = texs;
		this.w = w;
		this.h = h;

		this.frame = 0;
	}

	public void reverse() {
		for (int f = 0; f < texs.length; f++) {
			texs[texs.length - 1 - f] = texs[f];
		}
		frame = texs.length - 1 - frame;
	}

	public void render(float x, float y, Graphics g) {
		render(x, y, getAnimationWidth(), getAnimationHeight(), g);
	}

	public void render(float x, float y, float w, float h, Graphics g) {
		render(x, y, x + w, y, x + w, y + h, x, y + h, g);
	}

	public void render(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Graphics g) {
		g.drawRect(new TexturedRectangle(x1, y1, x2, y2, x3, y3, x4, y4, texs[frame]));

		framemillis = System.currentTimeMillis();

		if (framemillis - lastframemillis >= frameratemillis) {

			if (frame < texs.length - 1)
				frame++;
			else
				frame = 0;

			lastframemillis = System.currentTimeMillis();
		}
	}

	public void render(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float origx, float origy, float rot, Graphics g) {
		Vector2f[] vs = ArrayUtils.getVector2fs(new float[] { x1, y1, x2, y2, x3, y3, x4, y4 });
		
		for(Vector2f p : vs) {
			float s = MTrig.sin(rot);
			float c = MTrig.cos(rot);

			p.x -= origx;
			p.y -= origy;

			float xnew = p.x * c - p.y * s;
			float ynew = p.x * s + p.y * c;

			p.x = xnew + origx;
			p.y = ynew + origy;
		}

		g.drawRect(new TexturedRectangle(vs[0].x, vs[0].y, vs[1].x, vs[1].y, vs[2].x, vs[2].y, vs[3].x, vs[3].y, texs[frame]));

		framemillis = System.currentTimeMillis();

		if (framemillis - lastframemillis >= frameratemillis) {

			if (frame < texs.length - 1)
				frame++;
			else
				frame = 0;

			lastframemillis = System.currentTimeMillis();
		}
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public int getFrame() {
		return frame;
	}

	public int getLength() {
		return texs.length;
	}

	public int getAnimationWidth() {
		return getTextures()[0].getTextureWidth();
	}

	public int getAnimationHeight() {
		return getTextures()[0].getTextureHeight();
	}

	public boolean isLastFrame() {
		return frame + 1 == texs.length;
	}

	public Texture[] getTextures() {
		return texs;
	}

	public static Animation loadAnimationFromStrip(String location, int divwidth, int frameratemillis) throws FileNotFoundException, IOException {
		return new Animation(frameratemillis, TextureFactory.getTextureStrip(location, divwidth));
	}

	public static Animation loadAnimationFromGrid(String location, int divwidth, int divheight, int frameratemillis) throws FileNotFoundException, IOException {
		Texture[][] texs_g = TextureFactory.getTextureGrid(location, divwidth, divheight);
		Texture[] texs_s = new Texture[texs_g.length * texs_g[0].length];
		int cnt = 0;

		for (int x = 0; x < texs_g.length; x++)
			for (int y = 0; y < texs_g[0].length; y++) {
				texs_s[cnt] = texs_g[x][y];
				cnt++;
			}

		return new Animation(frameratemillis, texs_s);
	}
}
