package com.radirius.mercury.graphics;

import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.math.geometry.*;

/**
 * An abstraction for all graphics.
 *
 * @author wessles, Jeviny, Sri Harsha Chilakapati
 */
public interface Graphics {
	/**
	 * Initializes the graphics object.
	 */
	public void init();

	/**
	 * Prepares the graphics object for rendering.
	 */
	public void pre();

	/**
	 * Cleans up the graphics object at the end of rendering.
	 */
	public void post();

	/**
	 * Sets the line width.
	 */
	public void setLineWidth(float width);

	/**
	 * Scales the matrix.
	 *
	 * @param x The scale factor of x
	 * @param y The scale factor of y
	 */
	public void scale(float x, float y);

	/**
	 * The last scale that was set in the graphics object's x and y.
	 */
	public Vector2f getScaleDimensions();

	/**
	 * @return The average last scale that was set in the graphics object's x and y.
	 */
	public float getScale();

	/**
	 * Scales the matrix.
	 */
	public void scale(float factor);

	public void rotate(float rz);

	public float getRotation();

	public void translate(float x, float y);

	/**
	 * @return The last set font.
	 */
	public Font getFont();

	/**
	 * Sets the font.
	 */
	public void setFont(Font font);

	/**
	 * @return The background color.
	 */
	public Color getBackground();

	/**
	 * Sets the background color.
	 */
	public void setBackground(Color color);

	/**
	 * Sets the color based on RGBA values.
	 */
	public void setColor(float r, float g, float b, float a);

	/**
	 * Sets the color based on RGB values.
	 */
	public void setColor(float r, float g, float b);

	/**
	 * @return The last set color.
	 */
	public Color getColor();

	/**
	 * Sets the color.
	 */
	public void setColor(Color color);

	/**
	 * Uses a shader.
	 */
	public void useShader(Shader shader);

	/**
	 * Releases shaders.
	 */
	public void releaseShaders();

	/**
	 * @return The batcher.
	 */
	public Batcher getBatcher();

	/**
	 * Pushes raw vertices to the batcher.
	 */
	public void drawRawVertices(VAOBatcher.VertexData... verts);

	/**
	 * Pushes data in batcher to OpenGL.
	 */
	public void flush();

	/**
	 * Draws message at x and y.
	 */
	public void drawString(String message, float x, float y);

	/**
	 * Draws message at x and y with font.
	 */
	public void drawString(String message, Font font, float x, float y);

	/**
	 * Draws message at x and y.
	 */
	public void drawString(String message, float scale, float x, float y);

	/**
	 * Draws message at x and y with font.
	 */
	public void drawString(String message, float scale, Font font, float x, float y);

	/**
	 * Draws a Texture texture at x and y.
	 */
	public void drawTexture(Texture texture, float x, float y);

	/**
	 * Draws a Texture texture at x and y at w and h size.
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h);

	/**
	 * Draws a Texture texture at x and y at w and h size, rotated rot degrees by the local origin (0, 0).
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation);

	/**
	 * Draws a Texture texture at x and y at w and h size, rotated rot degrees by the local origin (localOriginX,
	 * localOriginY).
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rot, float localOriginX, float localOriginY);

	/**
	 * Draws a portion of the texture at x and y.
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y);

	/**
	 * Draws a portion of the texture at x1 and y1, to x2 and y2.
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h);

	/**
	 * Draws a Texture texture to the Shape region.
	 */
	public void drawTexture(Texture texture, Shape region);

	/**
	 * Draws a portion of a texture to the Shape region.
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region);

	/**
	 * Draws the portion sourceRegion of a texture to the Shape region
	 */
	public void drawTexture(Texture texture, Shape sourceregion, Shape region);

	/**
	 * Draws a Texture texture at x and y with a tint
	 */
	public void drawTexture(Texture texture, float x, float y, Color tint);

	/**
	 * Draws a Texture texture at x and y at w and h size with a tint
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint);

	/**
	 * Draws a Texture texture at x and y at w and h size, rotated rot degrees by the local origin (0, 0) with a tint
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, Color tint);

	/**
	 * Draws a Texture texture at x and y at w and h size, rotated rot degrees by the local origin (localOriginX,
	 * localOriginY) with a tint
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, float localOriginX, float localOriginY, Color tint);

	/**
	 * Draws a portion of the texture at x and y with a tint
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, Color tint);

	/**
	 * Draws a portion of the texture at x1 and y1, to x2 and y2 with a tint
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint);

	/**
	 * Draws a Texture texture to the Shape region with a tint
	 */
	public void drawTexture(Texture texture, Shape region, Color tint);

	/**
	 * Draws a portion of a texture to the Shape region with a tint
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region, Color tint);

	/**
	 * Draws the portion sourceRegion of a texture to the Shape region with a tint
	 */
	public void drawTexture(Texture texture, Shape sourceregion, Shape region, Color tint);

	/**
	 * Draws an animation taking in the x and y coordinates.
	 *
	 * @param animation The animation to draw.
	 * @param x         The x position.
	 * @param y         The y position.
	 */
	public void drawAnimation(Animation animation, float x, float y);

	/**
	 * Draws an animation taking in the x and y coordinates, plus the width w and height h.
	 *
	 * @param animation The animation to draw.
	 * @param x         The x position.
	 * @param y         The y position.
	 * @param w         The width.
	 * @param h         The height.
	 */
	public void drawAnimation(Animation animation, float x, float y, float w, float h);

	/**
	 * Draws an animation taking in the boundaries.
	 *
	 * @param animation The animation to draw.
	 * @param region    The animation's boundaries.
	 */
	public void drawAnimation(Animation animation, Shape region);

	/**
	 * Draws an animation taking in the boundaries.
	 *
	 * @param animation    The animation to draw.
	 * @param sourceRegion The source coordinates of the animation.
	 * @param region       The animation's boundaries.
	 */
	public void drawAnimation(Animation animation, Shape sourceRegion, Shape region);

	/**
	 * Draws a shape from a polygon.
	 */
	public void drawShape(Polygon... polygon);

	/**
	 * Traces a shape from a polygon.
	 */
	public void traceShape(Polygon... polygon);

	/**
	 * Draws a rectangle from given rectangle boundaries.
	 */
	public void drawRectangle(Rectangle... rectangle);

	/**
	 * Draws a rectangle from given coordinates.
	 */
	public void drawRectangle(float x, float y, float w, float h);

	/**
	 * Traces a rectangle from given rectangle boundaries.
	 */
	public void traceRectangle(Rectangle... rectangle);

	/**
	 * Traces a rectangle from given coordinates.
	 */
	public void traceRectangle(float x, float y, float w, float h);

	/**
	 * Draws a line from x1, y1 to x2, y2.
	 */
	public void drawLine(float x1, float y1, float x2, float y2);

	/**
	 * Draws a line from p1 to p2.
	 */
	public void drawLine(Vector2f p1, Vector2f p2);

	/**
	 * Draws a line.
	 */
	public void drawLine(Line... l);

	/**
	 * Draws a point.
	 */
	public void drawPoint(Point... point);

	/**
	 * Makes and draws a point.
	 */
	public void drawPoint(float x, float y);

	/**
	 * Cleanup any used resources like OpenGL buffers
	 */
	public void cleanup();
}
