package us.radiri.merc.test;

import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.logging.Logger;
import us.radiri.merc.resource.Loader;
import us.radiri.merc.util.TaskTiming;
import us.radiri.merc.util.TaskTiming.Task;

/**
 * @author wessles
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
    public void init() {
        TaskTiming.addTask(new TaskTiming.Task(1000) {
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
            }
        });
        
        // Hows aboot a jump scar!
        lard = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
    }
    
    @Override
    public void update(float delta) {
        
    }
    
    float zoom = 2;
    
    @Override
    public void render(Graphics g) {
        if (jumpscare) {
            g.drawTexture(lard, 0, 0);
            g.setScale(zoom += 0.4f);
        } else
            g.drawString(0, 0, "See the console.");
    }
    
    @Override
    public void cleanup() {
        
    }
    
    public static void main(String[] args) {
        new TaskTest();
    }
}
