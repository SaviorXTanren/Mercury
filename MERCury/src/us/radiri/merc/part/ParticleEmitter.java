package us.radiri.merc.part;

import us.radiri.merc.env.AdvancedMercEntity;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.geo.Vec2;
import us.radiri.merc.gfx.Color;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.math.MercMath;
import us.radiri.merc.util.WipingArrayList;

/**
 * An object that will emit particle from a given point in a given direction, at
 * a given speed (run-on!).
 * 
 * @author wessles
 */

public class ParticleEmitter extends AdvancedMercEntity {
    public WipingArrayList<Particle> parts;
    
    public Vec2 validangle;
    public float percentchance;
    
    public Color color;
    public float size;
    public boolean shrink;
    public float speed;
    public float damp;
    public Vec2 grav;
    public int lifeinframes;
    
    /**
     * @param spawnarea
     *            The area in which a particle may spawn.
     * @param validangle
     *            The 2 valid angles in between which any particle can thrust
     *            itself out of spawnarea with.
     * @param grav
     *            The value that adds to the x and y of each particle each
     *            frame.
     * @param percentchance
     *            The percent chance that a particle will spawn.
     * @param color
     *            The color of the particles.
     * @param size
     *            The size of the particles.
     * @param shrink
     *            The value by which the size of the particles will be
     *            multiplied each frame.
     * @param speed
     *            The speed of the particles.
     * @param damp
     *            The value by which the velocity of a particle will be
     *            multiplied each frame.
     * @param lifeinframes
     *            The amount of frames that will pass a single particle before
     *            death/removal.
     */
    public ParticleEmitter(Rectangle spawnarea, Vec2 validangle, Vec2 grav, float percentchance, Color color, float size, boolean shrink, float speed, float damp, int lifeinframes) {
        super(spawnarea.getX(), spawnarea.getY(), spawnarea.getWidth(), spawnarea.getHeight());
        
        parts = new WipingArrayList<Particle>();
        
        this.validangle = validangle;
        this.percentchance = percentchance;
        
        this.color = color;
        this.size = size;
        this.shrink = shrink;
        
        this.speed = speed;
        this.damp = damp;
        this.grav = grav;
        this.lifeinframes = lifeinframes;
    }
    
    @Override
    public void update(float delta) {
        if (MercMath.chance(percentchance)) {
            float angle = (float) MercMath.random(validangle.x, validangle.y);
            parts.add(new Particle(angle, this));
        }
        
        for (Particle part : parts)
            part.update(delta);
        
        parts.sweep();
    }
    
    @Override
    public void render(Graphics g) {
        for (Object part : parts)
            ((Particle) part).render(g);
    }
    
    @Override
    public boolean isValidPosition(float x, float y) {
        return true;
    }
}
