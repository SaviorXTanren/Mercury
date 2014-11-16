package com.radirius.mercury.graphics.particles;

import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.Texture;
import com.radirius.mercury.math.geometry.Polygon;
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
    /**
     * The texture of each particle.
     */
    public Texture texture;
    /**
     * The value by which the size of the particles will
     * be multiplied each frame.
     */
    public float growth;
    /**
     * The amount that each particle will rotate each
     * frame.
     */
    public float rotation;
    boolean wiped = false;
    /**
     * Bounds of the particle.
     */
    private Polygon bounds;
    /**
     * The color of the particle.
     */
    private Color color;
    /**
     * The velocity of the particle.
     */
    private Vector2f vel;
    /**
     * The amount of frames that the particle has
     * experienced.
     */
    private int life;
    /**
     * The parent ParticleSystem.
     */
    private ParticleSystem emitter;

    public Particle(float x, float y, float angle, ParticleSystem emitter) {
        this.emitter = emitter;
        texture = emitter.getOptions().texture;
        vel = new Vector2f(angle);
        vel.scale(emitter.getOptions().speed);
        float size = emitter.getOptions().size;
        if (emitter.getOptions().sidesOfBounds == 4)
            bounds = new Rectangle(x - size / 2, y - size / 2, size);
        else
            bounds = new Polygon(x, y, size / 2, emitter.getOptions().sidesOfBounds);
        color = emitter.getOptions().color.duplicate();
        growth = emitter.getOptions().growth;
        rotation = emitter.getOptions().rotation;
        life = emitter.getOptions().lifeinframes;
    }

    @Override
    public void update(float delta) {
        if (life < 0)
            wipe();
        vel.scale(emitter.getOptions().acceleration);
        bounds.translate(vel.x, vel.y);
        bounds.translate(emitter.getOptions().gravity.x, emitter.getOptions().gravity.y);
        bounds.scale(growth);
        if (bounds.getScale() <= 0)
            wipe();
        bounds.rotate(rotation);
        life -= 1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        if (texture == null)
            g.drawShape(bounds);
        else
            g.drawTexture(texture, bounds, g.getColor());
    }

    @Override
    public void wipe() {
        wiped = true;
    }

    @Override
    public boolean wiped() {
        return wiped;
    }
}
