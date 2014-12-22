package com.radirius.mercury.scene;

import java.util.ArrayList;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.Component;
import com.radirius.mercury.utilities.Wipeable;

/**
 * A scene graph node.
 *
 * @author Jeviny
 */
public class GameObject implements Component, Wipeable {
	/**
	 * The children nodes.
	 */
	protected ArrayList<GameObject> children = new ArrayList<GameObject>();

	/**
	 * Adds a child to the list of children nodes.
	 */
	public void addChild(GameObject child) {
		children.add(child);
	}

	/**
	 * Removes a child to the list of children nodes.
	 */
	public void removeChild(GameObject child) {
		children.remove(child);
	}
	
	/**
	 * Removes all children of the children nodes.
	 */
	public void removeAllChildren() {
		children.clear();
	}
	
	/**
	 * Initializes the children nodes.
	 */
	@Override
	public void init() {
		for (GameObject child : children)
			child.init();
	}

	/**
	 * Updates the children nodes.
	 */
	@Override
	public void update() {
		for (GameObject child : children)
			child.update();
	}

	/**
	 * Renders the children nodes.
	 *
	 * @param g The Graphics object.
	 */
	@Override
	public void render(Graphics g) {
		for (GameObject child : children)
			child.render(g);
	}

	/**
	 * Cleans up the children nodes.
	 */
	@Override
	public void cleanup() {
		for (GameObject child : children)
			child.cleanup();
	}

	private boolean wiped = false;

	@Override
	public void wipe() {
		wiped = true;
	}

	@Override
	public boolean wiped() {
		return wiped;
	}
}
