package com.wessles.MERCury.environment;

import com.wessles.MERCury.geom.Ellipse;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.graphics.Texture;

/**
 * @from MERCury in com.wessles.MERCury
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public abstract class AdvancedMercEntity implements MercEntity
{
    public Texture tex;
    public float x, y, w, h;
    
    public AdvancedMercEntity(float x, float y, float w, float h, Texture tex)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tex = tex;
    }
    
    public AdvancedMercEntity(float x, float y, Texture tex)
    {
        this.x = x;
        this.y = y;
        this.tex = tex;
        w = this.tex.getTextureWidth();
        h = this.tex.getTextureHeight();
    }
    
    public AdvancedMercEntity(float x, float y, float w, float h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    @Override
    public void render(Graphics g)
    {
        if (tex != null)
            g.drawTexture(tex, 0, 0, tex.getTextureWidth(), tex.getTextureHeight(), x, y, x + w, y + h);
    }
    
    public void move(float mx, float my)
    {
        float dx = x + mx, dy = y + my;
        if (isValidPosition(dx, y))
            x += mx;
        if (isValidPosition(x, dy))
            y += my;
    }
    
    public abstract boolean isValidPosition(float x, float y);
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, w, h);
    }
    
    public Ellipse getRadBounds()
    {
        return new Ellipse(x + w / 2, y + h / 2, w / 2, h / 2);
    }
}
