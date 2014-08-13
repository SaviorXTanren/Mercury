package radirius.merc.graphics.gui;

import radirius.merc.environment.Entity;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.SpriteSheet;
import radirius.merc.graphics.SubTexture;
import radirius.merc.graphics.Texture;
import radirius.merc.input.Input;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Vec2;
import radirius.merc.resource.Loader;

/**
 * @author wessles, Jeviny
 */
public class Component implements Entity {
    /** The current focused component. */
    public static Component focusedcomponent;
    
    /** A type that will take up a new line. */
    public static final int TYPE_NONSPAN = 0;
    /** A type that will not take up a new line. */
    public static final int TYPE_SPAN = 1;
    /** A type that is not really a normal component. */
    public static final int TYPE_NONE = 2;
    /** The type of component. */
    public int TYPE = TYPE_NONSPAN;
    
    public static final int FLOAT_LEFT = -1, FLOAT_CENTER = 0, FLOAT_RIGHT = 1;
    public int FLOAT = FLOAT_LEFT;
    
    public String content;
    
    public Rectangle bounds;
    
    public Component(String txt, float x, float y, float w, float h) {
        content = txt;
        
        bounds = new Rectangle(x, y, w, h);
    }
    
    @Override
    public void update(float delta) {
        if (Runner.getInstance().getInput().mouseClicked(Input.MOUSE_LEFT))
            if (isHovered(bounds))
                focus();
            else if (this == focusedcomponent)
                unfocus();
    }
    
    @Override
    public void render(Graphics g) {
        renderContent(g);
    }
    
    public void renderContent(Graphics g) {
        g.drawString(content, bounds.getX(), bounds.getY());
    }
    
    public boolean isFocused() {
        return this == focusedcomponent;
    }
    
    public void focus() {
        focusedcomponent = this;
    }
    
    public void unfocus() {
        focusedcomponent = null;
    }
    
    public static boolean isHovered(Rectangle bounds) {
        Input in = Runner.getInstance().getInput();
        Vec2 globalmousepos = in.getGlobalMousePosition();
        globalmousepos.div(Runner.getInstance().getGraphics().getScaleDimensions());
        
        return bounds.contains(globalmousepos);
    }
    
    public static boolean isClicked(Rectangle bounds) {
        Input in = Runner.getInstance().getInput();
        
        if (isHovered(bounds))
            if (in.mouseClicked(Input.MOUSE_LEFT))
                return true;
        
        return false;
    }
    
    private static SpriteSheet defaulttextures;
    
    public static SpriteSheet getDefaultTextures() {
        if (defaulttextures == null) {
            // The base texture!
            Texture spritesheet_texture = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/graphics/gui/gui_spritesheet.png"));
            
            // And all the little textures..
            SubTexture window_bar = new SubTexture(spritesheet_texture, 0, 0, 512, 42); // 0
            SubTexture panel_body = new SubTexture(spritesheet_texture, 1, 43, 17, 59); // 1
            SubTexture panel_border = new SubTexture(spritesheet_texture, 0, 43, 1, 512); // 2
            SubTexture button_left = new SubTexture(spritesheet_texture, 17, 43, 33, 74); // 3
            SubTexture button_body = new SubTexture(spritesheet_texture, 34, 43, 48, 74); // 4
            SubTexture checkbox_unchecked = new SubTexture(spritesheet_texture, 49, 43, 72, 66); // 5
            SubTexture checkbox_checked = new SubTexture(spritesheet_texture, 73, 43, 96, 66); // 6
            SubTexture imagebutton_idle = new SubTexture(spritesheet_texture, 145, 43, 176, 74); // 7
            SubTexture imagebutton_hover = new SubTexture(spritesheet_texture, 177, 43, 208, 74); // 8
            SubTexture imagebutton_active = new SubTexture(spritesheet_texture, 209, 43, 240, 74); // 9
            SubTexture text_field_left = new SubTexture(spritesheet_texture, 218, 43, 233, 74); // 10
            SubTexture text_field_body = new SubTexture(spritesheet_texture, 234, 43, 250, 74); // 11
            
            defaulttextures = SpriteSheet.loadSpriteSheet(spritesheet_texture, window_bar, panel_body, panel_border, button_left, button_body, checkbox_unchecked, checkbox_checked, imagebutton_idle, imagebutton_hover, imagebutton_active, text_field_left, text_field_body);
        }
        
        return defaulttextures;
    }
}
