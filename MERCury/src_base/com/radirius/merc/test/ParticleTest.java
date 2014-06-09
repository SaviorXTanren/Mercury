package com.radirius.merc.test;

import org.lwjgl.input.Keyboard;

import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.geo.Vec2;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.in.Input;
import com.radirius.merc.part.ParticleEmitter;
import com.radirius.merc.res.Loader;
import com.radirius.merc.spl.SplashScreen;

/**
 * @author wessles
 */

public class ParticleTest extends Core {
    Runner rnr = Runner.getInstance();
    
    ParticleEmitter emitter2, emitter1;
    
    public ParticleTest() {
        super("Particle Test");
        rnr.init(this, 500, 300);
        rnr.setMouseGrab(true);
        rnr.run();
    }
    
    public static void main(String[] args) {
        new ParticleTest();
    }
    
    Vec2 torchpos = new Vec2();
    Texture torch;
    
    @Override
    public void init() {
        torch = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/torch.png"));
        
        rnr.getGraphics().scale(4);
        
        emitter1 = new ParticleEmitter(new Rectangle(0, 0, 10, 10), new Vec2(265, 275), new Vec2(0, -0.01f), 1f, Color.red, 2, true, 0.2f, 1f, 100);
        emitter2 = new ParticleEmitter(new Rectangle(0, 0, 10, 10), new Vec2(265, 275), new Vec2(0, -0.05f), 1f, Color.yellow, 4, true, 0.3f, 1f, 50);
        
        rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
    }
    
    @Override
    public void update(float delta) {
        Input in = rnr.getInput();
        
        if (in.keyDown(Keyboard.KEY_ESCAPE))
            rnr.setMouseGrab(false);
        
        emitter1.move(in.getAbsoluteMouseX() / 4 - emitter1.getBounds().getX(), in.getAbsoluteMouseY() / 4 - emitter1.getBounds().getY());
        emitter2.move(in.getAbsoluteMouseX() / 4 - emitter2.getBounds().getX(), in.getAbsoluteMouseY() / 4 - emitter2.getBounds().getY());
        torchpos.set(emitter1.getBounds().getX() + emitter1.getBounds().getWidth() / 2-2, emitter1.getBounds().getY());
        
        emitter1.update(delta);
        emitter2.update(delta);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawTexture(torch, torchpos.x, torchpos.y);
        emitter2.render(g);
        emitter1.render(g);
    }
    
    @Override
    public void cleanup() {
    }
    
}
