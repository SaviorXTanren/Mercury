package com.radirius.mercury.graphics.wip.gui;

import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.*;
import com.radirius.mercury.input.Input;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.logging.Logger;
import com.radirius.mercury.utilities.misc.Updatable;

import java.util.ArrayList;

/**
 * This is the base to a GUI system meant to minimally mimick how HTML works. There are many different settings that all
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
	/** The main message displayed above the children components. */
	public String message;
	/** The color of the main message. */
	public Color textColor = Color.DEFAULT_DRAWING;
	/** The font of the main message. */
	public Font font = TrueTypeFont.ROBOTO_REGULAR;

	/** The different types of backgrounds. */
	public static enum BackgroundType {
		/** Will display no background. */
		noBackground,
		/** Will display a background of a certain color. */
		coloredBackground,
		/** Will display a texture as its background. */
		texturedBackground,
		/** Will display a texture (tinted to the background color) as its background. */
		tintedTexturedBackground
	}

	/** The type of background. */
	public BackgroundType background = BackgroundType.noBackground;
	/** The color of the background (will also tint texture if the background type is tintedTexturedBackground). */
	public Color backgroundColor = Color.DEFAULT_TEXTURE;
	/** The texture for the background. */
	public Texture backgroundTexture;

	/** The different types of borders. */
	public static enum BorderType {
		/** No border at all */
		noBorder,
		/** A rectangular border */
		border,
		/** A smoothed rectangular border */
		smoothBorder
	}

	/** The type of border. */
	public BorderType border = BorderType.noBorder;
	/** The thickness of the border. */
	public float borderThickness = 1f;
	/** The color of the border. */
	public Color borderColor = Color.WHITE;

	/** Meaning that the size fits its text */
	public static final int fitText = -1;
	/** The width of the component. */
	public float width = fitText;
	/** The height of the component. */
	public float height = fitText;

	/** Spacing. */
	public float marginLeft, marginRight, marginUp, marginDown;
	/** Padding. */
	public float paddingLeft, paddingRight, paddingUp, paddingDown;

	/** The way the component will sort under its parent. */
	public static enum ComponentType {
		/** This option will make this component take up a line. */
		div,
		/**
		 * This option will make this component continue horizontally unless it runs out of horizontal space, in which
		 * case it goes to the next line.
		 */
		span
	}

	/** The way the component will sort under its parent. */
	public ComponentType componentType = ComponentType.div;

	/** Children components. */
	public ArrayList<Component> children = new ArrayList<>();
	/** Parent component. */
	public Component parent;

	/**
	 * Makes a new component.
	 *
	 * @param message The content text
	 */
	public Component(String message) {
		this.message = message;
	}

	/**
	 * Shorthand to set all margins to the same value.
	 *
	 * @param margin The new margin for all sides
	 */
	public void setMargin(float margin) {
		this.marginLeft = margin;
		this.marginRight = margin;
		this.marginUp = margin;
		this.marginDown = margin;
	}

	/**
	 * Shorthand to set all padding to the same value.
	 *
	 * @param padding The new padding for all sides
	 */
	public void setPadding(float padding) {
		this.paddingLeft = padding;
		this.paddingRight = padding;
		this.paddingUp = padding;
		this.paddingDown = padding;
	}

	/**
	 * Adds child to component.
	 *
	 * @param component The child to add
	 */
	public void addChild(Component... component) {
		for (Component newChild : component)
			children.add(newChild);
	}

	/**
	 * Removes child.
	 *
	 * @param component The child to remove
	 */
	public void removeChild(Component component) {
		children.remove(component);
	}

	/**
	 * Removes child at index.
	 *
	 * @param index The index of the child to remove
	 */
	public void removeChild(int index) {
		children.remove(index);
	}


	@Override
	public void update() {
		// Check action checkers
		for (ActionCheck actionCheck : actionChecks)
			if (actionCheck.isActed())
				actionCheck.act();
			else
				actionCheck.noAct();

		// Check for generic mouse events
		if (isClicked())
			onMouseClick();
		if (isHovered())
			onMouseHover();
		else
			onNoMouseHover();

		// Update children
		for (Component child : children)
			child.update();
	}


	/**
	 * Returns whether or not the mouse hovers over the body of the component.
	 */
	public boolean isHovered() {
		if (getBodyBounds().contains(Input.getGlobalMousePosition()))
			return true;
		else
			return false;
	}

	/**
	 * Returns whether or not the mouse clicked on the body of the component.
	 */
	public boolean isClicked() {
		if (getBodyBounds().contains(Input.getGlobalMousePosition()) && Input.mouseClicked(Input.MOUSE_LEFT))
			return true;
		else
			return false;
	}

	/** Child-overwritable method called when mouse clicks the body of the component. */
	public void onMouseClick() {
	}

	/** Child-overwritable method called when mouse hovers over the body of the component. */
	public void onMouseHover() {
	}

	/** Child-overwritable method called when mouse does not hover over the body of the component. */
	public void onNoMouseHover() {
	}

	/**
	 * The generic component renderer. It will render every type of component, as well as its children, relying only on
	 * the options of the component(s).
	 *
	 * @param g The graphics object.
	 * @param x The x position of the component
	 * @param y The y position of the component
	 */
	public void render(Graphics g, float x, float y) {
		/* Save the graphics options before they change. */

		g.pushAttributes();


		/* Get all data about the component beforehand. */

		float totalWidth = getTotalWidth();
		float bodyWidth = totalWidth - marginLeft - marginRight;
		float contentWidth = bodyWidth - paddingLeft - paddingRight;

		String message = this.width == fitText ? this.message : fit(contentWidth, this.message);

		float totalHeight = getTotalHeight(message);
		float bodyHeight = totalHeight - marginUp - marginDown;
		float contentHeight = bodyHeight - paddingUp - paddingDown;
		float messageHeight = font.getHeight(message);
		float childrenHeight = contentHeight - messageHeight;


		/* The rendering begins. */

		// Opening margins
		x += marginLeft;
		y += marginUp;

		// Render the border
		bodyBounds = new Rectangle(x, y, bodyWidth, bodyHeight);
		if (border == BorderType.smoothBorder) {
			g.setColor(borderColor);
			g.setLineWidth(borderThickness * 2);
			g.traceShape(bodyBounds);
		} else if (border == BorderType.border) {
			g.setColor(borderColor);
			g.drawShape(new Rectangle(x - borderThickness, x - borderThickness, bodyWidth + borderThickness * 2, bodyHeight + borderThickness * 2));
		}

		// Render the background
		if (background == BackgroundType.coloredBackground) {
			g.setColor(backgroundColor);
			g.drawShape(bodyBounds);
		} else if (background == BackgroundType.texturedBackground) {
			if (backgroundTexture != null)
				g.drawTexture(backgroundTexture, bodyBounds);
		} else if (background == BackgroundType.tintedTexturedBackground)
			if (backgroundTexture != null)
				g.drawTexture(backgroundTexture, bodyBounds, backgroundColor);

		// Opening padding
		x += paddingLeft;
		y += paddingUp;

		// Render message & move to components
		g.setColor(textColor);
		g.setFont(font);
		g.drawString(message, x, y);
		y += messageHeight;

		// Render components and move to closing
		float cx = 0, cy = 0;
		float maxHeightThisLine = 0;
		for (Component component : children) {
			float componentWidth = component.getTotalWidth();
			String componentMessage = component.fit(componentWidth, component.message);
			float componentHeight = component.getTotalHeight(componentMessage);

			// If this string will go over the cutoff
			if (cx + componentWidth > contentWidth || component.componentType == ComponentType.div) {
				// Go down a line
				cx = 0;
				cy += maxHeightThisLine;
				maxHeightThisLine = 0;
			}

			component.render(g, cx + x, cy + y);

			cx += componentWidth;
			maxHeightThisLine = Math.max(componentHeight, maxHeightThisLine);
		}

		x += contentWidth;
		y += childrenHeight;

		// Closing padding
		x += paddingRight;
		y += paddingDown;

		// Closing margins
		x += marginRight;
		y += marginDown;


		/* Done rendering, now time to put the rendering options back where they were. */

		g.popAttributes();
	}

	protected String fit(float cutoffWidth, String message) {
		float x = 0;

		String[] segments = message.split(" ");
		String fitted = "";

		for (String segment : segments) {
			segment += " ";
			float segmentWidth = font.getWidth(segment);

			// If this string will go over the cutoff
			if (x + segmentWidth > cutoffWidth) {
				// Go down a line
				x = 0;
				segment = "\n" + segment;
			}

			fitted += segment;
			x += segmentWidth;
		}

		return fitted;
	}

	protected float getTotalWidth() {
		float width = this.width;

		if (this.width == fitText)
			width = marginLeft + paddingLeft + Math.max(font.getWidth(message), getChildrenSize().x) + paddingRight + marginRight;

		return width;
	}

	protected float getTotalHeight() {
		float width = getTotalWidth();
		String message = fit(width - marginLeft - paddingLeft - paddingRight - marginRight, this.message);

		float height = this.height;

		if (this.height == fitText) {
			float plainMessageWidth = font.getWidth(message);
			Vector2f childrenSize = getChildrenSize(plainMessageWidth);
			height = marginUp + paddingUp + font.getHeight(message) + childrenSize.y + paddingDown + marginDown;
		}

		return height;
	}

	protected float getTotalHeight(String message) {
		float height = this.height;

		if (this.height == fitText) {
			float plainMessageWidth = font.getWidth(message);
			Vector2f childrenSize = getChildrenSize(plainMessageWidth);
			height = marginUp + paddingUp + font.getHeight(message) + childrenSize.y + paddingDown + marginDown;
		}

		return height;
	}

	protected Vector2f getChildrenSize() {
		return getChildrenSize(Float.MAX_VALUE);
	}

	protected Vector2f getChildrenSize(float cutoffWidth) {
		float childrenWidth = 0;
		float childrenHeight;

		float x = 0, y = 0;
		float maxHeightThisLine = 0;

		for (Component component : children) {
			float componentWidth = component.getTotalWidth();
			String componentMessage = component.fit(componentWidth, component.message);
			float componentHeight = component.getTotalHeight(componentMessage);

			// If this string will go over the cutoff
			if (x + componentWidth > cutoffWidth || component.componentType == ComponentType.div) {
				// Go down a line
				x = 0;
				y += maxHeightThisLine;
				maxHeightThisLine = 0;
			}

			x += componentWidth;
			childrenWidth = Math.max(x, childrenWidth);
			maxHeightThisLine = Math.max(componentHeight, maxHeightThisLine);
		}
		childrenWidth = Math.max(x, childrenWidth);
		childrenHeight = y+maxHeightThisLine;

		return new Vector2f(childrenWidth, childrenHeight);
	}

	private Rectangle bodyBounds = new Rectangle(0, 0, 0, 0);


	public Rectangle getBodyBounds() {
		return bodyBounds;
	}

	public ArrayList<ActionCheck> actionChecks = new ArrayList<>();

	public Component addActionCheck(ActionCheck actionCheck) {
		actionCheck.setParent(this);
		actionChecks.add(actionCheck);
		return this;
	}

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
