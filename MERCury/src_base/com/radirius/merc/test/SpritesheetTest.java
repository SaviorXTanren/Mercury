package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.gfx.Spritesheet;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury_git in com.radirius.merc.test
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Mar 29, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class SpritesheetTest extends Core
{
	
	private Runner rnr = Runner.getInstance();
	private Spritesheet sheet;

	public SpritesheetTest(String name)
	{
		super(name);
		rnr.init(this, 800, 600);
		rnr.run();
	}

	@Override
	public void init(ResourceManager RM) throws IOException, MERCuryException
	{
		sheet = Spritesheet.loadSheet(Loader.streamFromClasspath("com/radirius/merc/test/tiles.txt"), Loader.streamFromClasspath("com/radirius/merc/test/tiles.png"));
	}

	@Override
	public void update(float delta) throws MERCuryException
	{
	}

	@Override
	public void render(Graphics g) throws MERCuryException
	{
		g.setBackground(Color.marble);
		g.drawTexture(sheet.getTexture("Grass"), 10, 10);
		g.drawTexture(sheet.getTexture("Void"), 80, 10);
	}

	@Override
	public void cleanup(ResourceManager RM) throws IOException, MERCuryException
	{
	}
	
	public static void main(String[] args)
	{
		new SpritesheetTest("Spritesheet Test");
	}
}
