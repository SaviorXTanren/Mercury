package radirius.merc.environment;

import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.Vec2;

/**
 * A base for all Entities that have a function in a MERCury game's environment.
 * 
 * @author wessles, Jeviny
 */

public class Entity implements Updatable, Renderable {
	private float x, y;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(float delta) {}
	public void render(Graphics g)  {}
	
	public Vec2 positionToVec2() {
		return new Vec2(x, y);
	}
}
