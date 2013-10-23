package com.wessles.MERCury.demo;

import java.io.IOException;

import com.wessles.MERCury.*;
import com.wessles.MERCury.opengl.*;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public class Demo extends Core {
	public float x = 0, y = 0;

	public Demo() {
		super(600, 300, false, true);
	}

	public void init(ResourceManager RM) {
		try {
			RM.loadTexture(Texture.loadTexture("res/scrolling_bg.png"), "scrolling-bg");
			RM.loadAnimation(Animation.loadAnimationFromStrip("res/player_strip.png", 5, 200), "player_anim");
			RM.loadAnimation(Animation.loadAnimationFromStrip("res/comet_strip.png", 8, 300), "comet_anim");
			RM.loadAnimation(Animation.loadAnimationFromStrip("res/comet_explosion.png", 8, 300), "comet_exp_anim");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Runner.getGraphicsObject().scale(2);
		Runner.setDeltaFactor(.1f);
	}

	public void render(Graphics g) {
		World.render(g);
	}

	public void update(float delta) {
		World.update(delta);
	}

	@Override
	public void cleanup(ResourceManager RM) {

	}

	public static void main(String[] args) {
		new Demo();
	}
}
