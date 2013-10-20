package com.wessles.MERCury.opengl;

import com.wessles.MERCury.geom.Point;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.geom.Triangle;

/**
 * @from MERCury
 * @author wessles
 * @website www.wessles.com
 */

public interface Graphics {
	public void init();

	public void pre();
	public void post();
	
	public void scale(float factor);
	public void scale(float x, float y);
	public void setBackground(Color col);
	
	public void setColor(Color color);
	public void useShader(Shader shader);
	public void releaseShaders();

	public void drawTexture(Texture texture, float x, float y);

	// public void renderAnimation(Animation animation, float x, float y);

	public void drawRect(Rectangle rectangle);
	public void drawRect(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4);
	public void drawRects(Rectangle[] rects);

	public void drawTriangle(Triangle triangle);
	public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3);
	public void drawTriangles(Triangle[] triangles);

	public void drawPoint(Point point);
	public void drawPoint(float x, float y);
	public void drawPoints(Point[] points);
}
