package radirius.merc.graphics;

import static org.lwjgl.opengl.GL11.glScalef;

import org.lwjgl.opengl.GL11;

import radirius.merc.framework.Runner;
import radirius.merc.graphics.font.Font;
import radirius.merc.graphics.font.TrueTypeFont;
import radirius.merc.math.MathUtil;
import radirius.merc.math.geometry.Line;
import radirius.merc.math.geometry.Point;
import radirius.merc.math.geometry.Polygon;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Triangle;
import radirius.merc.math.geometry.Vec2;

/**
 * An object used for graphics. It will draw just about anything for you.
 * 
 * @author wessles, Jeviny
 */

public class VAOGraphics implements Graphics {
	private VAOBatcher batcher;

	private Vec2 scale;
	private Font currentfont;
	private Color backgroundcolor;
	private Color currentcolor;

	@Override
	public void init() {
		batcher = new VAOBatcher();
		scale = new Vec2(1, 1);

		currentfont = TrueTypeFont.OPENSANS_REGULAR;

		backgroundcolor = Color.DEFAULT_BACKGROUND;
		currentcolor = Color.DEFAULT_DRAWING;
	}

	@Override
	public void pre() {
		batcher.begin();

		GL11.glClearColor(backgroundcolor.r, backgroundcolor.g, backgroundcolor.b, backgroundcolor.a);
	}

	@Override
	public void post() {
		batcher.end();
	}

	private float linewidth = 1;
	private boolean linewidthchanged = false;

	@Override
	public void setLineWidth(float width) {
		linewidth = width;
		linewidthchanged = true;
	}

	@Override
	public void setScale(float factor) {
		setScale(factor, factor);
	}

	@Override
	public void setScale(float x, float y) {
		flush();

		Camera cam = Runner.getInstance().getCamera();

		GL11.glLoadIdentity();

		glScalef(x, y, 1);
		Vec2 scaledorigin = new Vec2(cam.getOrigin().x / x, cam.getOrigin().y / y);
		GL11.glTranslatef(cam.x + scaledorigin.x, cam.y + scaledorigin.y, 0);

		scale.x = x;
		scale.y = y;

		if (!linewidthchanged)
			linewidth = 1 / getScale();
	}

	@Override
	public Vec2 getScaleDimensions() {
		return scale;
	}

	@Override
	public float getScale() {
		return (scale.x + scale.y) / 2;
	}

	@Override
	public void setFont(Font font) {
		currentfont = font;
	}

	@Override
	public Font getFont() {
		return currentfont;
	}
	
	@Override
	public void setBackground(Color color) {
		backgroundcolor = color;
	}

	@Override
	public Color getBackground() {
		return backgroundcolor;
	}

	@Override
	public void setColor(Color color) {
		currentcolor = color;

		batcher.setColor(currentcolor);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		setColor(new Color(r, g, b, a));
	}
	
	@Override
	public void setColor(float r, float g, float b) {
		setColor(r, g, b, 255);
	}
	
	@Override
	public Color getColor() {
		return currentcolor;
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
		batcher.flush();
	}

	@Override
	public void drawString(String message, float x, float y) {
		drawString(message, currentfont, x, y);
	}

	@Override
	public void drawString(String message, Font font, float x, float y) {
		drawString(message, 1, font, x, y);
	}

	@Override
	public void drawString(String message, float scale, float x, float y) {
		drawString(message, scale, currentfont, x, y);
	}

