package com.radirius.merc.test;

import java.io.*;

import com.radirius.merc.exc.*;
import com.radirius.merc.fmwk.*;
import com.radirius.merc.gfx.*;
import com.radirius.merc.gui.*;
import com.radirius.merc.res.*;

/**
 * @author Jeviny
 */

public class GUITest extends Core
{
	Runner heart = Runner.getInstance();
	
	String msg = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec a diam lectus. Sed sit amet ipsum mauris. Donec sed odio eros. Donec viverra mi quis quam pulvinar at malesuada arcu rhoncus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In rutrum accumsan ultricies.";
	
	Texture bg;
	
	public GUITest()
	{
		super("MERCury GUI Test");
		
		heart.init(this, 1024, 768);
		heart.run();
	}
	
	public static void main(String[] a)
	{
		new GUITest();
	}
	
	TextPanel tp;
	
	@Override
	public void init(ResourceManager rm) throws IOException, MERCuryException
	{
		bg = Texture.loadTexture(Loader.streamFromClasspath("com/radirius/merc/test/panel_main.png"));
		
		//tp = new TextPanel("MERCury GUI Demonstration", msg, 64, 64, 800, 600, 32, Color.white, Color.magenta);	
		//tp = new TextPanel("MERCury GUI Demonstration", msg, bg, 64, 64, 800, 600, 32, Color.black, Color.trans);
	}
	
	@Override
	public void update(float delta)
	{
		
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setBackground(Color.gray);
		
		// tp.render(g);
		
		g.drawTexture(bg, 0, 0, bg.getTextureWidth(), bg.getTextureHeight(), 0, 0);
		g.drawTexture(bg, 0, 0);
	}
	
	@Override
	public void cleanup(ResourceManager rm) throws IOException, MERCuryException
	{
		
	}
}
