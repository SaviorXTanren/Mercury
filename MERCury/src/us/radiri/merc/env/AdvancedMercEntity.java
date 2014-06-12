package us.radiri.merc.env;

import us.radiri.merc.geo.Ellipse;
import us.radiri.merc.geo.Rectangle;
import us.radiri.merc.gfx.Graphics;
import us.radiri.merc.gfx.Texture;

/**
 * A more advanced version of MercEntity, with movement and stuffs.
 * 
 * @author wessles
 */

public abstract class AdvancedMercEntity implements MercEntity {
    /** A texture to render the entity with (if provided) */
    public Texture tex;
    
    /** The x position */
    public float x;
    /** The y position */
    public float y;
    /** The x size */
    public float w;
    /** The y size */
    public float h;
    
    /**
     * @param x
     *            The x position
     * @param y
     *            The y position
     * @param w
     *            The x size
     * @param h
     *            The y size
     * @param tex
     *            A texture to render the entity with (if provided)
     */
    public AdvancedMercEntity(float x, float y, float w, float h, Texture tex) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tex = tex;
    }
    
    /**
     * @param x
     *            The x position
     * @param y
     *            The y position
     * @param tex
     *            A texture to render the entity with (if provided)
     */
    public AdvancedMercEntity(float x, float y, Texture tex) {
        this.x = x;
        this.y = y;
        this.tex = tex;
        
        w = this.tex.getTextureWidth();
        h = this.tex.getTextureHeight();
    }
    
    /**
     * @param x
     *            The x position
     * @param y
     *            The y position
     * @param w
     *            The x size
     * @param h
     *            The y size
     */
    public AdvancedMercEntity(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    @Override
    public void render(Graphics g) {
        if (tex != null)
            g.drawTexture(tex, 0, 0, tex.getTextureWidth(), tex.getTextureHeight(), x, y, x + w, y + h);
    }
    
    /**
     * Moves the entity if the hypothetical position passed into isValidPosition
     * is okay.
     * 
     * @param mx
     *            Movement x
     * @param my
     *            Movement y
     */
    public void move(float mx, float my) {
        float dx = x + mx, dy = y + my;
        
        if (isValidPosition(dx, y))
            x += mx;
        
        if (isValidPosition(x, dy))
            y += my;
    }
    
    /**
     * Returns whether or not a position is valid (i.e. does not collide with
     * walls or something) given x and y.
     * 
     * @param x
     *            The hypothetical x position.
     * @param y
     *            The hypothetical y position.
     */
    public abstract boolean isValidPosition(float x, float y);
    
    /**
     * @return A rectangle representing the 'bounds' of the entity.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
    
    /**
     * @return An ellipse representing the 'bounds' of the entity.
     */
    public Ellipse getRadBounds() {
        return new Ellipse(x + w / 2, y + h / 2, w / 2, h / 2);
    }
}
