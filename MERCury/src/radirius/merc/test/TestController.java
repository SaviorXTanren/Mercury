package radirius.merc.test;

import radirius.merc.framework.Core;
import radirius.merc.framework.Runner;
import radirius.merc.framework.splash.SplashScreen;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.input.Input;
import radirius.merc.math.geometry.Rectangle;
import radirius.merc.math.geometry.Vec2;

/**
 * @author wessles
 */

public class TestController extends Core {
	Runner rnr = Runner.getInstance();

	public TestController() {
		super("Test Controller!");
		rnr.init(this, 1200, 600);
		rnr.run();
	}

	public static void main(String[] args) {
		new TestController();
	}

	@Override
	public void init() {
		rnr.addSplashScreen(SplashScreen.getMERCuryDefault());
		rnr.getInput().initControllers();
	}

	@Override
	public void update(float delta) {

	}

	Vec2 vel = new Vec2(0, 0);
	Rectangle bounds = new Rectangle(0, 0, 100);

	@Override
	public void render(Graphics g) {
		if (!rnr.showSplashScreens(g))
			return;

		if (!rnr.inited)
			return;

		if (rnr.getInput().controllerButtonDown(2, 0))
			vel.scale(1.1f);
		if (rnr.getInput().controllerButtonClicked(9, 0) || rnr.getInput().keyClicked(Input.KEY_ESCAPE))
			rnr.end();

		vel.add(rnr.getInput().getLeftAnalogStick(0));

		if (bounds.getX() + vel.x < 0 || bounds.getX() + vel.x + bounds.getWidth() > rnr.getWidth())
			vel.x *= -1;
		if (bounds.getY() + vel.y < 0 || bounds.getY() + vel.y + bounds.getHeight() > rnr.getHeight())
			vel.y *= -1;

		bounds.translate(vel.x, vel.y);
		vel.scale(0.9f);
		bounds.rotateTo(-rnr.getInput().getRightAnalogStick(0).theta());

		g.setColor(Color.white);
		g.drawPolygon(bounds);
	}

	@Override
	public void cleanup() {

	}

}
