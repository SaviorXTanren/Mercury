package us.radiri.merc.gui;

import us.radiri.merc.environment.Renderable;
import us.radiri.merc.environment.Updatable;
import us.radiri.merc.geom.Rectangle;
import us.radiri.merc.graphics.Graphics;

/**
 * @author wessles, Jeviny
 */
public class Component implements Updatable, Renderable {
    public String content;
    
    private ActionCheck acheck;
    
    public Rectangle bounds;
    
    public Component(String txt, float x, float y, float w, float h) {
        content = txt;
        
        this.bounds = new Rectangle(x, y, w, h);
    }
    
    @Override
    public void update(float delta) {
        if (acheck != null)
            if (acheck.isActed())
                acheck.act();
            else
                acheck.noAct();
    }
    
    @Override
    public void render(Graphics g) {
        renderContent(g);
    }
    
    public void renderContent(Graphics g) {
        g.drawString(bounds.getX(), bounds.getY(), content);
    }
    
    public Component addActionCheck(ActionCheck acheck) {
        this.acheck = acheck;
        this.acheck.setParent(this);
        return this;
    }
    
    public static abstract class ActionCheck {
        public Component parent;
        
        public abstract boolean isActed();
        
        public abstract void act();
        
        public abstract void noAct();
        
        public void setParent(Component parent) {
            this.parent = parent;
        }
    }
}
