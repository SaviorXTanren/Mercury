package radirius.merc.environment;

import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.*;

/**
 * A base for all Entities that have a function in a Mercury game's environment.
 *
 * @author Jeviny
 */

public class Entity implements Updatable, Renderable {
	private float x, y, w, h, rot;
	private boolean alive = true;

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

	public Vec2 getPosition() {
		return new Vec2(x, y);
	}

	public Rectangle getBounds() {
		return (Rectangle) new Rectangle(x, y, w, h).rotate(rot);
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	public boolean isAlive() {
		return alive;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setRotation(float rot) {
		this.rot = rot;
	}
}
