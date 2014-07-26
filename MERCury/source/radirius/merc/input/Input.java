package radirius.merc.input;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import radirius.merc.framework.Runner;
import radirius.merc.geometry.Point;
import radirius.merc.geometry.Vec2;

/**
 * An object form of input.
 * 
 * @author wessles
 */
public class Input {
  // No, I did not type out all of these keys; I stole it from kevglass'
  // slick2d source code! Shout out to him for crafting this much stuff!
  // https://bitbucket.org/kevglass/slick/src/9d7443ec33af/trunk/Slick/src/org/newdawn/slick/Input.java?at=default
  public static final int KEY_ESCAPE = 0x01;
  public static final int KEY_1 = 0x02;
  public static final int KEY_2 = 0x03;
  public static final int KEY_3 = 0x04;
  public static final int KEY_4 = 0x05;
  public static final int KEY_5 = 0x06;
  public static final int KEY_6 = 0x07;
  public static final int KEY_7 = 0x08;
  public static final int KEY_8 = 0x09;
  public static final int KEY_9 = 0x0A;
  public static final int KEY_0 = 0x0B;
  public static final int KEY_MINUS = 0x0C;
  public static final int KEY_EQUALS = 0x0D;
  public static final int KEY_BACK = 0x0E;
  public static final int KEY_TAB = 0x0F;
  public static final int KEY_Q = 0x10;
  public static final int KEY_W = 0x11;
  public static final int KEY_E = 0x12;
  public static final int KEY_R = 0x13;
  public static final int KEY_T = 0x14;
  public static final int KEY_Y = 0x15;
  public static final int KEY_U = 0x16;
  public static final int KEY_I = 0x17;
  public static final int KEY_O = 0x18;
  public static final int KEY_P = 0x19;
  public static final int KEY_LBRACKET = 0x1A;
  public static final int KEY_RBRACKET = 0x1B;
  public static final int KEY_RETURN = 0x1C;
  public static final int KEY_ENTER = 0x1C;
  public static final int KEY_LCONTROL = 0x1D;
  public static final int KEY_A = 0x1E;
  public static final int KEY_S = 0x1F;
  public static final int KEY_D = 0x20;
  public static final int KEY_F = 0x21;
  public static final int KEY_G = 0x22;
  public static final int KEY_H = 0x23;
  public static final int KEY_J = 0x24;
  public static final int KEY_K = 0x25;
  public static final int KEY_L = 0x26;
  public static final int KEY_SEMICOLON = 0x27;
  public static final int KEY_APOSTROPHE = 0x28;
  public static final int KEY_GRAVE = 0x29;
  public static final int KEY_LSHIFT = 0x2A;
  public static final int KEY_BACKSLASH = 0x2B;
  // Wow, this is a lot.
  public static final int KEY_Z = 0x2C;
  public static final int KEY_X = 0x2D;
  public static final int KEY_C = 0x2E;
  public static final int KEY_V = 0x2F;
  public static final int KEY_B = 0x30;
  public static final int KEY_N = 0x31;
  public static final int KEY_M = 0x32;
  public static final int KEY_COMMA = 0x33;
  public static final int KEY_PERIOD = 0x34;
  public static final int KEY_SLASH = 0x35;
  public static final int KEY_RSHIFT = 0x36;
  public static final int KEY_MULTIPLY = 0x37;
  public static final int KEY_LMENU = 0x38;
  public static final int KEY_SPACE = 0x39;
  public static final int KEY_CAPITAL = 0x3A;
  public static final int KEY_F1 = 0x3B;
  public static final int KEY_F2 = 0x3C;
  public static final int KEY_F3 = 0x3D;
  public static final int KEY_F4 = 0x3E;
  public static final int KEY_F5 = 0x3F;
  public static final int KEY_F6 = 0x40;
  public static final int KEY_F7 = 0x41;
  public static final int KEY_F8 = 0x42;
  public static final int KEY_F9 = 0x43;
  public static final int KEY_F10 = 0x44;
  public static final int KEY_NUMLOCK = 0x45;
  public static final int KEY_SCROLL = 0x46;
  public static final int KEY_NUMPAD7 = 0x47;
  public static final int KEY_NUMPAD8 = 0x48;
  public static final int KEY_NUMPAD9 = 0x49;
  public static final int KEY_SUBTRACT = 0x4A;
  public static final int KEY_NUMPAD4 = 0x4B;
  public static final int KEY_NUMPAD5 = 0x4C;
  public static final int KEY_NUMPAD6 = 0x4D;
  // Oh my..
  public static final int KEY_ADD = 0x4E;
  public static final int KEY_NUMPAD1 = 0x4F;
  public static final int KEY_NUMPAD2 = 0x50;
  public static final int KEY_NUMPAD3 = 0x51;
  public static final int KEY_NUMPAD0 = 0x52;
  public static final int KEY_DECIMAL = 0x53;
  public static final int KEY_F11 = 0x57;
  public static final int KEY_F12 = 0x58;
  public static final int KEY_F13 = 0x64;
  public static final int KEY_F14 = 0x65;
  public static final int KEY_F15 = 0x66;
  public static final int KEY_KANA = 0x70;
  public static final int KEY_CONVERT = 0x79;
  public static final int KEY_NOCONVERT = 0x7B;
  public static final int KEY_YEN = 0x7D;
  public static final int KEY_NUMPADEQUALS = 0x8D;
  public static final int KEY_CIRCUMFLEX = 0x90;
  public static final int KEY_AT = 0x91;
  public static final int KEY_COLON = 0x92;
  public static final int KEY_UNDERLINE = 0x93;
  public static final int KEY_KANJI = 0x94;
  public static final int KEY_STOP = 0x95;
  public static final int KEY_AX = 0x96;
  public static final int KEY_UNLABELED = 0x97;
  public static final int KEY_NUMPADENTER = 0x9C;
  public static final int KEY_RCONTROL = 0x9D;
  public static final int KEY_NUMPADCOMMA = 0xB3;
  public static final int KEY_DIVIDE = 0xB5;
  public static final int KEY_SYSRQ = 0xB7;
  public static final int KEY_RMENU = 0xB8;
  public static final int KEY_PAUSE = 0xC5;
  public static final int KEY_HOME = 0xC7;
  // So many!
  public static final int KEY_UP = 0xC8;
  public static final int KEY_PRIOR = 0xC9;
  public static final int KEY_LEFT = 0xCB;
  public static final int KEY_RIGHT = 0xCD;
  public static final int KEY_END = 0xCF;
  public static final int KEY_DOWN = 0xD0;
  public static final int KEY_NEXT = 0xD1;
  public static final int KEY_INSERT = 0xD2;
  public static final int KEY_DELETE = 0xD3;
  public static final int KEY_LWIN = 0xDB;
  public static final int KEY_RWIN = 0xDC;
  public static final int KEY_APPS = 0xDD;
  public static final int KEY_POWER = 0xDE;
  public static final int KEY_SLEEP = 0xDF;
  public static final int KEY_LALT = KEY_LMENU;
  public static final int KEY_RALT = KEY_RMENU;
  
