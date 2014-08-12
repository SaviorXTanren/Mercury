package radirius.merc.graphics;

/**
 * @author opiop65 (base code), wessles (modifications)
 */

public class SubTexture extends Texture {
    
    private Texture parent;
    private int x, y, x2, y2;
    
    public SubTexture(Texture parent, int x, int y, int x2, int y2) {
        super(parent.getTextureId(), parent.getWidth(), parent.getHeight(), parent.getSourceImage(), parent.getBuffer());
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture() {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()));
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture(boolean fliphor, boolean flipvert) {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()), fliphor, flipvert);
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture(int filter) {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()), filter);
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture(boolean fliphor, boolean flipvert, int filter) {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()), fliphor, flipvert, filter);
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture(int rot, int filter) {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()), rot, filter);
    }
    
    /**
     * This is useful for when you want the functionality of a real Texture from
     * a SubTexture, such as GL_REPEAT, which is impossible with SubTextures.
     * 
     * @return A Texture version of the SubTexture.
     */
    public Texture convertToTexture(boolean fliphor, boolean flipvert, int rot, int filter) {
        return Texture.loadTexture(getParent().getSourceImage().getSubimage(x, y, getWidth(), getHeight()), fliphor, flipvert, rot, filter);
    }
    
    /** @return Returns the parent Texture. */
    public Texture getParent() {
        return parent;
    }
    
    /** @return The x location of the subtexture on the parent texture. */
    public int getSubX() {
        return x;
    }
    
    /** @return The y location of the subtexture on the parent texture. */
    public int getSubY() {
        return y;
    }
    
    /** @return The second x location of the subtexture on the parent texture. */
    public int getSubX2() {
        return x2;
    }
    
    /** @return The second y location of the subtexture on the parent texture. */
    public int getSubY2() {
        return y2;
    }
    
    /** @return The width of the subtexture of the parent texture. */
    @Override
    public int getWidth() {
        return x2 - x;
    }
    
    /** @return The height of the subtexture of the parent texture. */
    @Override
    public int getHeight() {
        return y2 - y;
    }
    
    /** @return The width of the parent texture. */
    public int getParentWidth() {
        return width;
    }
    
    /** @return The width of the parent texture. */
    public int getParentHeight() {
        return height;
    }
}
