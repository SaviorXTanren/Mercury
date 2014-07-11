package us.radiri.merc.gui;

import us.radiri.merc.environment.Renderable;
import us.radiri.merc.environment.Updatable;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.geom.Vec2;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.input.Input;

/**
 * @author wessles, Jeviny
 */
public class Component implements Updatable, Renderable {
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
        
    }
    
    @Override
    public void render(Graphics g) {
        renderContent(g);
    }
    
    public void renderContent(Graphics g) {
        g.drawString(bounds.getX(), bounds.getY(), content);
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
            if (in.mouseClicked(0))
                return true;
        
        return false;
    }
}