  public static final int MOUSE_LEFT = 0;
  public static final int MOUSE_RIGHT = 1;
  public static final int MOUSE_MID = 2;
  
  private ArrayList<Integer> eventkeystates = new ArrayList<Integer>();
  private char nextchar = 0;
  
  private ArrayList<Integer> eventmousebuttonstates = new ArrayList<Integer>();
  private int mousedwheel = 0;
  
  /** Creates the input things. */
  public void create() {
    try {
      Mouse.create();
      Keyboard.create();
    } catch (LWJGLException e) {
      e.printStackTrace();
    }
    
    setRepeatEventsEnabled(true);
  }
  
  /** Updates a list of things that happened every frame. */
  public void pollKeyboard() {
    nextchar = 0;
    eventkeystates.clear();
    
    while (Keyboard.next())
      if (Keyboard.getEventKeyState()) {
        eventkeystates.add(Keyboard.getEventKey());
        nextchar = Keyboard.getEventCharacter();
      }
  }
  
  /** Updates a list of things that happened every frame. */
  public void pollMouse() {
    eventmousebuttonstates.clear();
    
    while (Mouse.next())
      if (Mouse.getEventButtonState())
        eventmousebuttonstates.add(Mouse.getEventButton());
    
    mousedwheel = Mouse.getDWheel();
  }
  
