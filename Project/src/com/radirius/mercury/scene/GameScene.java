package com.radirius.mercury.scene;

import java.util.ArrayList;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.Component;
import com.radirius.mercury.utilities.WipingArrayList;

/**
 * A basic scene graph containing hierarchies of GameObjects.
 *
 * @author Jeviny
 */
public class GameScene implements Component {
	/**
	 * The children nodes.
	 */
	private WipingArrayList<GameObject> objects = new WipingArrayList<GameObject>();

	/**
	 * Initializes the children nodes.
	 */
	@Override
	public void init() {
		for (GameObject object : objects)
			object.init();
	}

	/**
	 * Updates the children nodes.
	 */
	@Override
	public void update() {
		for (GameObject object : objects)
			object.update();

		objects.sweep();
	}

	/**
	 * Renders the children nodes.
	 *
	 * @param g The Graphics object.
	 */
	@Override
	public void render(Graphics g) {
		for (GameObject object : objects)
			object.render(g);
	}

	/**
	 * Cleans up the children nodes.
	 */
	@Override
	public void cleanup() {
		for (GameObject object : objects)
			object.cleanup();
	}

	/**
	 * Adds a child to the list of children nodes.
	 */
	public void add(GameObject... object) {
		for (GameObject object0 : object)
			objects.add(object0);
	}

	/**
	 * Removes a child from the list of children nodes.
	 */
	public void remove(GameObject... object) {
		for (GameObject object0 : object)
			objects.remove(object0);
	}
	
	/**
	 * Clears the scene.
	 */
	public void clear() {
		objects.clear();
	}
	
	/**
	 * Returns The objects inside the scene.
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
}
