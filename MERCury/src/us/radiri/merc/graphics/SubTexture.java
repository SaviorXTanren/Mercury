package us.radiri.merc.graphics;

/**
 * @author opiop65 (base code), wessles (modifications)
 */

public class SubTexture {
    
    private Texture parent;
    private float x, y, w, h;
    
    public SubTexture(Texture parent, float x, float y, float w, float h) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public Texture getParent() {
        return parent;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getWidth() {
        return w;
    }
    
    public float getHeight() {
        return h;
    }
}
