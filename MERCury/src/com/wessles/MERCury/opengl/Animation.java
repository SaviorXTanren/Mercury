package com.wessles.MERCury.opengl;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.utils.TextureFactory;

/**
 * An easy to use animation class. Just render, and watch the moving picture!
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
		g.drawRect(new TexturedRectangle(x, y, x + w, y, x + w, y + h, x, y + h, texs[frame]));

		framemillis = System.currentTimeMillis();
		
		if (framemillis - lastframemillis >= frameratemillis) {
			
			if (frame < texs.length - 1)
				frame++;
			else
				frame = 0;

			lastframemillis = System.currentTimeMillis();
		}
	}
	
	public void render(float x, float y, float w, float h, Graphics g) {
		g.drawRect(new TexturedRectangle(x, y, x + w, y, x + w, y + h, x, y + h, texs[frame]));

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
	
	public int getWidth() {
		return getTextures()[0].getTextureWidth();
	}
	
	public int getHeight() {
		return getTextures()[0].getTextureHeight();
	}
	
	public boolean isLastFrame() {
		return frame+1==texs.length;
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
