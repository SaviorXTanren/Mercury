package org.radirius.merc.geom;

import org.radirius.merc.math.MercMath;

/**
 * A class for 2 dimensional vectors.
 * 
 * @author wessles
 */
public class Vec2 extends Vec {
    public float x = 0, y = 0;
    
    public Vec2() {
    }
    
    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2(float theta) {
        x = MercMath.cos(theta);
        y = MercMath.sin(theta);
    }
    
    @Override
    public Vec add(Vec vec) {
        Vec2 vec2 = (Vec2) vec;
        x += vec2.x;
        y += vec2.y;
        
        return this;
    }
    
    public Vec add(float theta) {
        x += (float) Math.cos(theta);
        y += (float) Math.sin(theta);
        
        return this;
    }
    
    @Override
    public Vec sub(Vec vec) {
        Vec2 vec2 = (Vec2) vec;
        x -= vec2.x;
        y -= vec2.y;
        
        return this;
    }
    
    public Vec sub(float theta) {
        x -= MercMath.cos(theta);
        y -= MercMath.sin(theta);
        
        return this;
    }
    
    @Override
    public Vec mul(Vec vec) {
        Vec2 vec2 = (Vec2) vec;
        x *= vec2.x;
        y *= vec2.y;
        
        return this;
    }
    
    @Override
    public Vec div(Vec vec) {
        Vec2 vec2 = (Vec2) vec;
        x /= vec2.x;
        y /= vec2.y;
        
        return this;
    }
    
    public Vec set(float theta) {
        x = (float) Math.toDegrees(Math.cos(theta));
        y = (float) Math.toDegrees(Math.sin(theta));
        
        return this;
    }
    
    @Override
    public Vec set(Vec vec) {
        Vec2 vec2 = (Vec2) vec;
        x = vec2.x;
        y = vec2.y;
        
        return this;
    }
    
    @Override
    public Vec set(float... coords) {
        set(new Vec2(coords[0], coords[1]));
        
        return this;
    }
    
    @Override
    public Vec scale(float a) {
        x *= a;
        y *= a;
        
        return this;
    }
    
    @Override
    public Vec negate() {
        scale(-1);
        
        return this;
    }
    
    @Override
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }
    
    @Override
    public Vec normalize() {
        float l = length();
        x /= l;
        y /= l;
        
        return this;
    }
    
    @Override
    public float dot(Vec other) {
        Vec2 vec2 = (Vec2) other;
        return x * vec2.x + y * vec2.y;
    }
    
    @Override
    public float distance(Vec other) {
        Vec2 vec2 = (Vec2) other;
        float dx = vec2.x - x;
        float dy = vec2.y - y;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
    
    public Vec rotate(float angle) {
        double rad = MercMath.toRadians(angle);
        double cos = MercMath.cos((float) rad);
        double sin = MercMath.sin((float) rad);
        
        x = (float) (x * cos - y * sin);
        y = (float) (x * sin + y * cos);
        
        return this;
    }
    
    public float theta() {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }
    
    @Override
    public Vec copy() {
        return new Vec2(x, y);
    }
    
    @Override
    public String toString() {
        return "Vec2(" + x + ", " + y + ")";
    }
}
