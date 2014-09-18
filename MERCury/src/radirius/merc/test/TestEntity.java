package radirius.merc.test;

import radirius.merc.environment.Entity;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.utilities.logging.Logger;

public class TestEntity extends Entity {
	int time = 0;
	
	public TestEntity(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		Logger.log("My name is " + getName() + "!");
	}
	
	@Override
	public void update(float delta) {
		time++;
		
		setX((float) Math.sin(time / 50.0) * 50.0f + 128);
		setY((float) Math.cos(time / 50.0) * 50.0f + 128);
		setRotation(time / 2.0f);
	}
	
	@Override
	public void render(Graphics g) {
		g.setLineWidth(4);
		
		g.setColor(new Color(0xFF00FF));
		g.drawRectangle(getBounds());
		g.setColor(new Color(0xFF0000));
		g.drawRectangle(getBounds().getCenter().getX() - 16, getBounds().getCenter().getY() - 16, 32, 32);
	}
}
