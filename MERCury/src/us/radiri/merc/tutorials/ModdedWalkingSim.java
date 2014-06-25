package us.radiri.merc.tutorials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import us.radiri.merc.command.Command;
import us.radiri.merc.command.CommandList;
import us.radiri.merc.framework.Core;
import us.radiri.merc.framework.Runner;
import us.radiri.merc.geom.Vec2;
import us.radiri.merc.graphics.Color;
import us.radiri.merc.graphics.Graphics;
import us.radiri.merc.graphics.Texture;
import us.radiri.merc.input.Input;
import us.radiri.merc.maths.MercMath;
import us.radiri.merc.resource.Loader;
import us.radiri.merc.util.TaskTiming;

/**
 * @author wessles
 */

public class ModdedWalkingSim extends Core {
    Runner rnr = Runner.getInstance();
    
    public ModdedWalkingSim(String name) {
        super(name);
        // Make a game window that is 500x500
        rnr.init(this, 500, 500);
        // Now run it!
        rnr.run();
    }
    
    // The amount of veggies and steroids in frame.
    public static final int MAX_ENTITIES = 10;
    // ArrayLists containing the locations of the steroids and veggies.
    CopyOnWriteArrayList<Vec2> steroids = new CopyOnWriteArrayList<Vec2>(), veggies = new CopyOnWriteArrayList<Vec2>();
    
    Texture texture;
    
    @Override
    public void init() {
        texture = Texture.loadTexture(Loader.streamFromClasspath("us/radiri/merc/test/dAWWWW.png"));
        
        // Make a timer that reccurs every 5 seconds, infinitely (a negative
        // value will do this).
        TaskTiming.addTask(new TaskTiming.Task(5000, -1) {
            @Override
            public void run() {
                // Empty the everything!
                steroids.clear();
                veggies.clear();
                
                for (int e = 0; e < MAX_ENTITIES; e++) {
                    // Place the new thingies at random locations.
                    steroids.add(new Vec2((int) MercMath.random(0, rnr.getWidth()), (int) MercMath.random(0, rnr.getHeight())));
                    
                    // If the veggie is too close (< 100 pixels), the game is no
                    // fair; let's check that it is at an okay distance.
                    Vec2 veggie = new Vec2((int) MercMath.random(0, rnr.getWidth()), (int) MercMath.random(0, rnr.getHeight()));
                    // Check difference to `position` using pythagorean theroem.
                    // We will keep on generating a random location until the
                    // distance is not less than 100
                    float dx = position.x - veggie.x, dy = position.y - veggie.y;
                    while (Math.sqrt(dx * dx + dy * dy) < 100) {
                        veggie = new Vec2((int) MercMath.random(0, rnr.getWidth()), (int) MercMath.random(0, rnr.getHeight()));
                        dx = position.x - veggie.x;
                        dy = position.y - veggie.y;
                    }
                    // Add in the fair veggie
                    veggies.add(veggie);
                }
            }
        });
        
        // THIS IS NOT IN THE ORIGINAL TUTORIAL
        // THIS IS FOR A DEMO OF THE COMMAND-LINE DEBUGGING FEATURE
        // IGNORE THIS WHOLE SECTION, IF YOU LIKE.
        // THE CODE WILL RUN FINE IF THIS IS GONE, TOO.
        // _________________________________________________________________________________________________________________________
        
        CommandList cheats = new CommandList("cheats", "Some awesome cheats for your game.");
        cheats.addCommand(new Command("setscore", "Sets the score to an Integer.") {
            @Override
            public void run(String... args) {
                score = Integer.valueOf(args[0]);
            }
        });
        cheats.addCommand(new Command("godmode", "Toggles godmode.") {
            @Override
            public void run(String... args) {
                if (PUSHVELOCITY != 2)
                    PUSHVELOCITY = 2;
                else
                    PUSHVELOCITY = 0.04f;
            }
        });
        CommandList.addCommandList(cheats);
        // _________________________________________________________________________________________________________________________
    }
    
