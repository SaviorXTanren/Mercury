package com.wessles.MERCury.demo;

import org.lwjgl.input.Keyboard;

import com.wessles.MERCury.Runner;
import com.wessles.MERCury.opengl.*;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class Player extends Entity {
	Animation anim;

	public Player() {
		super(40, 60, 20, 80);
		this.anim = Runner.getResourceManager().getAnimation("player_anim");
	}

	public void update(float delta) {
		if (Runner.getInput().keyDown(Keyboard.KEY_RIGHT) && x < 300 - w)
			x += 3f*delta;
		else if (Runner.getInput().keyDown(Keyboard.KEY_LEFT) && x > 0)
			x -= 3f*delta;
	}
	
	public void render(Graphics g) {
		anim.render(x, y, w, h, g);
	}
}
