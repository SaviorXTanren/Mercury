package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.*;
import com.radirius.mercury.graphics.wip.gui.themes.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.misc.Updatable;

import java.util.ArrayList;

/**
 * This is the base to a GUI system meant to minimally mimic how HTML works. There are many different settings that all
 * components share. ANY of these can be changed, but to varying effect. Components can store other 'children'
 * components inside of them, like the structure in an XML document would allow for. ActionCheck also allows for
 * functionality to be built into the components, much like javascript in HTML, only more embed. There is one central
 * rendering method that will draw every single component, allowing for extreme flexibility. Unlike swing or other GUI
 * frameworks, this one emulates XML scripting. Therefor, the renderer is dynamic, relying only on the options of the
 * given component.
 *
 * @author wessles
 */
public class Component implements Updatable {
	/**
	 * The color of the main message.
	 */
	public Color textColor = Color.DEFAULT_DRAWING, focusedTextColor, hoveredTextColor, clickedTextColor;
	/**
	 * The font of the main message.
	 */
	public Font font = TrueTypeFont.ROBOTO_REGULAR;

	/**
	 * The different types of backgrounds.
	 */
	public static enum BackgroundType {
		/**
		 * Will display a background of a certain color.
		 */
		coloredBackground,
		/**
		 * Will display a texture as its background.
		 */
		texturedBackground,
		/**
		 * Will display a texture (tinted to the background color) as its background.
		 */
		tintedTexturedBackground
	}

	/**
	 * The type of background.
	 */
	public BackgroundType background = BackgroundType.coloredBackground;
	/**
	 * The color of the background (will also tint texture if the background type is tintedTexturedBackground).
	 */
	public Color backgroundColor = Color.CLEAR, focusedBackgroundColor, hoveredBackgroundColor, clickedBackgroundColor;
	/**
	 * The texture for the background.
	 */
	public Texture backgroundTexture, focusedBackgroundTexture, hoveredBackgroundTexture, clickedBackgroundTexture;

	/**
	 * The different types of borders.
	 */
	public static enum BorderType {
		/**
		 * No border at all
		 */
		noBorder,
		/**
		 * A rectangular border
		 */
		border,
		/**
		 * A smoothed rectangular border
		 */
		smoothBorder
	}

	/**
	 * The type of border.
	 */
	public BorderType border = BorderType.noBorder;
	/**
	 * The thickness of the border.
	 */
	public float borderThickness = 1f;
	/**
	 * The color of the border.
	 */
	public Color borderColor = Color.WHITE, focusedBorderColor, hoveredBorderColor, clickedBorderColor;

	/**
	 * The x position of the component.
	 */
	public float x;
	/**
	 * The y position of the component.
	 */
	public float y;

	/**
	 * Meaning that the size fits its text
	 */
	public static final int fitText = -1;
	/**
	 * The width of the component.
	 */
	public float width = fitText;
	/**
	 * The height of the component.
	 */
	public float height = fitText;

	/**
	 * Spacing.
	 */
	public float marginLeft, marginRight, marginUp, marginDown;

	/**
	 * Shorthand to set all margins to the same value.
	 *
	 * @param margin
	 * 		The new margin for all sides
	 *
	 * @return this component
	 */
	public Component setMargin(float margin) {
		this.marginLeft = margin;
		this.marginRight = margin;
		this.marginUp = margin;
		this.marginDown = margin;

		return this;
	}

	/**
	 * Padding.
	 */
	public float paddingLeft, paddingRight, paddingUp, paddingDown;

	/**
	 * Shorthand to set all padding to the same value.
	 *
	 * @param padding
	 * 		The new padding for all sides
	 *
	 * @return this component
	 */
	public Component setPadding(float padding) {
		this.paddingLeft = padding;
		this.paddingRight = padding;
		this.paddingUp = padding;
		this.paddingDown = padding;

		return this;
	}

