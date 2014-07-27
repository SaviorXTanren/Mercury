package radirius.merc.framework;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.Shader;
import radirius.merc.graphics.VAOGraphics;
import radirius.merc.logging.Logger;
import radirius.merc.resource.Loader;

/**
 * The {@code Core} that will host the game. It is ran above by the
 * {@code Runner} class.
 * 
 * @author wessles
 */

public abstract class Core {
    public final String name;
    
    public Core(String name) {
        this.name = name;
    }
    
    /**
     * Called first (after {@code initDisplay}, {@code initGraphics}, and
     * {@code initAudio}), used to initialize all resources, and for whatever
     * you wish to do for initialization. Runs on a seperate thread, while the
     * main Thread continues to the game loop.
     */
    public abstract void init();
    
    /**
     * Called once every frame, and used to handle all logic. It is suggested
     * that you check that initialization has passed before you use resources.
     */
    public abstract void update(float delta);
    
    /**
     * Called once every frame, and used to render everything, via
     * {@code Graphics g}. It is suggested that you check that initialization
     * has passed before you use resources.
     */
    public abstract void render(Graphics g);
    
    /**
     * Called when the Runner is done
     */
    public abstract void cleanup();
    
    /**
     * Initializes the display.
     * 
     * @param WIDTH
     *            The width of the display
     * @param HEIGHT
     *            The height of the display
     * @param fullscreen
     *            Whether or not this is fullscreen
     * @param vsync
     *            Whether or not we should vsync
     */
    public void initDisplay(int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync) {
        try {
            Display.setVSyncEnabled(vsync);
            
            DisplayMode dm = new DisplayMode(WIDTH, HEIGHT);
            boolean screendimmatched = false;
            
            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                
                for (int i = 0; i < modes.length; i++)
                    if (modes[i].getWidth() == WIDTH && modes[i].getHeight() == HEIGHT && modes[i].isFullscreenCapable()) {
                        dm = modes[i];
                        screendimmatched = true;
                    }
                
                if (!screendimmatched)
                    Logger.warn("Dimensions " + WIDTH + "x" + HEIGHT + " is not supported! Defaulting to non-fullscreen.");
                else
                    Display.setFullscreen(true);
            }
            
            Display.setDisplayMode(dm);
            Display.setTitle(name);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        
        Runner.getInstance().setIcon(Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x64.png"), Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x32.png"), Loader.streamFromClasspath("radirius/merc/framework/merc_mascot_x16.png"));
    }
    
    /**
     * Initializes graphics.
     */
    public Graphics initGraphics() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        VAOGraphics graphicsobject = new VAOGraphics();
        Shader.loadDefaultShaders();
        Shader.releaseShaders();
        
        return graphicsobject;
    }
    
    /**
     * Initializes audio.
     */
    public void initAudio() {
        try {
            AL.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
}
