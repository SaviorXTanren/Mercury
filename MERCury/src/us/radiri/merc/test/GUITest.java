package us.radiri.merc.test;

import java.util.ArrayList;

import us.radiri.merc.font.TrueTypeFont;
import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.geom.Vec2;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.gui.CheckBox;
import us.radiri.merc.gui.Component;
import us.radiri.merc.gui.RadioCheckBox;
import us.radiri.merc.gui.TextBox;
import us.radiri.merc.gui.Window;
import us.radiri.merc.input.Input;
import us.radiri.merc.particles.ParticleEmitter;
import us.radiri.merc.particles.ParticleEmitter.ParticleSetup;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class GUITest extends Core {
    Runner rnr = Runner.getInstance();
    
    public GUITest(String name) {
        super(name);
        rnr.init(this, 1500, 800);
        rnr.run();
    }
    
    CheckBox texture_check, particle_check, particle_texture_check, particle_rotation_check, rotate_left_check,
            no_rotate_check, rotate_right_check;
    Window window;
    
    Texture tex;
    
    Rectangle bounds;
    
    ParticleEmitter emitter;
    
    @Override
    public void init() {
        texture_check = new CheckBox(" dAWWWW Texturing", 0, 0, 16, Color.black);
        
        particle_check = new CheckBox(" dAWWWW Particles (Must drag for effect)", 0, 0, 16, Color.black);
        particle_texture_check = new CheckBox("        Particle Texturing ", 0, 0, 16, false, Color.black,
                TrueTypeFont.OPENSANS_REGULAR);
        particle_rotation_check = new CheckBox("        Particle Rotation ", 0, 0, 16, false, Color.black,
                TrueTypeFont.OPENSANS_REGULAR);
        
        ArrayList<RadioCheckBox> group = new ArrayList<RadioCheckBox>();
        rotate_left_check = new RadioCheckBox(group, " Leftwards Rotation", 0, 0, 16, Color.black);
        rotate_left_check.FLOAT = Component.FLOAT_CENTER;
        no_rotate_check = new RadioCheckBox(group, " No Rotation", 0, 0, 16, Color.black);
        no_rotate_check.setTicked(true);
        no_rotate_check.FLOAT = Component.FLOAT_CENTER;
        rotate_right_check = new RadioCheckBox(group, " Rightwards Rotation", 0, 0, 16, Color.black);
        rotate_right_check.FLOAT = Component.FLOAT_CENTER;
        
        window = new Window("dAWWWW Settings!", Color.black, new Rectangle(30, 60, 500, 400));
        // Add things to the panel!
        window.addChildWithNewLine(new TextBox("Welcome to.. almost MERC-UI! Mess with dAWWWW here!", new Rectangle(0,
                0, 500, 100), 10, Color.black));
        window.addChild(texture_check, particle_check);
        window.addChild(particle_texture_check, particle_rotation_check);
        window.addNewLine();
        window.addChild(rotate_left_check, no_rotate_check, rotate_right_check);
        window.sortChildren();
        
        tex = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
        
        Vec2 screen_center = Runner.getInstance().getCamera().getBounds().getCenter();
        
        bounds = new Rectangle(screen_center.x - 50, screen_center.y, 100);
        
        ParticleSetup setp = new ParticleSetup();
        setp.acceleration = 1.002f;
        setp.speed = 0.2f;
        setp.size = 16;
        setp.lifeinframes = 500;
        setp.growth = 0.98f;
        emitter = new ParticleEmitter(new Rectangle(screen_center.x - 50, screen_center.y, 100), setp);
    }
    
    boolean dragging = false;
    Vec2 mouseanchor;
    
    @Override
    public void update(float delta) {
        Runner.getInstance().getGraphics().setScale(1);
        window.update(delta);
        
        Input in = Runner.getInstance().getInput();
        
        Runner.getInstance().getGraphics().setScale(zoom);
        
        float dx = 0, dy=0;
        
        if (in.mouseDown(0)) {
            if (Component.isHovered(bounds))
                if (!dragging) {
                    dragging = true;
                    mouseanchor = in.getGlobalMousePosition();
                }
            
            if (dragging) {
                dx = in.getGlobalMouseX() - mouseanchor.x;
                dy = in.getGlobalMouseY() - mouseanchor.y;
                
                bounds.translate(dx / zoom, dy / zoom);
                emitter.getEmitterBounds().translate(dx / zoom, dy / zoom);
                
                mouseanchor.add(new Vec2(dx, dy));
            }
            
        } else
            dragging = false;
        
        if (rotate_left_check.isTicked())
            bounds.rotate(-1);
        else if (rotate_right_check.isTicked())
            bounds.rotate(1);
        
        emitter.update(delta);
        
        if (particle_check.isTicked()) {
            if (dragging)
                emitter.generateParticle(5*Math.abs((int) ((dx + dy) / 2)));
        } else {
            particle_texture_check.setTicked(false);
            particle_rotation_check.setTicked(false);
        }
        
        emitter.getOptions().texture = particle_texture_check.isTicked() ? tex : null;
        emitter.getOptions().rotation = particle_rotation_check.isTicked() ? 2 : 0;
        
        if (Runner.getInstance().getInput().mouseWheelDown())
            zoom -= 0.01f;
        else if (Runner.getInstance().getInput().mouseWheelUp())
            zoom += 0.01f;
    }
    
    float zoom = 1;
    
    @Override
    public void render(Graphics g) {
        g.setBackground(Color.green);
        
        g.setScale(zoom);
        emitter.render(g);
        
        if (texture_check.isTicked())
            g.drawTexture(tex, bounds);
        else
            g.drawRect(bounds);
        
        g.flush();
        
        g.setScale(1);
        window.render(g);
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new GUITest("MERCury GUI Thingies!");
    }
}
