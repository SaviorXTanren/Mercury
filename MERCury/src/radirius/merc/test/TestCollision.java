package radirius.merc.test;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.math.geometry.Circle;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Star;
import radirius.merc.math.geometry.Triangle;
import radirius.merc.math.geometry.Vec2;

/**
 * According to this test, the following collision events are valid: [see method
 * render()].
 * 
 * @author wessles
 */

public class TestCollision extends Core {
	Runner rnr = Runner.getInstance();

	public TestCollision() {
		super("CollisionTest");
		rnr.init(this, 800, 600, false, false, true, true);
		rnr.run();
	}

	@Override
	public void init() {
		rnr.getGraphics().setScale(8);
	}

	@Override
	public void update(float delta) {

	}

	Rectangle r1 = new Rectangle(10, 30, 10);
	Rectangle r2 = new Rectangle(8, 60, 4);
	Circle c1 = new Circle(50, 30, 10);
	Circle c2 = new Circle(50.5f, 60, 7.5f);
	Triangle t1 = new Triangle(80, 30, 80, 40, 90, 30);
	Triangle t2 = new Triangle(80, 60, 80, 70, 90, 60);
	Vec2 testcol = new Vec2(0, 35);
	Star s1 = new Star(15, 15, 10, 10, 15, 15, 20);

	float starrot = 0;

	@Override
	public void render(Graphics g) {
		if (r1.intersects(r2) || r1.contains(testcol) || s1.intersects(r2)) {
			g.tracePolygon(r1);
			g.tracePolygon(r2);
		} else {
			g.drawPolygon(r1);
			g.drawPolygon(r2);
		}

		r2.translate(0, -0.05f);
		r1.rotate(0.2f);
		r2.rotate(0.1f);

		if (c1.intersects(c2) || c1.contains(testcol)) {
			g.tracePolygon(c1);
			g.tracePolygon(c2);
		} else {
			g.drawPolygon(c1);
			g.drawPolygon(c2);
		}

		c2.translate(0, -0.2f);
		c2.rotate(0.1f);

		if (t1.intersects(t2) || t1.contains(testcol)) {
			g.tracePolygon(t1);
			g.tracePolygon(t2);
		} else {
			g.drawPolygon(t1);
			g.drawPolygon(t2);
		}

		t2.translate(0f, -0.2f);
		t2.rotate(0.1f);

		g.setColor(Color.red.duplicate());
		g.getColor().a = 0.2f;
		g.drawPolygon(s1);
		double current_time = (double) System.currentTimeMillis() / 500f;
		s1 = (Star) new Star(s1.getCenter().x, s1.getCenter().y, 10 + 3 * (float) Math.sin(current_time), 10 + 3 * (float) Math.cos(current_time), 10).rotate(starrot += 1);
		g.setColor(Color.green);
		g.tracePolygon(s1);
	}

	@Override
	public void cleanup() {

	}

	public static void main(String[] args) {
		new TestCollision();
	}
}
