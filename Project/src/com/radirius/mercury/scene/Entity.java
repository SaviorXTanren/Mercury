package com.radirius.mercury.scene;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.*;

/**
 * A base for all Entities that have a function in a Mercury game's environment.
 *
 * @author Jeviny
 */
public class Entity extends GameObject implements Updatable, Renderable {
	private float x, y, w, h, rotation;

	public Entity(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Graphics g) {
	}

	public Vector2f getPosition() {
		return new Vector2f(x, y);
	}

	public Rectangle getBounds() {
		return (Rectangle) new Rectangle(x, y, w, h).rotate(rotation);
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
