package com.teama.merc.gui;

import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.teama.merc.fmwk.Runner;
import com.teama.merc.font.Font;
import com.teama.merc.font.TrueTypeFont;
import com.teama.merc.gfx.Color;
import com.teama.merc.gfx.Graphics;
import com.teama.merc.gfx.Texture;

/**
 * @from MERCury in package com.teama.merc.gui;
 * @authors wessles, Jeviny
 * @website www.wessles.com
 * @license (C) Mar 3, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class TextPanel extends Component
{
    public float margin;
    
    public Texture border_hor, border_vert, background_img;
    public Color textCol;
    public String title;
    
    private boolean hasBackground = false;
    
    private ArrayList<Button> buttons = new ArrayList<Button>();
    
	private Font titleFont = null;
	private Font txtFont = null;
    
    public TextPanel(String title, String txt, float x, float y, float w, float h, float margin, Color textCol)
    {
        this(title, txt, Texture.getEmptyTexture(), x, y, w, h, margin, textCol);
        
        hasBackground = false;
    }
    
    public TextPanel(String title, String txt, Texture background_img, float x, float y, float w, float h, float margin, Color textCol)
    {
    	super(fitStringToBounds(txt, w, margin), x, y, w, h, false, false);
    	
    	this.margin = margin;
    	this.background_img = background_img;
    	this.textCol = textCol;
    	this.title = title;
    	
    	try
    	{
    		titleFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Bold.ttf", 32, 1, true);
        	txtFont = TrueTypeFont.loadTrueTypeFont("com/teama/merc/gfx/OpenSans-Semibold.ttf", 20, 1, true);
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
    	
    	hasBackground = true;
    }
    
    public void render(Graphics g)
    {
    	if (!hasBackground)
    	{
            float borderwidth = Math.min(border_hor.getTextureHeight(), border_hor.getTextureWidth());
            
            g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y+h-borderwidth+margin);
            g.drawTexture(border_hor, 0, 0, borderwidth, h+margin*2, x+w-borderwidth, y-margin);
            
            borderwidth = Math.min(border_vert.getTextureHeight(), border_vert.getTextureWidth());
            
            g.drawTexture(border_hor, 0, 0, borderwidth, h+margin*2, x, y-margin);
            g.drawTexture(border_vert, 0, 0, w, borderwidth, x, y-margin);	
    	}
    	else
    	{
        	g.drawTexture(background_img, x, y, w, h);
    	}
        
        renderContent(g);
        
        for (int i = 0; i < buttons.size(); i++)
        {
        	Button b = buttons.get(i);
        	
        	b.render(g);
        }
    }
    
    public void renderContent(Graphics g)
    {
    	g.setFont(titleFont);
    	g.setColor(textCol);
        g.drawString(x + margin, y + margin, title);
        
        g.setFont(txtFont);
    	g.setColor(textCol);
        g.drawString(x + margin, (y + titleFont.getHeight()) + margin, txt);
        g.setColor(Color.white);
    }
    
    public void update()
    {
    	super.update();
    	
    	for (int i = 0; i < buttons.size(); i++)
    	{
    		Button b = buttons.get(i);
    		
    		b.update();
    	}
    }
    
    public void addButton(String txt, Texture side, Texture body, GridLocation location)
    {
    	// Was going to put some stuff here, and I did, but it was bad, so I removed it. 
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
            
            // If we are pushing the width limit or we are at the end of the text, new line (or not)!
            if (Runner.getInstance().getGraphics().getFont().getWidth(txt.substring(lidx, idx).toCharArray()) >= w - margin*2 || idx >= txt.length())
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
