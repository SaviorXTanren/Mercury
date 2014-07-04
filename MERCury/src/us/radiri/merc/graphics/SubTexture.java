package us.radiri.merc.graphics;

/**
 * @author opiop65 (base code), wessles (modifications)
 */

public class SubTexture extends Texture {
    
    private Texture parent;
    private float x, y, w, h;
    
    public SubTexture(Texture parent, float x, float y, float w, float h) {
        super(parent.getTextureId(), parent.getWidth(), parent.getHeight(), parent.isPoT(), parent.getSourceImage());
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public Texture getParent() {
        return parent;
    }
    
    public float getSubX() {
        return x;
    }
    
    public float getSubY() {
        return y;
    }
    
    public float getSubWidth() {
        return w;
    }
    
    public float getSubHeight() {
        return h;
    }
}
