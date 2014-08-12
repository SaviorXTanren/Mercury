package radirius.merc.graphics;

import radirius.merc.math.geometry.Rectangle;
import radirius.merc.resource.Resource;

/**
 * An easy to use animation class. Just render, and watch the moving picture.
 * 
 * @author wessles
 */
public class Animation implements Resource {
    
    private SpriteSheet texs;
    
    private int frame = 0;
    private int first = 0, last = 0;
    private boolean bounce;
    private int framestep = 1;
    
    private int frameratemillis;
    private long framemillis = 0, lastframemillis;
    
    /**
     * @param frameratemillis
     *            The frame rate in milliseconds
     * @param texs
     *            The textures, or frames.
     */
    public Animation(int frameratemillis, SpriteSheet texs) {
        this(frameratemillis, texs, 0, texs.getNumberOfSubTextures() - 1);
    }
    
    /**
     * @param frameratemillis
     *            The frame rate in milliseconds
     * @param texs
     *            The textures, or frames.
     */
    public Animation(int frameratemillis, SpriteSheet texs, boolean bounce) {
        this(frameratemillis, texs, 0, texs.getNumberOfSubTextures() - 1, bounce);
    }
    
    /**
     * @param frameratemillis
     *            The frame rate in milliseconds
     * @param texs
     *            The textures, or frames.
     * @param startframe
     *            The first frame of the animation.
     * @param endframe
     *            The last frame of the animation.
     */
    public Animation(int frameratemillis, SpriteSheet texs, int startframe, int endframe) {
        this(frameratemillis, texs, startframe, endframe, false);
    }
    
    /**
     * @param frameratemillis
     *            The frame rate in milliseconds
     * @param texs
     *            The textures, or frames.
     * @param startframe
     *            The first frame of the animation.
     * @param endframe
     *            The last frame of the animation.
     * @param bounce
     *            Whether or not the animation should reverse once it gets to
     *            the end.
     */
    public Animation(int frameratemillis, SpriteSheet texs, int startframe, int endframe, boolean bounce) {
        this.frameratemillis = frameratemillis;
        this.texs = texs;
        
        frame = 0;
        
        if (startframe < 0 || startframe > texs.getNumberOfSubTextures())
            throw new ArithmeticException("Invalid starting frame.");
        if (endframe < 0 || endframe > texs.getNumberOfSubTextures())
            throw new ArithmeticException("Invalid ending frame.");
        
        first = startframe;
        last = endframe + 1;
        
        this.bounce = bounce;
    }
    
    /**
     * Renders the current frame at x and y.
     */
    public void render(float x, float y, Graphics g) {
        render(x, y, texs.getTexture(frame).getWidth(), texs.getTexture(0).getHeight(), g);
    }
    
    /**
     * Renders the current frame at x and y, with a size of width w by height h.
     */
    public void render(float x, float y, float w, float h, Graphics g) {
        render(new Rectangle(x, y, w, h), g);
    }
    
    public void render(Rectangle bounds, Graphics g) {
        g.drawTexture(texs.getTexture(frame), bounds);
    }
    
    /**
     * Useful if you want to render the frames in a special way.
     * 
     * @return The current frame.
     */
    public SubTexture getCurrentFrame() {
        return texs.getTexture(frame);
    }
    
    /**
     * Moves on to the next frame in the animation if the current frame has been
     * around more than the frame-rate.
     * 
     * @return If this is the last frame.
     */
    public boolean nextFrame() {
        framemillis = System.currentTimeMillis();
        
        if (framemillis - lastframemillis >= frameratemillis) {
            
            frame += framestep;
            
            if (!(frame + framestep <= last && frame + framestep >= 0))
                if (bounce)
                    framestep *= -1;
                else
                    frame = first;
            
            lastframemillis = System.currentTimeMillis();
            return frame == first;
        }
        
        return false;
    }
    
    /** Sets the current frame. */
    public void setFrame(int frame) {
        this.frame = frame;
    }
    
    /** @return The current frame. */
    public int getFrame() {
        return frame;
    }
    
    /** @return How many frames there are. */
    public int getLength() {
        return last - first;
    }
    
    /** @return Whether or not we are at the last frame. */
    public boolean isLastFrame() {
        return bounce ? frame == first : frame == last;
    }
    
    /** @return The textures for all the frames. */
    public SpriteSheet getTextures() {
        return texs;
    }
    
    @Override
    public void clean() {
        texs.clean();
    }
}
