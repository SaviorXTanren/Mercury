package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.splash.SplashScreen;
import com.radirius.mercury.graphics.*;
import com.radirius.mercury.graphics.font.TrueTypeFont;
import org.lwjgl.opengl.GL11;

class FrameworkTest extends Core {
	private TrueTypeFont font;
	private int f = 0;

	public FrameworkTest() {
		super("Untitled Window", 1600, 900);

		setVsync(false);
		setExtraDebugDisplays(true);
		setDebugDisplays(true);
	}

	public static void main(String[] args) {
		new FrameworkTest().start();
	}

	@Override
	public void init() {
		font = (TrueTypeFont) TrueTypeFont.ROBOTO_MEDIUM.deriveFont(42f);

		getRunner().addSplashScreen(SplashScreen.getMercuryDefault());
		
		System.out.println("HEY");
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Graphics g) {
		System.out.println("HEY");
		float ym = -(Math.abs((float) Math.sin(f++ / 20.0) * 20.0f));

		g.setFont(font);
		g.setColor(System.currentTimeMillis() / 512 % 2 != 0 ? Color.RED : Color.BLUE);
		
			g.drawString("Mercury Framework Test.", 256, ym + 256);

		g.setColor(Color.CARROT);
		g.drawRectangle(0, 310, 1920, 800);

		getRunner().addDebugData("Frames Rendered", String.valueOf(f++));
		getRunner().addDebugData("OpenGL version", GL11.glGetString(GL11.GL_VERSION));
	}

	@Override
	public void cleanup() {
	}
}
