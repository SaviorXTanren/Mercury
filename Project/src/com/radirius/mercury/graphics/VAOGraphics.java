package com.radirius.mercury.graphics;

import org.lwjgl.opengl.GL11;

import com.radirius.mercury.framework.Runner;
import com.radirius.mercury.graphics.font.Font;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import com.radirius.mercury.math.MathUtil;
import com.radirius.mercury.math.geometry.Line;
import com.radirius.mercury.math.geometry.Point;
import com.radirius.mercury.math.geometry.Polygon;
import com.radirius.mercury.math.geometry.Rectangle;
import com.radirius.mercury.math.geometry.Shape;
import com.radirius.mercury.math.geometry.Triangle;
import com.radirius.mercury.math.geometry.Vector2f;

/**
 * An object used for graphics. It will draw just about anything for you.
 *
 * @author wessles, Jeviny
 */
public class VAOGraphics implements Graphics {
	private VAOBatcher batcher;

	private Font currentFont;

	private Color backgroundColor;
	private Color currentColor;

	@Override
	public void init() {
		batcher = new VAOBatcher();

		currentFont = TrueTypeFont.OPENSANS_REGULAR;

		backgroundColor = Color.DEFAULT_BACKGROUND;
		currentColor = Color.DEFAULT_DRAWING;
	}

	@Override
	public void pre() {
		batcher.begin();

		GL11.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
	}

	@Override
	public void post() {
		batcher.end();
	}

	private float lineWidth = 1;
	private boolean lineWidthChanged = false;

	@Override
	public void setLineWidth(float width) {
		lineWidth = width;
		lineWidthChanged = true;
	}

	@Override
	public void setScale(float factor) {
		setScale(factor, factor);
	}

	@Override
	public void setScale(float x, float y) {
		Camera cam = Runner.getInstance().getCamera();

		cam.setScale(x, y);

		if (!lineWidthChanged)
			lineWidth = 1 / getScale();
	}

	@Override
	public Vector2f getScaleDimensions() {
		return Runner.getInstance().getCamera().scale;
	}

	@Override
	public float getScale() {
		return (Runner.getInstance().getCamera().scale.x + Runner.getInstance().getCamera().scale.y) / 2;
	}

	@Override
	public void setFont(Font font) {
		currentFont = font;
	}

	@Override
	public Font getFont() {
		return currentFont;
	}

	@Override
	public void setBackground(Color color) {
		backgroundColor = color;
	}

	@Override
	public Color getBackground() {
		return backgroundColor;
	}

	@Override
	public void setColor(Color color) {
		currentColor = color;

		batcher.setColor(currentColor);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		setColor(new Color(r, g, b, a));
	}

	@Override
	public void setColor(float r, float g, float b) {
		setColor(r, g, b, 1f);
	}

	@Override
	public Color getColor() {
		return currentColor;
	}

	@Override
	public void useShader(Shader shad) {
		batcher.setShader(shad);
	}

	@Override
	public void releaseShaders() {
		batcher.clearShaders();
	}

	@Override
	public Batcher getBatcher() {
		return batcher;
	}

	@Override
	public void drawRawVertices(VAOBatcher.VertexData... verts) {
		if (verts.length % 3 != 0)
			throw new IllegalArgumentException("Vertices must be in multiples of 3 for triangle rendering!");

		for (VAOBatcher.VertexData vdo : verts)
			batcher.vertex(vdo);
	}

	@Override
	public void flush() {
		Runner.getInstance().getCamera().post(this);

		batcher.flush();

		Runner.getInstance().getCamera().pre(this);
	}

	@Override
	public void drawString(String message, float x, float y) {
		drawString(message, currentFont, x, y);
	}

	@Override
	public void drawString(String message, Font font, float x, float y) {
		drawString(message, 1, font, x, y);
	}

	@Override
	public void drawString(String message, float scale, float x, float y) {
		drawString(message, scale, currentFont, x, y);
	}

