package com.radirius.mercury.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.GraphicsUtils;
import com.radirius.mercury.utilities.logging.Logger;
import com.radirius.mercury.utilities.misc.Cleanable;
import com.radirius.mercury.utilities.misc.Initializable;

/**
 * An OpenGL batcher that manages and pushes vertices.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public class Batcher implements Initializable, Cleanable {

	@Override
	public void init() {
		vd = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * VL);
		cd = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * CL);
		td = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * TL);

		initGlBuffers();
		uploadData();

		active = false;
	}

	public static final int MAX_VERTICES_PER_RENDER_STACK = 4096;
	private static final int VL = 2, CL = 4, TL = 2;
	private FloatBuffer vd, cd, td;

	// OpenGL object handles
	private int vaoID;
	private int vboVertID;
	private int vboColID;
	private int vboTexID;

	/**
	 * Private function to create OpenGL vertex arrays and initialize the VBO
	 * DataStore to the max size of the render stack.
	 */
	private void initGlBuffers() {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		vboVertID = glGenBuffers();
		vboColID = glGenBuffers();
		vboTexID = glGenBuffers();

		glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
		glBufferData(GL_ARRAY_BUFFER, MAX_VERTICES_PER_RENDER_STACK * VL, GL_STREAM_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, vboColID);
		glBufferData(GL_ARRAY_BUFFER, MAX_VERTICES_PER_RENDER_STACK * CL, GL_STREAM_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
		glBufferData(GL_ARRAY_BUFFER, MAX_VERTICES_PER_RENDER_STACK * CL, GL_STREAM_DRAW);
	}

	/**
	 * Uploads data to OpenGL.
	 */
	public void flush() {
		vd.flip();
		cd.flip();
		td.flip();

		// Bind the VAO and enable attribute locations
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		// Upload the data
		uploadData();

		// Set uniforms
		getShader().setUniformMatrix4("proj", GraphicsUtils.getProjectionMatrix());
		getShader().setUniformMatrix4("view", GraphicsUtils.getCurrentMatrix());

		// Do the actual render
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		// Un-Bind the VAO and disable attribute locations
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);

		vd.clear();
		cd.clear();
		td.clear();

		vertexLastRender += vertexCount;
		vertexCount = 0;
	}

	/**
	 * Private function to handle uploading the data to the OpenGL native layer.
	 * Also sets the data pointers.
	 */
	private void uploadData() {
		glBindBuffer(GL_ARRAY_BUFFER, vboVertID);
		glBufferData(GL_ARRAY_BUFFER, vd, GL_STREAM_DRAW);

		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, vboColID);
		glBufferData(GL_ARRAY_BUFFER, cd, GL_STREAM_DRAW);

		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, vboTexID);
		glBufferData(GL_ARRAY_BUFFER, td, GL_STREAM_DRAW);

		glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
	}

	private boolean active;

	/**
	 * Pre rendering code. Activates batcher.
	 */
	public void pre() {
		if (active) {
			Logger.warn("Must be inactive before calling pre(); ignoring request.");

			return;
		}

		active = true;
	}

	/**
	 * Post rendering code. Deactivates batcher.
	 */
	public void post() {
		if (!active) {
			Logger.warn("Must be active before calling post(); ignoring request.");

			return;
		}

		vertexLastRender = 0;

		flush();

		active = false;
	}

	/**
	 * Returns Whether the batcher is in use (between pre() and post() calls)
	 */
	public boolean isActive() {
		return active;
	}

	private Texture lastTexture = Texture.getEmptyTexture();

	/**
	 * Sets the texture at active index 0 (default OpenGL texture).
	 *
	 * @param texture The texture to set
	 */
	public void setTexture(Texture texture) {
		if (texture.equals(lastTexture))
			return;

		activateTexture(0);

		flush();

		lastTexture = texture;
		texture.bind();
	}

	/**
	 * Sets the texture.
	 *
	 * @param texture The texture to set
	 * @param activeIndex The activation index (0 is GL_TEXTURE0, 1 is
	 *        GL_TEXTURE1, etc.)
	 */
	public void setTexture(Texture texture, int activeIndex) {
		flush();

		lastTexture = texture;

		activateTexture(activeIndex);

		texture.bind();
	}

	/**
	 * Sets the active texture using glActiveTexture().
	 *
	 * @param activateIndex The activation index (0 is GL_TEXTURE0, 1 is
	 *        GL_TEXTURE1, etc.)
	 */
	public void activateTexture(int activateIndex) {
		glActiveTexture(GL_TEXTURE0 + activateIndex);
	}

	/**
	 * Sets the texture to a blank default (not to be confused with unbinding;
	 * this sets the texture to an empty black texture).
	 */
	public void clearTextures() {
		if (lastTexture.equals(Texture.getEmptyTexture()))
			return;

		flush();

		lastTexture = Texture.getEmptyTexture();
		lastTexture.bind();
	}

	/**
	 * Returns The last set texture
	 */
	public Texture getTexture() {
		return lastTexture;
	}

	private Color lastColor = Color.DEFAULT_DRAWING;

	/**
	 * Sets the current color.
	 *
	 * @param color The color to set to
	 */
	public void setColor(Color color) {
		if (color.equals(lastColor))
			return;

		lastColor = color;
	}

	/**
	 * Sets the color to Color.DEFAULT_DRAWING.
	 */
	public void clearColors() {
		if (lastColor.equals(Color.DEFAULT_DRAWING))
			return;

		lastColor = Color.DEFAULT_DRAWING;
	}

	/**
	 * Returns The current color.
	 */
	public Color getColor() {
		return lastColor;
	}

	private Shader lastShader = Shader.DEFAULT_SHADER;

	/**
	 * Sets the current shader.
	 *
	 * @param shader The shader to switch to
	 */
	public void setShader(Shader shader) {
		if (lastShader.equals(shader))
			return;

		flush();

		lastShader = shader;
		Shader.useShader(shader);
	}

	/**
	 * Sets the shader to the Mercury default.
	 */
	public void clearShaders() {
		if (lastShader.equals(Shader.DEFAULT_SHADER))
			return;

		flush();

		lastShader = Shader.DEFAULT_SHADER;
		Shader.useShader(lastShader);
	}

	/**
	 * Returns The last set shader.
	 */
	public Shader getShader() {
		return lastShader;
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param x The x position
	 * @param y The y position
	 */
	public void drawTexture(Texture texture, float x, float y) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param sx1 The near source x coordinate
	 * @param sy1 The near source y coordinate
	 * @param sx2 The far source x coordinate
	 * @param sy2 The far source y coordinate
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param region The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Shape region) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region);
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture The texture to draw
	 * @param sourceRegion The region of the texture to draw
	 * @param region The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Shape sourceRegion, Shape region) {
		drawTexture(texture, sourceRegion, region, Color.DEFAULT_TEXTURE);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param x The x position
	 * @param y The y position
	 * @param tint The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, Color tint) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight(), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 * @param tint The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param sx1 The near source x coordinate
	 * @param sy1 The near source y coordinate
	 * @param sx2 The far source x coordinate
	 * @param sy2 The far source y coordinate
	 * @param x The x position
	 * @param y The y position
	 * @param w The width
	 * @param h The height
	 * @param tint The color to tint the texture
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture The texture to draw
	 * @param region The shape to draw the texture to
	 * @param tint The color to tint the texture
	 */
	public void drawTexture(Texture texture, Shape region, Color tint) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region, tint);
	}

	public void drawTexture(Texture texture, Shape sourceRegion, Shape region, Color tint) {
		if (region.getVertices().length != sourceRegion.getVertices().length)
			throw new IllegalArgumentException("The source region and the region must have an equal amount of vertices.");

		Color beforecolor = getColor();
		setColor(tint);

		sourceRegion = new Shape(sourceRegion);

		// Make a hypothetical sub-texture of the texture
		SubTexture subtexture = null;

		if (texture instanceof SubTexture)
			subtexture = (SubTexture) texture;

		if (texture instanceof SubTexture)
			sourceRegion.translate(subtexture.getSubX(), subtexture.getSubY());

		if (subtexture != null)
			for (Vector2f v : sourceRegion.getVertices()) {
				v.x /= subtexture.getParentWidth();
				v.y /= -subtexture.getParentHeight();
			}
		else
			for (Vector2f v : sourceRegion.getVertices()) {
				v.x /= texture.getWidth();
				v.y /= -texture.getHeight();
			}

		sourceRegion.regen();

		setTexture(texture);

		Vector2f[] vertices = region.getVertices();
		Vector2f[] sourceVertices = sourceRegion.getVertices();

		if (region instanceof Triangle) {
			flushOnOverflow(3);

			vertex(vertices[0].x, vertices[0].y, sourceVertices[0].x, sourceVertices[0].y);
			vertex(vertices[1].x, vertices[1].y, sourceVertices[1].x, sourceVertices[1].y);
			vertex(vertices[2].x, vertices[2].y, sourceVertices[2].x, sourceVertices[2].y);
		} else if (region instanceof Rectangle) {
			flushOnOverflow(6);

			vertex(vertices[0].x, vertices[0].y, sourceVertices[0].x, sourceVertices[0].y);
			vertex(vertices[1].x, vertices[1].y, sourceVertices[1].x, sourceVertices[1].y);
			vertex(vertices[3].x, vertices[3].y, sourceVertices[3].x, sourceVertices[3].y);

			vertex(vertices[2].x, vertices[2].y, sourceVertices[2].x, sourceVertices[2].y);
			vertex(vertices[3].x, vertices[3].y, sourceVertices[3].x, sourceVertices[3].y);
			vertex(vertices[1].x, vertices[1].y, sourceVertices[1].x, sourceVertices[1].y);
		} else {
			// # of sides == # of vertices
			// 3 == number of vertices in triangle
			// 3 * # of vertices = number of vertices we
			// need.
			flushOnOverflow(3 * vertices.length);

			for (int c = 0; c < vertices.length; c++) {
				vertex(region.getCenter().x, region.getCenter().y, sourceRegion.getCenter().x, sourceRegion.getCenter().y);

				if (c >= vertices.length - 1) {
					vertex(vertices[0].x, vertices[0].y, sourceVertices[0].x, sourceVertices[0].y);
					vertex(vertices[vertices.length - 1].x, vertices[vertices.length - 1].y, sourceVertices[sourceVertices.length - 1].x, sourceVertices[sourceVertices.length - 1].y);
				} else {
					vertex(vertices[c].x, vertices[c].y, sourceVertices[c].x, sourceVertices[c].y);
					vertex(vertices[c + 1].x, vertices[c + 1].y, sourceVertices[c + 1].x, sourceVertices[c + 1].y);
				}
			}
		}

		setColor(beforecolor);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param u The source x coordinate
	 * @param v The source y coordinate
	 */
	public void vertex(float x, float y, float u, float v) {
		vertex(x, y, lastColor, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param color The color of the vertex
	 * @param u The source x coordinate
	 * @param v The source y coordinate
	 */
	public void vertex(float x, float y, Color color, float u, float v) {
		vertex(x, y, color.r, color.g, color.b, color.a, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param r The red value of the color of the vertex (0.0-1.0)
	 * @param g The green value of the color of the vertex (0.0-1.0)
	 * @param b The blue value of the color of the vertex (0.0-1.0)
	 * @param u The source x coordinate
	 * @param v The source y coordinate
	 */
	public void vertex(float x, float y, float r, float g, float b, float u, float v) {
		vertex(x, y, r, g, b, 1, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x The x position
	 * @param y The y position
	 * @param r The red value of the color of the vertex (0.0-1.0)
	 * @param g The green value of the color of the vertex (0.0-1.0)
	 * @param b The blue value of the color of the vertex (0.0-1.0)
	 * @param a The alpha value of the color of the vertex (0.0-1.0)
	 * @param u The source x coordinate
	 * @param v The source y coordinate
	 */
	public void vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
		vertex(new VertexData(x, y, r, g, b, a, u, v));
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param vdo The data of the vertex (position, color, and source
	 *        coordinates)
	 */
	public void vertex(VertexData vdo) {
		vd.put(vdo.x).put(vdo.y);
		cd.put(vdo.r).put(vdo.g).put(vdo.b).put(vdo.a);
		td.put(vdo.u).put(vdo.v);

		vertexCount++;
	}

	/**
	 * Calls flush() if allocate's amount of vertices exceed the limit. If you
	 * don't call this before you draw one or more shapes, it could result in a
	 * shape being split into two data flushes.
	 *
	 * @param allocate The amount of vertices you will draw
	 */
	public void flushOnOverflow(int allocate) {
		if (vertexCount + allocate > MAX_VERTICES_PER_RENDER_STACK)
			flush();
	}

	private int vertexCount = 0;
	private int vertexLastRender = 0;

	/**
	 * Returns The amount of vertices rendered in the last frame
	 */
	public int getVerticesLastRendered() {
		return vertexLastRender;
	}

	@Override
	public void cleanup() {
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDeleteBuffers(vboVertID);
		glDeleteBuffers(vboColID);
		glDeleteBuffers(vboTexID);

		glDeleteVertexArrays(vaoID);
	}

	/**
	 * A class to hold the data of a vertex (position, color, and source
	 * coordinates).
	 */
	public static class VertexData {
		float x, y, r, g, b, a, u, v;

		/**
		 * @param x The x position
		 * @param y The y position
		 * @param r The red value of the color of the vertex (0.0-1.0)
		 * @param g The green value of the color of the vertex (0.0-1.0)
		 * @param b The blue value of the color of the vertex (0.0-1.0)
		 * @param a The alpha value of the color of the vertex (0.0-1.0)
		 * @param u The source x coordinate
		 * @param v The source y coordinate
		 */
		public VertexData(float x, float y, float r, float g, float b, float a, float u, float v) {
			this.x = x;
			this.y = y;
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
			this.u = u;
			this.v = v;
		}
	}
}