    // The constant by which `velocity` will be multiplied by every frame. This
    // will just make the `velocity` 98% of what it is every frame, eventually
    // slowing it to a stop.
    public static float DAMPENING = 0.98f;
    // The amount by which the `velocity` will be 'pushed' every frame, given a
    // certain input. For example, if we were to move to the right, we would do
    // `velocity.x += PUSHVELOCITY`.
    public static float PUSHVELOCITY = 0.04f;
    // A `Vec2`, where `velocity.x` is the movement in the x axis every frame,
    // and `velocity.y` is the movement in the y axis every frame.
    public Vec2 velocity = new Vec2(0, 0);
    // A `Vec2` where `position.x` is the x position, and `position.y` is the y
    // position.
    public Vec2 position = new Vec2(0, 0);
    
    public int score = 0;
    
    @Override
    public void update(float delta) {
        // Velocity adding based upon the input of the user.
        Input in = rnr.getInput();
        
        if (in.keyDown(Input.KEY_UP))
            velocity.y -= PUSHVELOCITY;
        else if (in.keyDown(Input.KEY_DOWN))
            velocity.y += PUSHVELOCITY;
        if (in.keyDown(Input.KEY_LEFT))
            velocity.x -= PUSHVELOCITY;
        else if (in.keyDown(Input.KEY_RIGHT))
            velocity.x += PUSHVELOCITY;
        
        // Now we use the velocity, and damp it!
        
        // If the hypothetical postion is out of bounds, don't move past the
        // bounds, and reverse the velocity (bouncy!)
        float hypotheticalx = velocity.x + position.x;
        if (hypotheticalx > 0 && hypotheticalx < rnr.getWidth())
            position.x = hypotheticalx;
        else
            velocity.x *= -1;
        float hypotheticaly = velocity.y + position.y;
        if (hypotheticaly > 0 && hypotheticaly < rnr.getHeight())
            position.y = hypotheticaly;
        else
            velocity.y *= -1;
        
        // Scaling is fancy-talk for multiply.
        velocity.scale(DAMPENING);
        
        // Checking collisions!
        
        // Checking steroids
        
        // The reason we don't just remove on the spot is due to the TaskTiming
        // running on a seperate thread. This would cause a
        // concurrentmodificationexception, as you can't modify the same
        // variable on two threads!
        ArrayList<Vec2> removals = new ArrayList<Vec2>();
        for (Iterator<Vec2> i = steroids.iterator(); i.hasNext();) {
            Vec2 vec = i.next();
            float dx = position.x - vec.x, dy = position.y - vec.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            // If the two collide, the steroid disappears, and you score.
            if (dist < 20) {
                removals.add(vec);
                score += 1;
            }
        }
        // Now we remove all of them!
        steroids.removeAll(removals);
        
        // Checking veggies
        removals.clear();
        for (Iterator<Vec2> i = veggies.iterator(); i.hasNext();) {
            Vec2 vec = i.next();
            // Find distance
            float dx = position.x - vec.x, dy = position.y - vec.y;
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            // If the two collide, the veggie disappears, and you lose points.
            if (dist < 20) {
                removals.add(vec);
                score -= 1;
            }
        }
        // Now we remove all of them!
        veggies.removeAll(removals);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        // No circle for the player!
//        g.drawCircle(position.x, position.y, 10);
        // And draw a Texture, by a center position.
        g.drawTexture(texture, position.x-texture.getWidth()/2, position.y-texture.getHeight()/2);
        for (Vec2 pos : steroids) {
            g.setColor(Color.green);
            // Still draw a circle!
            g.drawCircle(pos.x, pos.y, 10);
            // Draw the texture!
            g.drawTexture(texture, pos.x-texture.getWidth()/2, pos.y-texture.getHeight()/2);
        }
        for (Vec2 pos : veggies) {
            g.setColor(Color.red);
            g.drawCircle(pos.x, pos.y, 10);
        }
        g.setColor(Color.cyan);
        g.drawString(10, 10, "SCORE:" + score);
    }
    
    @Override
    public void cleanup() {
    }
    
    public static void main(String[] args) {
        new ModdedWalkingSim("Modded Walking Sim");
    }
    
}