	@Override
	public void drawString(String message, float scale, Font font, float x, float y) {
		if (font instanceof TrueTypeFont) {
			TrueTypeFont jf = (TrueTypeFont) font;

			float currentx = 0;

			boolean defaultcolor = currentcolor == Color.DEFAULT_DRAWING;

			if (defaultcolor)
				setColor(Color.DEFAULT_TEXT_COLOR);

			for (int ci = 0; ci < message.toCharArray().length; ci++) {
				if (message.toCharArray()[ci] == '\n') {
					y += jf.getHeight() * scale;
					
					currentx = 0;
				}

				TrueTypeFont.IntObject intobj = jf.chars[message.toCharArray()[ci]];
				
				batcher.drawTexture(font.getFontTexture(), new Rectangle(intobj.x, intobj.y, intobj.w, intobj.h), new Rectangle(x + currentx, y, intobj.w * scale, intobj.h * scale));
				
				currentx += intobj.w * scale;
			}

			if (defaultcolor)
				setColor(Color.DEFAULT_DRAWING);
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
		boolean default_color = currentcolor == Color.DEFAULT_DRAWING;

		if (default_color)
			setColor(Color.DEFAULT_TEXTURE_COLOR);

		batcher.drawTexture(texture, sourceregion, region);

		if (default_color)
			setColor(Color.DEFAULT_DRAWING);
	}

	/**
	 * A method that can be called without pulling a color, setting a color,
	 * binding, etc.
	 */
	private void drawFunctionlessRect(Rectangle... rectangle) {
		for (Rectangle _rectangle : rectangle) {
			float x1 = _rectangle.getVertices()[0].x;
			float y1 = _rectangle.getVertices()[0].y;
			float x2 = _rectangle.getVertices()[1].x;
			float y2 = _rectangle.getVertices()[1].y;
			float x3 = _rectangle.getVertices()[2].x;
			float y3 = _rectangle.getVertices()[2].y;
			float x4 = _rectangle.getVertices()[3].x;
			float y4 = _rectangle.getVertices()[3].y;

			batcher.flushIfOverflow(6);

			batcher.vertex(x1, y1, 0, 0);
			batcher.vertex(x2, y2, 0, 0);
			batcher.vertex(x4, y4, 0, 0);

			batcher.vertex(x3, y3, 0, 0);
			batcher.vertex(x4, y4, 0, 0);
			batcher.vertex(x2, y2, 0, 0);
		}
	}

	@Override
	public void drawPolygon(Polygon... polygon) {
		batcher.clearTextures();

		float w = batcher.getTexture().getWidth(), h = batcher.getTexture().getHeight();

		for (Polygon poly : polygon) {
			Vec2[] vs = poly.getVertices();

			if (poly instanceof Triangle) {
				batcher.flushIfOverflow(3);

				batcher.vertex(vs[0].x, vs[0].y, 0, 0);
				batcher.vertex(vs[1].x, vs[1].y, 0, 0);
				batcher.vertex(vs[2].x, vs[2].y, 0, 0);

				continue;
			} else if (poly instanceof Rectangle) {
				drawFunctionlessRect((Rectangle) poly);

				continue;
			}

			// # of sides == # of vertices
			// 3 == number of vertices in triangle
			// 3 * # of vertices = number of vertices we need.
			batcher.flushIfOverflow(3 * vs.length);

			for (int c = 0; c < vs.length; c++) {
				batcher.vertex(poly.getCenter().x, poly.getCenter().y, poly.getCenter().x / w, poly.getCenter().y / h);

				if (c >= vs.length - 1)
					batcher.vertex(vs[0].x, vs[0].y, vs[0].x / w, vs[0].y / h);
				else
					batcher.vertex(vs[c].x, vs[c].y, vs[c].x / w, vs[c].y / h);

				if (c >= vs.length - 1)
					batcher.vertex(vs[vs.length - 1].x, vs[vs.length - 1].y, vs[vs.length - 1].x / w, vs[vs.length - 1].y / h);
				else
					batcher.vertex(vs[c + 1].x, vs[c + 1].y, vs[c + 1].x / w, vs[c + 1].y / h);
			}
		}
	}

	@Override
	public void tracePolygon(Polygon... polygon) {
		batcher.clearTextures();
		
		for (Polygon poly : polygon) {
			batcher.flushIfOverflow(poly.getVertices().length * 6);

			Vec2[] vs = poly.getVertices();

			for (int c = 0; c < vs.length; c++) {
				Vec2 p1, p2;

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
		drawPolygon(rectangle);
	}
	
	@Override
	public void drawRectangle(float x, float y, float w, float h) {
		drawRectangle(new Rectangle(x, y, w, h));
	}

	@Override
	public void traceRectangle(Rectangle... rectangle) {
		tracePolygon(rectangle);
	}
	
	@Override
	public void traceRectangle(float x, float y, float w, float h) {
		traceRectangle(new Rectangle(x, y, w, h));
	}
	
	@Override
	public void drawLine(float x1, float y1, float x2, float y2) {
		drawLine(new Vec2(x1, y1), new Vec2(x2, y2));
	}

	@Override
	public void drawLine(Vec2 p1, Vec2 p2) {
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

		Vec2 p1 = new Vec2(l.getVertices()[0].x - MathUtil.cos(angle) * linewidth / 2, l.getVertices()[0].y - MathUtil.sin(angle) * linewidth / 2);
		Vec2 p2 = new Vec2(l.getVertices()[0].x + MathUtil.cos(angle) * linewidth / 2, l.getVertices()[0].y + MathUtil.sin(angle) * linewidth / 2);
		Vec2 p3 = new Vec2(l.getVertices()[1].x + MathUtil.cos(angle) * linewidth / 2, l.getVertices()[1].y + MathUtil.sin(angle) * linewidth / 2);
		Vec2 p4 = new Vec2(l.getVertices()[1].x - MathUtil.cos(angle) * linewidth / 2, l.getVertices()[1].y - MathUtil.sin(angle) * linewidth / 2);

		drawFunctionlessRect(new Rectangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y));
	}

	@Override
	public void drawLine(Line... l) {
		batcher.clearTextures();

		for (Line _l : l) {
			batcher.flushIfOverflow(6);

			drawFunctionlessLine(_l);
		}
	}

	@Override
	public void drawPoint(Point... point) {
		for (Point _point : point) {
			float x = _point.getX();
			float y = _point.getY();
			
			drawFunctionlessRect(new Rectangle(x, y, x + 1, y, x + 1, y + 1, x, y + 1));
		}
	}

	@Override
	public void drawPoint(float x, float y) {
		drawPoint(new Point(x, y));
	}
}