package com.radirius.mercury.scene;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.Component;

import java.util.ArrayList;

/**
 * A scene graph node.
 *
 * @author Jeviny
 */
public class GameObject implements Component {
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
	 * Initializes the children nodes.
	 */
	@Override
	public void init() {
		for (GameObject child : children)
			child.init();
	}

	/**
	 * Updates the children nodes.
	 *
	 * @param delta The delta time.
	 */
	@Override
	public void update(float delta) {
		for (GameObject child : children)
			child.update(delta);
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
}
