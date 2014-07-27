package radirius.merc.tests;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.geometry.Vec2;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.gui.CheckBox;
import radirius.merc.gui.Component;
import radirius.merc.gui.TextBar;
import radirius.merc.gui.TextBox;
import radirius.merc.gui.TextField;
import radirius.merc.gui.Window;
import radirius.merc.input.Input;
import radirius.merc.particles.ParticleEmitter;
import radirius.merc.particles.ParticleEmitter.ParticleSetup;
import radirius.merc.resource.Loader;

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
    
    CheckBox texture_check, particle_check, particle_texture_check;
    TextField particle_rotation_field, dAWWWW_rotation_field;
    Window window;
    
    Texture tex;
    
    Rectangle bounds;
    
    ParticleEmitter emitter;
    
    @Override
    public void init() {
        texture_check = new CheckBox(" dAWWWW Texturing", 0, 0, Color.black);
        
        particle_check = new CheckBox(" dAWWWW Particles (Must drag for effect)", 0, 0, Color.black);
        particle_texture_check = new CheckBox(" Particle Texturing ", 0, 0, Color.black);
        particle_rotation_field = new TextField("Particle Rotation:  ", 0, 0, TextField.INPUT_INTEGER, 5);
        dAWWWW_rotation_field = new TextField("dAWWWW Rotation:  ", 0, 0, TextField.INPUT_INTEGER, 5);
        
        window = new Window("dAWWWW Settings!", Color.black, new Rectangle(30, 60, 500, 400));
        // Add things to the panel!
        window.addChild(new TextBox("Welcome to MERCury! Mess with dAWWWW over to the right!", new Rectangle(0, 0, 450, 100), 10, Color.black));
        window.addChild(new TextBar("General", 0, 0), texture_check, dAWWWW_rotation_field);
        window.addNewLine();
        window.addChild(new TextBar("Particles", 0, 0), particle_check, particle_texture_check, particle_rotation_field);
        window.sortChildren();
        
        tex = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x64.png"));
        
        Vec2 screen_center = Runner.getInstance().getCamera().getBounds().getCenter();
        
        bounds = new Rectangle(screen_center.x, screen_center.y, tex.getWidth());
        
        ParticleSetup setp = new ParticleSetup();
        setp.acceleration = 1.01f;
        setp.speed = 0.2f;
        setp.size = 32;
        setp.lifeinframes = 200;
        setp.growth = 0.94f;
        emitter = new ParticleEmitter(new Rectangle(screen_center.x - 50, screen_center.y, 100), setp);
    }
    
    boolean dragging = false;
    Vec2 mouseanchor;
    
    float dAWWWW_rot = 0;
    
    @Override
    public void update(float delta) {
        Runner.getInstance().getGraphics().setScale(1);
        window.update(delta);
        
        Input in = Runner.getInstance().getInput();
        
        Runner.getInstance().getGraphics().setScale(zoom);
        
        float dx = 0, dy = 0;
        
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
        
        if (dAWWWW_rotation_field.wasEntered())
            dAWWWW_rot = Integer.valueOf(dAWWWW_rotation_field.getInput());
        bounds.rotate(dAWWWW_rot);
        
        emitter.update(delta);
        
        if (particle_check.isTicked()) {
            if (dragging)
                emitter.generateParticle(5 * Math.abs((int) ((dx + dy) / 2)));
        } else
            particle_texture_check.setTicked(false);
        
        emitter.getOptions().texture = particle_texture_check.isTicked() ? tex : null;
        if (particle_rotation_field.wasEntered())
            emitter.getOptions().rotation = Integer.valueOf(particle_rotation_field.getInput());
        
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
