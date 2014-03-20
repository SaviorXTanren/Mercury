package com.teama.merc.gui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.teama.merc.font.Font;
import com.teama.merc.font.TrueTypeFont;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

/**
 * @from MERCury in package com.teama.merc.gui;
 * @authors Jeviny
 * @website www.wessles.com
 * @license (C) Mar 3, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */
public class TextPanel extends TextBox
{
	public String title = "Untitled Panel";
	
	public TextPanel(String title, String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol)
	{
		super(txt, background_img, x, y, w, h, margin, textCol);
		
		this.title = title;
	}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render(Graphics g)
	{
    	g.drawTexture(background_img, x, y, w, h);
    	
		renderContents(g);
	}
	
	public void renderContents(Graphics g)
	{
		Font titleFont = null;
		Font textFont = null;
		
		try
		{
			titleFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Bold.ttf", 32, 1, true);
			textFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Semibold.ttf", 20, 1, true);
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

    	g.setColor(textCol);
		g.setFont(titleFont);	
		g.drawString(x + margin, y + margin, title);

    	g.setColor(textCol);
		g.setFont(textFont);
		g.drawString(x + margin, y + margin + titleFont.getHeight() / 2, txt);
		
        g.setColor(Color.white);
	}
}
