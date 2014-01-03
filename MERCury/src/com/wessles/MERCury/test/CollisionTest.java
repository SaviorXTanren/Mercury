package com.wessles.MERCury.test;

import com.wessles.MERCury.Core;
import com.wessles.MERCury.ResourceManager;
import com.wessles.MERCury.Runner;
import com.wessles.MERCury.geom.Ellipse;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.log.Logger;
import com.wessles.MERCury.opengl.Graphics;

/**
 * According to this test, the following collision events are valid: [see method render()].
 * 
 * 
 * @from MERCury in com.wessles.MERCury.test
 * @by wessles
 * @website www.wessles.com
 * @license (C) Dec 27, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class CollisionTest extends Core {
  Runner rnr = Runner.getInstance();
  
  public CollisionTest() {
    super("CollisionTest");
    rnr.init(this, 200, 200);
    rnr.run();
  }
  
  @Override
  public void init(ResourceManager RM) {
    
  }
  
  @Override
  public void update(float delta) {
    rnr.end();
  }
  
  @Override
  public void render(Graphics g) {
    /* RECTANGLE TO RECTANGLE */
    Rectangle r1 = new Rectangle(0, 0, 10, 0, 10, 10, 0, 10);
    Rectangle r2 = new Rectangle(5, 5, 15, 5, 15, 15, 5, 15);
    
    Logger.println("RECTANGLE TO RECTANGLE");
    
    Logger.println("rect1", r1.getX1(), r1.getY1(), r1.getX2(), r1.getY2());
    Logger.println("rect2", r2.getX1(), r2.getY1(), r2.getX2(), r2.getY2());
    Logger.println(r1.intersects(r2));
    
    Logger.println();
    
    /* RECTANGLE TO CIRCLE/ELLIPSE */
    Ellipse e1 = new Ellipse(5, 5, 3, 3);
    
    Logger.println("RECTANGLE TO CIRCLE/ELLIPSE");
    
    Logger.println("rect1", r1.getX1(), r1.getY1(), r1.getX2(), r1.getY2());
    Logger.println("circ/ellipse1", e1.getX1(), e1.getY1(), e1.getX2(), e1.getY2());
    Logger.println(r1.intersects(e1));
    Logger.println(e1.intersects(r1));
    
    Logger.println();
    
    /* CIRCLE/ELLIPSE TO CIRCLE/ELLIPSE */
    Ellipse e2 = new Ellipse(2, 2, 10, 4);
    
    Logger.println("RECTANGLE TO CIRCLE/ELLIPSE");
    
    Logger.println("circ/ellipse1", e1.getX1(), e1.getY1(), e1.getX2(), e1.getY2());
    Logger.println("circ/ellipse2", e2.getX1(), e2.getY1(), e2.getX2(), e2.getY2());
    Logger.println(e1.intersects(e2));
    
    Logger.println();
    
    rnr.end();
  }
  
  @Override
  public void cleanup(ResourceManager RM) {
    
  }
  
  public static void main(String[] args) {
    new CollisionTest();
  }
}
