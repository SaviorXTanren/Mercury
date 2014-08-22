package radirius.merc.test;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.graphics.font.TrueTypeFont;
import radirius.merc.graphics.gui.CheckBox;
import radirius.merc.graphics.gui.Component;
import radirius.merc.graphics.gui.TextBar;
import radirius.merc.graphics.gui.TextBox;
import radirius.merc.graphics.gui.TextField;
import radirius.merc.graphics.gui.Window;
import radirius.merc.graphics.particles.ParticleSystem;
import radirius.merc.graphics.particles.ParticleSystem.ParticleSetup;
import radirius.merc.input.Input;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Vec2;
import radirius.merc.resource.Loader;

/**
 * @author wessles, Jeviny
 */

public class TestGUI extends Core {
    Runner rnr = Runner.getInstance();
    
    public TestGUI(String name) {
        super(name);
        rnr.init(this, 1024, 640);
        rnr.run();
    }
    
    CheckBox texture_check, particle_check, particle_texture_check;
    TextField particle_rotation_field, dAWWWW_rotation_field;
    Window window;
    
    Texture tex, bg;
    
    Rectangle bounds;
    
    ParticleSystem emitter;
    
    TrueTypeFont fnt0;
    
    @Override
    public void init() {
        texture_check = new CheckBox(" Item Texturing", 0, 0, Color.white);
        
        particle_check = new CheckBox(" Item Particles (Must Drag For Effect)", 0, 0, Color.white);
        particle_texture_check = new CheckBox(" Particle Texturing ", 0, 0, Color.white);
        particle_rotation_field = new TextField("Particle Rotation:  ", 0, 0, TextField.INPUT_INTEGER, 5);
        dAWWWW_rotation_field = new TextField("Item Rotation:  ", 0, 0, TextField.INPUT_INTEGER, 5);
        
        window = new Window("Game Settings", new Color(220, 220, 220), new Rectangle(30, 76, 500, 400));
        // Add things to the panel!
        window.addChild(new TextBox("Welcome to MERCury! Here you can mess around with the item shown to your right.", new Rectangle(0, 0, 450, 100), 10, Color.white));
        window.addChild(new TextBar("General", 0, 0, Color.white), texture_check, dAWWWW_rotation_field);
        window.addNewLine();
        window.addChild(new TextBar("Particles", 0, 0, Color.white), particle_check, particle_texture_check, particle_rotation_field);
        window.sortChildren();
        
        tex = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x64.png"));
        bg = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/test/bg.png"));
        
        Vec2 screen_center = Runner.getInstance().getCamera().getBounds().getCenter();
        
        bounds = new Rectangle(rnr.getWidth() - 275, screen_center.y - 84, tex.getWidth());
        
        ParticleSetup setp = new ParticleSetup();
        setp.acceleration = 1.01f;
        setp.speed = 0.2f;
        setp.size = 32;
        setp.lifeinframes = 200;
        setp.growth = 0.94f;
        emitter = new ParticleSystem(setp);
        
        try {
            fnt0 = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("radirius/merc/graphics/font/OpenSans-Light.ttf"), 48, 1, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    boolean dragging = false;
    float dAWWWW_rot = 0;
    Vec2 mouseanchor;
    
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
                emitter.generateParticle(5 * Math.abs((int) ((dx + dy) / 2)), bounds.getCenter());
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
        g.drawTexture(bg, new Rectangle(0, 0, rnr.getWidth(), rnr.getHeight()));
        
        g.setScale(zoom);
        emitter.render(g);
        
        if (texture_check.isTicked())
            g.drawTexture(tex, bounds);
        else
            g.drawRect(bounds);
        
        g.flush();
        
        g.setScale(1);
        window.render(g);
        
        g.setColor(new Color(0, 0, 0, 100));
        g.drawRect(new Rectangle(0, rnr.getHeight() - 80, rnr.getWidth(), 80));
        
        g.setColor(Color.white);
        g.drawString("Get MERCury at merc.radiri.us!", 8, rnr.getHeight() - 80 - 32);
        g.setColor(new Color(240, 240, 240));
        g.setFont(fnt0);
        g.drawString("This is just a test! Demos Coming Soon.", 16, rnr.getHeight() - 78);
        g.setFont(TrueTypeFont.OPENSANS_REGULAR);
        
        g.setColor(Color.DEFAULT_TEXTURE_COLOR);
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] a) {
        new TestGUI("MERCury GUI Thingies!");
    }
}
