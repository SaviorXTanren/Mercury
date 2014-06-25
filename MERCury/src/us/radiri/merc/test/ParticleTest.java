package us.radiri.merc.test;

import org.lwjgl.input.Keyboard;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.geom.Vec2;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.input.Input;
import us.radiri.merc.particles.ParticleEmitter;
import us.radiri.merc.particles.ParticleEmitter.ParticleSetup;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class ParticleTest extends Core {
    Runner rnr = Runner.getInstance();
    
    ParticleEmitter emitter1;
    
    public ParticleTest() {
        super("Particle Test");
        rnr.init(this, 1500, 800, true);
        rnr.setMouseGrab(true);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new ParticleTest();
    }
    
    Vec2 torchpos = new Vec2();
    Texture parent;
    
    @Override
    public void init() {
        parent = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
        
        rnr.getGraphics().scale(4);
        
        ParticleSetup emitter1setup = new ParticleSetup();
        emitter1setup.validangle = new Vec2(225, 335);
        emitter1setup.gravity = new Vec2(0, 0.02f);
        emitter1setup.speed = 0.3f;
        emitter1setup.size = 16;
        emitter1setup.rotation = 10;
        emitter1setup.growth = 0.98f;
        emitter1setup.lifeinframes = 300;
        Color col = Color.magenta;
        emitter1setup.color = col;
        emitter1setup.texture = parent;
        
        emitter1 = new ParticleEmitter(new Rectangle(0, 0, parent.getWidth(), parent.getHeight()), emitter1setup);
    }
    
    int rate = 1;
    
    @Override
    public void update(float delta) {
        Input in = rnr.getInput();
        
        if (in.keyDown(Keyboard.KEY_ESCAPE)) {
            rnr.setMouseGrab(false);
            rnr.end();
        }
        
        emitter1.getEmitterBounds().translate(in.getAbsoluteMouseX() / 4 - emitter1.getEmitterBounds().getX(), in.getAbsoluteMouseY() / 4 - emitter1.getEmitterBounds().getY());
        torchpos.set(emitter1.getEmitterBounds().getCenter().x - parent.getWidth() / 2, emitter1.getEmitterBounds().getY());
        
        emitter1.update(delta);
        
        emitter1.generateParticle(rate);
        rate += (in.mouseWheelUp() ? 1 : (in.mouseWheelDown() ? -1 : 0));
    }
    
    @Override
    public void render(Graphics g) {
        emitter1.render(g);
        g.drawTexture(parent, torchpos.x, torchpos.y);
        rnr.addDebugData("Particles Per Tick: ", rate+"");
    }
    
    @Override
    public void cleanup() {
        
    }
}
