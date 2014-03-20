package com.teama.merc.gui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.teama.merc.font.TrueTypeFont;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

public class Panel extends TextBox
{
	public Panel(String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol)
	{
		super(txt, background_img, x, y, w, h, margin, textCol);
	}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render(Graphics g)
	{	
		try
		{
			TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Bold.ttf", 20, 1, true);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (FontFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
