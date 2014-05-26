package com.radirius.merc.gfx;

import com.radirius.merc.res.Resource;

/**
 * @author opiop65 (base code), wessles (modifications)
 */

public class SpriteSheet implements Resource {
    
    private Texture tex;
    private SubTexture[] subtexs;
    
    private SpriteSheet(Texture tex, SubTexture[] subtexs) {
        this.tex = tex;
        this.subtexs = subtexs;
    }
    
    public int getNumberOfSubTextures() {
        return subtexs.length;
    }
    
    /**
     * @return The parent texture of all subtextures in the SpriteSheet.
     */
    public Texture getParentTexture() {
        return tex;
    }
    
    /**
     * @return The texture corresponding to the texnum.
     */
    public SubTexture getTexture(int texnum) {
        return subtexs[texnum];
    }
    
    /**
     * Slices the Texture tex up, cutting vertically every divwidth length.
     */
    public static SpriteSheet loadSpriteSheet(Texture tex, float divwidth) {
        return loadSpriteSheet(tex, divwidth, tex.getTextureHeight());
    }
    
    /**
     * Slices the Texture tex up, cutting vertically every divwidth length, and
     * cutting horizontally every divheight length. The subtextures are counted
     * reading left to right.
     */
    public static SpriteSheet loadSpriteSheet(Texture tex, float divwidth, float divheight) {
        if (tex.getTextureWidth() % divwidth != 0)
            throw new ArithmeticException("The width of the Texture must be divisible by the division width!");
        
        // Number of subtextures that can fit on the x and y axis
        int numx = (int) (tex.getTextureWidth() / divwidth);
        int numy = (int) (tex.getTextureHeight() / divheight);
        // The subtextures!
        SubTexture[] subtexs = new SubTexture[numx * numy];
        
        for (int y = 0; y < numy; y++)
            for (int x = 0; x < numx; x++)
                subtexs[x + y * numx] = new SubTexture(tex, x * divwidth, y * divheight, divwidth, divheight);
        
        return loadSpriteSheet(tex, subtexs);
    }
    
    /**
     * Creates a spritesheet based off of a Texture tex, and a bunch of
     * SubTextures.
     */
    public static SpriteSheet loadSpriteSheet(Texture tex, SubTexture[] subtexs) {
        return new SpriteSheet(tex, subtexs);
    }
    
    @Override
    public void clean() {
        tex.clean();
    }
}