  /** Polls keyboard and mouse. */
  public void poll() {
    pollKeyboard();
    pollMouse();
  }
  
  /** @return If key was clicked. */
  public boolean keyClicked(int key) {
    for (Integer eventkey : eventkeystates)
      if (eventkey == key)
        return true;
    return false;
  }
  
  /** @return If key is down. */
  public boolean keyDown(int key) {
    if (Keyboard.isKeyDown(key))
      return true;
    return false;
  }
  
  /** @return If key is up. */
  public boolean keyUp(int key) {
    return !keyDown(key);
  }
  
  /** @return If mousebutton was clicked. */
  public boolean mouseClicked(int mousebutton) {
    for (Integer eventmousebutton : eventmousebuttonstates)
      if (eventmousebutton == mousebutton)
        return true;
    
    return false;
  }
  
  /** @return If mousebutton is down. */
  public boolean mouseDown(int mousebutton) {
    return Mouse.isButtonDown(mousebutton);
  }
  
  /** @return If mousebutton is up. */
  public boolean mouseUp(int mousebutton) {
    return !mouseDown(mousebutton);
  }
  
  /** @return If mouse wheel is going up. */
  public boolean mouseWheelUp() {
    return mousedwheel > 0;
  }
  
  /** @return If mouse wheel is going down. */
  public boolean mouseWheelDown() {
    return mousedwheel < 0;
  }
  
  /** @return Mouse position. */
  public Point getMousePosition() {
    return new Point(Mouse.getX(), Mouse.getY());
  }
  
  /** @return Mouse's x position. */
  public int getMouseX() {
    return Mouse.getX();
  }
  
  /** @return Mouse's y position. */
  public int getMouseY() {
    return Mouse.getY();
  }
  
  /**
   * @return The mouse 'correct' position (Has to do with the opengl origin
   *         being bottom left, and ours being top left).
   */
  public Vec2 getAbsoluteMousePosition() {
    return new Vec2(getMouseX(), Display.getHeight() - 1 - getMouseY());
  }
  
  /**
   * @return The mouse position's 'correct' x (Has to do with the opengl origin
   *         being bottom left, and ours being top left).
   */
  public int getAbsoluteMouseX() {
    return (int) getAbsoluteMousePosition().x;
  }
  
  /**
   * @return The mouse position's 'correct' y (Has to do with the opengl origin
   *         being bottom left, and ours being top left).
   */
  public int getAbsoluteMouseY() {
    return (int) getAbsoluteMousePosition().y;
  }
  
  /**
   * @return The global mouse position based on the displacement of the Runner's
   *         Camera and the scaling of the graphics.
   */
  public Vec2 getGlobalMousePosition() {
    Vec2 globalmousepos = getAbsoluteMousePosition();
    
    // Move to camera position
    globalmousepos.add(Runner.getInstance().getCamera().getPosition());
    return globalmousepos;
  }
  
  /**
   * @return The global mouse x position based on the displacement of the
   *         Runner's Camera and the scaling of the graphics.
   */
  public float getGlobalMouseX() {
    return getGlobalMousePosition().x;
  }
  
  /**
   * @return The global mouse y position based on the displacement of the
   *         Runner's Camera and the scaling of the graphics.
   */
  public float getGlobalMouseY() {
    return getGlobalMousePosition().y;
  }
  
  /**
   * @return The last character pressed.
   */
  public char getNextCharacter() {
    return nextchar;
  }
  
  /** Sets whether or not to accept repeating events. */
  public void setRepeatEventsEnabled(boolean repeatevents) {
    Keyboard.enableRepeatEvents(repeatevents);
  }
}
