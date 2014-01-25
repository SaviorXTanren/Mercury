package com.teama.merc.test;

import com.teama.merc.fmwk.Core;
import com.teama.merc.fmwk.Runner;
import com.teama.merc.geo.Rectangle;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Shader;
import com.teama.merc.res.ResourceManager;

/**
 * An object version of shaders. Does all of the tedius stuff for you and lets you use the shader easily.
 * 
 * @from MERCury in com.teama.merc.test
 * @authors opiop65
 * @website www.wessles.com
 * @license (C) Dec 23, 2013 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class ShaderTest extends Core
{

	Runner rnr = Runner.getInstance();
	Shader program;
	
	public ShaderTest()
	{
		super("MERCury Shader Test");
		rnr.init(this, 800, 600);
		rnr.run();
	}

	@Override
	public void init(ResourceManager RM)
	{
		program = Shader.getShader("res/gfx/shaders/custom.vs", Shader.VERTEX_SHADER);
	}

	@Override
	public void update(float delta)
	{
	}

	@Override
	public void render(Graphics g)
	{
		g.setBackground(Color.cyan);
		g.useShader(program);
		g.drawRect(new Rectangle(400, 300, 50, 50));
	}

	@Override
	public void cleanup(ResourceManager RM)
	{
	}

	public static void main(String[] args)
	{
		new ShaderTest();
	}
}
