package com.radirius.mercury.graphics;

import static com.radirius.mercury.graphics.VAOUtils.COLOR_ARRAY_POINTER;
import static com.radirius.mercury.graphics.VAOUtils.TEXTURE_COORD_ARRAY_POINTER;
import static com.radirius.mercury.graphics.VAOUtils.VERTEX_ARRAY_POINTER;
import static com.radirius.mercury.graphics.VAOUtils.disableBuffer;
import static com.radirius.mercury.graphics.VAOUtils.drawBuffers;
import static com.radirius.mercury.graphics.VAOUtils.enableBuffer;
import static com.radirius.mercury.graphics.VAOUtils.pointBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.math.geometry.Shape;
import com.radirius.mercury.math.geometry.Triangle;
import com.radirius.mercury.math.geometry.Vector2f;
import com.radirius.mercury.utilities.logging.Logger;

/**
 * A very simple OpenGL batcher.
 *
 * @author wessles, Jeviny
 */
public class VAOBatcher implements Batcher {
	private static final int VL = 2, CL = 4, TL = 2;
	public static final int MAX_VERTICES_PER_RENDER_STACK = 4096;

	private FloatBuffer vd, cd, td;

	private int vertexCount;
	private int vertexLastRender = 0;

	private boolean active;

	private Texture lastTexture = Texture.getEmptyTexture();
	private Shader lastShader = Shader.DEFAULT_SHADER;
	private Color lastColor = Color.DEFAULT_DRAWING;

