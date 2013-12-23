package com.wessles.MERCury.gui;

import java.util.ArrayList;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public class GUISystem implements Renderable {
  public final ArrayList<Component> components = new ArrayList<Component>();
  
  public GUISystem addComponent(Component c) {
    components.add(c);
    return this;
  }
  
  public void update() {
    for (Component c : components)
      c.update();
  }
  
  @Override
  public void render(Graphics g) {
    for (Component c : components)
      c.render(g);
  }
}
