package com.wessles.MERCury.demo;

import com.wessles.MERCury.Runner;
import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.opengl.Animated;
import com.wessles.MERCury.opengl.Animation;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Comet extends Entity implements Animated {
	Animation anim;
	Vector2f movement;
	boolean exploding = false;
	float rad = 1;
	boolean dead = false;

	public Comet(float x, float y, Vector2f movement) {
		super(x, y);
		this.anim = Runner.getResourceManager().getAnimation("comet_anim");
		this.movement = movement;
	}

	public void update(float delta) {
		x += movement.x*delta;
		y += movement.y*delta;
		
		if(!exploding)
			rad+= movement.y;
		
		if(y >= 100 && !exploding) {
			movement = Vector2f.get(0, 0);
			anim = Runner.getResourceManager().getAnimation("comet_exp_anim");
			exploding = true;
			anim.setFrame(0);
		}
		
		if(exploding)
			if(anim.isLastFrame())
				dead = true;
	}

	public void render(Graphics g) {
		anim.render(x, y, rad, rad, g);
	}

	public Animation getAnimation() {
		return anim;
	}
}
