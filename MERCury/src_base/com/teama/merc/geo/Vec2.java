package com.teama.merc.geo;

import com.teama.merc.math.MercMath;

/**
 * A Vector that contains 2 values: x and y. This class makes vector math easy, and makes your life a whole lot easier.
 * 
 * @from merc in com.teama.merc.geo
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */
public class Vec2 extends Vec
{
    public float x = 0, y = 0;
    
    public Vec2()
    {
    }
    
    public Vec2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public Vec2(float theta)
    {
        x = MercMath.cos(theta);
        y = MercMath.sin(theta);
    }
    
    @Override
    public void add(Vec vec)
    {
        Vec2 vec2 = (Vec2) vec;
        x += vec2.x;
        y += vec2.y;
    }
    
    public void add(float theta)
    {
        x += (float) Math.cos(theta);
        y += (float) Math.sin(theta);
    }
    
    @Override
    public void sub(Vec vec)
    {
        Vec2 vec2 = (Vec2) vec;
        x -= vec2.x;
        y -= vec2.y;
    }
    
    public void sub(float theta)
    {
        x -= MercMath.cos(theta);
        y -= MercMath.sin(theta);
    }
    
    @Override
    public void mul(Vec vec)
    {
        Vec2 vec2 = (Vec2) vec;
        x *= vec2.x;
        y *= vec2.y;
    }
    
    @Override
    public void div(Vec vec)
    {
        Vec2 vec2 = (Vec2) vec;
        x /= vec2.x;
        y /= vec2.y;
    }
    
    public void set(float theta)
    {
        x = (float) Math.toDegrees(Math.cos(theta));
        y = (float) Math.toDegrees(Math.sin(theta));
    }
    
    @Override
    public void set(Vec vec)
    {
        Vec2 vec2 = (Vec2) vec;
        x = vec2.x;
        y = vec2.y;
    }
    
    @Override
    public void set(float... coords)
    {
        set(new Vec2(coords[0], coords[1]));
    }
    
    @Override
    public void scale(float a)
    {
        x *= a;
        y *= a;
    }
    
    @Override
    public void negate()
    {
        scale(-1);
    }
    
    @Override
    public float length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    @Override
    public void normalize()
    {
        float l = length();
        x /= l;
        y /= l;
    }
    
    @Override
    public float dot(Vec other)
    {
        Vec2 vec2 = (Vec2) other;
        return x * vec2.x + y * vec2.y;
    }
    
    @Override
    public float distance(Vec other)
    {
        Vec2 vec2 = (Vec2) other;
        float dx = vec2.x - x;
        float dy = vec2.y - y;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
    
    public void rotate(float angle)
    {
        double rad = MercMath.toRadians(angle);
        double cos = MercMath.cos((float) rad);
        double sin = MercMath.sin((float) rad);
        
        x = (float) (x * cos - y * sin);
        y = (float) (x * sin + y * cos);
    }
    
    public float theta()
    {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }
    
    @Override
    public Vec copy()
    {
        return this;
    }
    
    @Override
    public String toString()
    {
        return "Vec2(" + x + ", " + y + ")";
    }
}
