package com.radirius.mercury.graphics;

import static com.radirius.mercury.graphics.VAOUtils.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.radirius.mercury.math.geometry.Rectangle;
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
	public void drawTexture(Texture texture, Rectangle region) {
		drawTexture(texture, 0, 0, texture.getWidth(), texture.getHeight(), region);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Rectangle region) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), region);
	}

	@Override
	public void drawTexture(Texture texture, Rectangle sourceregion, Rectangle region) {
		drawTexture(texture, sourceregion, region, Color.DEFAULT_TEXTURE_COLOR);
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
	public void drawTexture(Texture texture, Rectangle region, Color tint) {
		drawTexture(texture, 0, 0, texture.getWidth(), texture.getHeight(), region, tint);
	}

	@Override
	public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, Rectangle region, Color tint) {
		drawTexture(texture, new Rectangle(sx1, sy1, sx2 - sx1, sy2 - sy1), region, tint);
	}

	@Override
	public void drawTexture(Texture texture, Rectangle sourceregion, Rectangle region, Color tint) {
		Color beforecolor = getColor();
		setColor(tint);

		float x1 = region.getVertices()[0].x;
		float y1 = region.getVertices()[0].y;
		float x2 = region.getVertices()[1].x;
		float y2 = region.getVertices()[1].y;
		float x3 = region.getVertices()[2].x;
		float y3 = region.getVertices()[2].y;
		float x4 = region.getVertices()[3].x;
		float y4 = region.getVertices()[3].y;

		// Make a hypothetical subtexture of the texture
		SubTexture subtexture = null;

		if (texture instanceof SubTexture)
			subtexture = (SubTexture) texture;

		float w, h;

		if (texture instanceof SubTexture) {
			w = subtexture.getParentWidth();
			h = subtexture.getParentHeight();
			sourceregion.translate(subtexture.getSubX(), subtexture.getSubY());
		} else {
			w = texture.getWidth();
			h = texture.getHeight();
		}

		float sx1 = sourceregion.getVertices()[0].x, sy1 = sourceregion.getVertices()[0].y, sx2 = sourceregion.getVertices()[1].x, sy2 = sourceregion.getVertices()[1].y, sx3 = sourceregion.getVertices()[2].x, sy3 = sourceregion.getVertices()[2].y, sx4 = sourceregion.getVertices()[3].x, sy4 = sourceregion.getVertices()[3].y;

		sy1 = h - sy1;
		sy2 = h - sy2;
		sy3 = h - sy3;
		sy4 = h - sy4;

		sx1 /= w;
		sy1 /= h;
		sx2 /= w;
		sy2 /= h;
		sx3 /= w;
		sy3 /= h;
		sx4 /= w;
		sy4 /= h;

		setTexture(texture);
		flushIfOverflow(6);

		vertex(x1, y1, sx1, sy1);
		vertex(x2, y2, sx2, sy2);
		vertex(x4, y4, sx4, sy4);

		vertex(x3, y3, sx3, sy3);
		vertex(x4, y4, sx4, sy4);
		vertex(x2, y2, sx2, sy2);

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
	public void flushIfOverflow(int allocate) {
		if (vertexCount + allocate > MAX_VERTICES_PER_RENDER_STACK)
			flush();
	}

	@Override
	public int getVerticesLastRendered() {
		return vertexLastRender;
	}
}