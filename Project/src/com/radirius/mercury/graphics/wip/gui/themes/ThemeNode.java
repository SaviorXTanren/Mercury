package com.radirius.mercury.graphics.wip.gui.themes;

import com.radirius.mercury.graphics.wip.gui.Component;

/**
 * A node of a theme. A theme node is a set of attributes to be applied to a specific type of component.
 *
 * @author wessles
 */
public abstract class ThemeNode {
	public abstract void apply(Component component);
}
