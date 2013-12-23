package com.wessles.MERCury.gui;

import java.util.ArrayList;

import com.wessles.MERCury.Renderable;
import com.wessles.MERCury.opengl.Graphics;

/**
 * @from MERCury in com.wessles.MERCury.gui
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under GPLv2.0 license. You can find the license itself at bit.ly/1eyRQJ7.
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