	/**
	 * The way the component will sort under its parent.
	 */
	public static enum ComponentType {
		/**
		 * This option will make this component take up a line.
		 */
		div,
		/**
		 * This option will make this component continue horizontally unless it runs out of horizontal space, in which
		 * case it goes to the next line.
		 */
		span
	}

	/**
	 * The way the component will sort under its parent.
	 */
	public ComponentType componentType = ComponentType.span;

	/**
	 * If the component does not support multiple lines of children.
	 */
	public boolean singleLine = false;
	/**
	 * Whether the component can be focused on by clicking.
	 */
	public boolean focusable = false;

	public static Component focusedComponent;

	/**
	 * Called if the focus trains on this component.
	 */
	public void focus() {
		if (focusable) {
			if (focusedComponent != null)
				focusedComponent.unFocus();

			focusedComponent = this;
			onFocus();
		}
	}

	/**
	 * Called if the focus leaves this component.
	 */
	public void unFocus() {
		if (focusable && isFocused()) {
			focusedComponent = null;
			onUnFocus();
		}
	}

	/**
	 * @return if the component is focused.
	 */
	public boolean isFocused() {
		return focusable && this == focusedComponent;
	}

	/**
	 * Child-overwritable method called when the component is put in focus.
	 */
	public void onFocus() {
	}

	/**
	 * Child-overwritable method called when the component is put out of focus.
	 */
	public void onUnFocus() {
	}

	/**
	 * Children of the component. If the object is not a component or string, it will be treated as a string from
	 * *.toString().
	 */
	public ArrayList<Object> children = new ArrayList<>();
	/**
	 * Parent component.
	 */
	public Component parent;

	/**
	 * Adds a component or string as a child to component.
	 *
	 * @param child
	 * 		The child(ren) to add
	 *
	 * @return this component
	 */
	public Component addChild(Object... child) {
		for (Object object : child)
			if (object != null) {
				if (object instanceof Component)
					((Component) object).parent = this;
				children.add(object);
			}

		return this;
	}

	/**
	 * Removes a component or string as a child.
	 *
	 * @param child
	 * 		The child to remove
	 *
	 * @return this component
	 */
	public Component removeChild(Object child) {
		if (child instanceof Component)
			((Component) child).parent = null;
		children.remove(child);

		return this;
	}

	/**
	 * Removes child at index.
	 *
	 * @param index
	 * 		The index of the child to remove
	 *
	 * @return this component
	 */
	public Component removeChild(int index) {
		removeChild(children.get(index));
		return this;
	}

	/**
	 * @param message
	 * 		The content text
	 */
	public Component(String message) {
		this();
		addChild(message);
	}

	public Component() {
		applyTheme(new DefaultTheme());
	}

	/**
	 * Applies a theme to this component only.
	 *
	 * @param theme
	 * 		The theme to apply
	 *
	 * @return this component
	 */
	public Component applyTheme(Theme theme) {
		for (Class<?> klass : theme.getNodes().keySet())
			if (klass.isAssignableFrom(this.getClass()))
				theme.getNodes().get(klass).apply(this);

		return this;
	}

	/**
	 * Applies a theme to this component and its children.
	 *
	 * @param theme
	 * 		The theme to apply
	 *
	 * @return this component
	 */
	public Component applyThemeRecursively(Theme theme) {
		applyTheme(theme);
		for (Object child : children)
			if (child instanceof Component)
				((Component) child).applyThemeRecursively(theme);

		return this;
	}

	/**
	 * Only true if tab has been pressed and not released.
	 */
	private static boolean justTabbed = false;

