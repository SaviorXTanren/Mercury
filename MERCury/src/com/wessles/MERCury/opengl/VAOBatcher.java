package com.wessles.MERCury.opengl;

import static com.wessles.MERCury.opengl.VAOUtils.COLOR_ARRAY_POINTER;
import static com.wessles.MERCury.opengl.VAOUtils.TEXTURE_COORD_ARRAY_POINTER;
import static com.wessles.MERCury.opengl.VAOUtils.VERTEX_ARRAY_POINTER;
import static com.wessles.MERCury.opengl.VAOUtils.disableBuffer;
import static com.wessles.MERCury.opengl.VAOUtils.drawBuffers;
import static com.wessles.MERCury.opengl.VAOUtils.enableBuffer;
import static com.wessles.MERCury.opengl.VAOUtils.pointBuffer;
import static org.lwjgl.opengl.GL11.GL_COLOR;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glEnable;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import com.wessles.MERCury.utils.ColorUtils;

/**
 * A very simple batcher.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public class VAOBatcher implements Batcher {
	private static final int VL = 2, CL = 4, TL = 2;

	private final int maxvtx;

	private FloatBuffer vd, cd, td;
	private int vtxcount;
	private boolean active;

	private Texture last_tex = Texture.getEmptyTexture();
	private Color last_col = ColorUtils.DEFAULT_DRAWING;
	private Shader last_shader = Shader.getEmptyShader();

	public VAOBatcher() {
		this(1000);
	}

	public VAOBatcher(int maxvtx) {
		this.maxvtx = maxvtx;
		vtxcount = 0;
		vd = BufferUtils.createFloatBuffer(maxvtx * VL);
		cd = BufferUtils.createFloatBuffer(maxvtx * CL);
		td = BufferUtils.createFloatBuffer(maxvtx * TL);
		active = false;
	}

	public void begin() {
		if (active) {
			throw new IllegalStateException("Must be inactive before calling begin()!");
		}

		active = true;
	}

	public void end() {
		if (!active) {
			throw new IllegalStateException("Must be active before calling end()!");
		}

		vd.flip();
		cd.flip();
		td.flip();

		render();

		vd.clear();
		cd.clear();
		td.clear();

		vtxcount = 0;

		active = false;
	}

	public void render() {
		glEnable(GL_TEXTURE);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_COLOR);

		enableBuffer(GL_VERTEX_ARRAY);
		enableBuffer(GL_COLOR_ARRAY);
		enableBuffer(GL_TEXTURE_COORD_ARRAY);

		pointBuffer(VERTEX_ARRAY_POINTER, 2, vd);
		pointBuffer(COLOR_ARRAY_POINTER, 4, cd);
		pointBuffer(TEXTURE_COORD_ARRAY_POINTER, 2, td);

		drawBuffers(GL_TRIANGLES, vtxcount);

		disableBuffer(GL_VERTEX_ARRAY);
		disableBuffer(GL_COLOR_ARRAY);
		disableBuffer(GL_TEXTURE_COORD_ARRAY);
	}

	public void setTexture(Texture texture) {
		if (texture.equals(last_tex)) {
			return;
		}
		end();
		last_tex = texture;
		Texture.bindTexture(texture);
		begin();
	}

	public void clearTextures() {
		if (last_tex.equals(Texture.getEmptyTexture())) {
			return;
		}
		end();
		last_tex = Texture.getEmptyTexture();
		Texture.unbindTextures();
		begin();
	}

	public void setColor(Color color) {
		if (color.equals(last_col)) {
			return;
		}
		last_col = color;
	}

	public void clearColors() {
		if (last_col.equals(ColorUtils.DEFAULT_DRAWING)) {
			return;
		}
		last_col = ColorUtils.DEFAULT_DRAWING;
	}

	public void setShader(Shader shader) {
		if (last_shader.equals(shader)) {
			return;
		}
		end();
		last_shader = shader;
		Shader.useShader(shader);
		begin();
	}

	public void clearShaders() {
		if (last_shader.equals(Shader.getEmptyShader())) {
			return;
		}
		end();
		last_shader = Shader.getEmptyShader();
		Shader.releaseShaders();
		begin();
	}

	public void vertex(float x, float y, float u, float v) {
		vertex(x, y, last_col, u, v);
	}

	public void vertex(float x, float y, Color color, float u, float v) {
		vertex(x, y, color.r, color.g, color.b, color.a, u, v);
	}

	public void vertex(float x, float y, float r, float g, float b, float u, float v) {
		vertex(x, y, r, g, b, 1, u, v);
	}

	public void vertex(float x, float y, float r, float g, float b, float a, float u, float v) {
		if (vtxcount >= maxvtx - 1) {
			restart();
		}

		vd.put(x).put(y);
		cd.put(r).put(g).put(b).put(a);
		td.put(u).put(v);

		vtxcount++;
	}

	private void restart() {
		end();
		begin();
	}
}