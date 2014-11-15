package com.radirius.mercury.tutorials;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.particles.ParticleSystem;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.Vector2f;

/**
 * @author wessles
 */
public class ParticleTutorial extends Core {

	public ParticleTutorial(String name, int width, int height) {
		super(name, width, height);
	}

	public static void main(String[] args) {
		new ParticleTutorial("Mercury Particle Tutorial", 800, 600).start();
	}

	public ParticleSystem psys;

	@Override
	public void init() {
		ParticleSystem.ParticleSetup psetup = new ParticleSystem.ParticleSetup();

		// The initial size of the object
		psetup.size = 50f;
		// The sides of the shapes
		psetup.sidesOfBounds = 5;
		// The multiplier of growth per frame
		psetup.growth = 0.995f;
		// The rotation of each particle per frame
		psetup.rotation = 1f;

		// The speed at which the particles are first propelled.
		psetup.speed = 0.7f;
		// The acceleration of the object per frame
		psetup.acceleration = 1.005f;
		// The gravity of the object applied per frame
		psetup.gravity = new Vector2f(0, 1f);
		// The minimum and maximum valid angles of entry.
		psetup.angle = 0;

		// The amount of frames from creation until the particle dies.
		psetup.lifeinframes = 480;
		// The color of the particle.
		psetup.color = new Color(30, 50, 80, 70);

		psys = new ParticleSystem(psetup);
	}

	@Override
	public void update(float delta) {
		psys.update(delta);

		psys.getOptions().size = MathUtil.random(30, 65);
		psys.getOptions().color = MathUtil.nextBoolean() ? new Color(30, 50, 80, (int)MathUtil.random(50, 70)) : new Color(255, 255, 255, (int)MathUtil.random(20, 50));
		psys.getOptions().angle = 22.5f + 22.5f * (float) Math.cos(System.currentTimeMillis() / 600d);

		psys.generateParticle(MathUtil.chance(0.02f) ? 1 : 0, new Vector2f(0, 0));
	}

	@Override
	public void render(Graphics g) {
		psys.render(g);
	}

	@Override
	public void cleanup() {

	}
}
