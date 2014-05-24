package com.radirius.merc.tuts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.geo.Vec2;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.in.Input;
import com.radirius.merc.math.MercMath;
import com.radirius.merc.res.ResourceManager;
import com.radirius.merc.util.TaskTiming;

/**
 * @author wessles
 */

public class WalkingSim extends Core {
    Runner rnr = Runner.getInstance();
    
    public WalkingSim(String name) {
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
    
    @Override
    public void init(ResourceManager RM) throws IOException, MERCuryException {
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
                    while (Math.sqrt((dx * dx) + (dy * dy)) < 100) {
                        veggie = new Vec2((int) MercMath.random(0, rnr.getWidth()), (int) MercMath.random(0, rnr.getHeight()));
                        dx = position.x - veggie.x;
                        dy = position.y - veggie.y;
                    }
                    // Add in the fair veggie
                    veggies.add(veggie);
                }
            }
        });
    }
    
    // The constant by which `velocity` will be multiplied by every frame. This
    // will just make the `velocity` 98% of what it is every frame, eventually
    // slowing it to a stop.
    public static final float DAMPENING = 0.98f;
    // The amount by which the `velocity` will be 'pushed' every frame, given a
    // certain input. For example, if we were to move to the right, we would do
    // `velocity.x += PUSHVELOCITY`.
    public static final float PUSHVELOCITY = 0.04f;
    // A `Vec2`, where `velocity.x` is the movement in the x axis every frame,
    // and `velocity.y` is the movement in the y axis every frame.
    public Vec2 velocity = new Vec2(0, 0);
    // A `Vec2` where `position.x` is the x position, and `position.y` is the y
    // position.
    public Vec2 position = new Vec2(0, 0);
    
    public int score = 0;
    
    @Override
    public void update(float delta) throws MERCuryException {
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
    public void render(Graphics g) throws MERCuryException {
        g.setColor(Color.white);
        g.drawCircle(position.x, position.y, 10);
        for (Vec2 pos : steroids) {
            g.setColor(Color.green);
            g.drawCircle(pos.x, pos.y, 10);
        }
        for (Vec2 pos : veggies) {
            g.setColor(Color.red);
            g.drawCircle(pos.x, pos.y, 10);
        }
        g.setColor(Color.cyan);
        g.drawString(10, 10, "SCORE:"+score);
    }
    
    @Override
    public void cleanup(ResourceManager RM) throws IOException, MERCuryException {
    }
    
    public static void main(String[] args) {
        new WalkingSim("Next Gen Input!!!");
    }
    
}
