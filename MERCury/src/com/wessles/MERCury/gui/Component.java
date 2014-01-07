package com.wessles.MERCury.gui;

import com.wessles.MERCury.environment.Renderable;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.geom.TexturedRectangle;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.opengl.Texture;

/**
 * @from MERCury in com.wessles.MERCury.gui
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Component implements Renderable {
  public Texture tex;
  public String text;
  
  public boolean text_centered;
  
  private ActionCheck acheck;
  public float x, y, w, h;
  
  public Component(String text, Texture tex, float x, float y, float w, float h, boolean text_centered) {
    this.text = text;
    this.tex = tex;
    
    this.text_centered = text_centered;
    
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  public void update() {
    if (acheck != null)
      if (acheck.isActed())
        acheck.act();
      else
        acheck.noAct();
  }
  
  @Override
  public void render(Graphics g) {
    g.drawRect(new TexturedRectangle(new Rectangle(x, y, x + w, y, x + w, y + h, x, y + h), tex));
    
    if (text_centered) {
      float textx = g.getFont().getWidth(text.toCharArray()) / 2;
      float texty = g.getFont().getHeight() / 2;
      
      g.drawString(x - textx + w / 2, y - texty + h / 2, text);
    } else
      g.drawString(x, y, text);
  }
  
  public Component setActionCheck(ActionCheck acheck) {
    this.acheck = acheck;
    acheck.setParent(this);
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
