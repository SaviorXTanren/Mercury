package com.radirius.mercury.graphics.gui;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.scene.GameObject;
import com.radirius.mercury.utilities.misc.Renderable;
import com.radirius.mercury.utilities.misc.Updatable;

public class Component extends GameObject implements Updatable, Renderable {
	public static final int IDLE = 0, HOVERED = 1, CLICKED = 2;
	public int state = 0;

	public Color textColor = Color.WHITE;
	public Rectangle boundaries;

	private ActionCheck actionCheck;

	public Component(Rectangle boundaries) {
		this.boundaries = boundaries;
	}

	public Component(float x, float y, float width, float height) {
		this(new Rectangle(x, y, width, height));
	}

	@Override
	public void update() {
		isHovered(boundaries);
		isClicked(boundaries);
		
		if (actionCheck != null)
			if (actionCheck.isActed())
				actionCheck.act();
			else
				actionCheck.noAct();
	}

	@Override
	public void render(Graphics g) {}

	public boolean isHovered(Rectangle boundaries) {
		if (boundaries.contains(Input.getAbsoluteMousePosition())) {
			state = HOVERED;
			return true;
		} else {
			if (state != CLICKED)
				state = IDLE;

			return false;
		}
	}

	public boolean isClicked(Rectangle boundaries) {
		if (boundaries.contains(Input.getAbsoluteMousePosition()) && Input.mouseClicked(Input.MOUSE_LEFT)) {
			state = CLICKED;
			return true;
		} else {
			if (state != HOVERED)
				state = IDLE;

			return false;
		}
	}

	public Component addActionCheck(ActionCheck actionCheck) {
		this.actionCheck = actionCheck;
		this.actionCheck.setParent(this);
		return this;
	}

	public static abstract class ActionCheck {
		public Component parent;

		public abstract boolean isActed();

		public abstract void act();

		public abstract void noAct();

		public void setParent(Component parent) {
			this.parent = parent;
		}
	}
}
