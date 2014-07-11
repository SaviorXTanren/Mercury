package us.radiri.merc.gui;

import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.SpriteSheet;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.resource.Loader;

/**
 * @author wessles
 */

public class ImageButton extends Component implements Button {
    private Texture idle, hover, clicked;
    
    private boolean wasactive;
    
    public ImageButton(Rectangle bounds) {
        this(bounds, getDefaultTextures().getTexture(0), getDefaultTextures().getTexture(1), getDefaultTextures()
                .getTexture(2));
    }
    
    public ImageButton(Rectangle bounds, Texture idle, Texture hover, Texture clicked) {
        super("", bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        this.idle = idle;
        this.hover = hover;
        this.clicked = clicked;
    }
    
    @Override
    public void update(float delta) {
        wasactive = false;
        
        if (isClicked(bounds))
            wasactive = true;
    }
    
    @Override
    public void render(Graphics g) {
        if (!wasactive)
            if (!isHovered(bounds))
                g.drawTexture(idle, bounds);
            else
                g.drawTexture(hover, bounds);
        else
            g.drawTexture(clicked, bounds);
    }
    
    @Override
    public boolean wasActive() {
        boolean _wasclicked = wasactive;
        if (wasactive == true)
            wasactive = false;
        return _wasclicked;
    }
    
    private static SpriteSheet defaulttexture;
    
    public static SpriteSheet getDefaultTextures() {
        if (defaulttexture == null) {
            Texture sheet = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/gui/imagebutton.png"));
            defaulttexture = SpriteSheet.loadSpriteSheet(sheet, 64);
        }
        
        return defaulttexture;
    }
}
