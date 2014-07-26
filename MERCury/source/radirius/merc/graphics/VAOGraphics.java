package radirius.merc.graphics;

import static org.lwjgl.opengl.GL11.glScalef;

import org.lwjgl.opengl.GL11;

import radirius.merc.font.Font;
import radirius.merc.font.TrueTypeFont;
import radirius.merc.framework.Runner;
import radirius.merc.geometry.Circle;
import radirius.merc.geometry.Ellipse;
import radirius.merc.geometry.Line;
import radirius.merc.geometry.Point;
import radirius.merc.geometry.Polygon;
import radirius.merc.geometry.Rectangle;
import radirius.merc.geometry.Shape;
import radirius.merc.geometry.Star;
import radirius.merc.geometry.Triangle;
import radirius.merc.geometry.Vec2;
import radirius.merc.logging.Logger;
import radirius.merc.maths.MercMath;

/**
 * An object used for graphics. It will draw just about anything for you.
 * 
 * @author wessles, Jeviny
 */

public class VAOGraphics implements Graphics {
    private VAOBatcher batcher;
    
    private Vec2 scale;
    private Font current_font;
    private Color background_color;
    private Color current_color;
    
    @Override
    public void init() {
        batcher = new VAOBatcher();
        scale = new Vec2(1, 1);
        
        current_font = TrueTypeFont.OPENSANS_REGULAR;
        
        background_color = Color.DEFAULT_BACKGROUND;
        current_color = Color.DEFAULT_DRAWING;
    }
    
    @Override
    public void pre() {
        batcher.begin();
        
        GL11.glClearColor(background_color.r, background_color.g, background_color.b, background_color.a);
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
        Camera cam = Runner.getInstance().getCamera();
        
        GL11.glLoadIdentity();
        
        GL11.glTranslatef(cam.getOrigin().x, cam.getOrigin().y, 0);
        glScalef(x, y, 1);
        GL11.glTranslatef(-cam.getOrigin().x, -cam.getOrigin().y, 0);
        
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
        current_font = font;
    }
    
    @Override
    public Font getFont() {
        return current_font;
    }
    
    @Override
    public void setBackground(Color color) {
        background_color = color;
    }
    
    @Override
    public Color getBackground() {
        return background_color;
    }
    
    @Override
    public void setColor(Color color) {
        current_color = color;
        
        batcher.setColor(current_color);
    }
    