	@Override
	public void update() {
		// Check action checkers
		for (ActionCheck actionCheck : actionChecks)
			if (actionCheck.isActed())
				actionCheck.act();
			else
				actionCheck.noAct();

		boolean wasClicked = false;

		// Check for generic mouse events
		if (isClicked()) {
			onMouseClick();
			wasClicked = true;
		} else if (isHovered())
			onMouseHover();
		else
			onNoMouseHover();
		if (Input.mouseClicked(Input.MOUSE_LEFT) && !wasClicked && isFocused())
			unFocus();
		else if (wasClicked)
			focus();

		// Tabbing focus
		if (isFocused() && Input.keyClicked(Input.KEY_TAB) && parent != null && !justTabbed) {
			int thisIndex = parent.children.indexOf(this);

			boolean shift = Input.keyDown(Input.KEY_LSHIFT);

			for (int index = thisIndex + (shift ? -1 : 1); shift ? (index > 0) : (index < parent.children.size()); index += (shift ? -1 : 1)) {
				Object candidate = parent.children.get(index);
				if (candidate instanceof Component) {
					Component componentCandidate = (Component) candidate;
					if (componentCandidate.focusable) {
						componentCandidate.focus();
						justTabbed = true;
						break;
					}
				}
			}
		} else if (!Input.keyClicked(Input.KEY_TAB)) {
			justTabbed = false;
		}

		// Update children
		for (Object child : children)
			if (child instanceof Component)
				((Component) child).update();

		/* Update the component geometry */

		// Width
		lastTotalWidth = marginLeft + this.width + marginRight;
		if (this.width == fitText)
			lastTotalWidth = borderThickness + marginLeft + paddingLeft + processChildren().x + paddingRight + marginRight + borderThickness;

		// Height
		lastTotalHeight = marginUp + this.height + marginDown;
		if (this.height == fitText)
			lastTotalHeight = borderThickness + marginUp + paddingUp + processChildren(getTotalWidth() - borderThickness - marginLeft - paddingLeft - paddingRight - marginRight - borderThickness).y + paddingDown + marginDown + borderThickness;
	}

	/**
	 * The last total width calculated in update().
	 */
	private float lastTotalWidth;

	/**
	 * @return the last total width calculated in update().
	 */
	public float getTotalWidth() {
		return lastTotalWidth;
	}

	/**
	 * The last total height calculated in update().
	 */
	private float lastTotalHeight;

	/**
	 * @return the last total height calculated in update().
	 */
	public float getTotalHeight() {
		return lastTotalHeight;
	}

	/**
	 * @return the boundaries of the whole component (including margins).
	 */
	public Rectangle getTotalBounds() {
		return new Rectangle(x, y, getTotalWidth(), getTotalHeight());
	}

	/**
	 * @return the last body width calculated in update().
	 */
	public float getBodyWidth() {
		return getTotalWidth() - borderThickness - marginLeft - marginRight - borderThickness;
	}

	/**
	 * @return the last body height calculated in update().
	 */
	public float getBodyHeight() {
		return getTotalHeight() - borderThickness - marginUp - marginDown - borderThickness;
	}

	/**
	 * @return the boundaries of the component (excluding margins).
	 */
	public Rectangle getBodyBounds() {
		return new Rectangle(x + marginLeft, y + marginUp, getBodyWidth(), getBodyHeight());
	}

	/**
	 * @return the last content width calculated in update().
	 */
	public float getContentWidth() {
		return getBodyWidth() - paddingLeft - paddingRight;
	}

	/**
	 * @return the last content height calculated in update().
	 */
	public float getContentHeight() {
		return getBodyHeight() - paddingUp - paddingDown;
	}

	/**
	 * @return the boundaries of the component's content.
	 */
	public Rectangle getContentBounds() {
		return new Rectangle(x + borderThickness + marginLeft + paddingLeft, y + borderThickness + marginUp + paddingUp, getContentWidth(), getContentHeight());
	}

	/**
	 * @return whether or not the mouse hovers over the body of the component.
	 */
	public boolean isHovered() {
		return getBodyBounds().contains(Input.getGlobalMousePosition());
	}

	/**
	 * @return whether or not the mouse clicked on the body of the component.
	 */
	public boolean isClicked() {
		return isHovered() && Input.mouseClicked(Input.MOUSE_LEFT);
	}

	/**
	 * Called when mouse clicks the body of the component.
	 */
	public void onMouseClick() {
	}

