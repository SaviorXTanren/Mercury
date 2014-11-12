package com.radirius.mercury.tests;

import com.radirius.mercury.framework.Core;
import com.radirius.mercury.framework.splash.SplashScreen;
import com.radirius.mercury.graphics.Color;
import com.radirius.mercury.graphics.Graphics;
import com.radirius.mercury.graphics.font.TrueTypeFont;

class FrameworkTest extends Core {
	private TrueTypeFont font;
	private int f = 0;
	
	public FrameworkTest() {
		super("Untitled Window", 1920, 1200);
		
		setVsync(false);
		setExtraDebugDisplays(false);
		setDebugDisplays(true);
		setFullscreen(true);
	}
	
	@Override
	public void init() {
		font = (TrueTypeFont) TrueTypeFont.ROBOTO_MEDIUM.deriveFont(42f);
		
		getRunner().addSplashScreen(SplashScreen.getMercuryDefault());
	}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void render(Graphics g) {
		float ym = -(Math.abs((float) Math.sin(f++ / 20.0) * 20.0f));
		
		g.setFont(font);
		g.setColor(System.currentTimeMillis() / 512 % 2 != 0 ? Color.RED : Color.BLUE);
		g.drawString("Mercury Framework Test.", 256, ym + 256);
		
		g.setColor(Color.CARROT);
		g.drawRectangle(0, 310, 1920, 800);
		
		getRunner().addDebugData("Fucks Given", String.valueOf(f++));
	}
	
	@Override
	public void cleanup() {}
	
	public static void main(String[] args) {
		new FrameworkTest().start();
	}
}
