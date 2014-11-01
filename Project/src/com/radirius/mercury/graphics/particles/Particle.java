package com.radirius.mercury.graphics.particles;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.math.geometry.Vector2f;
import com.radirius.mercury.utilities.Wipeable;
import com.radirius.mercury.utilities.misc.Renderable;
import com.radirius.mercury.utilities.misc.Updatable;

/**
 * A class that will represent a single particle, with a
 * parent particle system.
 *
 * @author wessles
 */
public class Particle implements Updatable, Renderable, Wipeable {
	/** Size of the particle. */
	private float size;

	/** Position of the particle. */
	private Vector2f pos;
	/** Bounds of the particle. */
	private Rectangle bounds;
	/** The rotation of the particle. */
	private float rot;
	/** The rotational velocity of the particle. */
	private float rotdirection;
	/** The velocity of the particle. */
	private Vector2f vel;

	/**
	 * The amount of frames that the particle has
	 * experienced.
	 */
	private int life;

	// The parent particle system
	private ParticleSystem emitter;

	public Particle(float x, float y, float angle, ParticleSystem emitter) {
		size = emitter.getOptions().size;

		pos = new Vector2f(x, y);
		vel = new Vector2f(angle);
		rotdirection = MathUtil.nextBoolean() ? 1 : -1;
		vel.scale(emitter.getOptions().speed);

		bounds = new Rectangle(pos.x, pos.y, size);

		life = emitter.getOptions().lifeinframes;

		this.emitter = emitter;
	}

	@Override
	public void update(float delta) {
		if (life < 0)
			wipe();

		pos.add(new Vector2f(vel.x * delta, vel.y * delta));
		vel.add(emitter.getOptions().gravity);
		vel.scale(emitter.getOptions().acceleration);

		size *= emitter.getOptions().growth;
		if (size <= 0)
			wipe();

		bounds = new Rectangle(pos.x - size / 2, pos.y - size / 2, size);
		bounds.rotateTo(rot += emitter.getOptions().rotation * rotdirection);

		life -= 1;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(emitter.getOptions().color.duplicate());
		if (emitter.getOptions().texture == null)
			g.drawShape(bounds);
		else
			g.drawTexture(emitter.getOptions().texture, bounds, g.getColor());
	}

	boolean wiped = false;

	@Override
	public void wipe() {
		wiped = true;
	}

	@Override
	public boolean wiped() {
		return wiped;
	}
}
