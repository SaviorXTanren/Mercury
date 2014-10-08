package radirius.merc.environment;

import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.*;
import radirius.merc.utilities.Renderable;
import radirius.merc.utilities.Updatable;

/**
 * A base for all Entities that have a function in a Mercury game's environment.
 *
 * @author Jeviny
 */

public class Entity implements Updatable, Renderable {
	private float x, y, w, h, rotation;

	public Entity(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void update(float delta) {}
	public void render(Graphics g) {}

	public Vec2 getPosition() {
		return new Vec2(x, y);
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
