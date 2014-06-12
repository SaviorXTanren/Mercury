package us.radiri.merc.env;

import us.radiri.merc.gfx.Graphics;

/**
 * An abstraction for objects that can be rendered.
 * 
 * @author wessles
 */

public interface Renderable {
    /**
     * The render method. In here there should be peripheral activity, such as
     * graphics, or sound, given g.
     * 
     * @param g
     *            The graphics object.
     */
    public void render(Graphics g);
}
