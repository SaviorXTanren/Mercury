//package com.radirius.mercury.graphics.wip.gui;
//
//import com.radirius.mercury.graphics.Graphics;
//import com.radirius.mercury.math.geometry.Rectangle;
//
//import java.util.ArrayList;
//
//public class Panel extends Component {
//	public ArrayList<Component> childComponents = new ArrayList<>();
//
//	public Panel(Rectangle boundaries) {
//		super(boundaries);
//	}
//
//	public Panel(float x, float y, float width, float height) {
//		super(x, y, width, height);
//	}
//
//	boolean componentsModified = false;
//
//	@Override
//	public void update() {
//		super.update();
//
//		// If the components have changed, then resort.
//		if (componentsModified) {
//			sort();
//			componentsModified = false;
//		}
//
//		// Update the components
//		for (Component child : childComponents)
//			child.update();
//	}
//
//	public void render(Graphics g) {
//		// Render the components
//		for (Component child : childComponents)
//			child.render(g);
//	}
//
//	/**
//	 * Adds a component.
//	 *
//	 * @param component The component to be added.
//	 */
//	public void addComponent(Component component) {
//		childComponents.add(component);
//		componentsModified = true;
//	}
//
//	/**
//	 * Removes a component.
//	 *
//	 * @param component The component to be removed.
//	 */
//	public void removeComponent(Component component) {
//		childComponents.remove(component);
//		componentsModified = true;
//	}
//
//	/**
//	 * Removes a component at an index.
//	 *
//	 * @param index The index of the component to be removed.
//	 */
//	public void removeComponent(int index) {
//		childComponents.remove(index);
//		componentsModified = true;
//	}
//
//	/**
//	 * Sorts out all children components to fit the panel.
//	 */
//	public void sort() {
//		// The cursor
//		float x = 0, y = 0;
//		// Line height
//		float currentLineHeight = 0;
//
//		for (Component child : childComponents) {
//			// Find this line's height. It should be tall enough to fit the height of all components on it.
//			currentLineHeight = Math.max(currentLineHeight, child.boundaries.getTotalHeight());
//
//			// Check if the child overflows the boundaries. If it does, go to the next line.
//			if (x + child.boundaries.getTotalWidth() > boundaries.getTotalWidth()) {
//				x = 0;
//				y += currentLineHeight;
//				currentLineHeight = 0;
//			}
//
//			// Translate the child to its place.
//			child.boundaries.translateTo(boundaries.getX() + x, boundaries.getY() + y);
//
//			// Move the cursor forward.
//			x += child.boundaries.getTotalWidth();
//		}
//	}
//}