    @Override
    public Color getColor() {
        return current_color;
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
    public void drawString(float x, float y, String msg) {
        drawString(current_font, x, y, msg);
    }
    
    @Override
    public void drawString(Font font, float x, float y, String msg) {
        drawString(1, font, x, y, msg);
    }
    
    @Override
    public void drawString(float scale, float x, float y, String msg) {
        drawString(scale, current_font, x, y, msg);
    }
    
    @Override
    public void drawString(float scale, Font font, float x, float y, String msg) {
        if (font instanceof TrueTypeFont) {
            TrueTypeFont jf = (TrueTypeFont) font;
            
            float current_x = 0;
            
            boolean default_color = current_color == Color.DEFAULT_DRAWING;
            
            if (default_color)
                setColor(Color.DEFAULT_TEXT_COLOR);
            
            for (int ci = 0; ci < msg.toCharArray().length; ci++) {
                if (msg.toCharArray()[ci] == '\n') {
                    y += jf.getHeight() * scale;
                    current_x = 0;
                }
                
                TrueTypeFont.IntObject intobj = jf.chars[msg.toCharArray()[ci]];
                batcher.drawTexture(font.getFontTexture(), new Rectangle(intobj.x, intobj.y, intobj.w,
                        intobj.h), new Rectangle(x + current_x, y, intobj.w * scale,
                        intobj.h * scale));
                current_x += intobj.w * scale;
            }
            
            if (default_color)
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
    public void drawTexture(Texture texture, float x, float y, float w, float h, float rot, float local_origin_x,
            float local_origin_y) {
        drawTexture(texture, (Rectangle) new Rectangle(x, y, w, h).rotate(rot, local_origin_x, local_origin_y));
    }
    
    @Override
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y) {
        drawTexture(texture, sx1, sy1, sx2, sy2, x, y, texture.getWidth(), texture.getHeight());
    }
    
    @Override
    public void drawTexture(Texture texture, float sx1, float sy1, float sx2, float sy2, float x, float y, float w,
            float h) {
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
        boolean default_color = current_color == Color.DEFAULT_DRAWING;
        
        if (default_color)
            setColor(Color.DEFAULT_TEXTURE_COLOR);
        
        batcher.drawTexture(texture, sourceregion, region);
        
        if (default_color)
            setColor(Color.DEFAULT_DRAWING);
    }
    
    @Override
    public void drawShape(Shape... shapes) {
        for (Shape shape : shapes)
            if (shape instanceof Circle)
                drawCircle((Circle) shape);
            else if (shape instanceof Ellipse)
                drawEllipse((Ellipse) shape);
            else if (shape instanceof Line)
                drawLine((Line) shape);
            else if (shape instanceof Point)
                drawPoint((Point) shape);
            else if (shape instanceof Polygon)
                drawPolygon((Polygon) shape);
            else if (shape instanceof Rectangle)
                drawRect((Rectangle) shape);
            else if (shape instanceof Star)
                drawStar((Star) shape);
            else if (shape instanceof Triangle)
                drawTriangle((Triangle) shape);
            else
                Logger.warn("Shape type not found; nothing rendered.");
    }
    
    @Override
    public void drawRect(Rectangle... rectangle) {
        batcher.clearTextures();
        
        drawFunctionlessRect(rectangle);
    }
    
    /**
     * A method that can be called without pulling a color, setting a color,
     * binding, etc.
     */
    private void drawFunctionlessRect(Rectangle... rectangle) {
        float w = batcher.getTexture().getWidth(), h = batcher.getTexture().getHeight();
        
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
            
            batcher.vertex(x1, y1, x1 / w, y1 / h);
            batcher.vertex(x2, y2, x2 / w, y2 / h);
            batcher.vertex(x4, y4, x4 / w, y4 / h);
            
            batcher.vertex(x3, y3, x3 / w, y3 / h);
            batcher.vertex(x4, y4, x4 / w, y4 / h);
            batcher.vertex(x2, y2, x2 / w, y2 / h);
        }
    }
    
    @Override
    public void drawRect(float x, float y, float w, float h) {
        drawRect(new Rectangle(x, y, w, h));
    }
    
    @Override
    public void drawTriangle(Triangle... triangle) {
        batcher.clearTextures();
        
        float w = batcher.getTexture().getWidth(), h = batcher.getTexture().getHeight();
        
        for (Triangle _triangle : triangle) {
            float x1 = _triangle.getVertices()[0].x;
            float y1 = _triangle.getVertices()[0].y;
            float x2 = _triangle.getVertices()[1].x;
            float y2 = _triangle.getVertices()[1].y;
            float x3 = _triangle.getVertices()[2].x;
            float y3 = _triangle.getVertices()[2].y;
            
            batcher.flushIfOverflow(3);
            
            batcher.vertex(x1, y1, x1 / w, y1 / h);
            batcher.vertex(x3, y3, x3 / w, y3 / h);
            batcher.vertex(x2, y2, x2 / w, y2 / h);
        }
    }
    
    @Override
    public void drawTriangle(float x, float y, float w, float h) {
        drawTriangle(new Triangle(x, y, w, h));
    }
    
    @Override
    public void drawPolygon(Polygon... polygon) {
        batcher.clearTextures();
        
        float w = batcher.getTexture().getWidth(), h = batcher.getTexture().getHeight();
        
        for (Polygon _polygon : polygon) {
            Vec2[] vs = _polygon.getVertices();
            
            for (int c = 0; c < vs.length; c++) {
                batcher.flushIfOverflow(3);
                
                batcher.vertex(_polygon.getCenter().x, _polygon.getCenter().y, _polygon.getCenter().x / w,
                        _polygon.getCenter().y / h);
                
                if (c >= vs.length - 1)
                    batcher.vertex(vs[0].x, vs[0].y, vs[0].x / w, vs[0].y / h);
                else
                    batcher.vertex(vs[c].x, vs[c].y, vs[c].x / w, vs[c].y / h);
                
                if (c >= vs.length - 1)
                    batcher.vertex(vs[vs.length - 1].x, vs[vs.length - 1].y, vs[vs.length - 1].x / w,
                            vs[vs.length - 1].y / h);
                else
                    batcher.vertex(vs[c + 1].x, vs[c + 1].y, vs[c + 1].x / w, vs[c + 1].y / h);
            }
        }
    }
    
    @Override
    public void drawStar(Star... star) {
        drawPolygon(star);
    }
    
    @Override
    public void drawEllipse(Ellipse... ellipse) {
        drawPolygon(ellipse);
    }
    
    @Override
    public void drawEllipse(float x, float y, float radx, float rady) {
        drawEllipse(new Ellipse(x, y, radx, rady));
    }
    
    @Override
    public void drawCircle(Circle... circle) {
        drawEllipse(circle);
    }
    
    @Override
    public void drawCircle(float x, float y, float radius) {
        drawCircle(new Circle(x, y, radius));
    }
    
    @Override
    public void traceShape(Shape... shapes) {
        for (Shape shape : shapes)
            if (shape instanceof Circle)
                traceCircle((Circle) shape);
            else if (shape instanceof Ellipse)
                traceEllipse((Ellipse) shape);
            else if (shape instanceof Line)
                drawLine((Line) shape);
            else if (shape instanceof Point)
                drawPoint((Point) shape);
            else if (shape instanceof Polygon)
                tracePolygon((Polygon) shape);
            else if (shape instanceof Rectangle)
                traceRect((Rectangle) shape);
            else if (shape instanceof Star)
                traceStar((Star) shape);
            else if (shape instanceof Triangle)
                traceTriangle((Triangle) shape);
            else
                Logger.warn("Shape type not found; nothing rendered.");
    }
    
    @Override
    public void traceRect(Rectangle... rectangle) {
        batcher.clearTextures();
        
        for (Rectangle _rectangle : rectangle) {
            Vec2 p1 = _rectangle.getVertices()[0];
            Vec2 p2 = _rectangle.getVertices()[1];
            Vec2 p3 = _rectangle.getVertices()[2];
            Vec2 p4 = _rectangle.getVertices()[3];
            
            batcher.flushIfOverflow(24);
            
            drawFunctionlessLine(new Line(p1, p2));
            drawFunctionlessLine(new Line(p2, p3));
            drawFunctionlessLine(new Line(p3, p4));
            drawFunctionlessLine(new Line(p4, p1));
        }
    }
    
    @Override
    public void traceRect(float x, float y, float w, float h) {
        traceRect(new Rectangle(x, y, w, h));
    }
    
    @Override
    public void traceTriangle(Triangle... triangle) {
        batcher.clearTextures();
        
        for (Triangle _triangle : triangle) {
            Vec2 p1 = _triangle.getVertices()[0];
            Vec2 p2 = _triangle.getVertices()[1];
            Vec2 p3 = _triangle.getVertices()[2];
            
            batcher.flushIfOverflow(18);
            
            drawFunctionlessLine(new Line(p1, p2));
            drawFunctionlessLine(new Line(p2, p3));
            drawFunctionlessLine(new Line(p3, p1));
        }
    }
    
    @Override
    public void traceTriangle(float x, float y, float w, float h) {
        traceTriangle(new Triangle(x, y, w, h));
    }
    
    @Override
    public void tracePolygon(Polygon... polygon) {
        batcher.clearTextures();
        for (Polygon _polygon : polygon) {
            batcher.flushIfOverflow(_polygon.getVertices().length * 6);
            
            Vec2[] vs = _polygon.getVertices();
            
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
    public void traceStar(Star... star) {
        tracePolygon(star);
    }
    
    @Override
    public void traceEllipse(Ellipse... ellipse) {
        tracePolygon(ellipse);
    }
    
    @Override
    public void traceEllipse(float x, float y, float radx, float rady) {
        traceEllipse(new Ellipse(x, y, radx, rady));
    }
    
    @Override
    public void traceCircle(Circle... circle) {
        traceEllipse(circle);
    }
    
    @Override
    public void traceCircle(float x, float y, float radius) {
        traceCircle(new Circle(x, y, radius));
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
        float angle = MercMath.atan2(dx, dy) - 90;
        
        Vec2 p1 = new Vec2(l.getVertices()[0].x - MercMath.cos(angle) * linewidth / 2, l.getVertices()[0].y
                - MercMath.sin(angle) * linewidth / 2);
        Vec2 p2 = new Vec2(l.getVertices()[0].x + MercMath.cos(angle) * linewidth / 2, l.getVertices()[0].y
                + MercMath.sin(angle) * linewidth / 2);
        Vec2 p3 = new Vec2(l.getVertices()[1].x + MercMath.cos(angle) * linewidth / 2, l.getVertices()[1].y
                + MercMath.sin(angle) * linewidth / 2);
        Vec2 p4 = new Vec2(l.getVertices()[1].x - MercMath.cos(angle) * linewidth / 2, l.getVertices()[1].y
                - MercMath.sin(angle) * linewidth / 2);
        
        drawFunctionlessRect(new Rectangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y));
    }
    
    @Override
    public void drawLine(Line... l) {
        batcher.clearColors();
        
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
            drawRect(new Rectangle(x, y, x + 1, y, x + 1, y + 1, x, y + 1));
        }
    }
    
    @Override
    public void drawPoint(float x, float y) {
        drawPoint(new Point(x, y));
    }
}