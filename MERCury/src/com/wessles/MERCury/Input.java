package com.wessles.MERCury;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.geom.Point;

/**
 * An object form of input.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Input {
	public static final int KEY_NONE = 0;
	public static final int KEY_ESCAPE = 1;
	public static final int KEY_1 = 2;
	public static final int KEY_2 = 3;
	public static final int KEY_3 = 4;
	public static final int KEY_4 = 5;
	public static final int KEY_5 = 6;
	public static final int KEY_6 = 7;
	public static final int KEY_7 = 8;
	public static final int KEY_8 = 9;
	public static final int KEY_9 = 10;
	public static final int KEY_0 = 11;
	public static final int KEY_MINUS = 12;
	public static final int KEY_EQUALS = 13;
	public static final int KEY_BACK = 14;
	public static final int KEY_TAB = 15;
	public static final int KEY_Q = 16;
	public static final int KEY_W = 17;
	public static final int KEY_E = 18;
	public static final int KEY_R = 19;
	public static final int KEY_T = 20;
	public static final int KEY_Y = 21;
	public static final int KEY_U = 22;
	public static final int KEY_I = 23;
	public static final int KEY_O = 24;
	public static final int KEY_P = 25;
	public static final int KEY_LBRACKET = 26;
	public static final int KEY_RBRACKET = 27;
	public static final int KEY_RETURN = 28;
	public static final int KEY_LCONTROL = 29;
	public static final int KEY_A = 30;
	public static final int KEY_S = 31;
	public static final int KEY_D = 32;
	public static final int KEY_F = 33;
	public static final int KEY_G = 34;
	public static final int KEY_H = 35;
	public static final int KEY_J = 36;
	public static final int KEY_K = 37;
	public static final int KEY_L = 38;
	public static final int KEY_SEMICOLON = 39;
	public static final int KEY_APOSTROPHE = 40;
	public static final int KEY_GRAVE = 41;
	public static final int KEY_LSHIFT = 42;
	public static final int KEY_BACKSLASH = 43;
	public static final int KEY_Z = 44;
	public static final int KEY_X = 45;
	public static final int KEY_C = 46;
	public static final int KEY_V = 47;
	public static final int KEY_B = 48;
	public static final int KEY_N = 49;
	public static final int KEY_M = 50;
	public static final int KEY_COMMA = 51;
	public static final int KEY_PERIOD = 52;
	public static final int KEY_SLASH = 53;
	public static final int KEY_RSHIFT = 54;
	public static final int KEY_MULTIPLY = 55;
	public static final int KEY_LMENU = 56;
	public static final int KEY_SPACE = 57;
	public static final int KEY_CAPITAL = 58;
	public static final int KEY_F1 = 59;
	public static final int KEY_F2 = 60;
	public static final int KEY_F3 = 61;
	public static final int KEY_F4 = 62;
	public static final int KEY_F5 = 63;
	public static final int KEY_F6 = 64;
	public static final int KEY_F7 = 65;
	public static final int KEY_F8 = 66;
	public static final int KEY_F9 = 67;
	public static final int KEY_F10 = 68;
	public static final int KEY_NUMLOCK = 69;
	public static final int KEY_SCROLL = 70;
	public static final int KEY_NUMPAD7 = 71;
	public static final int KEY_NUMPAD8 = 72;
	public static final int KEY_NUMPAD9 = 73;
	public static final int KEY_SUBTRACT = 74;
	public static final int KEY_NUMPAD4 = 75;
	public static final int KEY_NUMPAD5 = 76;
	public static final int KEY_NUMPAD6 = 77;
	public static final int KEY_ADD = 78;
	public static final int KEY_NUMPAD1 = 79;
	public static final int KEY_NUMPAD2 = 80;
	public static final int KEY_NUMPAD3 = 81;
	public static final int KEY_NUMPAD0 = 82;
	public static final int KEY_DECIMAL = 83;
	public static final int KEY_F11 = 87;
	public static final int KEY_F12 = 88;
	public static final int KEY_F13 = 100;
	public static final int KEY_F14 = 101;
	public static final int KEY_F15 = 102;
	public static final int KEY_KANA = 112;
	public static final int KEY_CONVERT = 121;
	public static final int KEY_NOCONVERT = 123;
	public static final int KEY_YEN = 125;
	public static final int KEY_NUMPADEQUALS = 141;
	public static final int KEY_CIRCUMFLEX = 144;
	public static final int KEY_AT = 145;
	public static final int KEY_COLON = 146;
	public static final int KEY_UNDERLINE = 147;
	public static final int KEY_KANJI = 148;
	public static final int KEY_STOP = 149;
	public static final int KEY_AX = 150;
	public static final int KEY_UNLABELED = 151;
	public static final int KEY_NUMPADENTER = 156;
	public static final int KEY_RCONTROL = 157;
	public static final int KEY_NUMPADCOMMA = 179;
	public static final int KEY_DIVIDE = 181;
	public static final int KEY_SYSRQ = 183;
	public static final int KEY_RMENU = 184;
	public static final int KEY_PAUSE = 197;
	public static final int KEY_HOME = 199;
	public static final int KEY_UP = 200;
	public static final int KEY_PRIOR = 201;
	public static final int KEY_LEFT = 203;
	public static final int KEY_RIGHT = 205;
	public static final int KEY_END = 207;
	public static final int KEY_DOWN = 208;
	public static final int KEY_NEXT = 209;
	public static final int KEY_INSERT = 210;
	public static final int KEY_DELETE = 211;
	public static final int KEY_LMETA = 219;

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
