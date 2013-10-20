package com.wessles.MERCury;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.geom.Point;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class Input {
	
	public void create() {
		try {
			Mouse.create();
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public boolean keyDown(int key) {
		if(Keyboard.isKeyDown(key))
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
		return Mouse.isButtonDown(0);
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
	
	public Point getAbsoluteMousePosition() {
		return new Point(Mouse.getX(), Display.getHeight()-1-Mouse.getY());
	}
	
	public int getAbsoluteMouseX() {
		return Mouse.getX();
	}
	
	public int getAbsoluteMouseY() {
		return Display.getHeight()-1-Mouse.getY();
	}
}
