package com.wessles.MERCury.demo;

import com.wessles.MERCury.Runner;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class Background extends Entity implements Textured {
	Texture tex;

	public Background(float x, float y) {
		super(x, y);
		tex = Runner.getResourceManager().getTexture("scrolling-bg");
	}

	public void update(float delta) {
		
	}

	public void render(Graphics g) {
		g.drawTexture(tex, x, y);
	}
	
	public Texture getTexture() {
		return null;
	}

	public static void allStep() {
		for(Entity ent : World.entities) {
			Background bg = null;
			if(ent instanceof Background) {
				bg = (Background) ent;
				if(bg.x > -598)
					bg.x -= 2;
				else
					bg.x = 592;
			}
		}
	}
}
