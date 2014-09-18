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
 * This is a testing program for most of Mercury's functionality.
 * Should only be used for debugging.
 * 
 * Note: Is full of *really* hackish code. For testing purposes
 * only, should never be used as a demo.
 * 
 * @author Jeviny
 */
public class MercuryTest extends Core {
	private static final String TITLE = "Mercury Testing Program";
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	
	public MercuryTest() {
		super(TITLE, WIDTH, HEIGHT);
	}

	int time0 = 0, time1 = 0;
	
	Font font;
	TestEntity te;
	
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
		
		te = new TestEntity(0, 0, 256, 256);
		
		getRunner().addSplashScreen(SplashScreen.getMercuryDefault());
	}
	
	public void update(float delta) {
		time0++;
		time1 = time0 / 32;
		
		Logger.debug(getRunner().getFPS());
		
		te.update(delta);
	}
	
	Color[] c = new Color[] { Color.TURQUOISE, Color.GREEN_SEA, Color.GREEN, Color.DARK_GREEN, Color.BLUE, Color.OCEAN_BLUE, Color.PURPLE, Color.VIOLET, Color.CHARCOAL, Color.ASPHALT, Color.YELLOW, Color.ORANGE, Color.CARROT, Color.PUMPKIN, Color.RED, Color.CRIMSON };
	
	public void render(Graphics g) {
		Color bg = null;
		
		if (time1 >= c.length || time0 >= ((c.length) * 32))
			time0 = time1 = 0;
		
		bg = c[time1];
		
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
		
		te.render(g);
	}
	
	public void cleanup() {}
	
	public static void main(String[] args) {
		new MercuryTest().run();
	}
}
