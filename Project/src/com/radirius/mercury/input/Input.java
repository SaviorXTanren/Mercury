package com.radirius.mercury.input;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.graphics.Camera;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

/**
 * An object form of input.
 *
 * @author wessles, Jeviny
 */
public class Input {
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
	public static final int KEY_LALT = KEY_LMENU;
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
	public static final int KEY_RALT = KEY_RMENU;
	public static final int KEY_PAUSE = 0xC5;
	public static final int KEY_HOME = 0xC7;
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
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;
	public static final int MOUSE_MID = 2;

	private static ArrayList<Integer> eventKeyStates = new ArrayList<Integer>();
	private static ArrayList<Integer> eventMouseButtonStates = new ArrayList<Integer>();
	private static ArrayList<ControllerEvent> eventControllerButtonStates = new ArrayList<ControllerEvent>();

	private static int mousedWheel = 0;
	private static char nextCharacter = 0;

	private static boolean keyboardPolling = true;
	private static boolean mousePolling = true;
	private static boolean controllerPolling = true;

	/**
	 * Creates the input things.
	 */
	public static void init() {
		try {
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		setRepeatEventsEnabled(true);
	}

	/**
	 * Updates a list of things that happened every frame.
	 */
	public static void pollKeyboard() {
		if (!keyboardPolling)
			return;

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				eventKeyStates.add(Keyboard.getEventKey());
				nextCharacter = Keyboard.getEventCharacter();
			}
		}
	}

	/**
	 * Updates a list of things that happened every frame.
	 */
	public static void pollMouse() {
		if (!mousePolling)
			return;

		while (Mouse.next()) {
			if (Mouse.getEventButtonState())
				eventMouseButtonStates.add(Mouse.getEventButton());
		}

		mousedWheel = Mouse.getDWheel();
	}

	/**
	 * Updates a list of things that happened every frame.
	 */
	public static void pollControllers() {
		if (!controllerPolling)
			return;

		if (!Controllers.isCreated())
			return;

		int controllerId = 0;

		while (Controllers.next()) {
			if (Controllers.isEventButton()) {
				int button = Controllers.getEventControlIndex();
				boolean state = Controllers.getEventButtonState();

				if (!state)
					eventControllerButtonStates.add(new ControllerEvent(button, controllerId));
			}

			controllerId++;
		}
	}

	/**
	 * Polls keyboard and mouse.
	 */
	public static void poll() {
		purgeBuffers();
		pollKeyboard();
		pollMouse();
		pollControllers();
	}

	public static void purgeBuffers() {
		eventKeyStates.clear();
		nextCharacter = 0;

		eventMouseButtonStates.clear();

		eventControllerButtonStates.clear();
	}

	/**
	 * @return If key was clicked.
	 */
	public static boolean keyClicked(int key) {
		for (Integer eventkey : eventKeyStates)
			if (eventkey == key)
				return true;
		return false;
	}

	/**
	 * @return If any key was clicked.
	 */
	public static boolean wasKeyClicked() {
		return eventKeyStates.size() != 0;
	}

	/**
	 * @return If key is down.
	 */
	public static boolean keyDown(int key) {
		if (Keyboard.isKeyDown(key))
			return true;
		return false;
	}

	/**
	 * @return If key is up.
	 */
	public static boolean keyUp(int key) {
		return !keyDown(key);
	}

	/**
	 * @return The last character pressed.
	 */
	public static char getNextCharacter() {
		return nextCharacter;
	}

	/**
	 * Sets whether or not to accept repeating events.
	 */
	public static void setRepeatEventsEnabled(boolean repeatevents) {
		Keyboard.enableRepeatEvents(repeatevents);
	}

	/**
	 * @return If mousebutton was clicked.
	 */
	public static boolean mouseClicked(int mousebutton) {
		for (Integer eventmousebutton : eventMouseButtonStates)
			if (eventmousebutton == mousebutton)
				return true;

		return false;
	}

	/**
	 * @return If mousebutton is down.
	 */
	public static boolean mouseDown(int mousebutton) {
		return Mouse.isButtonDown(mousebutton);
	}

	/**
	 * @return If mousebutton is up.
	 */
	public static boolean mouseUp(int mousebutton) {
		return !mouseDown(mousebutton);
	}

	/**
	 * @return If mouse wheel is going up.
	 */
	public static boolean mouseWheelUp() {
		return mousedWheel > 0;
	}

	/**
	 * @return If mouse wheel is going down.
	 */
	public static boolean mouseWheelDown() {
		return mousedWheel < 0;
	}

	/**
	 * @return Mouse position.
	 */
	public static Point getMousePosition() {
		return new Point(Mouse.getX(), Mouse.getY());
	}

	/**
	 * @return Mouse's x position.
	 */
	public static int getMouseX() {
		return Mouse.getX();
	}

	/**
	 * @return Mouse's y position.
	 */
	public static int getMouseY() {
		return Mouse.getY();
	}

	/**
	 * @return The mouse 'correct' position (Has to do with the opengl origin being bottom left, and ours being top
	 * left).
	 */
	public static Vector2f getAbsoluteMousePosition() {
		return new Vector2f(getMouseX(), Display.getHeight() - 1 - getMouseY());
	}

	/**
	 * @return The mouse position's 'correct' x (Has to do with the opengl origin being bottom left, and ours being top
	 * left).
	 */
	public static int getAbsoluteMouseX() {
		return (int) getAbsoluteMousePosition().x;
	}

	/**
	 * @return The mouse position's 'correct' y (Has to do with the opengl origin being bottom left, and ours being top
	 * left).
	 */
	public static int getAbsoluteMouseY() {
		return (int) getAbsoluteMousePosition().y;
	}

	/**
	 * @return The global mouse position based on the displacement of the game's camera and the scaling of the graphics.
	 */
	public static Vector2f getGlobalMousePosition() {
		Vector2f globalmousepos = getAbsoluteMousePosition();
		Camera cam = Core.getCurrentCore().getCamera();

		// Scale the mouse position
		globalmousepos.div(cam.getScaleDimensions());
		// Move the mouse position to the camera
		globalmousepos.add(new Vector2f(cam.getPositionX() - cam.getOrigin().x / cam.getScaleDimensions().x, cam.getPositionY() - cam.getOrigin().y / cam.getScaleDimensions().y));

		// Rotate to alignment
		float origx = cam.getPositionX();
		float origy = cam.getPositionY();

		float s = MathUtil.sin(-cam.getRotation());
		float c = MathUtil.cos(-cam.getRotation());

		globalmousepos.x -= origx;
		globalmousepos.y -= origy;

		float xnew = globalmousepos.x * c - globalmousepos.y * s;
		float ynew = globalmousepos.x * s + globalmousepos.y * c;

		globalmousepos.x = xnew + origx;
		globalmousepos.y = ynew + origy;

		return globalmousepos;
	}

	/**
	 * @return The global mouse x position based on the displacement of the game's camera and the scaling of the
	 * graphics.
	 */
	public static float getGlobalMouseX() {
		return getGlobalMousePosition().x;
	}

	/**
	 * @return The global mouse y position based on the displacement of the game's camera and the scaling of the
	 * graphics.
	 */
	public static float getGlobalMouseY() {
		return getGlobalMousePosition().y;
	}

	public static void initControllers() {
		if (Controllers.isCreated())
			return;

		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static boolean controllerButtonClicked(int button, int controller) {
		if (!Controllers.isCreated())
			return false;

		for (ControllerEvent cevent : eventControllerButtonStates)
			if (cevent.button == button && cevent.controller == controller)
				return true;

		return false;
	}

	/**
	 * @return Whether or not the button on controller is down; null if controllers have not been initialized.
	 */
	public static boolean controllerButtonDown(int button, int controller) {
		if (!Controllers.isCreated())
			return false;

		return Controllers.getController(controller).isButtonPressed(button);
	}

	/**
	 * @return Whether or not the button on controller is up; null if controllers have not been initialized.
	 */
	public static boolean controllerButtonUp(int button, int controller) {
		return !controllerButtonDown(button, controller);
	}

	/**
	 * @return A vector containing the x and y value of the left analog stick. null if controllers have not been
	 * initialized.
	 */
	public static Vector2f getLeftAnalogStick(int controller) {
		if (!Controllers.isCreated())
			return null;

		Vector2f result = new Vector2f(0, 0);

		Controller control = Controllers.getController(controller);
		result.x = control.getXAxisValue();
		result.y = control.getYAxisValue();

		return result;
	}

	/**
	 * @return A vector containing the x and y value of the right analog stick. null if controllers have not been
	 * initialized.
	 */
	public static Vector2f getRightAnalogStick(int controller) {
		if (!Controllers.isCreated())
			return null;

		Vector2f result = new Vector2f(0, 0);

		Controller control = Controllers.getController(controller);
		result.x = control.getRXAxisValue();
		result.y = control.getRZAxisValue();

		return result;
	}

	/**
	 * @return A vector containing the x and y value of the dpad. null if controllers have not been initialized.
	 */
	public static Vector2f getDPad(int controller) {
		if (!Controllers.isCreated())
			return null;

		Vector2f result = new Vector2f(0, 0);

		Controller control = Controllers.getController(controller);
		result.x = control.getPovX();
		result.y = control.getPovY();

		return result;
	}

	/**
	 * @return The amount of controllers. -1 if controllers have not been initialized.
	 */
	public static int getControllerCount() {
		if (!Controllers.isCreated())
			return -1;

		return Controllers.getControllerCount();
	}

	/**
	 * Enables event polling.
	 *
	 * @param keyboard Enable keyboard polling?
	 */
	public static void setKeyboardPollingEnabled(boolean keyboard) {
		keyboardPolling = keyboard;
	}

	/**
	 * Enables event polling.
	 *
	 * @param mouse Enable mouse polling?
	 */
	public static void setMousePollingEnabled(boolean mouse) {
		mousePolling = mouse;
	}

	/**
	 * Enables event polling.
	 *
	 * @param controller Enable controller polling?
	 */
	public static void setControllerPollingEnabled(boolean controller) {
		controllerPolling = controller;
	}

	/**
	 * Enables event polling.
	 *
	 * @param keyboard   Enable keyboard polling?
	 * @param mouse      Enable mouse polling?
	 * @param controller Enable controller polling?
	 */
	public static void setPollingEnabled(boolean keyboard, boolean mouse, boolean controller) {
		keyboardPolling = keyboard;
		mousePolling = mouse;
		controllerPolling = controller;
	}

	/**
	 * Holds data for a controller event.
	 */
	private static class ControllerEvent {
		public final int button, controller;

		public ControllerEvent(int button, int controller) {
			this.button = button;
			this.controller = controller;
		}
	}
}