	public VAOBatcher() {
		vertexCount = 0;

		vd = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * VL);
		cd = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * CL);
		td = BufferUtils.createFloatBuffer(MAX_VERTICES_PER_RENDER_STACK * TL);

		active = false;
	}

	@Override
	public void begin() {
		if (active) {
			Logger.warn("Must be inactive before calling begin(); ignoring request.");

			return;
		}

		vertexLastRender = 0;

		active = true;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void end() {
		if (!active) {
			Logger.warn("Must be active before calling end(); ignoring request.");

			return;
		}

		flush();

		active = false;
	}

	@Override
	public void flush() {
		vd.flip();
		cd.flip();
		td.flip();

		glEnable(GL_TEXTURE);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR);

		enableBuffer(GL_VERTEX_ARRAY);
		enableBuffer(GL_COLOR_ARRAY);
		enableBuffer(GL_TEXTURE_COORD_ARRAY);

		pointBuffer(VERTEX_ARRAY_POINTER, 2, vd);
		pointBuffer(COLOR_ARRAY_POINTER, 4, cd);
		pointBuffer(TEXTURE_COORD_ARRAY_POINTER, 2, td);

		drawBuffers(GL_TRIANGLES, vertexCount);

		disableBuffer(GL_VERTEX_ARRAY);
		disableBuffer(GL_COLOR_ARRAY);
		disableBuffer(GL_TEXTURE_COORD_ARRAY);

		vd.clear();
		cd.clear();
		td.clear();

		vertexLastRender += vertexCount;
		vertexCount = 0;
	}

	@Override
	public void setTexture(Texture texture) {
		if (texture.equals(lastTexture))
			return;

		flush();

		lastTexture = texture;
		Texture.bindTexture(texture);
	}

	@Override
	public void setTexture(Texture texture, int activeIndex) {
		if (texture.equals(lastTexture))
			return;

		flush();

		lastTexture = texture;
		
		activateTexture(activeIndex);
		
		Texture.bindTexture(texture);
	}
	
	@Override
	public void activateTexture(int activateIndex) {
		glActiveTexture(activateIndex);
	}

	@Override
	public void clearTextures() {
		if (lastTexture.equals(Texture.getEmptyTexture()))
			return;

		flush();

		lastTexture = Texture.getEmptyTexture();
		Texture.bindTexture(lastTexture);
	}

	@Override
	public Texture getTexture() {
		return lastTexture;
	}

	@Override
	public void setColor(Color color) {
		if (color.equals(lastColor))
			return;

		lastColor = color;
	}

	@Override
	public void clearColors() {
		if (lastColor.equals(Color.DEFAULT_DRAWING))
			return;

		lastColor = Color.DEFAULT_DRAWING;
	}

	@Override
	public Color getColor() {
		return lastColor;
	}

	@Override
	public void setShader(Shader shader) {
		if (lastShader.equals(shader))
			return;

		flush();

		lastShader = shader;
		Shader.useShader(shader);
	}

	@Override
	public void clearShaders() {
		if (lastShader.equals(Shader.DEFAULT_SHADER))
			return;

		flush();

		lastShader = Shader.DEFAULT_SHADER;
		Shader.useShader(lastShader);
	}

	@Override
	public Shader getShader() {
		return lastShader;
	}

	@Override
	public void drawTexture(Texture texture, float x, float y) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h) {
		drawTexture(texture, x, y, w, h, 0);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rot) {
		drawTexture(texture, x, y, w, h, rot, 0, 0);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rot, float local_origin_x, float local_origin_y) {
		drawTexture(texture, (Rectangle) new Rectangle(x, y, w, h).rotate(rot, local_origin_x, local_origin_y));
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y) {
		drawTexture(texture, sx1, sy1, sx2, sy2, x, y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h) {
		drawTexture(texture, sx1, sy1, sx2, sy2, new Rectangle(x, y, w, h));
	}

	@Override
	public void drawTexture(Texture texture, Shape region) {
		drawTexture(texture, 0, 0, texture.getWidth(), texture.getHeight(), region);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), region);
	}

	@Override
	public void drawTexture(Texture texture, Shape sourceRegion, Shape region) {
		drawTexture(texture, sourceRegion, region, Color.DEFAULT_TEXTURE_COLOR);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, Color tint) {
		drawTexture(texture, x, y, texture.getWidth(), texture.getHeight(), tint);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, x, y, w, h, 0, tint);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rot, Color tint) {
		drawTexture(texture, x, y, w, h, rot, 0, 0, tint);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rot, float local_origin_x, float local_origin_y, Color tint) {
		drawTexture(texture, (Rectangle) new Rectangle(x, y, w, h).rotate(rot, local_origin_x, local_origin_y), tint);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, Color tint) {
		drawTexture(texture, sx1, sy1, sx2, sy2, x, y, texture.getWidth(), texture.getHeight(), tint);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w, float h, Color tint) {
		drawTexture(texture, sx1, sy1, sx2, sy2, new Rectangle(x, y, w, h), tint);
	}

	@Override
	public void drawTexture(Texture texture, Shape region, Color tint) {
		drawTexture(texture, 0, 0, texture.getWidth(), texture.getHeight(), region, tint);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Shape region, Color tint) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), region, tint);
	}

	@Override
	public void drawTexture(Texture texture, Shape sourceRegion, Shape region, Color tint) {
		if (region.getVertices().length != sourceRegion.getVertices().length)
			throw new IllegalArgumentException("The source region and the region must have an equal amount of vertices.");

		Color beforecolor = getColor();
		setColor(tint);

		Shape tsourceregion = new Shape(sourceRegion);
		sourceRegion = tsourceregion;

		// Make a hypothetical sub-texture of the texture
		SubTexture subtexture = null;

		if (texture instanceof SubTexture)
			subtexture = (SubTexture) texture;

		if (texture instanceof SubTexture)
			sourceRegion.translate(subtexture.getSubX(), subtexture.getSubY());

		for (Vector2f v : sourceRegion.getVertices()) {
			v.x /= subtexture.getParentWidth();
			v.y /= -subtexture.getParentHeight();
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

	@Override
	public void vertex(float x, float y, float u, float v) {
		vertex(x, y, lastColor, u, v);
	}

	@Override
	public void vertex(float x, float y, Color color, float u, float v) {
		vertex(x, y, color.r, color.g, color.b, color.a, u, v);
	}

	@Override
	public void vertex(float x, float y, float r, float g, float b, float u, float v) {
		vertex(x, y, r, g, b, 1, u, v);
	}

	@Override
	public void vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
		vertex(new VertexData(x, y, r, g, b, a, u, v));
	}

	public void vertex(VertexData vdo) {
		vd.put(vdo.x).put(vdo.y);
		cd.put(vdo.r).put(vdo.g).put(vdo.b).put(vdo.a);
		td.put(vdo.u).put(vdo.v);

		vertexCount++;
	}

	public static class VertexData {
		float x, y, r, g, b, a, u, v;

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
	public void flushOnOverflow(int allocate) {
		if (vertexCount + allocate > MAX_VERTICES_PER_RENDER_STACK)
			flush();
	}

	@Override
	public int getVerticesLastRendered() {
		return vertexLastRender;
	}
}