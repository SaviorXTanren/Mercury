package radirius.merc.main.splash;

import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.main.Runner;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.resource.Loader;
import radirius.merc.utilities.TaskTiming;
import radirius.merc.utilities.TaskTiming.Task;
import radirius.merc.utilities.easing.EasingUtils;
import radirius.merc.utilities.easing.EasingValue;

/**
 * @author wessles
 */

public class SplashScreen {
    public boolean showing = false;
    private boolean _return_ = true;
    
    public long showtimemillis;
    public Texture tex;
    private boolean fittoscreen;
    
    /**
     * @param tex
     *            The texture of the splash screen.
     * @param showtimemillis
     *            The time that the splash screen is shown.
     * @param fittoscreen
     *            Whether or not to fit the image to the screen while still
     *            maintaining the aspect ratio.
     */
    public SplashScreen(Texture tex, long showtimemillis, boolean fittoscreen) {
        this.showtimemillis = showtimemillis;
        this.tex = tex;
        this.fittoscreen = fittoscreen;
    }
    
    /**
     * @param tex
     *            The texture of the splash screen.
     * @param showtimemillis
     *            The time that the splash screen is shown.
     */
    public SplashScreen(Texture tex, long showtimemillis) {
        this(tex, showtimemillis, false);
    }
    
    EasingValue easeval;
    
    /**
     * Shows the splash screen on screen, whilst checking if it is time to stop
     * as well.
     * 
     * @return Whether or not the splash is done.
     */
    public boolean show(Graphics g) {
        if (!showing) {
            TaskTiming.addTask(new Task(showtimemillis) {
                @Override
                public void run() {
                    _return_ = false;
                }
            });
            
            easeval = new EasingValue(EasingUtils.BOUNCING_EASE_QUINT, 0, 1, showtimemillis);
            
            showing = true;
        }
        
        Rectangle cam = Runner.getInstance().getCamera().getBounds();
        float width = tex.getWidth();
        float height = tex.getHeight();
        
        if (fittoscreen) {
            // Fit to the camera
            float scale = cam.getWidth() / tex.getWidth();
            width = cam.getWidth();
            height = tex.getHeight() * scale;
            scale = cam.getHeight() / height;
            height = cam.getHeight();
            width *= scale;
        }
        
        g.setColor(new Color(0, 0, 0, easeval.get()));
        g.drawTexture(tex, cam.getX() + cam.getWidth() / 2 - width / 2, cam.getY() + cam.getHeight() / 2 - height / 2, width, height);
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
        tex = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/main/splash/splash.png"), Texture.FILTER_LINEAR);
        
        return new SplashScreen(tex, 3000);
    }
}
