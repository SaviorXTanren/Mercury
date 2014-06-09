package com.radirius.merc.spl;

import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Vec2;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.res.Loader;
import com.radirius.merc.util.TaskTiming;
import com.radirius.merc.util.TaskTiming.Task;

/**
 * @author wessles
 */

public class SplashScreen {
    public boolean showing = false;
    private boolean _return_ = true;
    
    public long showtimemillis;
    public Texture tex;
    
    /**
     * @param tex
     *            The texture of the splash screen.
     * @param showtimemillis
     *            The time that the splash screen is shown.
     */
    public SplashScreen(Texture tex, long showtimemillis) {
        this.showtimemillis = showtimemillis;
        this.tex = tex;
    }
    
    /**
     * Shows the splash screen on screen, whilst checking if it is time to stop
     * as well.
     * 
     * @return Whether or not the splash is done.
     */
    public boolean show(Graphics g) {
        if (!showing) {
            // Evil timer
            TaskTiming.addTask(new Task(showtimemillis) {
                @Override
                public void run() {
                    _return_ = false;
                }
            });
            
            showing = true;
        }
        
        Vec2 scale = Runner.getInstance().getGraphics().getScale();
        int scrw = (int) (Runner.getInstance().getWidth() / scale.x), scrh = (int) (Runner.getInstance().getHeight() / scale.y);
        float width = tex.getTextureWidth(), height = tex.getTextureHeight();
        float aspect = width / height;
        
        width = scrw;
        height = width / aspect;
        
        g.drawTexture(tex, 0, 0 + scrh / 2 - height / 2, width, height);
        return _return_;
    }
    
    /**
     * Show some love for MERCury and give some credit!
     * 
     * @return The love of all developers from MERCury, unless you are a child
     *         murderer. Even if you code you can't get anybody's love. Sicko.
     */
    public static SplashScreen getMERCuryDefault() {
        Texture tex = null;
        tex = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/spl/splash.png"));
        
        return new SplashScreen(tex, 3000);
    }
}
