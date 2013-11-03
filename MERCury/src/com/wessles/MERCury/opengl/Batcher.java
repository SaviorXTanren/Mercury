package com.wessles.MERCury.opengl;

/**
 * An abstraction for batchers.
 * 
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */
public interface Batcher {

	public void begin();

	public void end();

	public void render();

	public void setColor(Color color);

	public void clearColors();

	public void setShader(Shader shader);

	public void clearShaders();

	public void setTexture(Texture texture);

	public void clearTextures();

	public void vertex(float x, float y, float u, float v);

	public void vertex(float x, float y, Color color, float u, float v);

	public void vertex(float x, float y, float r, float g, float b, float u, float v);

	public void vertex(float x, float y, float r, float g, float b, float a, float u, float v);
}
