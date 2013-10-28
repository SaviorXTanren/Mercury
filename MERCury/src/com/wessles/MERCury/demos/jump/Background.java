package com.wessles.MERCury.demos.jump;

import org.lwjgl.opengl.Display;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.Updatable;
import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.maths.MTrig;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * @from Jump
 * @author wessles
 * @website www.wessles.com
 */
public class Background implements Updatable, Renderable, Textured {
	Texture tex;
	float angle = 0;
	float x = 0;
	float y = 0;
	float rx = 0;
	float ry = 0;
	
	public Background() {
		this.tex = Runner.getResourceManager().getTexture("backdrop_im");
	}
	
	public void update(float delta) {
		x = Display.getWidth() / 2;
		y = Display.getHeight() / 2;
		rx = Display.getWidth() * MTrig.cos(angle);
		ry = Display.getHeight() * MTrig.sin(angle);
		
		angle+= 10;
	}
	
	public void render(Graphics g) {
		g.drawRect(new TexturedRectangle(x - rx, y - ry, x + ry, y - rx, x + rx, y + ry, x - ry, y + rx, Runner.getResourceManager().getTexture("backdrop_im")));
	}
	
	public Texture getTexture() {
		return tex;
	}
}
