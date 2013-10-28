package com.wessles.MERCury.demos.jump;

import org.lwjgl.opengl.Display;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.Updatable;
import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.opengl.Animated;
import com.wessles.MERCury.opengl.Animation;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * @from Jump
 * @author wessles
 * @website www.wessles.com
 */
public class CenterPoint implements Updatable, Renderable, Textured, Animated{
	Texture tex;
	Animation anm;
	
	boolean caught = false;
	
	public CenterPoint() {
		tex = Runner.getResourceManager().getTexture("cpoint_im");
		anm = Runner.getResourceManager().getAnimation("cpoint_anm");
	}
	
	public void update(float delta) {
		if(anm.isLastFrame()) {
			caught = false;
			anm.setFrame(0);
		}
	}
	

	public void render(Graphics g) {
		float x = Display.getWidth()/2-90, y = 0;
		
		if(caught)
			anm.render(x, y, 180, 180, g);
		else
			g.drawRect(new TexturedRectangle(x, y, x+180, y, x+180, y+180, x, y+180, tex));
	}
	
	public void catchKey() {
		caught = true;
	}
	
	
	public Texture getTexture() {
		return tex;
	}

	public Animation getAnimation() {
		return anm;
	}


}
