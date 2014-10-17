package com.radirius.mercury.scene;

import java.util.ArrayList;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.Component;

/**
 * A scene graph node.
 * 
 * @author Jeviny
 */
public class GameObject implements Component {
	protected ArrayList<GameObject> children = new ArrayList<GameObject>();

	/**
	 * Adds a child to the list of children nodes.
	 */
	public void addChild(GameObject child) {
		children.add(child);
	}

	@Override
	public void init() {
		for (GameObject child : children)
			child.init();
	}
	
	@Override
	public void update(float delta) {
		for (GameObject child : children)
			child.update(delta);
	}
	
	@Override
	public void render(Graphics g) {
		for (GameObject child : children)
			child.render(g);
	}
	
	@Override
	public void cleanup() {
		for (GameObject child : children)
			child.cleanup();
	}
}
