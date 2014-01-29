package com.teama.merc.gfx;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import com.teama.merc.geo.Vec2;

/**
 * An object for the camera.
 * 
 * @from merc in com.teama.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class Camera
{
    public int SCALE = 1;
    
    private float x, y;
    
    private Vec2 snap;
    private Vec2 relsnap;
    private boolean snapped = false;
    
    public Camera(float x, float y)
    {
        this.x = x;
        this.y = y;
        relsnap = new Vec2(0, 0);
    }
    
    public void pre(Graphics g)
    {
        if (snap != null)
        {
            snapped = true;
            
            x = snap.x;
            y = snap.y;
        } else
            snapped = false;
        
        glPushMatrix();
        glTranslatef(-x + relsnap.x, -y + relsnap.y, 0);
        
        g.pre();
    }
    
    public void post(Graphics g)
    {
        g.post();
        
        glPopMatrix();
    }
    
    public void snapTo(Vec2 snap)
    {
        this.snap = snap;
    }
    
    public void setSnapRelativeToScreen(Vec2 relsnap)
    {
        this.relsnap = relsnap;
    }
    
    public Vec2 getSnap()
    {
        return snap;
    }
    
    public boolean snapped()
    {
        return snapped;
    }
    
    public float getOffsetX()
    {
        return x;
    }
    
    public float getOffsetY()
    {
        return y;
    }
    
    public Vec2 getOffset()
    {
        return new Vec2(x, y);
    }
}