	@Override
	public void drawString(String message, float scale, Font font, float x, float y) {
		if (font instanceof TrueTypeFont) {
			TrueTypeFont ttf = (TrueTypeFont) font;

			float xCurrent = 0;

			for (int i = 0; i < message.toCharArray().length; i++) {
				if (message.toCharArray()[i] == '\n') {
					y += ttf.getHeight() * scale;

					xCurrent = 0;
				}

				TrueTypeFont.IntObject intObject = ttf.chars[message.toCharArray()[i]];

				batcher.drawTexture(font.getFontTexture(), new Rectangle(intObject.x, intObject.y, intObject.w, intObject.h), new Rectangle(x + xCurrent, y, intObject.w * scale, intObject.h * scale), getColor());

				xCurrent += intObject.w * scale;
			}
		}
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
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation) {
		drawTexture(texture, x, y, w, h, rotation, 0, 0);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, float localOriginX, float localOriginY) {
		drawTexture(texture, (Rectangle) new Rectangle(x, y, w, h).rotate(rotation, localOriginX, localOriginY));
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
		batcher.drawTexture(texture, sourceRegion, region);
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
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, Color tint) {
		drawTexture(texture, x, y, w, h, rotation, 0, 0, tint);
	}

	@Override
	public void drawTexture(Texture texture, float x, float y, float w, float h, float rotation, float localOriginX, float localOriginY, Color tint) {
		drawTexture(texture, (Rectangle) new Rectangle(x, y, w, h).rotate(rotation, localOriginX, localOriginY), tint);
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
		batcher.drawTexture(texture, sourceRegion, region, tint);
	}

	@Override
	public void drawPoint(float x, float y) {
		drawPoint(new Point(x, y));
	}

	@Override
	public void drawAnimation(Animation animation, float x, float y) {
		drawAnimation(animation, new Rectangle(x, y, animation.getCurrentFrame().getWidth(), animation.getCurrentFrame().getHeight()));
	}

	@Override
	public void drawAnimation(Animation animation, float x, float y, float w, float h) {
		drawAnimation(animation, new Rectangle(x, y, w, h));
	}

	@Override
	public void drawAnimation(Animation animation, Shape region) {
		drawTexture(animation.getCurrentFrame(), region);

		animation.passFrame();
	}

	@Override
	public void drawAnimation(Animation animation, Shape sourceRegion, Shape region) {
		drawTexture(animation.getCurrentFrame(), sourceRegion, region);
		
		animation.passFrame();
	}
	
	/**
	 * A method that can be called without pulling a color, setting a color,
	 * binding, etc.
	 */
	private void drawFunctionlessRectangle(Rectangle... rectangle) {
		for (Rectangle rectangle0 : rectangle) {
			float x1 = rectangle0.getVertices()[0].x, x2 = rectangle0.getVertices()[1].x,
				  x3 = rectangle0.getVertices()[2].x, x4 = rectangle0.getVertices()[3].x;
			
			float y1 = rectangle0.getVertices()[0].y, y2 = rectangle0.getVertices()[1].y,
				  y3 = rectangle0.getVertices()[2].y, y4 = rectangle0.getVertices()[3].y;

			batcher.flushOnOverflow(6);

			batcher.vertex(x1, y1, 0, 0);
			batcher.vertex(x2, y2, 0, 0);
			batcher.vertex(x4, y4, 0, 0);

			batcher.vertex(x3, y3, 0, 0);
			batcher.vertex(x4, y4, 0, 0);
			batcher.vertex(x2, y2, 0, 0);
		}
	}

