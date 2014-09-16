package radirius.merc.test;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import radirius.merc.framework.Core;
import radirius.merc.framework.splash.SplashScreen;
import radirius.merc.graphics.Color;
import radirius.merc.graphics.Graphics;
import radirius.merc.graphics.font.Font;
import radirius.merc.graphics.font.TrueTypeFont;
import radirius.merc.resource.Loader;
import radirius.merc.utilities.logging.Logger;

/**
 * This is a testing program for most of MERCury's functionality.
 * Should only be used for debugging.
 * 
 * Note: Is full of *really* hackish code. For testing purposes
 * only, should never be used as a demo.
 * 
 * @author Jeviny
 */
public class FuncTest extends Core {
	private static final String TITLE = "MERCury Functionality Test";
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	
	public FuncTest() {
		super(TITLE, WIDTH, HEIGHT);
	}

	int time = 0;
	int t = time;
	
	Font font;
	
	public void init() {
		try {
			font = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("radirius/merc/test/assets/EBGaramond.otf"), 144f, 1, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getRunner().addSplashScreen(SplashScreen.getMercuryDefault());
	}
	
	public void update(float delta) {
		time++;
		t = time / 32;
		
		Logger.debug(getRunner().getFPS());
	}
	
	Color[] c = new Color[] { Color.TURQUOISE, Color.GREEN_SEA, Color.GREEN, Color.DARK_GREEN, Color.BLUE, Color.OCEAN_BLUE, Color.PURPLE, Color.VIOLET, Color.CHARCOAL, Color.ASPHALT, Color.YELLOW, Color.ORANGE, Color.CARROT, Color.PUMPKIN, Color.RED, Color.CRIMSON };
	
	public void render(Graphics g) {
		Color bg = null;
		
		if (t >= c.length || time >= ((c.length) * 32))
			time = t = 0;
		
		bg = c[t];
		
		g.setBackground(bg);
		
		String message = "Mercury!";
		
		g.setFont(font);
		
		for (int i = 0; i < 12; i++) {
			g.setColor(new Color(0, 0, 0, i));
			g.drawString(message, ((getRunner().getWidth() / 2 - font.getWidth(message) / 2)), (((getRunner().getHeight() / 2 - font.getHeight()) + i) - 6));
			g.setColor(new Color(0, 0, 0, i / 2));
			g.drawString(message, ((getRunner().getWidth() / 2 - font.getWidth(message) / 2) - 2), (((getRunner().getHeight() / 2 - font.getHeight()) + i) - 6));
			g.drawString(message, ((getRunner().getWidth() / 2 - font.getWidth(message) / 2) + 2), (((getRunner().getHeight() / 2 - font.getHeight()) + i) - 6));
		}
		
		g.setColor(Color.WHITE);
		g.drawString(message, getRunner().getWidth() / 2 - font.getWidth(message) / 2, getRunner().getHeight() / 2 - font.getHeight());
		
		g.setFont(TrueTypeFont.OPENSANS_SEMIBOLD);
		
		g.drawString("This is a test.", getRunner().getWidth() / 2 - g.getFont().getWidth("This is a test.") / 2, (getRunner().getHeight() / 2 - g.getFont().getHeight() / 2) + 24);
		
		g.setColor(new Color(0xFF00FF));
		g.drawRectangle(0, 0, 256, 256);
	}
	
	public void cleanup() {}
	
	public static void main(String[] args) {
		new FuncTest().run();
	}
}
