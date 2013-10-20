package com.wessles.MERCury.demo;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.opengl.Display;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		World.render(g);
	}

	public void update(float delta) {
		World.update();
	}
	
	public Graphics initGraphics() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_BLEND);
		glEnable(GL_ALPHA_TEST);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_SCALE);
		glDepthMask(true);
		glDepthFunc(GL_LEQUAL);
		
		glAlphaFunc(GL_GREATER, 0.1f);
		
		Graphics g = new VAOGraphics();
		g.scale(2);
		return g;
	}

	@Override
	public void cleanup(ResourceManager RM) {

	}

	public static void main(String[] args) {
		new Demo();
	}
}
