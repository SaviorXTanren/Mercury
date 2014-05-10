package com.radirius.merc.gfx;

/**
 * @author opiop65
 */

public class SubTexture {
    
    private Spritesheet sheet;
    private float x, y, size;
    
    public SubTexture(Spritesheet sheet, float x, float y, float size) {
        this.sheet = sheet;
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    public Spritesheet getSheet() {
        return sheet;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getSize() {
        return size;
    }
    
    @Override
    public String toString() {
        return "SIZE: " + size + " X: " + x + " Y: " + y;
    }
}
