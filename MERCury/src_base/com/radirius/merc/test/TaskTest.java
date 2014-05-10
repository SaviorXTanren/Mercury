package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Texture;
import com.radirius.merc.log.Logger;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.util.TaskTiming;
import com.radirius.merc.util.TaskTiming.Task;

/**
 * @from MERCury in com.radirius.merc.test
 * @authors wessles
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TaskTest extends Core {
    Runner rnr = Runner.getInstance();
    
    boolean jumpscare = false;
    Texture lard;
    
    public TaskTest() {
        super("Task Test!");
        rnr.init(this, 500, 500);
        rnr.run();
    }
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
        TaskTiming.addTask(new Task(1000) {
            @Override
            public void run() {
                Logger.debug("It has been 1 second.");
            }
        });
        TaskTiming.addTask(new Task(5000) {
            @Override
            public void run() {
                Logger.debug("It has been 5 seconds.");
            }
        });
        TaskTiming.addTask(new Task(10000) {
            @Override
            public void run() {
                Logger.debug("It has been 10 seconds. And...");
            }
        });
        TaskTiming.addTask(new Task(15000) {
            @Override
            public void run() {
                Logger.debug("It has been 15 se-- BOOOO!");
                jumpscare = true;
            }
        });
        TaskTiming.addTask(new Task(15400) {
            @Override
            public void run() {
                Logger.debug("Haw haw, that was golden.");
                jumpscare = false;
            }
        });
        
        TaskTiming.addTask(new Task(7000, -1) {
            @Override
            public void run() {
                Logger.debug("INFINITELY RECURRING 7 SECOND REMINDER OF YOUR POOP");
                jumpscare = false;
            }
        });
        
        // Hows aboot a jump scar!
        RM.loadResource(Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/tuts/lard.png")), "tex_lard");
        lard = (Texture) RM.retrieveResource("tex_lard");
    }
    
    @Override
    public void update(float delta) throws MERCuryException {
        
    }
    
    float zoom = 2;
    
    @Override
    public void render(Graphics g) throws MERCuryException {
        if (jumpscare) {
            g.drawTexture(lard, 0, 0);
            g.scale(zoom += 0.4f);
        }
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
        
    }
    
    public static void main(String[] args) {
        new TaskTest();
    }
}
