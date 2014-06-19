package us.radiri.merc.gfx;

import static org.lwjgl.opengl.GL11.*;
import static us.radiri.merc.gfx.VAOUtils.COLOR_ARRAY_POINTER;
import static us.radiri.merc.gfx.VAOUtils.TEXTURE_COORD_ARRAY_POINTER;
import static us.radiri.merc.gfx.VAOUtils.VERTEX_ARRAY_POINTER;
import static us.radiri.merc.gfx.VAOUtils.disableBuffer;
import static us.radiri.merc.gfx.VAOUtils.drawBuffers;
import static us.radiri.merc.gfx.VAOUtils.enableBuffer;
import static us.radiri.merc.gfx.VAOUtils.pointBuffer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import us.radiri.merc.log.Logger;

/**
 * A very simple batcher.
 * 
 * @author wessles
 */
public class VAOBatcher implements Batcher {
    private static final int VL = 2, CL = 4, TL = 2;
    public static final int MAX_VERTICES_PER_RENDER_STACK = 4096;
    
    private FloatBuffer vd, cd, td;
    
    private int vtxcount;
    private int vertexlastrender = 0;
    
    private boolean active;
    
    private Texture last_tex = Texture.getEmptyTexture();
    private Color last_col = Color.DEFAULT_DRAWING;
    private Shader last_shader = Shader.getDefaultShader();
    
    public VAOBatcher() {
        vtxcount = 0;
        
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
        
        vertexlastrender = 0;
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
        
        drawBuffers(GL_TRIANGLES, vtxcount);
        
        disableBuffer(GL_VERTEX_ARRAY);
        disableBuffer(GL_COLOR_ARRAY);
        disableBuffer(GL_TEXTURE_COORD_ARRAY);
        
        vd.clear();
        cd.clear();
        td.clear();
        
        vertexlastrender += vtxcount;
        vtxcount = 0;
    }
    
    @Override
    public void setTexture(Texture texture) {
        if (texture.equals(last_tex))
            return;
        flush();
        last_tex = texture;
        Texture.bindTexture(texture);
    }
    
    @Override
    public void clearTextures() {
        if (last_tex.equals(Texture.getEmptyTexture()))
            return;
        flush();
        last_tex = Texture.getEmptyTexture();
        Texture.bindTexture(last_tex);
    }
    
    @Override
    public void setColor(Color color) {
        if (color.equals(last_col))
            return;
        
        last_col = color;
    }
    
    @Override
    public void clearColors() {
        if (last_col.equals(Color.DEFAULT_DRAWING))
            return;
        last_col = Color.DEFAULT_DRAWING;
    }
    
    @Override
    public void setShader(Shader shader) {
        if (last_shader.equals(shader))
            return;
        flush();
        last_shader = shader;
        Shader.useShader(shader);
    }
    
    @Override
    public void clearShaders() {
        if (last_shader.equals(Shader.getDefaultShader()))
            return;
        flush();
        last_shader = Shader.getDefaultShader();
        Shader.useShader(last_shader);
    }
    
    @Override
    public void vertex(float x, float y, float u, float v) {
        vertex(x, y, last_col, u, v);
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
        
        vtxcount++;
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
        if (vtxcount + allocate > MAX_VERTICES_PER_RENDER_STACK)
            flush();
    }
    
    @Override
    public int getVerticesLastRendered() {
        return vertexlastrender;
    }
}