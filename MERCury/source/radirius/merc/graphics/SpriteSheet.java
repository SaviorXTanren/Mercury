package radirius.merc.graphics;

import radirius.merc.resource.Resource;

/**
 * @author opiop65 (base code), wessles (modifications)
 */

public class SpriteSheet implements Resource {
    private Texture basetex;
    private SubTexture[] subtexs;
    
    private SpriteSheet(Texture basetex, SubTexture... subtexs) {
        this.basetex = basetex;
        this.subtexs = subtexs;
    }
    
    /** @return The number of subtextures. */
    public int getNumberOfSubTextures() {
        return subtexs.length;
    }
    
    /**
     * @return The texture corresponding to the texnum.
     */
    public SubTexture getTexture(int texnum) {
        return subtexs[texnum];
    }
    
    /** @return The base texture for all SubTextures. */
    public Texture getBaseTexture() {
        return basetex;
    }
    
    /**
     * Slices the Texture tex up, cutting vertically every divwidth length.
     */
    public static SpriteSheet loadSpriteSheet(Texture tex, int divwidth) {
        return loadSpriteSheet(tex, divwidth, tex.getHeight());
    }
    
    /**
     * Slices the Texture tex up, cutting vertically every divwidth length, and
     * cutting horizontally every divheight length. The subtextures are counted
     * reading left to right.
     */
    public static SpriteSheet loadSpriteSheet(Texture tex, int divwidth, int divheight) {
        if (tex.getWidth() % divwidth != 0)
            throw new ArithmeticException("The width of the Texture must be divisible by the division width!");
        
        // Number of subtextures that can fit on the x and y axis
        int numx = (int) (tex.getWidth() / divwidth);
        int numy = (int) (tex.getHeight() / divheight);
        // The subtextures!
        SubTexture[] subtexs = new SubTexture[numx * numy];
        
        for (int y = 0; y < numy; y++)
            for (int x = 0; x < numx; x++)
                subtexs[x + y * numx] = new SubTexture(tex, x * divwidth, y * divheight, (x + 1) * divwidth, (y + 1)
                        * divheight);
        
        return new SpriteSheet(tex, subtexs);
    }
    
    /** @return A spritesheet based off of Texture tex, with SubTextures subtexs. */
    public static SpriteSheet loadSpriteSheet(Texture tex, SubTexture... subtexs) {
        return new SpriteSheet(tex, subtexs);
    }
    
    @Override
    public void clean() {
        
    }
}