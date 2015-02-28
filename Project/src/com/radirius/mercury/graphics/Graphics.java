package com.radirius.mercury.graphics;

import com.radirius.mercury.graphics.font.*;
import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.GraphicsUtils;
import com.radirius.mercury.utilities.misc.*;
import org.lwjgl.opengl.Display;

import java.util.Stack;

import static org.lwjgl.opengl.GL11.*;

/**
 * An object used to simplify rendering for those who do not wish to use the batcher.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public class Graphics implements Initializable, Cleanable {

	@Override
	public void init() {
		batcher = new Batcher();
		batcher.init();

		camera = new Camera(0, 0);

		currentFont = TrueTypeFont.ROBOTO_REGULAR;

		GraphicsUtils.pushMatrix();
		getCamera().updateTransforms();
	}

	/**
	 * Pre rendering code.
	 */
	public void pre() {
		batcher.begin();

		glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
		glClear(GL_COLOR_BUFFER_BIT);
	}

	/**
	 * Post rendering code.
	 */
	public void post() {
		batcher.end();
	}

	private Batcher batcher;

	/**
	 * Returns The batcher being used
	 */
	public Batcher getBatcher() {
		return batcher;
	}

	private Camera camera;

	/**
	 * Returns The camera being used
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets the camera's origin (anchor) point.
	 *
	 * @param origin The new camera anchor
	 */
	public void setCameraOrigin(Vector2f origin) {
		getCamera().setOrigin(origin);
	}

	/**
	 * Returns The camera's origin (anchor) point
	 */
	public Vector2f getCameraOrigin() {
		return getCamera().getOrigin();
	}

	/**
	 * Translates the camera.
	 *
	 * @param x The x position
	 * @param y The y position
	 */
	public void translate(float x, float y) {
		getCamera().translate(x, y);
	}

	/**
	 * Translates the camera by its origin to a point.
	 *
	 * @param x The x position
	 * @param y The y position
	 */
	public void translateTo(float x, float y) {
		getCamera().translateTo(x, y);
	}

	/**
	 * Returns The position of the camera
	 */
	public Vector2f getPosition() {
		return getCamera().getPosition();
	}

	/**
	 * Sets the scale of the camera.
	 *
	 * @param x The x scaling
	 * @param y The y scaling
	 */
	public void setScale(float x, float y) {
		getCamera().setScale(x, y);

		if (!lineWidthChanged)
			lineWidth = 1 / getScale();
	}

	/**
	 * Sets the scale of the camera.
	 *
	 * @param scale The scale
	 */
	public void setScale(float scale) {
		setScale(scale, scale);
	}

	/**
	 * Returns The dimensions of the current camera scaling
	 */
	public Vector2f getScaleDimensions() {
		return getCamera().getScaleDimensions();
	}

	/**
	 * Returns The scaling of the camera
	 */
	public float getScale() {
		return (getScaleDimensions().x + getScaleDimensions().y) / 2;
	}

	/**
	 * Rotates the camera.
	 *
	 * @param rotation The rotation
	 */
	public void rotate(float rotation) {
		getCamera().rotate(rotation);
	}

	/**
	 * Sets the rotation of the camera.
	 *
	 * @param rotation The rotation
	 */
	public void setRotation(float rotation) {
		getCamera().setRotation(rotation);
	}

	/**
	 * Returns The rotation of the camera
	 */
	public float getRotation() {
		return getCamera().getRotation();
	}

	/**
	 * Returns The boundaries of the camera (the window's frame in the game world)
	 */
	public Rectangle getCameraBounds() {
		return getCamera().getBounds();
	}

	private Color backgroundColor = Color.DEFAULT_BACKGROUND;

	/**
	 * Sets the background color.
	 *
	 * @param color The color to switch to
	 */
	public void setBackground(Color color) {
		backgroundColor = color;
	}

	/**
	 * Returns The color of the background
	 */
	public Color getBackground() {
		return backgroundColor;
	}

	private Color currentColor = Color.DEFAULT_DRAWING;

	/**
	 * Sets the drawing color.
	 *
	 * @param r A red value between 0.0 and 1.0
	 * @param g A green value between 0.0 and 1.0
	 * @param b A blue value between 0.0 and 1.0
	 * @param a An alpha value between 0.0 and 1.0
	 */
	public void setColor(float r, float g, float b, float a) {
		setColor(new Color(r, g, b, a));
	}

	/**
	 * Sets the drawing color.
	 *
	 * @param r A red value between 0.0 and 1.0
	 * @param g A green value between 0.0 and 1.0
	 * @param b A blue value between 0.0 and 1.0
	 */
	public void setColor(float r, float g, float b) {
		setColor(r, g, b, 1f);
	}

	/**
	 * Sets the drawing color.
	 *
	 * @param color The new drawing color
	 */
	public void setColor(Color color) {
		currentColor = color;
	}

	/**
	 * Returns The current drawing color
	 */
	public Color getColor() {
		return currentColor;
	}

	private Font currentFont;

	/**
	 * Sets the current font.
	 *
	 * @param font The font to switch to
	 */
	public void setFont(Font font) {
		currentFont = font;
	}

	/**
	 * Returns The current font
	 */
	public Font getFont() {
		return currentFont;
	}

	/**
	 * A tiny class to store the current color and font.
	 */
	private class GraphicsAttributes {
		public Color color;
		public Font font;
		public float lineWidth;
		public boolean smoothJoints;

		public GraphicsAttributes(Color color, Font font, float lineWidth, boolean smoothJoints) {
			this.color = color;
			this.font = font;
			this.lineWidth = lineWidth;
			this.smoothJoints = smoothJoints;
		}
	}

	/**
	 * A stack of stored color and font attributes.
	 */
	private Stack<GraphicsAttributes> attributeStack = new Stack<>();

	public void pushAttributes() {
		// Copy current attributes to stack
		attributeStack.push(new GraphicsAttributes(getColor(), getFont(), getLineWidth(), isSmoothJoints()));
	}

	public void popAttributes() {
		GraphicsAttributes popped = attributeStack.pop();
		setColor(popped.color);
		setFont(popped.font);
		setLineWidth(popped.lineWidth);
		setSmoothJoints(popped.smoothJoints);
	}

	public void defaultAttributes() {
		setColor(Color.DEFAULT_DRAWING);
		setFont(TrueTypeFont.ROBOTO_REGULAR);
		this.lineWidth = 1f;
		this.smoothJoints = true;
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawString(String message, float x, float y) {
		drawString(message, currentFont, x, y);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawString(String message, float x, float y, Color color) {
		drawString(message, currentFont, x, y, color);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawString(String message, Font font, float x, float y) {
		drawString(message, 1, font, x, y);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawString(String message, Font font, float x, float y, Color color) {
		drawString(message, 1, font, x, y, color);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawString(String message, float scale, float x, float y) {
		drawString(message, scale, currentFont, x, y);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawString(String message, float scale, float x, float y, Color color) {
		drawString(message, scale, currentFont, x, y, color);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawString(String message, float scale, Font font, float x, float y) {
		drawString(message, scale, font, x, y, currentColor);
	}

	/**
	 * Draws a string.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawString(String message, float scale, Font font, float x, float y, Color color) {
		Font oldFont = getFont();

		float xCurrent = 0;

		for (int i = 0; i < message.toCharArray().length; i++) {
			if (message.toCharArray()[i] == '\n') {
				y += font.getHeight() * scale;

				xCurrent = 0;

				continue;
			}

			SubTexture subTexture = font.getFontSpriteSheet().getTexture(message.toCharArray()[i]);

			drawTexture(subTexture, new Rectangle(x + xCurrent, y, subTexture.getWidth() * scale, subTexture.getHeight() * scale), color);

			xCurrent += subTexture.getWidth() * scale;
		}

		setFont(oldFont);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawCenteredString(String message, float x, float y) {
		drawCenteredString(message, currentFont, x, y);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawCenteredString(String message, float x, float y, Color color) {
		drawCenteredString(message, currentFont, x, y, color);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawCenteredString(String message, Font font, float x, float y) {
		drawCenteredString(message, 1, font, x, y);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawCenteredString(String message, Font font, float x, float y, Color color) {
		drawCenteredString(message, 1, font, x, y, color);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawCenteredString(String message, float scale, float x, float y) {
		drawCenteredString(message, scale, currentFont, x, y);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawCenteredString(String message, float scale, float x, float y, Color color) {
		drawCenteredString(message, scale, currentFont, x, y, color);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawCenteredString(String message, float scale, Font font, float x, float y) {
		drawCenteredString(message, scale, font, x, y, currentColor);
	}

	/**
	 * Draws a string centered at x and y.
	 *
	 * @param message The string to draw
	 * @param scale   The scale of the string
	 * @param font    The font in which to draw the string
	 * @param x       The x position
	 * @param y       The y position
	 * @param color   The color of the text
	 */
	public void drawCenteredString(String message, float scale, Font font, float x, float y, Color color) {
		if (font instanceof TrueTypeFont) {
			float height = font.getHeight() * scale;

			String[] lines = message.split("\n");
			for (String line : lines) {
				float width = font.getWidth(line) * scale;
				drawString(line, scale, font, x - width / 2, y - height / 2, color);
				y += height;
			}
		}
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param x       The x position
	 * @param y       The y position
	 */
	public void drawTexture(Texture texture, float x, float y) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param x       The x position
	 * @param y       The y position
	 * @param w       The width
	 * @param h       The height
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param sx1     The near source x coordinate
	 * @param sy1     The near source y coordinate
	 * @param sx2     The far source x coordinate
	 * @param sy2     The far source y coordinate
	 * @param x       The x position
	 * @param y       The y position
	 * @param w       The width
	 * @param h       The height
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param region  The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Shape region) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region);
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture      The texture to draw
	 * @param sourceRegion The region of the texture to draw
	 * @param region       The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Shape sourceRegion, Shape region) {
		batcher.drawTexture(texture, sourceRegion, region);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param x       The x position
	 * @param y       The y position
	 * @param tint    The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, Color tint) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight(), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param x       The x position
	 * @param y       The y position
	 * @param w       The width
	 * @param h       The height
	 * @param tint    The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param sx1     The near source x coordinate
	 * @param sy1     The near source y coordinate
	 * @param sx2     The far source x coordinate
	 * @param sy2     The far source y coordinate
	 * @param x       The x position
	 * @param y       The y position
	 * @param w       The width
	 * @param h       The height
	 * @param tint    The color to tint the texture
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param region  The shape to draw the texture to
	 * @param tint    The color to tint the texture
	 */
	public void drawTexture(Texture texture, Shape region, Color tint) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region, tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture      The texture to draw
	 * @param sourceRegion The region of the texture to draw
	 * @param region       The shape to draw the texture to
	 * @param tint         The color to tint the texture
	 */
	public void drawTexture(Texture texture, Shape sourceRegion, Shape region, Color tint) {
		batcher.drawTexture(texture, sourceRegion, region, tint);
	}

	/**
	 * Draws a polygonal shape.
	 *
	 * @param polygon Shape to draw
	 */
	public void drawShape(Polygon... polygon) {
		batcher.clearTextures();

		for (Polygon polygon0 : polygon) {
			Vector2f[] vertices = polygon0.getVertices();

			if (polygon0 instanceof Triangle) {
				batcher.flushOnOverflow(3);

				batcher.vertex(vertices[0].x, vertices[0].y, currentColor, 0, 0);
				batcher.vertex(vertices[1].x, vertices[1].y, currentColor, 0, 0);
				batcher.vertex(vertices[2].x, vertices[2].y, currentColor, 0, 0);

				continue;
			} else if (polygon0 instanceof Rectangle) {
				drawFunctionlessRectangle((Rectangle) polygon0);

				continue;
			}

			// # of sides == # of vertices
			// 3 == number of vertices in triangle
			// 3 * # of vertices = number of vertices we need.
			batcher.flushOnOverflow(3 * vertices.length);

			for (int c = 0; c < vertices.length; c++) {
				batcher.vertex(polygon0.getCenter().x, polygon0.getCenter().y, currentColor, 0, 0);

				if (c >= vertices.length - 1)
					batcher.vertex(vertices[0].x, vertices[0].y, currentColor, 0, 0);
				else
					batcher.vertex(vertices[c].x, vertices[c].y, currentColor, 0, 0);

				if (c >= vertices.length - 1)
					batcher.vertex(vertices[vertices.length - 1].x, vertices[vertices.length - 1].y, currentColor, 0, 0);
				else
					batcher.vertex(vertices[c + 1].x, vertices[c + 1].y, currentColor, 0, 0);
			}
		}
	}

	/**
	 * Outlines a polygonal shape.
	 *
	 * @param polygon Shape to trace
	 */
	public void traceShape(Polygon... polygon) {
		batcher.clearTextures();

		for (Polygon polygon0 : polygon) {
			batcher.flushOnOverflow(polygon0.getVertices().length * 6);

			Vector2f[] vs = polygon0.getVertices();

			for (int c = 0; c < vs.length; c++) {
				Vector2f p1, p2;

				if (c >= vs.length - 1)
					p1 = vs[0];
				else
					p1 = vs[c];

				if (c >= vs.length - 1)
					p2 = vs[vs.length - 1];
				else
					p2 = vs[c + 1];

				drawFunctionlessLine(new Line(p1, p2));
			}
		}
	}

	/**
	 * Draws a rectangle.
	 *
	 * @param rectangle The rectangle to draw
	 */
	public void drawRectangle(Rectangle... rectangle) {
		drawShape(rectangle);
	}

	/**
	 * Draws a rectangle.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public void drawRectangle(float x, float y, float w, float h) {
		drawRectangle(new Rectangle(x, y, w, h));
	}

	/**
	 * Outlines a rectangle.
	 *
	 * @param rectangle The rectangle to trace
	 */
	public void traceRectangle(Rectangle... rectangle) {
		traceShape(rectangle);
	}

	/**
	 * Traces a rectangle.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public void traceRectangle(float x, float y, float w, float h) {
		traceRectangle(new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a point.
	 *
	 * @param x The x position
	 * @param y The y position
	 */
	public void drawPoint(float x, float y) {
		drawPoint(new Point(x, y));
	}

	/**
	 * Draws a point.
	 *
	 * @param point The point to draw
	 */
	public void drawPoint(Point... point) {
		for (Point point0 : point) {
			float x = point0.getX();
			float y = point0.getY();

			drawFunctionlessRectangle(new Rectangle(x, y, x + 1, y, x + 1, y + 1, x, y + 1));
		}
	}

	private void drawFunctionlessRectangle(Rectangle... rectangle) {
		for (Rectangle rectangle0 : rectangle) {
			float x1 = rectangle0.getVertices()[0].x, x2 = rectangle0.getVertices()[1].x, x3 = rectangle0.getVertices()[2].x, x4 = rectangle0.getVertices()[3].x;

			float y1 = rectangle0.getVertices()[0].y, y2 = rectangle0.getVertices()[1].y, y3 = rectangle0.getVertices()[2].y, y4 = rectangle0.getVertices()[3].y;

			batcher.flushOnOverflow(6);

			batcher.vertex(x1, y1, currentColor, 0, 0);
			batcher.vertex(x2, y2, currentColor, 0, 0);
			batcher.vertex(x4, y4, currentColor, 0, 0);

			batcher.vertex(x3, y3, currentColor, 0, 0);
			batcher.vertex(x4, y4, currentColor, 0, 0);
			batcher.vertex(x2, y2, currentColor, 0, 0);
		}
	}

	/**
	 * Draws a line.
	 *
	 * @param x1 The first point's x
	 * @param y1 The first point's y
	 * @param x2 The last point's x
	 * @param y2 The last point's y
	 */
	public void drawLine(float x1, float y1, float x2, float y2) {
		drawLine(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}

	/**
	 * Draws a line.
	 *
	 * @param p1 The first point on the line
	 * @param p2 The last point on the line
	 */
	public void drawLine(Vector2f p1, Vector2f p2) {
		drawLine(new Line(p1, p2));
	}

	/**
	 * Draws a line.
	 *
	 * @param line The line to draw
	 */
	public void drawLine(Line... line) {
		batcher.clearTextures();

		for (Line line0 : line) {
			batcher.flushOnOverflow(6);

			drawFunctionlessLine(line0);
		}
	}

	/**
	 * Draws an animation.
	 *
	 * @param animation The animation to draw
	 * @param region    The region to draw the animation to.
	 */
	public void drawAnimation(Animation animation, Shape region) {
		drawTexture(animation.getCurrentFrame(), region);
	}

	/**
	 * Draws an animation.
	 *
	 * @param animation    The animation to draw
	 * @param sourceRegion The source region of the animation to draw.
	 * @param region       The region to draw the animation to.
	 */
	public void drawAnimation(Animation animation, Shape sourceRegion, Shape region) {
		drawTexture(animation.getCurrentFrame(), sourceRegion, region);
	}

	public void drawAnimation(Animation animation, float x, float y, float w, float h) {
		drawAnimation(animation, new Rectangle(x, y, w, h));
	}

	public void drawAnimation(Animation animation, float x, float y) {
		drawAnimation(animation, x, y, animation.getCurrentFrame().getWidth(), animation.getCurrentFrame().getHeight());
	}

	private float lineWidth = 1;
	private boolean lineWidthChanged = false;
	private boolean smoothJoints = true;

	/**
	 * Sets the line width.
	 *
	 * @param width The width in pixels of the line
	 */
	public void setLineWidth(float width) {
		lineWidth = width;
		lineWidthChanged = true;
	}

	/**
	 * Returns the current line width.
	 */
	public float getLineWidth() {
		return lineWidth;
	}

	/**
	 * Sets whether the first joints on lines will smoothed.
	 *
	 * @param smoothJoints Whether the first joints on lines will smooth
	 */
	public void setSmoothJoints(boolean smoothJoints) {
		this.smoothJoints = smoothJoints;
	}

	/**
	 * Returns whether the first joints on lines will smooth
	 */
	public boolean isSmoothJoints() {
		return smoothJoints;
	}

	/**
	 * A method that renders a line without clearing textures.
	 *
	 * @param line The line to render
	 */
	private void drawFunctionlessLine(Line line) {
		float dx = line.getVertices()[0].x - line.getVertices()[1].x;
		float dy = line.getVertices()[0].y - line.getVertices()[1].y;
		float angle = (float) (Math.atan2(dy, dx) - Math.PI / 2);

		Vector2f p1 = new Vector2f(line.getVertices()[0].x - (float) Math.cos(angle) * lineWidth / 2, line.getVertices()[0].y - (float) Math.sin(angle) * lineWidth / 2);
		Vector2f p2 = new Vector2f(line.getVertices()[0].x + (float) Math.cos(angle) * lineWidth / 2, line.getVertices()[0].y + (float) Math.sin(angle) * lineWidth / 2);
		Vector2f p3 = new Vector2f(line.getVertices()[1].x + (float) Math.cos(angle) * lineWidth / 2, line.getVertices()[1].y + (float) Math.sin(angle) * lineWidth / 2);
		Vector2f p4 = new Vector2f(line.getVertices()[1].x - (float) Math.cos(angle) * lineWidth / 2, line.getVertices()[1].y - (float) Math.sin(angle) * lineWidth / 2);

		drawFunctionlessRectangle(new Rectangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y));

		if (smoothJoints) {
			drawShape(new Polygon(line.getVertices()[0].x, line.getVertices()[0].y, lineWidth / 2, 8));
			drawShape(new Polygon(line.getVertices()[1].x, line.getVertices()[1].y, lineWidth / 2, 8));
		}
	}

	@Override
	public void cleanup() {
		batcher.cleanup();
	}

	/**
	 * Initializes graphics & handle OpenGL initialization calls.
	 */
	public static Graphics initGraphics() {
		GraphicsUtils.getProjectionMatrix().initOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		Graphics graphicsObject = new Graphics();

		Shader.loadDefaultShaders();

		return graphicsObject;
	}
}
