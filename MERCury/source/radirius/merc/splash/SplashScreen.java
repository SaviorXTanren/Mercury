package radirius.merc.splash;

import radirius.merc.framework.Runner;
import radirius.merc.geometry.Rectangle;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Texture;
import radirius.merc.resource.Loader;
import radirius.merc.utils.TaskTiming;
import radirius.merc.utils.TaskTiming.Task;

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
   *          The texture of the splash screen.
   * @param showtimemillis
   *          The time that the splash screen is shown.
   */
  public SplashScreen(Texture tex, long showtimemillis) {
    this.showtimemillis = showtimemillis;
    this.tex = tex;
  }
  
  /**
   * Shows the splash screen on screen, whilst checking if it is time to stop as
   * well.
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
    
    // Fit to the camera
    Rectangle cam = Runner.getInstance().getCamera().getBounds();
    float scale = cam.getWidth() / tex.width;
    float width = cam.getWidth();
    float height = tex.height * scale;
    scale = cam.getHeight() / height;
    height = cam.getHeight();
    width *= scale;
    
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
    tex = Texture.loadTexture(Loader.streamFromClasspath("radirius/merc/splash/splash.png"), Texture.FILTER_LINEAR);
    
    return new SplashScreen(tex, 3000);
  }
}
