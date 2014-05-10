package com.radirius.merc.gfx;

/**
 * @from MERCury_git in com.radirius.merc.gfx
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Mar 27, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
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
