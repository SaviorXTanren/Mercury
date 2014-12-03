package com.radirius.mercury.scene;

import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.utilities.*;

import java.util.ArrayList;

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
	public void init() {
		for (GameObject object : objects)
			object.init();
	}

	/**
	 * Updates the children nodes.
	 *
	 * @param delta The delta time.
	 */
	public void update(float delta) {
		for (GameObject object : objects)
			object.update(delta);

		objects.sweep();
	}

	/**
	 * Renders the children nodes.
	 *
	 * @param g The Graphics object.
	 */
	public void render(Graphics g) {
		for (GameObject object : objects)
			object.render(g);
	}

	/**
	 * Cleans up the children nodes.
	 */
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
	 * @return The objects inside the scene.
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}
}
