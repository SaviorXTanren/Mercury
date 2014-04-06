package com.radirius.merc.test;

import java.io.IOException;

import com.radirius.merc.data.TextFile;
import com.radirius.merc.exc.MERCuryException;
import com.radirius.merc.fmwk.Core;
import com.radirius.merc.fmwk.Runner;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.res.Loader;
import com.radirius.merc.res.ResourceManager;

/**
 * @from MERCury_git in com.radirius.merc.test
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Apr 1, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class TextFileTest extends Core
{
	private Runner rnr = Runner.getInstance();
	
	public TextFileTest(String name)
	{
		super(name);
		rnr.init(this, 800, 600);
		rnr.run();
	}

	@Override
	public void init(ResourceManager RM) throws IOException, MERCuryException
	{
		TextFile file = TextFile.loadFile(Loader.loadFromClasspath("com/radirius/merc/test/test.txt"));
		System.out.println(file.getContent());
		file.write("HAHAHAH");
		System.out.println(file.getContent());
	}

	@Override
	public void update(float delta) throws MERCuryException
	{
	}

	@Override
	public void render(Graphics g) throws MERCuryException
	{
	}

	@Override
	public void cleanup(ResourceManager RM) throws IOException, MERCuryException
	{
	}

	public static void main(String[] args)
	{
		new TextFileTest("Text File Test");
	}
}
