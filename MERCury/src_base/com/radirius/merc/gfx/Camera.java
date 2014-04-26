package com.radirius.merc.gfx;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Vec2;

/**
 * An object for the camera.
 * 
 * @from merc in com.radirius.merc.gfx
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class Camera
{
    private float x, y;
    private Vec2 origin = new Vec2(0, 0);

    public Camera(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void pre(Graphics g)
    {
        glPushMatrix();
        glTranslatef(x - origin.x, y - origin.y, 0);

        g.pre();
    }

    public void post(Graphics g)
    {
        g.post();

        glPopMatrix();
    }

    public void setOrigin(Vec2 origin)
    {
        this.origin = origin;
    }

    public Vec2 getOrigin()
    {
        return origin;
    }

    public void zoom(float zoom, Graphics g)
    {
        g.scale(zoom);
    }
    
    public void translate(float x, float y)
    {
        this.x -= x;
        this.y -= y;
    }

    public float getPositionX()
    {
        return getPosition().x;
    }

    public float getPositionY()
    {
        return getPosition().y;
    }

    public Vec2 getPosition()
    {
        return new Vec2(Runner.getInstance().getWidth()-x, Runner.getInstance().getHeight()-y);
    }
}
