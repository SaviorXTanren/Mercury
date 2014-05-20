package com.radirius.merc.gui;

import java.awt.*;
import java.io.*;
import java.util.*;

import com.radirius.merc.fmwk.*;
import com.radirius.merc.font.*;
import com.radirius.merc.font.Font;
import com.radirius.merc.geo.Rectangle;
import com.radirius.merc.gfx.*;
import com.radirius.merc.gfx.Color;
import com.radirius.merc.gfx.Graphics;
import com.radirius.merc.res.*;

/**
 * @author wessles, Jeviny
 */
public class TextPanel extends Component
{
	public float margin;

	public Texture border_hor, border_vert;
	
	public Texture background_img;
	public Color background_col;
	public Color textCol;
	
	public String title;

	private ArrayList<Button> buttons = new ArrayList<Button>();

	private Font titleFont = null;
	private Font txtFont = null;

	private int bg_mode = 0;
	
	private int BG_TEXTURE = 0;
	private int BG_COLOUR = 1;
	private int BG_TRANSPARENT = 2;
	
	public TextPanel(String title, String txt, float x, float y, float w, float h, float margin, Color textCol)
	{
		this(title, txt, Texture.getEmptyTexture(), x, y, w, h, margin, textCol, Color.trans);
		
		bg_mode = BG_TRANSPARENT;
	}

	public TextPanel(String title, String txt, float x, float y, float w, float h, float margin, Color textCol, Color background_col)
	{
		this(title, txt, Texture.getEmptyTexture(), x, y, w, h, margin, textCol, background_col);
		
		bg_mode = BG_COLOUR;
	}

	public TextPanel(String title, String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol, Color background_col)
	{
		super(fitStringToBounds(txt, w, margin), x, y, w, h, false, false);

		this.margin = margin;
		this.background_img = background_img;
		this.background_col = background_col;
		this.textCol = textCol;
		this.title = title;
		
		bg_mode = BG_TEXTURE;

		try
		{
			titleFont = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/merc/gfx/OpenSans-Bold.ttf"), 32, 1, true);
			txtFont = TrueTypeFont.loadTrueTypeFont(Loader.streamFromClasspath("com/radirius/merc/gfx/OpenSans-Semibold.ttf"), 20, 1, true);
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

	@Override
	public void render(Graphics g)
	{
		if (bg_mode == BG_TRANSPARENT)
		{
			float borderwidth = Math.min(border_hor.getTextureHeight(), border_hor.getTextureWidth());

			g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y + h - borderwidth + margin);
			g.drawTexture(border_hor, 0, 0, borderwidth, h + margin * 2, x + w - borderwidth, y - margin);

			borderwidth = Math.min(border_vert.getTextureHeight(), border_vert.getTextureWidth());

			g.drawTexture(border_hor, 0, 0, borderwidth, h + margin * 2, x, y - margin);
			g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y - margin);
		} 
		else if (bg_mode == BG_TEXTURE)
		{
			g.drawTexture(background_img, x, y, w, h);
		}
		else if (bg_mode == BG_COLOUR)
		{
			g.setColor(background_col);
			g.drawRect(new Rectangle(x, y, w, h));
		}

		renderContent(g);

		for (int i = 0; i < buttons.size(); i++)
		{
			Button b = buttons.get(i);

			b.render(g);
		}
	}

	@Override
	public void renderContent(Graphics g)
	{
		g.setFont(titleFont);
		g.setColor(textCol);
		g.drawString(x + margin, y + margin, title);

		g.setFont(txtFont);
		g.setColor(textCol);
		g.drawString(x + margin, y + titleFont.getHeight() + margin, txt);
		g.setColor(Color.white);
	}
	
	@Override
	public void update()
	{
		super.update();

		for (int i = 0; i < buttons.size(); i++)
		{
			Button b = buttons.get(i);

			b.update();
		}
	}
	
	public void setTitleFont(Font fnt)
	{
		this.titleFont = fnt;
	}
	
	public void setTextFont(Font fnt)
	{
		this.txtFont = fnt;
	}
	
	public Font getTitleFont()
	{
		return this.titleFont;
	}
	
	public Font getTextFont()
	{
		return this.txtFont;
	}

	public void addButton(String txt, Texture side, Texture body, GridLocation location)
	{
		// Was going to put some stuff here, and I did, but it was bad, so I
		// removed it.
		// ^^^ Story of the year.
	}

	private static String fitStringToBounds(String txt, float w, float margin)
	{
		// A line of text
		String linetxt = "";

		// The index character in the original string
		int idx = 0;
		int lidx = 0;

		// The result
		String finaltxt = "";

		// Begin fitting!
		while (idx < txt.length())
		{
			// Write character to line, and add to index
			linetxt += txt.charAt(idx);
			idx++;

			// If we are pushing the width limit or we are at the end of the
			// text, new line (or not)!
			if (Runner.getInstance().getGraphics().getFont().getWidth(txt.substring(lidx, idx).toCharArray()) >= w - margin * 2 || idx >= txt.length())
			{
				// Making sure no words get cut off, or split in half.
				int lastspace = linetxt.lastIndexOf(' ');

				if (lastspace > 0)
				{
					idx -= linetxt.length() - lastspace;
					linetxt = linetxt.substring(0, lastspace);
				}

				// Trim whitespace
				linetxt = linetxt.trim();

				// Add line to text, and reset variables
				finaltxt += linetxt;
				finaltxt += "\n";
				linetxt = "";
				lidx = idx;
			}
		}

		return finaltxt;
	}

	public static enum GridLocation
	{
		TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, MID_LEFT, MID_RIGHT;
	}
}
