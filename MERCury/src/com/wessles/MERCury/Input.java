package com.wessles.MERCury;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.geom.Point;

/**
 * An object form of input.
 * 
 * @from MERCury in com.wessles.MERCury
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class Input {
  public static final int MOUSE_LEFT = 0;
  public static final int MOUSE_RIGHT = 3;
  public static final int MOUSE_MID = 2;
  
  public void create() {
    try {
      Mouse.create();
      Keyboard.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
  }
  
  public boolean keyDown(int key) {
    if (Keyboard.isKeyDown(key))
      return true;
    return false;
  }
  
  public boolean keyClicked(int key) {
    while (Keyboard.next())
      if (Keyboard.getEventKeyState())
        if (Keyboard.getEventKey() == key)
          return true;
    return false;
  }
  
  public boolean keyUp(int key) {
    return !keyDown(key);
  }
  
  public boolean mouseDown(int button) {
    while (Mouse.next())
      if (Mouse.getEventButtonState())
        if (Mouse.getEventButton() == button)
          return true;
    
    return false;
  }
  
  public boolean mouseUp(int button) {
    return !mouseDown(button);
  }
  
  public Point getMousePosition() {
    return new Point(Mouse.getX(), Mouse.getY());
  }
  
  public int getMouseX() {
    return Mouse.getX();
  }
  
  public int getMouseY() {
    return Mouse.getY();
  }
  
  /**
   * 'Corrects' the mouse position.
   */
  public Point getAbsoluteMousePosition() {
    return new Point(Mouse.getX(), Display.getHeight() - 1 - Mouse.getY());
  }
  
  /**
   * 'Corrects' the mouse position's x.
   */
  public int getAbsoluteMouseX() {
    return Mouse.getX();
  }
  
  /**
   * 'Corrects' the mouse position's y.
   */
  public int getAbsoluteMouseY() {
    return Display.getHeight() - 1 - Mouse.getY();
  }
}