	/**
	 * Called when mouse hovers over the body of the component.
	 */
	public void onMouseHover() {
	}

	/**
	 * Called when mouse does not hover over the body of the component.
	 */
	public void onNoMouseHover() {
	}

	/**
	 * The generic component renderer. It will render every type of component, as well as its children, relying only on
	 * the options of the component(s).
	 *
	 * @param g
	 * 		The graphics object
	 */
	public void render(Graphics g) {
		/* Save the graphics options before they change. */

		g.pushAttributes();

		/* The rendering begins. */

		// Render the border
		Rectangle bodyBounds = getBodyBounds();

		if (isFocused() && focusedBorderColor != null)
			g.setColor(focusedBorderColor);
		else if (isClicked() && clickedBorderColor != null)
			g.setColor(clickedBorderColor);
		else if (isHovered() && hoveredBorderColor != null)
			g.setColor(hoveredBorderColor);
		else
			g.setColor(borderColor);

		if (border == BorderType.smoothBorder) {
			g.setLineWidth(borderThickness * 2);
			g.traceFigure(bodyBounds);
		} else if (border == BorderType.border) {
			g.drawFigure(new Rectangle(x + marginLeft - borderThickness, y + marginUp - borderThickness, getBodyWidth() + borderThickness * 2, getBodyHeight() + borderThickness * 2));
		}

		// Render the background
		Color color;

		if (isFocused() && focusedBackgroundColor != null)
			color = focusedBackgroundColor;
		else if (isClicked() && clickedBackgroundColor != null)
			color = clickedBackgroundColor;
		else if (isHovered() && hoveredBackgroundColor != null)
			color = hoveredBackgroundColor;
		else
			color = backgroundColor;

		if (background == BackgroundType.coloredBackground) {
			g.setColor(color);
			g.drawFigure(bodyBounds);
		} else if (background == BackgroundType.texturedBackground) {
			if (backgroundTexture != null)
				g.drawTexture(backgroundTexture, bodyBounds);
		} else if (background == BackgroundType.tintedTexturedBackground)
			if (backgroundTexture != null)
				g.drawTexture(backgroundTexture, bodyBounds, color);

		// Render content
		processChildren(getContentWidth(), g, x + marginLeft + paddingLeft, y + marginUp + paddingUp);

		/* Done rendering, now time to put the rendering options back where they were. */

		g.popAttributes();
	}

	/**
	 * Processes the children. If g is not null, then it will render them as well. Cutoff width is assumed infinite.
	 *
	 * @return the width / height that all children/sub-children take up
	 */
	protected Vector2f processChildren() {
		return processChildren(Float.MAX_VALUE, null, 0, 0);
	}

	/**
	 * Processes the children. If g is not null, then it will render them as well.
	 *
	 * @param cutoffWidth
	 * 		The cutoff width for when there needs to be a new line
	 *
	 * @return the width / height that all children/sub-children take up
	 */
	protected Vector2f processChildren(float cutoffWidth) {
		return processChildren(cutoffWidth, null, 0, 0);
	}

	/**
	 * Processes the children. If g is not null, then it will render them as well. Cutoff width is assumed infinite.
	 *
	 * @param g
	 * 		A graphics object for rendering the children (only will render if not null)
	 * @param x
	 * 		The starting x
	 * @param y
	 * 		The starting y
	 *
	 * @return the width / height that all children/sub-children take up
	 */
	protected Vector2f processChildren(Graphics g, float x, float y) {
		return processChildren(Float.MAX_VALUE, g, x, y);
	}

