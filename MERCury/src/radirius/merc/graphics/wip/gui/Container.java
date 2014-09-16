package radirius.merc.graphics.wip.gui;

import java.util.ArrayList;

/**
 * @authors wessles, Jeviny
 */
public interface Container {
	/** Adds a child Component to the Container. */
	public void addChild(Component... child);

	/** Adds a new line. */
	public void addNewLine();

	/**
	 * @return A list of all child components belonging to the parent container.
	 */
	public ArrayList<Component> getChildren();
}
