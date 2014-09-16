package radirius.merc.test;

import radirius.merc.framework.Core;
import radirius.merc.framework.splash.SplashScreen;
import radirius.merc.graphics.Graphics;

public class SetupTest extends Core {
	public SetupTest() {
		super("Setup Test", 800, 600);
	}

	public void init() {
		getRunner().addSplashScreen(SplashScreen.getMercuryDefault());
	}
	
	public void update(float delta) {}
	public void render(Graphics g)  {}
	public void cleanup() 		{}
	
	public static void main(String[] args) {
		new SetupTest().run();
	}
}
