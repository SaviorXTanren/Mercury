package com.radirius.mercury.graphics.wip.gui.themes;

import java.util.HashMap;

/**
 * A theme is a set of theming nodes filled with attributes that apply to specific types of components to create a unifying aesthetic for the whole GUI.
 *
 * @author wessles
 */
public class Theme {
	private HashMap<Class, ThemeNode> nodes = new HashMap<>();

	public HashMap<Class, ThemeNode> getNodes() {
		return nodes;
	}

	public void addNode(Class klass, ThemeNode node) {
		nodes.put(klass, node);
	}
}