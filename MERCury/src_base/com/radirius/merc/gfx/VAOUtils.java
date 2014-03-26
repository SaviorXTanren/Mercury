package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * A utilities class for vertex array objects.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class VAOUtils
{
    public static final int TEXTURE_COORD_ARRAY_POINTER = 0, VERTEX_ARRAY_POINTER = 1, COLOR_ARRAY_POINTER = 2;
    
    public static FloatBuffer getFloatBuffer(int amount, float[] data)
    {
        return getFloatBuffer(2, amount, data);
    }
    
    public static FloatBuffer getFloatBuffer(int size, int amount, float[] data)
    {
        FloatBuffer buff = BufferUtils.createFloatBuffer(size * amount);
        buff.put(data);
        buff.flip();
        return buff;
    }
    
    public static void pointBuffer(int type, FloatBuffer buff)
    {
        pointBuffer(type, 2, buff);
    }
    
    public static void pointBuffer(int type, int size, FloatBuffer buff)
    {
        if (type == TEXTURE_COORD_ARRAY_POINTER)
            glTexCoordPointer(size, 0, buff);
        else if (type == VERTEX_ARRAY_POINTER)
            glVertexPointer(size, 0, buff);
        else if (type == COLOR_ARRAY_POINTER)
            glColorPointer(size, 0, buff);
    }
    
    public static void drawBuffers(int mode, int vertices)
    {
        glDrawArrays(mode, 0, vertices);
    }
    
    public static void enableBuffer(int cap)
    {
        glEnableClientState(cap);
    }
    
    public static void disableBuffer(int cap)
    {
        glDisableClientState(cap);
    }
}