	@Override
	public void drawShape(Polygon... polygon) {
		batcher.clearTextures();

		for (Polygon polygon0 : polygon) {
			Vector2f[] vertices = polygon0.getVertices();

			if (polygon0 instanceof Triangle) {
				batcher.flushOnOverflow(3);

				batcher.vertex(vertices[0].x, vertices[0].y, 0, 0);
				batcher.vertex(vertices[1].x, vertices[1].y, 0, 0);
				batcher.vertex(vertices[2].x, vertices[2].y, 0, 0);

				continue;
			} else if (polygon0 instanceof Rectangle) {
				drawFunctionlessRectangle((Rectangle) polygon0);
				
				continue;
			}

			// # of sides == # of vertices
			// 3 == number of vertices in triangle
			// 3 * # of vertices = number of vertices we
			// need.
			batcher.flushOnOverflow(3 * vertices.length);

			for (int c = 0; c < vertices.length; c++) {
				batcher.vertex(polygon0.getCenter().x, polygon0.getCenter().y, 0, 0);

				if (c >= vertices.length - 1)
					batcher.vertex(vertices[0].x, vertices[0].y, 0, 0);
				else
					batcher.vertex(vertices[c].x, vertices[c].y, 0, 0);

				if (c >= vertices.length - 1)
					batcher.vertex(vertices[vertices.length - 1].x, vertices[vertices.length - 1].y, 0, 0);
				else
					batcher.vertex(vertices[c + 1].x, vertices[c + 1].y, 0, 0);
			}
		}
	}

	@Override
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

	@Override
	public void drawRectangle(Rectangle... rectangle) {
		drawShape(rectangle);
	}

	@Override
	public void drawRectangle(float x, float y, float w, float h) {
		drawRectangle(new Rectangle(x, y, w, h));
	}

	@Override
	public void traceRectangle(Rectangle... rectangle) {
		traceShape(rectangle);
	}

	@Override
	public void traceRectangle(float x, float y, float w, float h) {
		traceRectangle(new Rectangle(x, y, w, h));
	}

	@Override
	public void drawLine(float x1, float y1, float x2, float y2) {
		drawLine(new Vector2f(x1, y1), new Vector2f(x2, y2));
	}

	@Override
	public void drawLine(Vector2f p1, Vector2f p2) {
		drawLine(new Line(p1, p2));
	}

	/**
	 * A method that can be called without pulling a color, setting a color,
	 * binding, etc.
	 */
	private void drawFunctionlessLine(Line l) {
		float dx = l.getVertices()[0].x - l.getVertices()[1].x;
		float dy = l.getVertices()[0].y - l.getVertices()[1].y;
		float angle = MathUtil.atan2(dx, dy) - 90;

		Vector2f p1 = new Vector2f(l.getVertices()[0].x - MathUtil.cos(angle) * lineWidth / 2, l.getVertices()[0].y - MathUtil.sin(angle) * lineWidth / 2);
		Vector2f p2 = new Vector2f(l.getVertices()[0].x + MathUtil.cos(angle) * lineWidth / 2, l.getVertices()[0].y + MathUtil.sin(angle) * lineWidth / 2);
		Vector2f p3 = new Vector2f(l.getVertices()[1].x + MathUtil.cos(angle) * lineWidth / 2, l.getVertices()[1].y + MathUtil.sin(angle) * lineWidth / 2);
		Vector2f p4 = new Vector2f(l.getVertices()[1].x - MathUtil.cos(angle) * lineWidth / 2, l.getVertices()[1].y - MathUtil.sin(angle) * lineWidth / 2);

		drawFunctionlessRectangle(new Rectangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y));
		drawShape(new Polygon(l.getVertices()[0].x, l.getVertices()[0].y, lineWidth / 2, 8));
		drawShape(new Polygon(l.getVertices()[1].x, l.getVertices()[1].y, lineWidth / 2, 8));
	}

	@Override
	public void drawLine(Line... line) {
		batcher.clearTextures();

		for (Line line0 : line) {
			batcher.flushOnOverflow(6);

			drawFunctionlessLine(line0);
		}
	}

	@Override
	public void drawPoint(Point... point) {
		for (Point point0 : point) {
			float x = point0.getX();
			float y = point0.getY();

			drawFunctionlessRectangle(new Rectangle(x, y, x + 1, y, x + 1, y + 1, x, y + 1));
		}
	}
}