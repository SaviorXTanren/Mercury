package com.wessles.MERCury.demo;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.Updatable;
import com.wessles.MERCury.opengl.*;

/**
 * @from MERCtest
 * @author wessles
 * @website www.wessles.com
 */
public abstract class Entity implements Renderable, Updatable {
	public float x, y, w, h;

	public Entity(float x, float y, float w, float h) {
		this(x, y);
		this.w = w;
		this.h = h;
	}

	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		this.w = 0;
		this.h = 0;
	}

	public abstract void update(float delta);
	
	public abstract void render(Graphics g);
}
