package com.wessles.MERCury.demos.jump;

import org.lwjgl.opengl.Display;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.Updatable;
import com.wessles.MERCury.opengl.Animated;
import com.wessles.MERCury.opengl.Animation;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from Jump
 * @author wessles
 * @website www.wessles.com
 */
public class Player implements Updatable, Renderable, Animated {
	Animation anm;
	
	float size = 1f;
	float size_c = 0.1f;
	
	public Player() {
		this.anm = Runner.getResourceManager().getAnimation("skalk_anm");
	}
	
	public void update(float delta) {
		if (size > 1.5f) {
			size_c = -.05f;
			size = 1.4f;
		} else if (size < 1) {
			size_c = .05f;
			size = 1.1f;
		}
		
		size += size_c;
	}
	
	public void render(Graphics g) {
		anm.render(Display.getWidth() / 2 - 45, Display.getHeight() / 2 - 50, 90 * size, 180 * size, g);
	}
	
	public Animation getAnimation() {
		return anm;
	}
}