	/**
	 * Processes the children. If g is not null, then it will render them as well.
	 *
	 * @param cutoffWidth
	 * 		The cutoff width for when there needs to be a new line
	 * @param g
	 * 		A graphics object for rendering the children (only will render if not null)
	 * @param x
	 * 		The starting x
	 * @param y
	 * 		The starting y
	 *
	 * @return the width / height that all children/sub-children take up
	 */
	protected Vector2f processChildren(float cutoffWidth, Graphics g, float x, float y) {
		float originalX = x;

		float childrenWidth = 0;
		float childrenHeight;

		float maxHeightThisLine = 0;

		Color textColor;

		if (isFocused() && focusedTextColor != null)
			textColor = focusedTextColor;
		else if (isClicked() && clickedTextColor != null)
			textColor = clickedTextColor;
		else if (isHovered() && hoveredTextColor != null)
			textColor = hoveredTextColor;
		else
			textColor = this.textColor;

		childrenLoop:
		for (Object child : children) {
			if (child instanceof Component) {
				Component component = (Component) child;

				float componentWidth = component.getTotalWidth();
				float componentHeight = component.getTotalHeight();

				// If this string will go over the cutoff
				if (x - originalX + componentWidth > cutoffWidth || component.componentType == ComponentType.div) {
					if (x - originalX + componentWidth > cutoffWidth && singleLine)
						break;

					// Go down a line
					x = originalX;
					y += maxHeightThisLine;
					maxHeightThisLine = 0;
				}

				component.x = x;
				component.y = y;

				if (g != null)
					component.render(g);

				x += componentWidth;
				childrenWidth = Math.max(x, childrenWidth);
				maxHeightThisLine = Math.max(componentHeight, maxHeightThisLine);

				// The div takes up a line, so let's take up a line
				if (component.componentType == ComponentType.div && !singleLine) {
					// Go down a line
					x = originalX;
					y += maxHeightThisLine;
					maxHeightThisLine = 0;
				}
			} else {
				String string = child.toString();

				// Go through all lines (adding a -1 will make blank spaces included)
				String[] lines = string.split("\n", -1);
				for (int lnId = 0; lnId < lines.length; lnId++) {
					maxHeightThisLine = Math.max(font.getHeight(), maxHeightThisLine);

					String line = lines[lnId];

					// Go through all spaced segments in the line
					String[] segments = line.split(" ", -1);
					for (int segId = 0; segId < segments.length; segId++) {
						String segment = segments[segId];
						if (segId != segments.length - 1)
							segment += " ";

						if (singleLine) {
							for (char segChar : segment.toCharArray()) {
								float charWidth = font.getWidth(segChar + "");

								if (x - originalX + charWidth > cutoffWidth)
									break childrenLoop;

								if (g != null)
									g.drawString(segChar + "", font, x, y, textColor);

								x += charWidth;
								childrenWidth = Math.max(x, childrenWidth);
							}
						} else {
							float segmentWidth = font.getWidth(segment);

							// If this string will go over the cutoff
							if (x - originalX + segmentWidth > cutoffWidth) {
								// Go down a line
								x = originalX;
								y += maxHeightThisLine;
							}

							if (g != null)
								g.drawString(segment, font, x, y, textColor);

							x += segmentWidth;
							childrenWidth = Math.max(x, childrenWidth);
						}
					}

					if (string.contains("\n") && lnId != lines.length - 1 && !singleLine) {
						x = originalX;
						y += maxHeightThisLine;
						maxHeightThisLine = 0;
					}
				}
			}
		}

		childrenWidth = Math.max(x, childrenWidth);
		childrenHeight = y + maxHeightThisLine;

		return new Vector2f(childrenWidth, childrenHeight);
	}

	/**
	 * List of action checks.
	 */
	public ArrayList<ActionCheck> actionChecks = new ArrayList<>();

	/**
	 * Adds an action check.
	 *
	 * @param actionCheck
	 * 		The check to add
	 *
	 * @return this component
	 */
	public Component addActionCheck(ActionCheck actionCheck) {
		actionCheck.setParent(this);
		actionChecks.add(actionCheck);
		return this;
	}

	/**
	 * An object that waits for a condition and executes an action based on the condition.
	 */
	public static abstract class ActionCheck {
		public Component parent;

		public abstract boolean isActed();

		public abstract void act();

		public abstract void noAct();

		public void setParent(Component parent) {
			this.parent = parent;
		}
	}
}
