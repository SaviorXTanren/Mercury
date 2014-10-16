package com.radirius.mercury.graphics.particles;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.Vector2f;
import com.radirius.mercury.utilities.Renderable;
import com.radirius.mercury.utilities.Updatable;
import com.radirius.mercury.utilities.WipingArrayList;

/**
 * An object that simulates a mass of particles.
 *
 * @author wessles
 */
public class ParticleSystem implements Updatable, Renderable {
	/**
	 * A setup for particle systems.
	 */
	public static class ParticleSetup {
		/** The 2 valid angles in between which any particle can go through. */
		public Vector2f validangle = new Vector2f(0, 360);

		/** The color of the particles. */
		public Color color = Color.DEFAULT_TEXTURE_COLOR;
		/** The texture of each particle. */
		public Texture texture;
		/** The size of the particles. */
		public float size = 1;
		/**
		 * The value by which the size of the particles will be multiplied each
		 * frame.
		 */
		public float growth = 1;
		/** The amount that each particle will rotate each frame. */
		public float rotation = 0;

		/** The speed by which the particle will be launched out of the emitter. */
		public float speed = 0.01f;
		/**
		 * The value by which the velocity of a particle will be multiplied each
		 * frame.
		 */
		public float acceleration = 0.98f;
		/** The value that adds to the x and y of each particle each frame. */
		public Vector2f gravity = new Vector2f(0, 0);
		/**
		 * The amount of frames that will pass a single particle before
		 * death/removal.
		 */
		public int lifeinframes = 1000;
	}

	private WipingArrayList<Particle> parts;
	private ParticleSetup pesetup;

	/**
	 * @param pesetup
	 *            The particle's setup.
	 */
	public ParticleSystem(ParticleSetup pesetup) {
		parts = new WipingArrayList<Particle>();
		this.pesetup = pesetup;
	}

	/**
	 * Generates new particles launching at an angle.
	 *
	 * @param amount
	 *            The amount of particles to generate.
	 * @param point
	 *            The point from which the particles emmit.
	 * @param angle
	 *            The angle at which the particles emmmit.
	 */
	public void generateParticle(int amount, Vector2f point, float angle) {
		parts.add(new Particle(point.x, point.y, angle, this));
	}

	/**
	 * Generates new particles launching at random angles.
	 *
	 * @param amount
	 *            The amount of particles to generate.
	 * @param point
	 *            The point from which the particles emmit.
	 */
	public void generateParticle(int amount, Vector2f point) {
		for (int p = 0; p < amount; p++) {
			float angle = (float) MathUtil.random(pesetup.validangle.x, pesetup.validangle.y);
			generateParticle(1, point, angle);
		}
	}

	@Override
	public void update(float delta) {
		for (Particle part : parts)
			part.update(delta);

		parts.sweep();
	}

	@Override
	public void render(Graphics g) {
		for (Object part : parts)
			((Particle) part).render(g);
	}

	/**
	 * @return The ParticleSetup options.
	 */
	public ParticleSetup getOptions() {
		return pesetup;
	}
}
