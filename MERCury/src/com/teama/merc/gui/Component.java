package com.teama.merc.gui;

import com.teama.merc.env.Renderable;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.geo.TexturedRectangle;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

/**
 * @from merc in com.teama.merc.gui
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Component implements Renderable
{
    public Texture tex;
    public String txt;
    
    public boolean txtCentered;
    
    private ActionCheck acheck;
    public float x, y, w, h;
    
    public Component(String txt, Texture tex, float x, float y, float w, float h, boolean txtCentered)
    {
        this.txt = txt;
        this.tex = tex;
        
        this.txtCentered = txtCentered;
        
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
        g.drawRect(new TexturedRectangle(new Rectangle(x, y, x + w, y, x + w, y + h, x, y + h), tex));
        
        if (txtCentered)
        {
            float textx = g.getFont().getWidth(txt.toCharArray()) / 2;
            float texty = g.getFont().getHeight() / 2;
            
            g.drawString(x - textx + w / 2, y - texty + h / 2, txt);
        } else
            g.drawString(x, y, txt);
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
