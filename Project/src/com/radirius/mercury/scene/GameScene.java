package com.radirius.mercury.scene;

import java.util.ArrayList;

import com.radirius.mercury.graphics.Graphics;

/**
 * A basic scene graph containing hierarchies of
 * GameObjects.
 *
 * @author Jeviny
 */
public class GameScene {
	/** The children nodes. */
	private static ArrayList<GameObject> objects = new ArrayList<GameObject>();

	/**
	 * Adds a child to the list of children nodes.
	 */
	public static void addObject(GameObject... object) {
		for (GameObject object0 : object)
			objects.add(object0);
	}

	/**
	 * Initializes the children nodes.
	 */
	public static void init() {
		for (GameObject object : objects)
			object.init();
	}

	/**
	 * Updates the children nodes.
	 * 
	 * @param delta
	 *            The delta time.
	 */
	public static void update(float delta) {
		for (GameObject object : objects)
			object.update(delta);
	}

	/**
	 * Renders the children nodes.
	 * 
	 * @param g
	 *            The Graphics object.
	 */
	public static void render(Graphics g) {
		for (GameObject object : objects)
			object.render(g);
	}

	/**
	 * Cleans up the children nodes.
	 */
	public static void cleanup() {
		for (GameObject object : objects)
			object.cleanup();
	}

	/**
	 * @return The objects inside the scene.
	 */
	public static ArrayList<GameObject> getObjects() {
		return objects;
	}
}
