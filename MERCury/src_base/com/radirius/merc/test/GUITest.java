package com.radirius.merc.test;

import java.io.*;

import com.radirius.merc.exc.*;
import com.radirius.merc.fmwk.*;
import com.radirius.merc.gfx.*;
import com.radirius.merc.res.*;

/**
 * @author Jeviny
 */

public class GUITest extends Core
{
	Runner heart = Runner.getInstance();
	
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
	
	@Override
	public void init(ResourceManager rm) throws IOException, MERCuryException
	{
		// TODO: Create Components
	}
	
	@Override
	public void update(float delta)
	{
		
	}
	
	@Override
	public void render(Graphics g)
	{
		g.setBackground(Color.gray);
		
		// TODO: Render Components
	}
	
	@Override
	public void cleanup(ResourceManager rm) throws IOException, MERCuryException
	{
		
	}
}