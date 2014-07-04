package us.radiri.merc.test;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.gui.CheckBox;
import us.radiri.merc.particles.ParticleEmitter;
import us.radiri.merc.particles.ParticleEmitter.ParticleSetup;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class CheckBoxTest extends Core {
    Runner rnr = Runner.getInstance();
    
    public CheckBoxTest(String name) {
        super(name);
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    CheckBox texture_check, rotate_check, particle_check, particle_texture_check, particle_rotation_check;
    
    Texture tex;
    
    Rectangle bounds;
    
    ParticleEmitter emitter;
    
    @Override
    public void init() {
        texture_check = new CheckBox(" Texturing", 20, 100, 16);
        rotate_check = new CheckBox(" Rotation", 20, texture_check.bounds.getY2() + 10, 16);
        particle_check = new CheckBox(" Particles", 20, rotate_check.bounds.getY2() + 10, 16);
        particle_texture_check = new CheckBox(" Texturing Particles", 40, particle_check.bounds.getY2() + 10, 16);
        particle_rotation_check = new CheckBox(" Rotate Particles", 40, particle_texture_check.bounds.getY2() + 10, 16);
        
        tex = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
        
        bounds = new Rectangle(200, 200, 100);
        
        ParticleSetup setp = new ParticleSetup();
        setp.acceleration = 1.001f;
        setp.speed = 0.5f;
        setp.size = 16;
        emitter = new ParticleEmitter(new Rectangle(200, 200, 100), setp);
    }
    
    @Override
    public void update(float delta) {
        texture_check.update(delta);
        rotate_check.update(delta);
        particle_check.update(delta);
        particle_texture_check.update(delta);
        particle_rotation_check.update(delta);
        
        if (rotate_check.isTicked())
            bounds.rotate(1);
        
        emitter.update(delta);
        
        if (particle_check.isTicked())
            emitter.generateParticle(2);
        else {
            particle_texture_check.setTicked(false);
            particle_rotation_check.setTicked(false);
        }
        
        emitter.getOptions().texture = particle_texture_check.isTicked() ? tex : null;
        emitter.getOptions().rotation = particle_rotation_check.isTicked() ? 2 : 0;
    }
    
    @Override
    public void render(Graphics g) {
        texture_check.render(g);
        rotate_check.render(g);
        particle_check.render(g);
        particle_texture_check.render(g);
        particle_rotation_check.render(g);
        
        emitter.render(g);
        
        if (texture_check.isTicked())
            g.drawTexture(tex, bounds);
        else
            g.drawRect(bounds);
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new CheckBoxTest("Checkbox test");
    }
}
