package com.radirius.mercury.graphics;

import com.radirius.mercury.math.geometry.*;
import com.radirius.mercury.utilities.GraphicsUtils;
import com.radirius.mercury.utilities.logging.Logger;
import com.radirius.mercury.utilities.misc.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * An OpenGL batcher that manages and pushes vertices.
 *
 * @author wessles
 * @author Jeviny
 * @author Sri Harsha Chilakapati
 */
public class Batcher implements Initializable, Cleanable, Usable {

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
	 * Private function to create OpenGL vertex arrays and initialize the VBO DataStore to the max size of the render
	 * stack.
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
		Shader.getCurrentShader().setUniformMatrix4("proj", GraphicsUtils.getProjectionMatrix());
		Shader.getCurrentShader().setUniformMatrix4("view", GraphicsUtils.getCurrentMatrix());

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
	 * Private function to handle uploading the data to the OpenGL native layer. Also sets the data pointers.
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

	/**
	 * Calls flush() if allocated amount of vertices exceed the limit. If you don't call this before you draw one or
	 * more shapes, it could result in a shape being split into two data flushes.
	 *
	 * @param allocate
	 * 		The amount of vertices you will draw
	 */
	public void flushOnOverflow(int allocate) {
		if (vertexCount + allocate > MAX_VERTICES_PER_RENDER_STACK)
			flush();
	}

	private int vertexCount = 0;
	private int vertexLastRender = 0;

	/**
	 * @return the amount of vertices rendered in the last frame
	 */
	public int getVerticesLastRendered() {
		return vertexLastRender;
	}

	private boolean active;

	/**
	 * Pre rendering code. Activates batcher.
	 */
	@Override
	public void begin() {
		if (active) {
			Logger.warn("Must be inactive before calling begin(); ignoring request.");

			return;
		}

		Shader.DEFAULT_SHADER.bind();

		active = true;
	}

	/**
	 * Post rendering code. Deactivates batcher.
	 */
	@Override
	public void end() {
		if (!active) {
			Logger.warn("Must be active before calling end(); ignoring request.");

			return;
		}

		vertexLastRender = 0;

		flush();

		active = false;
	}

	/**
	 * @return whether the batcher is in use (between begin() and end() calls)
	 */
	public boolean isActive() {
		return active;
	}

	private Texture lastTexture = Texture.getEmptyTexture();

	/**
	 * Sets the texture at active index 0 (default OpenGL texture).
	 *
	 * @param texture
	 * 		The texture to set
	 */
	public void setTexture(Texture texture) {
		flush();

		lastTexture = texture;
		texture.bind();
	}

	/**
	 * Sets the active texture using glActiveTexture().
	 *
	 * @param activateIndex
	 * 		The activation index (0 is GL_TEXTURE0, 1 is GL_TEXTURE1, etc.)
	 */
	public void activateTexture(int activateIndex) {
		glActiveTexture(GL_TEXTURE0 + activateIndex);
	}

	/**
	 * Sets the texture to a blank default (not to be confused with unbinding; this sets the texture to an empty black
	 * texture).
	 */
	public void clearTextures() {
		setTexture(Texture.getEmptyTexture());
	}

