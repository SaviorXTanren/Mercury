package com.wessles.MERCury;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.wessles.MERCury.log.Logger;
import com.wessles.MERCury.opengl.Graphics;
import com.wessles.MERCury.utils.Camera;

/**
 * A class that will run your core, and give out the graphics object, current core, resource manager, and input ly.
 * 
 * @from MERCury in com.wessles.MERCury
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under GPLv2.0 license. You can find the license itself at bit.ly/1eyRQJ7.
 */

public class Runner {
  private final static Runner singleton = new Runner();
  
  private boolean running = false;
  private boolean vsync;
  private boolean logfps = false;
  private int delta = 1;
  private int FPS_TARGET = 60, FPS;
  private long lastframe;
  private float deltafactor = 1;
  
  private Core core;
  
  private Graphics graphicsobject;
  private ResourceManager RM;
  
  private Camera camera;
  private Input input;
  
  public void init(Core core, int WIDTH, int HEIGHT) {
    init(core, WIDTH, HEIGHT, false);
  }
  
  public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen) {
    init(core, WIDTH, HEIGHT, fullscreen, true, null);
  }
  
  public void init(Core core, int WIDTH, int HEIGHT, boolean fullscreen, boolean vsync, File log) {
    if (log != null)
      Logger.setLog(log);
    
    // Init some stuffs!
    Logger.println("# MERCury Started!");
    Logger.println();
    Logger.println("  __  __ ______ _____   _____                 ");
    Logger.println(" |  \\/  |  ____|  __ \\ / ____|                ");
    Logger.println(" | \\  / | |__  | |__) | |    _   _ _ __ _   _ ");
    Logger.println(" | |\\/| |  __| |  _  /| |   | | | | '__| | | |");
    Logger.println(" | |  | | |____| | \\ \\| |___| |_| | |  | |_| |");
    Logger.println(" |_|  |_|______|_|  \\_\\_____\\__,|_| |   \\__, |");
    Logger.println("                                        __/ |");
    Logger.println("                                       |___/ ");
    Logger.println("Maitenance Enhanced and Reliable Coding Engine");
    Logger.println("             by wessles");
    Logger.println();
    Logger.println("             Email wessles@wessles.com for support");
    Logger.println("             Go to www.reddit.com/r/MERCuryGameEngine for community");
    Logger.println();
    Logger.println("                        Copyright www.wessles.com 2013");
    Logger.println("                        All Rights Reserved");
    Logger.println();
    
    Logger.printDateAndTime();
    
    Logger.println();
    
    this.core = core;
    Logger.println("#MERCury: Made Core...");
    this.vsync = vsync;
    this.core.initDisplay(WIDTH, HEIGHT, fullscreen, vsync);
    Logger.println("#MERCury: Initialized Display...");
    graphicsobject = this.core.initGraphics();
    Logger.println("#MERCury: Made Graphics...");
    camera = new Camera(0, 0);
    Logger.println("#MERCury: Initialized Camera...");
    this.core.initAudio();
    Logger.println("#MERCury: Initialized Audio...");
    RM = new ResourceManager();
    Logger.println("#MERCury: Initialized Resource Manager...");
    
    graphicsobject.init();
    Logger.println("#MERCury: Initialized Graphics...");
    this.core.init(RM);
    Logger.println("#MERCury: Initialized Core...");
    input = new Input();
    Logger.println("#MERCury: Created Input...");
    input.create();
    Logger.println("#MERCury: Initialized Input...");
    
    Logger.println("#MERCury: Done Initializing.");
    
    Logger.println("#MERCury: Ready to begin game loop. Awaiting permission from Core...");
  }
  
  public void run() {
    Logger.println("#MERCury: Run permission granted by Core...");
    Logger.println("#MERCury: Starting Game Loop...");
    running = true;
    Logger.println("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
    Logger.println();
    Logger.println();
    
    // To the main loop!
    
    int _FPS = 0;
    long lastfps;
    
    /* Initial 'last time...' Otherwise the first delta will be about 50000000. */
    lastfps = lastframe = Sys.getTime() * 1000 / Sys.getTimerResolution();
    
    while (running) {
      // Set time for FPS and Delta calculations
      long time = Sys.getTime() * 1000 / Sys.getTimerResolution();
      
      // Calculate delta
      delta = (int) (time - lastframe);
      
      // Update FPS
      if (time - lastfps < 1000)
        _FPS++;
      else {
        lastfps = time;
        FPS = _FPS;
        _FPS = 0;
      }
      
      if (FPS == 0)
        FPS = FPS_TARGET;
      
      // End all time calculations.
      lastframe = time;
      
      // Log FPS
      if (logfps)
        Logger.println("FPS:" + FPS);
      
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      
      core.update(delta());
      
      camera.pre(graphicsobject);
      core.render(graphicsobject);
      camera.post(graphicsobject);
      
      if (Display.isCloseRequested())
        end();
      
      Display.update();
      Display.sync(FPS_TARGET);
    }
    
    Logger.println();
    Logger.println();
    Logger.println("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-");
    Logger.println("#MERCury: Ending Game Loop...");
    
    Logger.println("#MERCury: Starting Cleanup...");
    core.cleanup(RM);
    Logger.println("#MERCury: Cleaned up Core...");
    RM.cleanup();
    Logger.println("#MERCury: Cleaned up ResourceManager...");
    core.cleanupAudio();
    Logger.println("#MERCury: Cleaned up OpenAL Sound...");
    Logger.println("#MERCury: MERCury Shutting down...");
    Logger.printDateAndTime();
    Logger.cleanup();
  }
  
  public int fps() {
    return FPS;
  }
  
  public void setFpsTarget(int target) {
    FPS_TARGET = target;
  }
  
  public void setLogFPS(boolean logfps) {
    this.logfps = logfps;
  }
  
  public int width() {
    return Display.getWidth();
  }
  
  public float width(float numerator) {
    return width() / numerator;
  }
  
  public int height() {
    return Display.getHeight();
  }
  
  public float height(float numerator) {
    return height() / numerator;
  }
  
  public float aspectRatio() {
    return width() / height();
  }
  
  public float aspectRatio(float numerator) {
    return width(numerator) / height(numerator);
  }
  
  public float getTime() {
    return Sys.getTime() * 1000 / Sys.getTimerResolution();
  }
  
  public void sleep(int milliseconds) throws InterruptedException {
    Thread.sleep(milliseconds);
  }
  
  public void mousegrab(boolean grab) {
    Mouse.setGrabbed(grab);
  }
  
  public void vsync(boolean vsync) {
    this.vsync = vsync;
    Display.setVSyncEnabled(vsync);
  }
  
  public boolean focused() {
    return Display.isActive();
  }
  
  public void setTitle(String title) {
    Display.setTitle(title);
  }
  
  public void setResizable(boolean resizable) {
    Display.setResizable(resizable);
    
    remakeDisplay();
  }
  
  private void remakeDisplay() {
    Display.destroy();
    core().initDisplay(width(), height(), Display.isFullscreen(), vsync);
    graphicsobject = core().initGraphics();
  }
  
  public void end() {
    running = false;
  }
  
  public float delta() {
    return delta * deltafactor;
  }
  
  public void setDeltaFactor(float factor) {
    deltafactor = factor;
  }
  
  public Core core() {
    return core;
  }
  
  public Graphics graphics() {
    return graphicsobject;
  }
  
  public ResourceManager resourceManager() {
    return RM;
  }
  
  public Input input() {
    return input;
  }
  
  public Camera camera() {
    return camera;
  }
  
  public static Runner getInstance() {
    return singleton;
  }
}
