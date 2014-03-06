package com.teama.merc.gui;

import com.teama.merc.env.Renderable;
import com.teama.merc.gfx.Graphics;

/**
 * @from merc in com.teama.merc.gui
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class Component implements Renderable
{
    public String txt;
    
    public boolean cx, cy;
    
    private ActionCheck acheck;
    public float x, y, w, h;
    
    public Component(String txt, float x, float y, float w, float h, boolean centerx, boolean centery)
    {
        this.txt = txt;
        
        this.cx = centerx;
        this.cy = centery;
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void update()
    {
        if (acheck != null)
            if (acheck.isActed())
                acheck.act();
            else
                acheck.noAct();
    }
    
    @Override
    public void render(Graphics g)
    {
        renderContent(g);
    }
    
    public void renderContent(Graphics g)
    {
        float tx = 0, ty = 0;
        
        if (cx)
            tx = w / 2 - g.getFont().getWidth(txt.toCharArray()) / 2;
        if (cy)
            ty = h / 2 - g.getFont().getHeight() / 4;
        
        g.drawString(x + tx, y + ty, txt);
    }
    
    public Component setActionCheck(ActionCheck acheck)
    {
        this.acheck = acheck;
        acheck.setParent(this);
        return this;
    }
    
    public static abstract class ActionCheck
    {
        public Component parent;
        
        public abstract boolean isActed();
        
        public abstract void act();
        
        public abstract void noAct();
        
        public void setParent(Component parent)
        {
            this.parent = parent;
        }
    }
}