	/**
	 * @return the last set texture
	 */
	public Texture getTexture() {
		return lastTexture;
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 */
	public void drawTexture(Texture texture, float x, float y) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param w
	 * 		The width
	 * @param h
	 * 		The height
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param sx1
	 * 		The near source x coordinate
	 * @param sy1
	 * 		The near source y coordinate
	 * @param sx2
	 * 		The far source x coordinate
	 * @param sy2
	 * 		The far source y coordinate
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param w
	 * 		The width
	 * @param h
	 * 		The height
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h));
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param region
	 * 		The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Figure region) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region);
	}

	/**
	 * Draws a texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param sourceRegion
	 * 		The region of the texture to draw
	 * @param region
	 * 		The shape to draw the texture to
	 */
	public void drawTexture(Texture texture, Figure sourceRegion, Figure region) {
		drawTexture(texture, sourceRegion, region, Color.DEFAULT_TEXTURE);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param tint
	 * 		The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, Color tint) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight(), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param w
	 * 		The width
	 * @param h
	 * 		The height
	 * @param tint
	 * 		The color to tint the texture
	 */
	public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param sx1
	 * 		The near source x coordinate
	 * @param sy1
	 * 		The near source y coordinate
	 * @param sx2
	 * 		The far source x coordinate
	 * @param sy2
	 * 		The far source y coordinate
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param w
	 * 		The width
	 * @param h
	 * 		The height
	 * @param tint
	 * 		The color to tint the texture
	 */
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), new Rectangle(x, y, w, h), tint);
	}

	/**
	 * Draws a tinted texture.
	 *
	 * @param texture
	 * 		The texture to draw
	 * @param region
	 * 		The shape to draw the texture to
	 * @param tint
	 * 		The color to tint the texture
	 */
	public void drawTexture(Texture texture, Figure region, Color tint) {
		drawTexture(texture, new Rectangle(0, 0, texture.getWidth(), texture.getHeight()), region, tint);
	}

	public void drawTexture(Texture texture, Figure sourceRegion, Figure region, Color tint) {
		if (region.getVertices().length != sourceRegion.getVertices().length)
			throw new IllegalArgumentException("The source region and the region must have an equal amount of vertices.");

		sourceRegion = new Figure(sourceRegion);

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

			vertex(vertices[0].x, vertices[0].y, tint, sourceVertices[0].x, sourceVertices[0].y);
			vertex(vertices[1].x, vertices[1].y, tint, sourceVertices[1].x, sourceVertices[1].y);
			vertex(vertices[2].x, vertices[2].y, tint, sourceVertices[2].x, sourceVertices[2].y);
		} else if (region instanceof Rectangle) {
			flushOnOverflow(6);

			vertex(vertices[0].x, vertices[0].y, tint, sourceVertices[0].x, sourceVertices[0].y);
			vertex(vertices[1].x, vertices[1].y, tint, sourceVertices[1].x, sourceVertices[1].y);
			vertex(vertices[3].x, vertices[3].y, tint, sourceVertices[3].x, sourceVertices[3].y);

			vertex(vertices[2].x, vertices[2].y, tint, sourceVertices[2].x, sourceVertices[2].y);
			vertex(vertices[3].x, vertices[3].y, tint, sourceVertices[3].x, sourceVertices[3].y);
			vertex(vertices[1].x, vertices[1].y, tint, sourceVertices[1].x, sourceVertices[1].y);
		} else {
			// # of sides == # of vertices
			// 3 == number of vertices in triangle
			// 3 * # of vertices = number of vertices we
			// need.
			flushOnOverflow(3 * vertices.length);

			for (int c = 0; c < vertices.length; c++) {
				vertex(region.getCenter().x, region.getCenter().y, tint, sourceRegion.getCenter().x, sourceRegion.getCenter().y);

				if (c >= vertices.length - 1) {
					vertex(vertices[0].x, vertices[0].y, tint, sourceVertices[0].x, sourceVertices[0].y);
					vertex(vertices[vertices.length - 1].x, vertices[vertices.length - 1].y, tint, sourceVertices[sourceVertices.length - 1].x, sourceVertices[sourceVertices.length - 1].y);
				} else {
					vertex(vertices[c].x, vertices[c].y, tint, sourceVertices[c].x, sourceVertices[c].y);
					vertex(vertices[c + 1].x, vertices[c + 1].y, tint, sourceVertices[c + 1].x, sourceVertices[c + 1].y);
				}
			}
		}
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param u
	 * 		The source x coordinate
	 * @param v
	 * 		The source y coordinate
	 */
	public void vertex(float x, float y, float u, float v) {
		vertex(x, y, Color.DEFAULT_DRAWING, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param color
	 * 		The color of the vertex
	 * @param u
	 * 		The source x coordinate
	 * @param v
	 * 		The source y coordinate
	 */
	public void vertex(float x, float y, Color color, float u, float v) {
		vertex(x, y, color.r, color.g, color.b, color.a, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param r
	 * 		The red value of the color of the vertex (0.0-1.0)
	 * @param g
	 * 		The green value of the color of the vertex (0.0-1.0)
	 * @param b
	 * 		The blue value of the color of the vertex (0.0-1.0)
	 * @param u
	 * 		The source x coordinate
	 * @param v
	 * 		The source y coordinate
	 */
	public void vertex(float x, float y, float r, float g, float b, float u, float v) {
		vertex(x, y, r, g, b, 1, u, v);
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param x
	 * 		The x position
	 * @param y
	 * 		The y position
	 * @param r
	 * 		The red value of the color of the vertex (0.0-1.0)
	 * @param g
	 * 		The green value of the color of the vertex (0.0-1.0)
	 * @param b
	 * 		The blue value of the color of the vertex (0.0-1.0)
	 * @param a
	 * 		The alpha value of the color of the vertex (0.0-1.0)
	 * @param u
	 * 		The source x coordinate
	 * @param v
	 * 		The source y coordinate
	 */
	public void vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
		vertex(new VertexData(x, y, r, g, b, a, u, v));
	}

	/**
	 * Adds a new vertex to the buffer.
	 *
	 * @param vdo
	 * 		The data of the vertex (position, color, and source coordinates)
	 */
	public void vertex(VertexData vdo) {
		vd.put(vdo.x).put(vdo.y);
		cd.put(vdo.r).put(vdo.g).put(vdo.b).put(vdo.a);
		td.put(vdo.u).put(vdo.v);

		vertexCount++;
	}

	/**
	 * A class to hold the data of a vertex (position, color, and source coordinates).
	 */
	public static class VertexData {
		float x, y, r, g, b, a, u, v;

		/**
		 * @param x
		 * 		The x position
		 * @param y
		 * 		The y position
		 * @param r
		 * 		The red value of the color of the vertex (0.0-1.0)
		 * @param g
		 * 		The green value of the color of the vertex (0.0-1.0)
		 * @param b
		 * 		The blue value of the color of the vertex (0.0-1.0)
		 * @param a
		 * 		The alpha value of the color of the vertex (0.0-1.0)
		 * @param u
		 * 		The source x coordinate
		 * @param v
		 * 		The source y coordinate
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

	@Override
	public void cleanup() {
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDeleteBuffers(vboVertID);
		glDeleteBuffers(vboColID);
		glDeleteBuffers(vboTexID);

		glDeleteVertexArrays(vaoID);
	}
}
