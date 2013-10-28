package com.wessles.MERCury.demos.jump;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.Updatable;
import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.maths.Random;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;
import com.wessles.MERCury.opengl.Textured;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Key implements Updatable, Renderable, Textured {
	public static final int UP = 0, RIGHT=1, DOWN=2, LEFT=3;
	
	int dir;
	
	boolean won = false;
	
	Texture tex;
	float x = -100;
	boolean dead = false;

	public Key() {
		tex = Runner.getResourceManager().getTexture("key_" + (dir = (int) Random.getRandom(0, 3)));
	}

	public void update(float delta) {
		x += 4;
		if(x > Display.getWidth())
			dead = true;
		
		if(x > Display.getWidth()/2-4 && x < Display.getWidth()/2+2)
			switch(dir) {
				case UP: {if(Runner.getInput().keyClicked(Keyboard.KEY_UP)) won=true;}
				case RIGHT: {if(Runner.getInput().keyClicked(Keyboard.KEY_RIGHT)) won=true;}
				case DOWN: {if(Runner.getInput().keyClicked(Keyboard.KEY_DOWN)) won=true;}
				case LEFT: {if(Runner.getInput().keyClicked(Keyboard.KEY_LEFT)) won=true;}
			}
	}

	public void render(Graphics g) {
		g.drawRect(new TexturedRectangle(x, 45, x+90, 45, x+90, 135, x, 135, tex));
	}

	public Texture getTexture() {
		return tex;
	}
}
